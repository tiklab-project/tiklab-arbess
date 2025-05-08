package io.tiklab.arbess.ws.server;

import com.alibaba.fastjson.JSONObject;
import io.tiklab.arbess.home.service.PipelineHomeService;
import io.tiklab.arbess.pipeline.execute.model.PipelineQueue;
import io.tiklab.arbess.pipeline.execute.model.PipelineQueueQuery;
import io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
import io.tiklab.arbess.pipeline.execute.service.PipelineExecService;
import io.tiklab.arbess.pipeline.execute.service.PipelineExecServiceImpl;
import io.tiklab.arbess.pipeline.execute.service.PipelineQueueService;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.arbess.support.agent.model.AgentMessage;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.arbess.stages.model.StageInstance;
import io.tiklab.arbess.stages.service.StageInstanceServer;
import io.tiklab.arbess.support.approve.model.ApprovePipeline;
import io.tiklab.arbess.support.approve.service.ApprovePipelineService;
import io.tiklab.arbess.support.approve.service.ApproveService;
import io.tiklab.arbess.support.postprocess.model.PostprocessInstance;
import io.tiklab.arbess.support.postprocess.service.PostprocessInstanceService;
import io.tiklab.arbess.support.util.service.PipelineUtilService;
import io.tiklab.arbess.support.util.util.PipelineFileUtil;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.arbess.task.build.model.TaskBuildProduct;
import io.tiklab.arbess.task.build.service.TaskBuildProductService;
import io.tiklab.arbess.task.build.service.TaskBuildProductServiceImpl;
import io.tiklab.arbess.task.code.service.SpotbugsScanService;
import io.tiklab.arbess.task.codescan.model.SpotbugsBugSummary;
import io.tiklab.arbess.task.deploy.model.TaskDeployInstance;
import io.tiklab.arbess.task.deploy.service.TaskDeployInstanceServiceImpl;
import io.tiklab.arbess.task.message.model.TaskMessage;
import io.tiklab.arbess.task.task.model.TaskInstance;
import io.tiklab.arbess.task.task.service.TasksInstanceService;
import io.tiklab.arbess.task.task.service.TasksInstanceServiceImpl;
import io.tiklab.arbess.task.test.model.MavenTest;
import io.tiklab.arbess.task.test.model.TestOnRelevance;
import io.tiklab.arbess.task.test.service.MavenTestService;
import io.tiklab.arbess.task.test.service.RelevanceTestOnService;
import io.tiklab.arbess.ws.service.WebSocketMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;

@Service
public class WebSocketMessageServiceImpl implements WebSocketMessageService {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineInstanceService pipelineInstanceService;

    @Autowired
    StageInstanceServer stageInstanceServer;

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    MavenTestService mavenTestService;

    @Autowired
    SpotbugsScanService spotbugsScanService;

    @Autowired
    RelevanceTestOnService relevanceTestOnService;

    @Autowired
    PipelineUtilService utilService;

    @Autowired
    PipelineHomeService homeService;

    @Autowired
    PostprocessInstanceService postprocessInstanceService;

    @Autowired
    TaskDeployInstanceServiceImpl deployInstanceService;

    @Autowired
    TaskBuildProductService taskBuildProductService;

    @Autowired
    PipelineQueueService queueService;

    @Autowired
    PipelineExecService execService;

    @Autowired(required = false)
    ApprovePipelineService approvePipelineService;

    @Autowired(required = false)
    ApproveService approveService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String distributeMessage(AgentMessage agentMessage){
        String type = agentMessage.getType();
        Object message = agentMessage.getMessage();
        if (type.contains("deploy_instance_log")){
            messageTaskDeployInstanceLogHandle( message);
        }else if (type.contains("deploy_end_instance_log")){
            messageTaskDeployInstanceLogHandle(message);
        }else if (type.contains("log")){
            logMessageHandle(message);
        }else if (type.contains("pipeline_run")){
            pipelineMessageHandle( message);
        }else if (type.contains("stage_run")){
            stageMessageHandle(message);
        } else if (type.contains("post_run")){
            postMessageHandle(message);
        } else if (type.contains("task_run")){
            taskMessageHandle(message);
        }else if (type.contains("maven_unittest")){
            mavenTestTaskMessageHandle(message);
        }else if (type.contains("task_codescan")){
            codeScanTaskMessageHandle(message);
        } else if (type.contains("test_teston_exec")){
            testOnTaskMessageHandle(message);
        }else if (type.contains("send_message")){
            messageTaskMessageHandle(message);
        }else if (type.contains("save_pipeline_instance")){
            messageSavePipelineInstanceHandle(message);
        }
        return null;
    }

