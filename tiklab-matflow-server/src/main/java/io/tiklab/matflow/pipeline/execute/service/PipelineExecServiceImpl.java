package io.tiklab.matflow.pipeline.execute.service;

import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.home.service.PipelineHomeService;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.pipeline.definition.service.PipelineService;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.setting.service.ResourcesService;
import io.tiklab.matflow.stages.service.StageExecService;
import io.tiklab.matflow.support.postprocess.service.PostprocessExecService;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtilService;
import io.tiklab.matflow.support.version.PipelineVersionService;
import io.tiklab.matflow.task.message.model.TaskExecMessage;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksExecService;
import io.tiklab.matflow.task.task.service.TasksService;
import io.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PipelineExecServiceImpl implements PipelineExecService  {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineHomeService homeService;

    @Autowired
    PostprocessExecService postExecService;

    @Autowired
    PipelineInstanceService instanceService;

    @Autowired
    TasksService tasksService;

    @Autowired
    TasksExecService tasksExecService;

    @Autowired
    StageExecService stageExecService;

    @Autowired
    ResourcesService resourcesService;

    @Autowired
    PipelineVersionService versionService;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    JoinTemplate joinTemplate;

    private final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线id:流水线实例id
    private final  Map<String,String> pipelineIdOrInstanceId = new HashMap<>();

    //流水线实例id:流水线实例
    public static  Map<String,PipelineInstance> instanceIdOrInstance = new HashMap<>();

    //任务线程池
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public static final Map<String ,String> execMap = new HashMap<>();

    public static final Map<String ,Integer> execCacheMap = new HashMap<>();

    /**
     * 流水线开始运行
     * @param pipelineId 流水线id
     * @param startWAy   运行方式
     * @return 是否正在运行
     */
    @Override
    public PipelineInstance start(String pipelineId, int startWAy) {

        resourcesService.judgeResources();

        // 判断同一任务是否在运行
        Pipeline pipeline = putExecMap(pipelineId,startWAy);

        // 放入等待执行的缓存中
        if (pipeline.getState() == 3 ){
            logger.info("并行任务已满，等待执行！");
            return null;
        }
        execMap.put(pipelineId,pipelineId);
        String loginId = LoginContext.getLoginId();
        return beginExecPipeline(pipeline,loginId, startWAy);
    }


    /**
     * 放入正在执行的流水线缓存中
     * @param pipelineId 流水线id
     * @param startWAy 执行方式
     * @return 流水线信息
     */
    private Pipeline putExecMap(String pipelineId,int startWAy){

        int version = versionService.version();

        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        int size = execMap.size();

        // 放入等待执行的缓存中
        if ((version == 1 && size >= 1) || (version == 2 && size >= 1) ){
            pipeline.setState(3);
            pipelineService.updatePipeline(pipeline);
            execCacheMap.put(pipelineId,startWAy);
            return pipeline;
        }
        return pipeline;
    }

    /**
     * 执行等待执行的流水线
     */
    private void execCachePipeline(){
        if (execCacheMap.size() == 0){
            return;
        }
        Map.Entry<String,Integer> entry = execCacheMap.entrySet().iterator().next();
        String key = entry.getKey();
        Integer value = entry.getValue();
        logger.info("执行待执行流水线："+ key);
        Pipeline pipeline = pipelineService.findPipelineById(key);

        execCacheMap.remove(key);
        String loginId = LoginContext.getLoginId();
        beginExecPipeline(pipeline,loginId, value);
    }

    /**
     * 执行流水线
     * @param pipeline 流水线信息
     * @param startWAy 流水线执行方式
     * @return 流水线实例
     */
    private PipelineInstance beginExecPipeline(Pipeline pipeline,String loginId,int startWAy){
        String pipelineId = pipeline.getId();
        pipeline.setState(2);
        pipelineService.updatePipeline(pipeline);
        logger.info(pipeline.getName() + "开始运行。");

        PipelineInstance pipelineInstance =
                instanceService.initializeInstance(pipelineId,loginId, startWAy);
        String instanceId = pipelineInstance.getInstanceId();
        // 运行实例放入内存中
        pipelineIdOrInstanceId.put(pipelineId, instanceId);
        instanceIdOrInstance.put(instanceId, pipelineInstance);
        instanceService.pipelineRunTime(instanceId);

        HashMap<String,Object> map = homeService.initMap(pipeline);
        map.put("title","流水线执行消息");
        map.put("message","开始执行");
        homeService.log(PipelineFinal.LOG_RUN, PipelineFinal.LOG_TEM_RUN, map);

        executorService.submit((Callable<Object>) () -> {
            try {
                Thread.currentThread().setName(pipelineId);
                boolean b = true;
                int type = pipeline.getType();

                postExecService.createPipelinePostInstance(pipelineId,instanceId);

                if (type == 1) {
                    b = execTask(pipeline,instanceId);
                }
                if (type == 2) {
                    // 创建多阶段运行实例
                    stageExecService.createStageExecInstance(pipelineId, instanceId);
                    b = stageExecService.execStageTask(pipeline, instanceId);
                }
                logger.info("执行后置任务.");
                TaskExecMessage taskExecMessage = new TaskExecMessage(pipeline,b);
                boolean postState = postExecService.execPipelinePost(taskExecMessage);

                if (!b || !postState){
                    b = false;
                }

                // 执行完成
                pipelineExecEnd(pipelineId, b);
                // 移除缓存中正在运行的流水线
                execMap.remove(pipelineId);
                // 执行等待的流水线
                execCachePipeline();
            }catch (Exception e){
                logger.info("流水线执行出错了：" + e.getMessage() );
                stop(pipelineId);
                // 移除缓存中正在运行的流水线
                execMap.remove(pipelineId);
                // 执行等待的流水线
                execCachePipeline();
                return false;
            }
            return true;
        });

        joinTemplate.joinQuery(pipelineInstance);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return pipelineInstance;
    }

    /**
     * 执行任务
     * @param pipeline 流水线信息
     * @param instanceId 流水线实例id
     * @return 任务执行状态
     */
    private boolean execTask(Pipeline pipeline,String instanceId){
        String pipelineId = pipeline.getId();
        boolean b = false;
        //日志文件根路径
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,2)+instanceId;
        // 创建多任务运行实例
        List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
        int i =0 ;
        for (Tasks task : tasks) {
            tasksExecService.createTaskExecInstance(task, instanceId, 1,fileAddress);
            postExecService.createTaskPostInstance(pipelineId,instanceId ,task.getTaskId());
        }
        // 执行任务
        for (Tasks task : tasks) {
            try {
                b = tasksExecService.execTask(pipelineId, task.getTaskType(), task.getTaskId());
            }catch (Exception e){
                logger.info("错误："+e.getMessage());
            }
            TaskExecMessage taskExecMessage = new TaskExecMessage(pipeline,task.getTaskName(),task.getTaskId(),b);
            boolean b1 = postExecService.execTaskPostTask(taskExecMessage);
            if (!b || !b1){
                return false;
            }
        }
        return b;
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

        // 添加资源配置
        resourcesService.instanceResources(integer);

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

    }


    public void stop(String pipelineId){

        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        int type = pipeline.getType();

        // 流水线在缓存区
        if (!Objects.isNull(execCacheMap.get(pipelineId))){
            pipeline.setState(1);
            pipelineService.updatePipeline(pipeline);
            // 移除缓存中正在运行的流水线
            execMap.remove(pipelineId);
            // 执行等待的流水线
            execCachePipeline();
            return;
        }

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

        // 流水线显示在运行，但未产生实例
        if (Objects.isNull(instanceId)){
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

            // 移除缓存中正在运行的流水线
            execMap.remove(pipelineId);
            // 执行等待的流水线
            execCachePipeline();

            return;
        }

        Integer integer = instanceService.findPipelineRunTime(instanceId);
        PipelineInstance instance = instanceIdOrInstance.get(instanceId);
        if (instance == null){
            instance = instanceService.findOneInstance(instanceId);
        }
        instance.setRunTime(integer);

        // 添加资源配置
        resourcesService.instanceResources(integer);

        // 更新状态
        instance.setRunStatus(PipelineFinal.RUN_HALT);
        instanceService.updateInstance(instance);
        instanceService.removePipelineRunTime(instanceId);
        tasksExecService.stopThread(instanceId);
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);
        instanceIdOrInstance.remove(instanceId);
        pipelineIdOrInstanceId.remove(pipelineId);

        // 移除缓存中正在运行的流水线
        execMap.remove(pipelineId);
        // 执行等待的流水线
        execCachePipeline();
    }


    public PipelineInstance findPipelineInstance(String instanceId){
      return instanceIdOrInstance.get(instanceId);
    }





}


































