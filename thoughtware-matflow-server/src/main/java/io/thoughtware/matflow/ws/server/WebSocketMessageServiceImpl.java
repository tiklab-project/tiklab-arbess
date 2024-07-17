package io.thoughtware.matflow.ws.server;

import com.alibaba.fastjson.JSONObject;
import io.thoughtware.matflow.home.service.PipelineHomeService;
import io.thoughtware.matflow.pipeline.execute.service.PipelineExecServiceImpl;
import io.thoughtware.matflow.support.agent.model.AgentMessage;
import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.pipeline.definition.service.PipelineService;
import io.thoughtware.matflow.pipeline.instance.model.PipelineInstance;
import io.thoughtware.matflow.pipeline.instance.service.PipelineInstanceService;
import io.thoughtware.matflow.stages.model.StageInstance;
import io.thoughtware.matflow.stages.service.StageInstanceServer;
import io.thoughtware.matflow.support.postprocess.model.PostprocessInstance;
import io.thoughtware.matflow.support.postprocess.service.PostprocessInstanceService;
import io.thoughtware.matflow.support.util.service.PipelineUtilService;
import io.thoughtware.matflow.support.util.util.PipelineFileUtil;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.matflow.task.code.service.SpotbugsScanService;
import io.thoughtware.matflow.task.codescan.model.SpotbugsBugSummary;
import io.thoughtware.matflow.task.message.model.TaskMessage;
import io.thoughtware.matflow.task.task.model.TaskInstance;
import io.thoughtware.matflow.task.task.service.TasksInstanceService;
import io.thoughtware.matflow.task.task.service.TasksInstanceServiceImpl;
import io.thoughtware.matflow.task.test.model.MavenTest;
import io.thoughtware.matflow.task.test.model.TestOnRelevance;
import io.thoughtware.matflow.task.test.service.MavenTestService;
import io.thoughtware.matflow.task.test.service.RelevanceTestOnService;
import io.thoughtware.matflow.ws.service.WebSocketMessageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String distributeMessage(AgentMessage agentMessage){
        String type = agentMessage.getType();
        Object message = agentMessage.getMessage();
        if (type.contains("log")){
            logMessageHandle( message);
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
        PipelineInstance instance = pipelineInstanceService.findOneInstance(instanceId);
        int runtime = pipelineInstanceService.findInstanceRuntime(instanceId);
        instance.setRunTime(runtime+1);
        instance.setRunStatus(sourceInstance.getRunStatus());
        pipelineInstanceService.updateInstance(instance);

        // 设置流水线为未运行
        String pipelineId = instance.getPipeline().getId();
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);
        PipelineExecServiceImpl.pipelineIdOrInstanceId.remove(pipelineId);
        sendPipelineRunMessage(pipeline,instanceId,true);

    }

    // 发送消息
    public void sendPipelineRunMessage(Pipeline pipeline,String instanceId,Boolean state){
        Map<String,Object> map = homeService.initMap(pipeline);
        map.put("instanceId",instanceId);
        map.put("link", PipelineFinal.RUN_LINK);
        PipelineInstance instance = pipelineInstanceService.findOneInstance(instanceId);

        if (state){
            map.put("message","运行成功");
            map.put("execStatus","运行成功");
            map.put("colour","info");
        }else {
            map.put("colour","warning");
            map.put("message","运行失败");
            map.put("execStatus","运行失败");
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
        if (split.length < 1000){
            return;
        }
        String logAddress = oneTaskInstance.getLogAddress();
        logger.info("日志过长，写入文件：{}",logAddress);

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