    /**
     * 流水线消息处理
     * @param message 消息内容
     */
    private void pipelineMessageHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        PipelineInstance sourceInstance = JSONObject.parseObject(jsonString,PipelineInstance.class);
        // 更新实例状态
        String instanceId = sourceInstance.getInstanceId();
        String runStatus = sourceInstance.getRunStatus();
        PipelineInstance instance = pipelineInstanceService.findOneInstance(instanceId);
        int runtime = pipelineInstanceService.findInstanceRuntime(instanceId);
        instance.setRunTime(runtime+1);
        instance.setRunStatus(runStatus);
        pipelineInstanceService.updateInstance(instance);

        // 设置流水线为未运行
        String pipelineId = instance.getPipeline().getId();
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);
        PipelineExecServiceImpl.pipelineIdOrInstanceId.remove(pipelineId);

        sendPipelineRunMessage(pipeline,instanceId,runStatus);

        // 更新审批状态
        if (!Objects.isNull(approvePipelineService)){
            ApprovePipeline approvePipeline = approvePipelineService.findApproveByInstance(instanceId);
            if (!Objects.isNull(approvePipeline)){
                String approveId = approvePipeline.getApproveId();
                if (runStatus.equals(RUN_SUCCESS)){
                    approvePipeline.setStatus(runStatus);
                    approvePipelineService.updateApprovePipeline(approvePipeline);
                }else {
                    approvePipeline.setStatus(runStatus);
                    approvePipelineService.updateApprovePipeline(approvePipeline);
                    // 运行失败，更新其他状态
                    approvePipelineService.updateApproveInstance(approveId);
                }
                approveService.updateApprovePipeline(approvePipeline);
            }
        }


        // 运行待运行的流水线
        PipelineInstanceQuery instanceQuery = new PipelineInstanceQuery();
        instanceQuery.setState(RUN_WAIT);
        List<PipelineInstance> pipelineInstanceList = pipelineInstanceService.findPipelineInstanceList(instanceQuery);

        if (Objects.isNull(pipelineInstanceList) || pipelineInstanceList.isEmpty()){
            return;
        }

        logger.warn("存在待运行的流水线，数量：{}",pipelineInstanceList.size());
        PipelineInstance pipelineInstance = pipelineInstanceList.get(0);
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        pipelineInstance.setCreateTime(format);
        pipelineInstance.setRunStatus(RUN_RUN);
        pipelineInstanceService.updateInstance(pipelineInstance);

        String userId = pipelineInstance.getUser().getId();
        String id = pipelineInstance.getPipeline().getId();

        PipelineRunMsg pipelineRunMsg = new PipelineRunMsg(id, userId, 1);
        pipelineRunMsg.setInstanceId(pipelineInstance.getInstanceId());
        pipelineRunMsg.setTriggerType(APPROVE);
        if (!Objects.isNull(approvePipelineService)){
            ApprovePipeline approvePipeline = approvePipelineService.findApproveByInstance(pipelineInstance.getInstanceId());
            if (!Objects.isNull(approvePipeline)){
                approvePipeline.setStatus(RUN_RUN);
                approvePipelineService.updateApprovePipeline(approvePipeline);
            }
        }

        // 运行流水线
        try {
            execService.start(pipelineRunMsg);
        }catch (Exception e){
            e.printStackTrace();
            if (!Objects.isNull(approvePipelineService)){
                ApprovePipeline approvePipeline = approvePipelineService.findApproveByInstance(pipelineInstance.getInstanceId());
                if (!Objects.isNull(approvePipeline)){
                    approvePipeline.setStatus(RUN_ERROR);
                    approveService.updateApprovePipeline(approvePipeline);
                }
            }
        }
    }

    // 发送消息
    public void sendPipelineRunMessage(Pipeline pipeline,String instanceId,String state){
        Map<String,Object> map = homeService.initMap(pipeline);
        map.put("instanceId",instanceId);
        map.put("link", PipelineFinal.RUN_LINK);
        PipelineInstance instance = pipelineInstanceService.findOneInstance(instanceId);

        switch (state){
            case RUN_SUCCESS ->{
                map.put("message","运行成功");
                map.put("execStatus","运行成功");
                map.put("colour","info");
            }
            case RUN_ERROR ->{
                map.put("colour","warning");
                map.put("message","运行失败");
                map.put("execStatus","运行失败");
            }
            case RUN_HALT ->{
                map.put("colour","warning");
                map.put("message","停止运程");
                map.put("execStatus","停止运程");
            }
            default -> {
                map.put("colour","info");
                map.put("message","运行成功");
                map.put("execStatus","运行成功");
            }
        }

        String time = PipelineUtil.formatDateTime(instance.getRunTime());
        map.put("execTime",time);
        homeService.log(PipelineFinal.LOG_TYPE_RUN,  map);
        map.put("dmMessage",true);
        homeService.settingMessage(PipelineFinal.MES_RUN,  map);
    }

    /**
     * 阶段消息处理
     * @param message 消息内容
     */
    private void stageMessageHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        StageInstance sourceStageInstance = JSONObject.parseObject(jsonString,StageInstance.class);
        StageInstance stageInstance = stageInstanceServer.findOneStageInstance(sourceStageInstance.getId());
        stageInstance.setStageTime(sourceStageInstance.getStageTime());
        stageInstance.setStageState(sourceStageInstance.getStageState());
        stageInstanceServer.updateStageInstance(stageInstance);
    }

    /**
     * 任务消息处理
     * @param message 消息内容
     */
    private void taskMessageHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);

        TaskInstance sourceTaskInstance = JSONObject.parseObject(jsonString,TaskInstance.class);
        String id = sourceTaskInstance.getId();
        TaskInstance taskInstance = tasksInstanceService.findOneTaskInstance(id);

        TaskInstance mapTaskInstance = TasksInstanceServiceImpl.taskInstanceMap.get(id);
        if (!Objects.isNull(mapTaskInstance)){
            String runLog = mapTaskInstance.getRunLog();
            taskInstance.setRunTime(sourceTaskInstance.getRunTime());
            taskInstance.setRunLog(runLog);
            taskInstance.setRunState(sourceTaskInstance.getRunState());
            String logAddress = taskInstance.getLogAddress();
            PipelineFileUtil.logWriteFile(runLog,logAddress);
        }
        tasksInstanceService.updateTaskInstance(taskInstance);
        TasksInstanceServiceImpl.taskInstanceMap.remove(taskInstance.getId());
    }

    /**
     * 日志消息处理
     * @param message 消息内容
     */
    private void logMessageHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        TaskInstance sourceTaskInstance = JSONObject.parseObject(jsonString,TaskInstance.class);
        String id = sourceTaskInstance.getId();
        TaskInstance taskInstance = TasksInstanceServiceImpl.taskInstanceMap.get(id);
        if (!Objects.isNull(taskInstance)){
            if (!StringUtils.isEmpty(sourceTaskInstance.getRunLog())){
                sourceTaskInstance.setRunLog(taskInstance.getRunLog()+"\n"+sourceTaskInstance.getRunLog());
            }else {
                sourceTaskInstance.setRunLog(taskInstance.getRunLog());
            }
        }
        updateTaskInstance(sourceTaskInstance);
        TasksInstanceServiceImpl.taskInstanceMap.put(sourceTaskInstance.getId(), sourceTaskInstance);
    }

    /**
     * 日志过长更新
     * @param taskInstance 日志信息
     */
    private void updateTaskInstance(TaskInstance taskInstance){
        if (Objects.isNull(taskInstance)){
            return;
        }

        String id = taskInstance.getId();
        TaskInstance oneTaskInstance = tasksInstanceService.findOneTaskInstance(id);
        if (Objects.isNull(oneTaskInstance)){
            return;
        }

        String runLog = taskInstance.getRunLog();
        if (StringUtils.isEmpty(runLog)){
            return;
        }

        String[] split = runLog.split("\n");
        if (taskInstance.getTaskSort() < 2){
            if (split.length < 3){
                return;
            }
        }else {
            if (split.length < 30){
                return;
            }
        }
        String logAddress = oneTaskInstance.getLogAddress();
        logger.info("the log is too long, write to file：{}",logAddress);

        PipelineFileUtil.logWriteFile(runLog,logAddress);

        taskInstance.setRunLog("");
    }

    /**
     * 单元测试结果
     * @param message 消息内容
     */
    private void mavenTestTaskMessageHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        MavenTest mavenTest = JSONObject.parseObject(jsonString, MavenTest.class);
        String id = mavenTest.getId();
        MavenTest oneMavenTest = mavenTestService.findOneMavenTest(id);
        if (Objects.isNull(oneMavenTest)){
            mavenTestService.creatMavenTest(mavenTest);
        }else {
            mavenTestService.updateMavenTest(mavenTest);
        }
    }

    /**
     * 发送消息结果
     * @param message 消息内容
     */
    private void messageTaskMessageHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        TaskMessage taskMessage = JSONObject.parseObject(jsonString, TaskMessage.class);
        Pipeline pipeline = pipelineService.findOnePipeline(taskMessage.getPipelineId());
        Map<String, Object> stringObjectMap = homeService.initMap(pipeline);
        stringObjectMap.putAll(taskMessage.getMap());
        homeService.message(stringObjectMap,taskMessage.getList());
    }

    /**
     * 发送消息结果
     * @param message 消息内容
     */
    private void messageSavePipelineInstanceHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        TaskBuildProduct taskBuildProduct = JSONObject.parseObject(jsonString, TaskBuildProduct.class);
        taskBuildProductService.createBuildProduct(taskBuildProduct);
    }

    /**
     * 发送消息结果
     * @param message 消息内容
     */
    private void messageTaskDeployInstanceLogHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        TaskDeployInstance deployInstance = JSONObject.parseObject(jsonString, TaskDeployInstance.class);
        TaskDeployInstance sourceDeployInstance = deployInstanceService.findDeployInstance(deployInstance.getId());
        sourceDeployInstance.setRunStatus(deployInstance.getRunStatus());
        sourceDeployInstance.setRunTime(deployInstance.getRunTime());
        deployInstanceService.updateDeployInstance(sourceDeployInstance);
    }

    private void messageTaskDeployInstanceLogEndHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        TaskDeployInstance deployInstance = JSONObject.parseObject(jsonString, TaskDeployInstance.class);
        TaskDeployInstance sourceDeployInstance = deployInstanceService.findDeployInstance(deployInstance.getId());
        sourceDeployInstance.setRunStatus(deployInstance.getRunStatus());
        deployInstanceService.updateDeployInstance(sourceDeployInstance);
    }

    /**
     * 代码扫描结果
     * @param message 消息内容
     */
    private void codeScanTaskMessageHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        SpotbugsBugSummary spotbugsBugSummary = JSONObject.parseObject(jsonString, SpotbugsBugSummary.class);
        String xmlFileContent = spotbugsBugSummary.getXmlFileContent();
        String pipelineId = spotbugsBugSummary.getPipelineId();

        String logAddress = utilService.findPipelineDefaultAddress(pipelineId, 2)+"/spotbugs/spotbugs-"+new Date().getTime()+".xml";

        PipelineFileUtil.createFile(logAddress);
        PipelineFileUtil.logWriteFile(xmlFileContent,logAddress);
        logger.info("服务端报保存代码扫描文件：{}", logAddress);
        spotbugsBugSummary.setXmlPath(logAddress);
        spotbugsScanService.creatSpotbugs(spotbugsBugSummary);
    }

    /**
     * testOn自动化测试
     * @param message 消息内容
     */
    private void testOnTaskMessageHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        TestOnRelevance testOnRelevance = JSONObject.parseObject(jsonString, TestOnRelevance.class);
        relevanceTestOnService.createRelevance(testOnRelevance);
    }

    /**
     * testOn自动化测试
     * @param message 消息内容
     */
    private void postMessageHandle(Object message){
        String jsonString = JSONObject.toJSONString(message);
        PostprocessInstance postprocessInstance = JSONObject.parseObject(jsonString, PostprocessInstance.class);
        String instanceId = postprocessInstance.getInstanceId();

        List<PostprocessInstance> postInstanceList = postprocessInstanceService.findPipelinePostInstance(instanceId);
        if (postInstanceList.isEmpty()){
            return;
        }
        PostprocessInstance postInstance = postInstanceList.get(0);
        postInstance.setPostState(postprocessInstance.getPostState());
        postprocessInstanceService.updatePostInstance(postInstance);
    }


}
