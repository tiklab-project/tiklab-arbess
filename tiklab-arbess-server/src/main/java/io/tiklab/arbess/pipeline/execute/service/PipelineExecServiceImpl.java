package io.tiklab.arbess.pipeline.execute.service;

import io.tiklab.arbess.home.service.PipelineHomeService;
import io.tiklab.arbess.task.build.model.TaskBuildProduct;
import io.tiklab.arbess.task.build.model.TaskBuildProductQuery;
import io.tiklab.arbess.task.build.service.TaskBuildProductService;
import io.tiklab.core.exception.SystemException;
import io.tiklab.arbess.pipeline.instance.service.PipelineInstanceServiceImpl;
import io.tiklab.arbess.support.agent.model.Agent;
import io.tiklab.arbess.support.agent.model.AgentMessage;
import io.tiklab.arbess.pipeline.execute.model.PipelineDetails;
import io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
import io.tiklab.arbess.setting.model.Scm;
import io.tiklab.arbess.setting.service.ResourcesService;
import io.tiklab.arbess.setting.service.ScmService;
import io.tiklab.arbess.stages.model.Stage;
import io.tiklab.arbess.stages.service.StageExecService;
import io.tiklab.arbess.stages.service.StageService;
import io.tiklab.arbess.support.agent.service.AgentService;
import io.tiklab.arbess.support.disk.service.DiskService;
import io.tiklab.arbess.support.postprocess.model.Postprocess;
import io.tiklab.arbess.support.postprocess.service.PostprocessExecService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.service.PipelineUtilService;
import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.support.variable.service.VariableService;
import io.tiklab.arbess.support.version.service.PipelineVersionService;
import io.tiklab.arbess.task.task.service.TasksExecService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.arbess.ws.server.SocketServerHandler;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.definition.service.PipelineService;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.arbess.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import static io.tiklab.arbess.support.util.util.PipelineFinal.PIPELINE_RUN_KEY;

/**
 * 流水线运行服务
 */
