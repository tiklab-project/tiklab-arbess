package net.tiklab.matflow.pipeline.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.home.service.PipelineHomeService;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.service.PipelineService;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import net.tiklab.matflow.stages.service.StageExecService;
import net.tiklab.matflow.support.postprocess.service.PostprocessService;
import net.tiklab.matflow.support.util.PipelineFinal;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.task.task.service.TasksExecService;
import net.tiklab.matflow.task.task.service.TasksExecServiceImpl;
import net.tiklab.matflow.task.task.service.TasksService;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 流水线运行服务
 */
@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private PipelineHomeService homeService;


    @Autowired
    private PostprocessService postServer;

    @Autowired
    private PipelineInstanceService instanceService;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private TasksExecService tasksExecService;

    @Autowired
    private StageExecService stageExecService;

    @Autowired
    private JoinTemplate joinTemplate;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线id:流水线实例id
    private final static Map<String,String> pipelineIdOrInstanceId = new HashMap<>();

    //流水线实例id:流水线实例
    public  static Map<String,PipelineInstance> instanceIdOrInstance = new HashMap<>();

    //任务线程池
    public static ExecutorService executorService = Executors.newCachedThreadPool();

    //运行时间
    private final static Map<String,Integer> runTime = TasksExecServiceImpl.runTime;

    /**
     * 流水线开始运行
     * @param pipelineId 流水线id
     * @param startWAy 运行方式
     * @return 是否正在运行
     */
    @Override
    public PipelineInstance start(String pipelineId,int startWAy) {

        // 判断同一任务是否在运行
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);

        pipeline.setState(2);
        pipelineService.updatePipeline(pipeline);
        logger.info(pipeline.getName() + "开始运行。");
        Thread.currentThread().setName(pipelineId);
        PipelineInstance pipelineInstance =
                instanceService.initializeInstance(pipelineId, startWAy);
        String instanceId = pipelineInstance.getInstanceId();
        // 运行实例放入内存中
        pipelineIdOrInstanceId.put(pipelineId, instanceId);
        instanceIdOrInstance.put(instanceId, pipelineInstance);
        tasksExecService.time(instanceId);

        executorService.submit((Callable<Object>) () -> {

            boolean b = true;
            int type = pipeline.getType();

            if (type == 1) {
                // 创建多任务运行实例
                List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
                for (Tasks task : tasks) {
                    tasksExecService.createTaskExecInstance(task, instanceId, 1);
                }
                // 执行任务
                for (Tasks task : tasks) {
                    b = tasksExecService.execTask(pipelineId, task.getTaskType(), task.getTaskId());
                }
            }

            if (type == 2) {
                // 创建多阶段运行实例
                stageExecService.createStageExecInstance(pipelineId, instanceId);
                b = stageExecService.execStageTask(pipelineId, instanceId);
            }
            tasksExecService.stopThread(instanceId);
            // 执行完成
            pipelineExecEnd(pipelineId, b);
            return true;
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new ApplicationException(e);
        }
        joinTemplate.joinQuery(pipelineInstance);
        return pipelineInstance;

    }

    /**
     * 更新流水线运行状态
     * @param pipelineId 流水线id
     * @param state 运行状态
     */
    private void pipelineExecEnd(String pipelineId , boolean state){
        String instanceId = pipelineIdOrInstanceId.get(pipelineId);
        Integer integer = runTime.get(instanceId);
        PipelineInstance pipelineInstance = instanceIdOrInstance.get(instanceId);
        pipelineInstance.setRunTime(integer);
        if (state){
            pipelineInstance.setRunStatus(PipelineFinal.RUN_SUCCESS);
        }else {
            pipelineInstance.setRunStatus(PipelineFinal.RUN_ERROR);
        }
        //更新状态
        instanceService.updateInstance(pipelineInstance);
        tasksExecService.stopThread(instanceId);
        //更新流水线状态
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);
        //移除内存
        pipelineIdOrInstanceId.remove(pipelineId);
        instanceIdOrInstance.remove(instanceId);
        logger.info(pipeline.getName() + "运行完成...");
        // tasksExecService.stopThread(pipelineId);
    }


    public void stop(String pipelineId){

        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        int type = pipeline.getType();

        String instanceId = pipelineIdOrInstanceId.get(pipelineId);
        //多任务
        if (type == 1){
            List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
            for (Tasks task : tasks) {
                String taskId = task.getTaskId();
                tasksExecService.stopTask(taskId);
            }
        }

        //多阶段
        if (type == 2){
            stageExecService.stopStage(pipelineId);
        }

        if (instanceId == null){
            pipeline.setState(1);
            pipelineService.updatePipeline(pipeline);
            return;
        }

        Integer integer = runTime.get(instanceId);
        PipelineInstance instance = instanceIdOrInstance.get(instanceId);
        if (integer == null){
            integer = 0;
        }
        if (instance == null){
            instance = instanceService.findOneInstance(instanceId);
        }
        instance.setRunTime(integer);
        instance.setRunStatus(PipelineFinal.RUN_HALT);
        instanceService.updateInstance(instance);
        tasksExecService.stopThread(instanceId);
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);
        instanceIdOrInstance.remove(instanceId);
        pipelineIdOrInstanceId.remove(pipelineId);
    }





}


































