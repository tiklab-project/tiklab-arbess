package io.thoughtware.matflow.pipeline.execute.service;

import io.thoughtware.matflow.home.service.PipelineHomeService;
import io.thoughtware.matflow.pipeline.execute.model.PipelineRunMsg;
import io.thoughtware.matflow.setting.service.ResourcesService;
import io.thoughtware.matflow.stages.model.Stage;
import io.thoughtware.matflow.stages.service.StageExecService;
import io.thoughtware.matflow.stages.service.StageService;
import io.thoughtware.matflow.support.disk.service.DiskService;
import io.thoughtware.matflow.support.postprocess.service.PostprocessExecService;
import io.thoughtware.matflow.support.util.util.PipelineFileUtil;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.service.PipelineUtilService;
import io.thoughtware.matflow.support.version.service.PipelineVersionService;
import io.thoughtware.matflow.task.task.model.TaskExecMessage;
import io.thoughtware.matflow.task.task.model.Tasks;
import io.thoughtware.matflow.task.task.service.TasksExecService;
import io.thoughtware.matflow.task.task.service.TasksService;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.toolkit.join.JoinTemplate;
import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.pipeline.definition.service.PipelineService;
import io.thoughtware.matflow.pipeline.instance.model.PipelineInstance;
import io.thoughtware.matflow.pipeline.instance.model.PipelineInstanceQuery;
import io.thoughtware.matflow.pipeline.instance.service.PipelineInstanceService;
import io.thoughtware.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
    PipelineInstanceService pipelineInstanceService;

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
    DiskService diskService;

    @Autowired
    StageService stageService;

    @Autowired
    JoinTemplate joinTemplate;

    public final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //任务线程池
    public final ExecutorService executorService = Executors.newCachedThreadPool();

    //流水线id:流水线实例id
    public static final  Map<String,String> pipelineIdOrInstanceId = new HashMap<>();

    //流水线实例id:流水线实例
    public static final  Map<String,PipelineInstance> instanceIdOrInstance = new HashMap<>();

    // 流水线缓存区
    private static final List<PipelineRunMsg> runMsgList = new ArrayList<>();

    /**
     * 流水线开始运行
     * @param runMsg 流水线id
     * @return 是否正在运行
     */
    @Override
    public PipelineInstance start(PipelineRunMsg runMsg) {

        diskService.validationStorageSpace();

        // 判断同一任务是否在运行
        Pipeline pipeline = validExecPipeline(runMsg);
        judgeConfig(pipeline);
        runMsg.setPipeline(pipeline);
        // 放入等待执行的缓存中
        if (pipeline.getState() == 3 ){
            logger.warn("并行任务已满，等待执行！");
            throw new ApplicationException(2000,"并行任务已满，等待执行！");
        }

        // 资源限制
        resourcesService.judgeResources();

        return beginExecPipeline(runMsg);
    }

    /**
     * 流水线不存在任务
     * @param pipeline 流水线
     */
    public void judgeConfig(Pipeline pipeline){
        int type = pipeline.getType();
        String pipelineId = pipeline.getId();
        if (type == 1){
            List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
            if (tasks.isEmpty()){
                throw new ApplicationException(2000,"当前流水线不存在任务！");
            }
        }else {
            List<Stage> allMainStage = stageService.findAllMainStage(pipelineId);
            if (allMainStage.isEmpty()){
                throw new ApplicationException(2000,"当前流水线不存在任务！");
            }
        }

    }

    /**
     * 放入正在执行的流水线缓存中
     * @param runMsg 流水线id
     * @return 流水线信息
     */
    public Pipeline validExecPipeline(PipelineRunMsg runMsg){
        String pipelineId = runMsg.getPipelineId();
        int version = versionService.version();

        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        runMsg.setPipeline(pipeline);

        int size = pipelineIdOrInstanceId.size();

        // 资源限制放入缓存中等待执行
        if ((version == 1 && size >= 2) || (version == 2 && size >= 4) ){
            pipeline.setState(3);
            pipelineService.updatePipeline(pipeline);
            // 放入缓存池
            runMsgList.add(runMsg);
            return pipeline;
        }
        return pipeline;
    }

    /**
     * 执行等待执行的流水线
     */
    public void execCachePipeline(){
        if (runMsgList.isEmpty()){
            return;
        }
        PipelineRunMsg pipelineRunMsg = runMsgList.get(0);
        beginExecPipeline(pipelineRunMsg);
        runMsgList.remove(0);
    }

    public boolean cleanCachePipeline(String pipelineId){
        int index = -1;
        for (int i = 0; i < runMsgList.size(); i++) {
            String id = runMsgList.get(i).getPipelineId();
            if (!id.equals(pipelineId)){
                continue;
            }
            index = i;
        }
        if (index == -1){
            return false;
        }
        runMsgList.remove(index);
        return true;
    }

    public PipelineInstance updatePipelineRunState(PipelineRunMsg runMsg){
        Pipeline pipeline = runMsg.getPipeline();
        String pipelineId = pipeline.getId();
        pipeline.setState(2);
        pipelineService.updatePipeline(pipeline);
        logger.warn("流水线{}开始运行",pipeline.getName());
        PipelineInstance pipelineInstance = pipelineInstanceService.initializeInstance(runMsg);
        String instanceId = pipelineInstance.getInstanceId();

        joinTemplate.joinQuery(pipelineInstance);

        // 运行实例放入内存中
        putPipelineInstanceId(pipelineId, instanceId);
        instanceIdOrInstance.put(instanceId, pipelineInstance);
        pipelineInstanceService.pipelineRunTime(instanceId);
        return pipelineInstance;
    }

    /**
     * 执行流水线
     * @param runMsg 流水线信息
     * @return 流水线实例
     */
    public PipelineInstance beginExecPipeline(PipelineRunMsg runMsg){
        String pipelineId = runMsg.getPipelineId();
        Pipeline pipeline = runMsg.getPipeline();

        PipelineInstance pipelineInstance = updatePipelineRunState(runMsg);
        String instanceId = pipelineInstance.getInstanceId();

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

                TaskExecMessage taskExecMessage = new TaskExecMessage(pipeline,b);
                boolean postState = postExecService.execPipelinePost(taskExecMessage);

                if (!b || !postState){
                    b = false;
                }

                // 执行完成
                pipelineExecEnd(pipelineId, b);

                tasksExecService.stopThread(pipelineId);

            }catch (Exception e){
                logger.error("流水线执行出错了：{}",e.getMessage() );

                pipelineExecEnd(pipelineId,false);

                stop(pipelineId);
                return false;
            }
            return true;
        });

        try {
            Thread.sleep(200);
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
    public boolean execTask(Pipeline pipeline,String instanceId){
        String pipelineId = pipeline.getId();
        boolean b = false;
        //日志文件根路径
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,2) + instanceId;

        // 创建多任务运行实例
        List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
        for (Tasks task : tasks) {
            tasksExecService.createTaskExecInstance(task, instanceId, 1,fileAddress);
            postExecService.createTaskPostInstance(pipelineId,instanceId ,task.getTaskId());
        }

        // 执行任务
        for (Tasks task : tasks) {
            try {
                b = tasksExecService.execTask(pipelineId, task.getTaskType(), task.getTaskId());
            }catch (Exception e){
                logger.error("任务{}执行出错：{}",task.getTaskName(),e.getMessage());
                b = false;
            }
            // 执行任务具体的后置任务
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
    public void pipelineExecEnd(String pipelineId , boolean state){
        String instanceId = findPipelineInstanceId(pipelineId);
        PipelineInstance pipelineInstance = findPipelineInstance(instanceId);
        if (Objects.isNull(pipelineInstance)){
            pipelineInstanceService.findOneInstance(instanceId);
        }

        Integer integer = pipelineInstanceService.findPipelineRunTime(instanceId);
        if (!Objects.isNull(integer)){
            pipelineInstance.setRunTime(integer);
        }

        // 添加资源配置
        resourcesService.instanceResources(integer);
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);

        if (state){
            pipelineInstance.setRunStatus(PipelineFinal.RUN_SUCCESS);
        }else {
            pipelineInstance.setRunStatus(PipelineFinal.RUN_ERROR);
            //运行失败更改为运行的任务状态
            List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
            for (Tasks task : tasks) {
                String taskId = task.getTaskId();
                tasksExecService.stopTask(taskId);
            }
        }

        //更新流水线运行实例状态
        pipelineInstanceService.updateInstance(pipelineInstance);

        //更新流水线状态
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);

        // 清除内存中保存的运行信息
        cleanPipeline(pipelineId,instanceId);

        // 发送流水线运行消息
        sendPipelineRunMessage(pipeline,instanceId,state);


        String defaultAddress = utilService.findPipelineDefaultAddress(pipelineId, 1);
        logger.warn("删除流水线源数据......");
        PipelineFileUtil.deleteFile(new File(defaultAddress));

        logger.warn("流水线：" +pipeline.getName() + "运行完成...");
    }

    public void stop(String pipelineId){

        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        int type = pipeline.getType();

        // 流水线在缓存区
        if (cleanCachePipeline(pipelineId)){
            pipeline.setState(1);
            pipelineService.updatePipeline(pipeline);
            // 执行等待的流水线
            execCachePipeline();
            return;
        }

        String instanceId = findPipelineInstanceId(pipelineId);

        // 内存中没有运行信息，历史显示正在运行
        if (Objects.isNull(instanceId)){
            PipelineInstanceQuery pipelineInstanceQuery = new PipelineInstanceQuery();
            pipelineInstanceQuery.setPipelineId(pipelineId);
            pipelineInstanceQuery.setState(PipelineFinal.RUN_RUN);
            List<PipelineInstance> pipelineInstanceList = pipelineInstanceService.findPipelineInstanceList(pipelineInstanceQuery);
            if (!pipelineInstanceList.isEmpty()){
                instanceId = pipelineInstanceList.get(0).getInstanceId();
            }
        }

        //多任务
        if (type == 1){
            List<Tasks> tasks = tasksService.finAllPipelineTask(pipelineId);
            for (Tasks task : tasks) {
                String taskId = task.getTaskId();
                tasksExecService.stopTask(taskId);
            }
            if (!Objects.isNull(instanceId)){
                tasksExecService.stop(instanceId,null,null);
            }
        }

        //多阶段
        if (type == 2){
            stageExecService.stopStage(pipelineId);
            stageExecService.stop(instanceId);
        }

        // 流水线显示在运行，但未产生实例
        if (Objects.isNull(instanceId)){
            List<PipelineInstance> allInstance = pipelineInstanceService.findAllInstance();
            allInstance.sort(Comparator.comparing(PipelineInstance::getCreateTime).reversed());
            PipelineInstance latelyInstance = allInstance.get(0);
            String runStatus = latelyInstance.getRunStatus();
            if(Objects.equals(runStatus, PipelineFinal.RUN_RUN)){
                latelyInstance.setRunStatus(PipelineFinal.RUN_HALT);
                pipelineInstanceService.updateInstance(latelyInstance);
            }
            pipeline.setState(1);
            pipelineService.updatePipeline(pipeline);

            // 执行等待的流水线
            execCachePipeline();

            return;
        }

        PipelineInstance instance = findPipelineInstance(instanceId);
        if (Objects.isNull(instance)){
            instance = pipelineInstanceService.findOneInstance(instanceId);
        }

        Integer integer = pipelineInstanceService.findPipelineRunTime(instanceId);
        instance.setRunTime(integer);

        // 添加资源配置
        resourcesService.instanceResources(integer);

        // 更新流水线实例状态
        instance.setRunStatus(PipelineFinal.RUN_HALT);
        pipelineInstanceService.updateInstance(instance);

        // 更新流水线状态
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);

        // 清除内存中保存的运行信息
        cleanPipeline(pipelineId,instanceId);

        // 执行等待的流水线
        execCachePipeline();


        String defaultAddress = utilService.findPipelineDefaultAddress(pipelineId, 1);
        logger.warn("删除流水线源数据......");
        PipelineFileUtil.deleteFile(new File(defaultAddress));

        tasksExecService.stopThread(pipelineId);

    }

    /**
     * 获取内存中流水线的运行实例
     * @param instanceId 实例id
     * @return 实例
     */
    public PipelineInstance findPipelineInstance(String instanceId){
      return instanceIdOrInstance.get(instanceId);
    }

    /**
     * 获取流水线对应的实例id
     * @param pipelineId 流水线
     * @return 实例id
     */
    public String findPipelineInstanceId(String pipelineId){
        return pipelineIdOrInstanceId.get(pipelineId);
    }

    public void removePipelineInstanceId(String pipelineId){
        pipelineIdOrInstanceId.remove(pipelineId);
    }

    public void putPipelineInstanceId(String pipelineId, String instanceId){
        pipelineIdOrInstanceId.put(pipelineId, instanceId);
    }

    /**
     * 清除内存中的流水线缓存
     * @param pipelineId 流水线id
     * @param instanceId 实例id
     */
    public void cleanPipeline(String pipelineId,String instanceId){
        instanceIdOrInstance.remove(instanceId);
        removePipelineInstanceId(pipelineId);

        pipelineInstanceService.removeInstanceRunTime(instanceId);

        // 停止时间计时器
        // tasksExecService.stopThread(pipelineId);
    }

    // 发送消息
    public void sendPipelineRunMessage(Pipeline pipeline,String instanceId,Boolean state){
        Map<String,Object> map = homeService.initMap(pipeline);
        map.put("instanceId",instanceId);
        map.put("link",PipelineFinal.RUN_LINK);
        if (state){
            map.put("message","运行成功");
        }else {
            map.put("message","运行失败");
        }
        homeService.log(PipelineFinal.LOG_TYPE_RUN,  map);
        map.put("dmMessage",true);
        homeService.settingMessage(PipelineFinal.MES_RUN,  map);
    }


}


































