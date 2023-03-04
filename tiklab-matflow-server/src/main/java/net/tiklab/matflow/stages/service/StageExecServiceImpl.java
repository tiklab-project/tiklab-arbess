package net.tiklab.matflow.stages.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecServiceImpl;
import net.tiklab.matflow.stages.model.Stage;
import net.tiklab.matflow.stages.model.StageInstance;
import net.tiklab.matflow.support.util.PipelineFinal;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.task.task.service.TasksExecService;
import net.tiklab.matflow.task.task.service.TasksExecServiceImpl;
import net.tiklab.matflow.task.task.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class StageExecServiceImpl implements  StageExecService {

    @Autowired
    StageService stageService;

    @Autowired
    StageInstanceServer stageInstanceServer;

    @Autowired
    TasksExecService tasksExecService;

    @Autowired
    TasksService tasksService;

    //阶段任务实例id及阶段任务实例
    public static  Map<String , StageInstance> stageInstanceIdOrStageInstance = new HashMap<>();

    //阶段id及阶段任务实例
    private static final Map<String , String> stageIdOrStageInstanceId = new HashMap<>();

    private final static Map<String,Integer> runTime = TasksExecServiceImpl.runTime;

    @Override
    public void createStageExecInstance(String pipelineId,String instanceId) {

        List<Stage> allMainStage = stageService.findAllMainStage(pipelineId);
        for (Stage mainStage : allMainStage) {
            //创建主阶段日志
            StageInstance stageInstance = new StageInstance();
            stageInstance.setInstanceId(instanceId);
            stageInstance.setStageSort(mainStage.getStageSort());
            stageInstance.setStageState(PipelineFinal.RUN_WAIT);
            String stageInstanceId = stageInstanceServer.createStageInstance(stageInstance);
            stageInstance.setId(stageInstanceId);
            stageIdOrStageInstanceId.put(mainStage.getStageId(),stageInstanceId);
            stageInstanceIdOrStageInstance.put(stageInstanceId,stageInstance);

            String stageId = mainStage.getStageId();
            List<Stage> otherStage = stageService.findOtherStage(stageId);
            //获取阶段下的并行任务
            for (Stage stage : otherStage) {
                String id = stage.getStageId();
                List<Tasks> tasks = tasksService.finAllStageTask(id);
                //获取串行任务
                for (Tasks task : tasks) {
                    tasksExecService.createTaskExecInstance(task,id,2);
                }
                StageInstance instance = new StageInstance();
                instance.setParentId(stageInstanceId);
                instance.setStageSort(stage.getStageSort());
                instance.setStageState(PipelineFinal.RUN_WAIT);
                String otherStageInstanceId = stageInstanceServer.createStageInstance(instance);
                instance.setId(otherStageInstanceId);
                //阶段运行信息放入内存
                stageIdOrStageInstanceId.put(id,otherStageInstanceId);
                stageInstanceIdOrStageInstance.put(otherStageInstanceId,instance);
            }
        }
    }

    @Override
    public boolean execStageTask(String pipelineId, String instanceId) {
        List<Stage> allMainStage = stageService.findAllMainStage(pipelineId);
        boolean state = true;
        for (Stage mainStage : allMainStage) {
            String mainStageId = mainStage.getStageId();
            tasksExecService.time(mainStageId);
            //更新主阶段状态阶段
            String stageInstanceId = stageIdOrStageInstanceId.get(mainStageId);
            StageInstance stageInstance = stageInstanceIdOrStageInstance.get(stageInstanceId);
            stageInstance.setStageState(PipelineFinal.RUN_RUN);
            stageInstanceIdOrStageInstance.put(stageInstanceId,stageInstance);

            List<Stage> otherStage = stageService.findOtherStage(mainStageId);

            Map<String , Future<Boolean>> futureMap = new HashMap<>();
            for (Stage stage : otherStage) {
                String stagesId = stage.getStageId();
                //更新从阶段状态为运行
                String otherStageInstanceId = stageIdOrStageInstanceId.get(stagesId);
                tasksExecService.time(otherStageInstanceId);
                StageInstance instance = stageInstanceIdOrStageInstance.get(otherStageInstanceId);
                instance.setStageState(PipelineFinal.RUN_RUN);
                stageInstanceIdOrStageInstance.put(otherStageInstanceId,instance);

                ExecutorService threadPool = PipelineExecServiceImpl.executorService;
                //放入线程执行
                try {
                    Future<Boolean> future = threadPool.submit(() -> {
                        Thread.currentThread().setName(stagesId);
                        //获取阶段任务，执行
                        List<Tasks> tasks = tasksService.finAllStageTask(stagesId);
                        for (Tasks task : tasks) {
                            boolean b = tasksExecService.execTask(pipelineId, task.getTaskType(), task.getTaskId());
                            if (!b){
                                return  false;
                            }
                        }
                        return true;
                    });
                    futureMap.put(stagesId,future);
                }catch (ApplicationException e){
                    throw new ApplicationException(e);
                }
                //等待获取执行结果
                for (Stage stage1 : otherStage) {
                    try {
                        String stageId = stage1.getStageId();
                        state = futureMap.get(stageId).get();
                        if (!state){
                            threadPool.shutdown();
                            break;
                        }
                        updateStageExecState(stageId,state);
                    } catch (InterruptedException | ExecutionException | ApplicationException e) {
                        throw new ApplicationException(e);
                    }
                }
                threadPool.shutdown();
                //更新并行阶段状态
                updateStageExecState(stagesId,state);
                if (!state){
                    break;
                }
            }
            //更新主阶段状态
            updateStageExecState(mainStageId,state);
            if (!state){
                break;
            }
        }
        return true;
    }

    /**
     * 更新阶段运行实例
     * @param stageId 阶段id
     * @param state 状态
     */
    private void updateStageExecState(String stageId,boolean state){
        String stageInstanceId = stageIdOrStageInstanceId.get(stageId);
        Integer integer = runTime.get(stageInstanceId);
        StageInstance instance = stageInstanceIdOrStageInstance.get(stageInstanceId);
        instance.setStageTime(integer);
        if (state){
            instance.setStageState(PipelineFinal.RUN_SUCCESS);
        }else {
            instance.setStageState(PipelineFinal.RUN_ERROR);
        }
        //更新数据，移除内存
        tasksExecService.stopThread(stageInstanceId);
        stageInstanceServer.updateStageInstance(instance);
        stageIdOrStageInstanceId.remove(stageId);
        stageInstanceIdOrStageInstance.remove(stageInstanceId);
    }


    public void stopStageTask(String instanceId){
        List<StageInstance> stageInstance = stageInstanceServer.findAllMainStageInstance(instanceId);
        for (StageInstance instance : stageInstance) {
            String mainStageId = instance.getId();
            List<StageInstance> allStageInstance = stageInstanceServer.findAllStageInstance(mainStageId);
            for (StageInstance otherStageInstance : allStageInstance) {
                String id = otherStageInstance.getId();


            }
        }


    }

}











































