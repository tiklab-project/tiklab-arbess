package io.thoughtware.matflow.stages.service;

import io.thoughtware.core.exception.SystemException;
import io.thoughtware.matflow.support.agent.model.Agent;
import io.thoughtware.matflow.support.agent.model.AgentMessage;
import io.thoughtware.matflow.pipeline.execute.model.PipelineDetails;
import io.thoughtware.matflow.stages.model.Stage;
import io.thoughtware.matflow.stages.model.StageInstance;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.service.PipelineUtilService;
import io.thoughtware.matflow.task.task.model.Tasks;
import io.thoughtware.matflow.task.task.service.TasksExecService;
import io.thoughtware.matflow.task.task.service.TasksService;
import io.thoughtware.matflow.ws.server.SocketServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Objects;

/**
 * 阶段运行服务
 */
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

    @Autowired
    PipelineUtilService utilService;

    private final Logger logger = LoggerFactory.getLogger(StageExecServiceImpl.class);

    @Override
    public List<Stage> createStageExecInstance(String pipelineId,String instanceId) {
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,2) + instanceId;
        List<Stage> allMainStage = stageService.findAllStagesOrTask(pipelineId);
        for (Stage mainStage : allMainStage) {
            //创建主阶段日志
            String stageId = mainStage.getStageId();
            String mainAddress = fileAddress + "/" + stageId;
            String stageInstanceId = initStageInstance(mainStage, instanceId, true,mainAddress);
            mainStage.setInstanceId(stageInstanceId);
            List<Stage> otherStage = stageService.findOtherStage(stageId);
            //获取阶段下的并行任务
            for (Stage stage : otherStage) {
                String id = stage.getStageId();
                String otherStageInstanceId = initStageInstance(stage, stageInstanceId, false,fileAddress);
                stage.setInstanceId(otherStageInstanceId);
                List<Tasks> tasks = tasksService.finAllStageTaskOrTask(id);
                String otherAddress = mainAddress + "/"+otherStageInstanceId;
                //获取串行任务
                for (Tasks task : tasks) {
                    String taskInstanceId = tasksExecService.createTaskExecInstance(task, otherStageInstanceId, 2, otherAddress);
                    task.setInstanceId(taskInstanceId);
                }
                stage.setTaskValues(tasks);
            }
            mainStage.setStageList(otherStage);
        }
        return allMainStage;
    }

    /**
     * 初始化阶段运行实例
     * @param stage 阶段信息
     * @param id 主实例id
     * @param isMain 是否为主阶段
     * @param stagePath 日志文件地址
     * @return 阶段运行实例id
     */
    private String initStageInstance(Stage stage , String id,boolean isMain,String stagePath){
        StageInstance stageInstance = new StageInstance();
        if (isMain){
            stageInstance.setInstanceId(id);
        }else {
            stageInstance.setParentId(id);
        }
        stageInstance.setStageName(stage.getStageName());
        stageInstance.setStageAddress(stagePath);
        stageInstance.setStageSort(stage.getStageSort());
        stageInstance.setStageState(PipelineFinal.RUN_WAIT);
        String stageInstanceId = stageInstanceServer.createStageInstance(stageInstance);
        stageInstance.setId(stageInstanceId);
        return stageInstanceId;
    }

    @Override
    public boolean execStageTask(PipelineDetails pipelineDetails) {

        // AgentMessage agentMessage = new AgentMessage();
        // agentMessage.setType("exec");
        // agentMessage.setMessage(pipelineDetails);
        // agentMessage.setPipelineId(pipelineDetails.getPipelineId());
        //
        // Agent agent = pipelineDetails.getAgent();
        //
        // String id = agent.getAddress();
        //
        // WebSocketSession session = SocketServerHandler.sessionMap.get(id);
        // if (Objects.isNull(session)) {
        //     throw new SystemException("客户端推送消息失败，无法获取客户端连接,客户端信息："+id);
        // }
        //
        // try {
        //     SocketServerHandler.instance().sendHandleMessage(id,agentMessage);
        // } catch (Exception e) {
        //     throw new SystemException("客户端推送消息失败,错误信息：" + e.getMessage());
        // }
        return true;
    }












}











































