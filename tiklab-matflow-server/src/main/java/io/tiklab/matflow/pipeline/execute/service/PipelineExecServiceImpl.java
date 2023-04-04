package io.tiklab.matflow.pipeline.execute.service;

import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.home.service.PipelineHomeService;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.service.PipelineService;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.stages.service.StageExecService;
import io.tiklab.matflow.support.postprocess.service.PostprocessService;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksExecService;
import io.tiklab.matflow.task.task.service.TasksService;
import io.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
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

    private final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线id:流水线实例id
    private final  Map<String,String> pipelineIdOrInstanceId = new HashMap<>();

    //流水线实例id:流水线实例
    public static  Map<String,PipelineInstance> instanceIdOrInstance = new HashMap<>();

    //任务线程池
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Value("${matflow.cloud:true}")
    boolean idCe;

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
        instanceService.pipelineRunTime(instanceId);

        HashMap<String,Object> map = homeService.initMap(pipeline);
        map.put("title","流水线执行消息");
        map.put("message","开始执行");
        homeService.log(PipelineFinal.LOG_RUN, PipelineFinal.LOG_TEM_RUN, map);
        homeService.settingMessage(PipelineFinal.MES_RUN, map);
        //日志文件根路径
        String fileAddress = PipelineUtil.findFileAddress(pipelineId,2)+instanceId;

        if (idCe){
            executorService.submit((Callable<Object>) () -> {
                boolean b = true;
                int type = pipeline.getType();

                if (type == 1) {
                    // 创建多任务运行实例
                    List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
                    for (Tasks task : tasks) {
                        tasksExecService.createTaskExecInstance(task, instanceId, 1,fileAddress);
                    }
                    // 执行任务
                    for (Tasks task : tasks) {
                        b = tasksExecService.execTask(pipelineId, task.getTaskType(), task.getTaskId());
                        if (!b){
                            break;
                        }
                    }
                }

                if (type == 2) {
                    // 创建多阶段运行实例
                    stageExecService.createStageExecInstance(pipelineId, instanceId);
                    b = stageExecService.execStageTask(pipelineId, instanceId);
                }
                // 执行完成
                pipelineExecEnd(pipelineId, b);
                return true;
            });
        }else {
            boolean b = true;
            int type = pipeline.getType();

            if (type == 1) {
                // 创建多任务运行实例
                List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
                for (Tasks task : tasks) {
                    tasksExecService.createTaskExecInstance(task, instanceId, 1,fileAddress);
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
            // 执行完成
            pipelineExecEnd(pipelineId, b);
        }


        joinTemplate.joinQuery(pipelineInstance);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return pipelineInstance;

    }

    /**
     * 更新流水线运行状态
     * @param pipelineId 流水线id
     * @param state 运行状态
     */
    private void pipelineExecEnd(String pipelineId , boolean state){
        String instanceId = pipelineIdOrInstanceId.get(pipelineId);
        Integer integer = instanceService.findPipelineRunTime(instanceId);
        PipelineInstance pipelineInstance = instanceIdOrInstance.get(instanceId);
        pipelineInstance.setRunTime(integer);

        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        HashMap<String,Object> map = homeService.initMap(pipeline);
        if (state){
            pipelineInstance.setRunStatus(PipelineFinal.RUN_SUCCESS);
            map.put("message","执行成功");
        }else {
            pipelineInstance.setRunStatus(PipelineFinal.RUN_ERROR);
            //运行失败更改为运行的任务状态
            List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
            for (Tasks task : tasks) {
                String taskId = task.getTaskId();
                tasksExecService.stopTask(taskId);
            }
            map.put("message","执行失败");
        }
        //更新状态
        instanceService.updateInstance(pipelineInstance);
        tasksExecService.stopThread(instanceId);
        //更新流水线状态

        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);
        //移除内存
        pipelineIdOrInstanceId.remove(pipelineId);
        instanceIdOrInstance.remove(instanceId);
        instanceService.removePipelineRunTime(instanceId);

        logger.info("流水线：" +pipeline.getName() + "运行完成...");
        map.put("title","流水线执行消息");
        homeService.log(PipelineFinal.LOG_PIPELINE, PipelineFinal.LOG_TEM_RUN, map);
        homeService.settingMessage(PipelineFinal.MES_RUN, map);

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
            List<PipelineInstance> allInstance = instanceService.findAllInstance();
            allInstance.sort(Comparator.comparing(PipelineInstance::getCreateTime).reversed());
            PipelineInstance latelyInstance = allInstance.get(0);
            String runStatus = latelyInstance.getRunStatus();
            if(Objects.equals(runStatus, PipelineFinal.RUN_RUN)){
                latelyInstance.setRunStatus(PipelineFinal.RUN_HALT);
                instanceService.updateInstance(latelyInstance);
            }
            pipeline.setState(1);
            pipelineService.updatePipeline(pipeline);
            return;
        }

        Integer integer = instanceService.findPipelineRunTime(instanceId);
        PipelineInstance instance = instanceIdOrInstance.get(instanceId);
        if (instance == null){
            instance = instanceService.findOneInstance(instanceId);
        }
        instance.setRunTime(integer);
        instance.setRunStatus(PipelineFinal.RUN_HALT);
        instanceService.updateInstance(instance);
        instanceService.removePipelineRunTime(instanceId);
        tasksExecService.stopThread(instanceId);
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);
        instanceIdOrInstance.remove(instanceId);
        pipelineIdOrInstanceId.remove(pipelineId);
    }


    public PipelineInstance findPipelineInstance(String instanceId){
      return instanceIdOrInstance.get(instanceId);
    }





}


