@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService  {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PostprocessExecService postExecService;

    @Autowired
    PipelineInstanceService pipelineInstanceService;

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

    @Autowired
    ScmService scmService;

    @Autowired
    VariableService variableService;

    @Autowired
    AgentService agentService;

    @Autowired
    PipelineHomeService homeService;

    @Autowired
    TaskBuildProductService taskBuildProductService;

    public final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线id:流水线实例id
    public static final  Map<String,String> pipelineIdOrInstanceId = new HashMap<>();

    //流水线id:agent
    public static final Map<String , Agent> pipelineIdOrAgentId = new HashMap<>();


    /**
     * 流水线开始运行
     * @param runMsg 流水线id
     * @return 是否正在运行
     */
    @Override
    public PipelineInstance start(PipelineRunMsg runMsg) {

        Boolean permissions = homeService.findPermissions(runMsg.getPipelineId(), PIPELINE_RUN_KEY);
        if (!permissions) {
            throw new ApplicationException("您没有权限执行该流水线,请联系管理员授权!");
        }

        Agent agent;
        if (StringUtils.isEmpty(runMsg.getAgentId())){
            agent = agentService.findDefaultAgent();
        }else {
            agent = agentService.findAgent(runMsg.getAgentId());
        }
        if (Objects.isNull(agent)){
            throw new ApplicationException("无法获取到流水线执行Agent！");
        }

        WebSocketSession session = SocketServerHandler.sessionMap.get(agent.getAddress());
        if (Objects.isNull(session)){
            throw new ApplicationException("流水线Agent断开连接，无法执行。");
        }

        // 判断同一任务是否在运行
        Pipeline pipeline = validExecPipeline(runMsg);
        String pipelineId = pipeline.getId();

        List<String> strings = stageService.validStagesMustField(pipelineId);
        if (!Objects.isNull(strings) && !strings.isEmpty()){
            throw new ApplicationException("流水线未配置完成，请完善配置后在执行。");
        }

        pipelineIdOrAgentId.put(pipelineId, agent);

        // 判断磁盘空间是否足够
        diskService.validationStorageSpace();

        // 资源限制
        resourcesService.judgeResources();

        // 进入执行
        runMsg.setPipeline(pipeline);
        runMsg.setAgent(agent);
        return beginExecPipeline(runMsg);
    }

    @Override
    public PipelineInstance rollBackStart(PipelineRunMsg runMsg) {

        Boolean permissions = homeService.findPermissions(runMsg.getPipelineId(), PIPELINE_RUN_KEY);
        if (!permissions) {
            throw new ApplicationException("您没有权限执行该流水线,请联系管理员授权!");
        }

        Agent agent;
        if (StringUtils.isEmpty(runMsg.getAgentId())){
            agent = agentService.findDefaultAgent();
        }else {
            agent = agentService.findAgent(runMsg.getAgentId());
        }
        if (Objects.isNull(agent)){
            throw new ApplicationException("无法获取到流水线执行Agent！");
        }

        WebSocketSession session = SocketServerHandler.sessionMap.get(agent.getAddress());
        if (Objects.isNull(session)){
            throw new ApplicationException("流水线Agent断开连接，无法执行。");
        }

        // 判断同一任务是否在运行
        Pipeline pipeline = validExecPipeline(runMsg);
        String pipelineId = pipeline.getId();

        List<String> strings = stageService.validStagesMustField(pipelineId);
        if (!Objects.isNull(strings) && !strings.isEmpty()){
            throw new ApplicationException("流水线未配置完成，请完善配置后在执行。");
        }

        pipelineIdOrAgentId.put(pipelineId, agent);

        // 判断磁盘空间是否足够
        diskService.validationStorageSpace();

        // 资源限制
        resourcesService.judgeResources();

        // 进入执行
        runMsg.setPipeline(pipeline);
        runMsg.setAgent(agent);
        return rollBackBeginExecPipeline(runMsg);
    }

    /**
     * 放入正在执行的流水线缓存中
     * @param runMsg 流水线id
     * @return 流水线信息
     */
    public Pipeline validExecPipeline(PipelineRunMsg runMsg){
        String pipelineId = runMsg.getPipelineId();

        List<Stage> allMainStage = stageService.findAllMainStage(pipelineId);
        if (allMainStage.isEmpty()){
            throw new ApplicationException(2000,"当前流水线不存在可构建任务！");
        }

        Boolean isVip = versionService.isVip();

        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);

        int size = pipelineIdOrInstanceId.size();

        // 资源限制放入缓存中等待执行
        if ((!isVip && size >= 2) || (isVip && size >= 4) ){
            throw new ApplicationException(2000,"并行任务已满，等待执行！");
        }
        return pipeline;
    }

    /**
     * 执行流水线
     * @param runMsg 流水线信息
     * @return 流水线实例
     */
    public PipelineInstance beginExecPipeline(PipelineRunMsg runMsg){
        String pipelineId = runMsg.getPipelineId();
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        pipeline.setState(2);
        pipelineService.updatePipeline(pipeline);
        runMsg.setPipeline(pipeline);

        logger.info("流水线{}开始运行",pipeline.getName());
        PipelineInstance pipelineInstance = pipelineInstanceService.initializeInstance(runMsg);
        // 添加到缓存
        String instanceId = pipelineInstance.getInstanceId();
        pipelineInstanceService.instanceRuntime(pipelineInstance.getInstanceId());
        joinTemplate.joinQuery(pipelineInstance);
        // 运行实例放入内存中
        pipelineIdOrInstanceId.put(pipelineId, instanceId);

        try {
            // 创建多阶段运行实例
            List<Stage> stageList = stageExecService.createStageExecInstance(pipelineId, instanceId);

            List<Postprocess> postprocessList = postExecService.createPipelinePostInstance(pipelineId, instanceId);

            PipelineDetails pipelineDetails = new PipelineDetails();

            // 流水线基本运行信息
            pipelineDetails.setPipelineId(pipelineId);
            pipelineDetails.setInstanceId(instanceId);
            pipelineDetails.setRunWay(runMsg.getRunWay());
            pipelineDetails.setAgent(runMsg.getAgent());

            // 流水线运行任务
            pipelineDetails.setStageList(stageList);

            // 流水线后置处理
            pipelineDetails.setPostprocessList(postprocessList);

            // 数据路径，源码，日志保存
            String sourceDir = utilService.findPipelineDefaultAddress(pipelineId,1);
            String logDir = utilService.findPipelineDefaultAddress(pipelineId,2);
            pipelineDetails.setSourceDir(sourceDir);
            pipelineDetails.setLogDir(logDir);

            // 变量
            List<Variable> variableList = variableService.findAllVariable(pipelineId);
            pipelineDetails.setVariableList(variableList);

            AgentMessage agentMessage = new AgentMessage();
            agentMessage.setType("exec");
            agentMessage.setMessage(pipelineDetails);
            agentMessage.setPipelineId(pipelineId);

            Agent agent = pipelineDetails.getAgent();

            String id = agent.getAddress();

            WebSocketSession session = SocketServerHandler.sessionMap.get(id);
            if (Objects.isNull(session)) {
                throw new SystemException("客户端推送消息失败，无法获取客户端连接,客户端信息："+id);
            }

            try {
                logger.info("发送流水线执行信息到客户端......");
                SocketServerHandler.instance().sendHandleMessage(id,agentMessage);
            } catch (Exception e) {
                throw new SystemException("客户端推送消息失败,错误信息：" + e.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("流水线执行出错了：{}",e.getMessage() );
            stop(pipelineId);
        }
        return pipelineInstance;
    }

    /**
     * 执行回滚流水线
     * @param runMsg 流水线信息
     * @return 流水线实例
     */
    public PipelineInstance rollBackBeginExecPipeline(PipelineRunMsg runMsg){
        String pipelineId = runMsg.getPipelineId();

        String rollBackInstanceId = runMsg.getInstanceId();
        TaskBuildProductQuery productQuery = new TaskBuildProductQuery();
        productQuery.setInstanceId(rollBackInstanceId);
        List<TaskBuildProduct> buildProductList = taskBuildProductService.findBuildProductList(productQuery);
        if (buildProductList.isEmpty()){
            throw new ApplicationException("无法获取到部署文件，回滚失败!");
        }

        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        pipeline.setState(2);
        pipelineService.updatePipeline(pipeline);
        runMsg.setPipeline(pipeline);

        TaskBuildProduct taskBuildProduct = buildProductList.get(0);

        logger.info("流水线{}开始运行",pipeline.getName());
        PipelineInstance pipelineInstance = pipelineInstanceService.initializeInstance(runMsg);
        // 添加到缓存
        String instanceId = pipelineInstance.getInstanceId();
        pipelineInstanceService.instanceRuntime(pipelineInstance.getInstanceId());
        joinTemplate.joinQuery(pipelineInstance);

        // 运行实例放入内存中
        pipelineIdOrInstanceId.put(pipelineId, instanceId);

        try {
            // 创建多阶段运行实例
            List<Stage> stageList = stageExecService.createRollBackStageExecInstance(pipelineId, instanceId);

            List<Postprocess> postprocessList = postExecService.createPipelinePostInstance(pipelineId, instanceId);
            PipelineDetails pipelineDetails = new PipelineDetails();
            pipelineDetails.setTaskBuildProduct(taskBuildProduct);

            // 流水线基本运行信息
            pipelineDetails.setPipelineId(pipelineId);
            pipelineDetails.setInstanceId(instanceId);
            pipelineDetails.setRunWay(runMsg.getRunWay());
            pipelineDetails.setAgent(runMsg.getAgent());

            // 流水线运行任务
            pipelineDetails.setStageList(stageList);

            // 流水线后置处理
            pipelineDetails.setPostprocessList(postprocessList);

            // 数据路径，源码，日志保存
            String sourceDir = utilService.findPipelineDefaultAddress(pipelineId,1);
            String logDir = utilService.findPipelineDefaultAddress(pipelineId,2);
            pipelineDetails.setSourceDir(sourceDir);
            pipelineDetails.setLogDir(logDir);

            // 变量
            List<Variable> variableList = variableService.findAllVariable(pipelineId);
            pipelineDetails.setVariableList(variableList);

            AgentMessage agentMessage = new AgentMessage();
            agentMessage.setType("exec");
            agentMessage.setMessage(pipelineDetails);
            agentMessage.setPipelineId(pipelineId);

            Agent agent = pipelineDetails.getAgent();

            String id = agent.getAddress();

            WebSocketSession session = SocketServerHandler.sessionMap.get(id);
            if (Objects.isNull(session)) {
                throw new SystemException("客户端推送消息失败，无法获取客户端连接,客户端信息："+id);
            }

            try {
                logger.info("发送流水线执行信息到客户端......");
                SocketServerHandler.instance().sendHandleMessage(id,agentMessage);
            } catch (Exception e) {
                throw new SystemException("客户端推送消息失败,错误信息：" + e.getMessage());
            }
        }catch (Exception e){
            logger.error("流水线执行出错了：{}",e.getMessage() );
            stop(pipelineId);
        }
        return pipelineInstance;
    }

    @Override
    public void stop(String pipelineId){
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);

        Agent agent = pipelineIdOrAgentId.get(pipelineId);
        if (Objects.isNull(agent)){
            pipeline.setState(1);
            pipelineService.updatePipeline(pipeline);

            PipelineInstanceQuery pipelineInstanceQuery = new PipelineInstanceQuery();
            pipelineInstanceQuery.setState(PipelineFinal.RUN_RUN);
            pipelineInstanceQuery.setPipelineId(pipelineId);
            List<PipelineInstance> pipelineInstanceList = pipelineInstanceService.findPipelineInstanceList(pipelineInstanceQuery);
            for (PipelineInstance pipelineInstance : pipelineInstanceList) {
                String instanceId = pipelineInstance.getInstanceId();
                pipelineInstance.setRunStatus(PipelineFinal.RUN_HALT);
                int runtime = pipelineInstanceService.findInstanceRuntime(instanceId);
                pipelineInstance.setRunTime(runtime);
                pipelineInstanceService.updateInstance(pipelineInstance);
            }
            removeExecCache(pipelineId);
            return;
        }

        try {
            AgentMessage agentMessage = new AgentMessage();
            agentMessage.setType("stop");
            agentMessage.setMessage(pipelineId);
            agentMessage.setPipelineId(pipelineId);
            SocketServerHandler.instance().sendHandleMessage(agent.getAddress(),agentMessage);
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        // 添加资源配置
        // resourcesService.instanceResources(integer);

        // 更新流水线状态
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);

        PipelineInstanceQuery pipelineInstanceQuery = new PipelineInstanceQuery();
        pipelineInstanceQuery.setState(PipelineFinal.RUN_RUN);
        pipelineInstanceQuery.setPipelineId(pipelineId);
        List<PipelineInstance> pipelineInstanceList = pipelineInstanceService.findPipelineInstanceList(pipelineInstanceQuery);
        for (PipelineInstance pipelineInstance : pipelineInstanceList) {
            pipelineInstance.setRunStatus(PipelineFinal.RUN_HALT);
            int runtime = pipelineInstanceService.findInstanceRuntime(pipelineInstance.getInstanceId());
            pipelineInstance.setRunTime(runtime);
            pipelineInstanceService.updateInstance(pipelineInstance);
        }
        removeExecCache(pipelineId);

    }

    public void removeExecCache(String pipelineId){
        String instanceId = pipelineIdOrInstanceId.get(pipelineId);
        PipelineInstanceServiceImpl.runTimeMap.remove(instanceId);
        pipelineInstanceService.stopThread(instanceId);
        pipelineIdOrInstanceId.remove(pipelineId);
    }

    @Override
    public void keepOn(String pipelineId){
        Agent agent = pipelineIdOrAgentId.get(pipelineId);
        String id = agent.getAddress();
        AgentMessage agentMessage = new AgentMessage();
        agentMessage.setType("keepOn");
        agentMessage.setMessage(pipelineId);
        agentMessage.setPipelineId(pipelineId);
        WebSocketSession session = SocketServerHandler.sessionMap.get(id);
        if (Objects.isNull(session)) {
            throw new SystemException("客户端推送消息失败，无法获取客户端连接,客户端信息："+id);
        }

        try {
            SocketServerHandler.instance().sendHandleMessage(id,agentMessage);
        } catch (Exception e) {
            throw new SystemException("客户端推送消息失败,错误信息：" + e.getMessage());
        }
    }

}


































