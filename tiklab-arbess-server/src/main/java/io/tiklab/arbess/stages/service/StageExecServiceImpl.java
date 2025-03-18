package io.tiklab.arbess.stages.service;

import com.alibaba.fastjson.JSON;
import io.tiklab.arbess.pipeline.execute.model.PipelineDetails;
import io.tiklab.arbess.stages.model.Stage;
import io.tiklab.arbess.stages.model.StageInstance;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.service.PipelineUtilService;
import io.tiklab.arbess.task.deploy.model.TaskDeploy;
import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.arbess.task.task.service.TasksExecService;
import io.tiklab.arbess.task.task.service.TasksService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                List<Tasks> tasks = tasksService.finStageTaskOrTask(id);
                String otherAddress = mainAddress + "/"+otherStageInstanceId;
                //获取串行任务
                for (Tasks task : tasks) {
                    String taskInstanceId = tasksExecService.createTaskExecInstance(task, otherStageInstanceId, 2, otherAddress);
                    tasksExecService.createDeployInstance(task, taskInstanceId);
                    task.setInstanceId(taskInstanceId);
                }
                stage.setTaskValues(tasks);
            }
            mainStage.setStageList(otherStage);
        }
        return allMainStage;
    }

    @Override
    public List<Stage> createRollBackStageExecInstance(String pipelineId,String instanceId) {
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,2) + instanceId;
        List<Stage> allMainStage = stageService.findAllStagesOrTask(pipelineId);

        if (Objects.isNull(allMainStage) || allMainStage.isEmpty()){
            throw new ApplicationException("任务被删除无法回滚！");
        }

        List<Stage> otherRollBackStageList = new ArrayList<>();
        List<Stage> mainRollBackStageList = new ArrayList<>();
        List<Tasks> tasksRollBackList = new ArrayList<>();
        Stage mainRollBackStage = new Stage();
        Stage otherRollBackStage = null;
        Tasks deployTask = null;

        for (Stage mainStage : allMainStage) {
            //创建主阶段日志
            String stageId = mainStage.getStageId();
            List<Stage> otherStageList = stageService.findOtherStage(stageId);
            //获取阶段下的并行任务
            for (Stage stage : otherStageList) {
                List<Tasks> tasks = tasksService.finStageTaskOrTask(stage.getStageId());
                //获取串行任务
                for (Tasks task : tasks) {
                    String taskType = task.getTaskType();
                    if (!taskType.equals(PipelineFinal.TASK_DEPLOY_LINUX)){
                        continue;
                    }
                    String object = JSON.toJSONString(task.getValues());
                    TaskDeploy taskDeploy = JSON.parseObject(object, TaskDeploy.class);
                    if (taskDeploy.getAuthType() != 1){
                        continue;
                    }
                    deployTask = task;
                    break;
                }

                stage.setTaskValues(tasks);
                if (!Objects.isNull(deployTask)){
                    otherRollBackStage = stage;
                    break;
                }
            }
            if (!Objects.isNull(otherRollBackStage)){
                mainRollBackStage = mainStage;
                break;
            }
            mainStage.setStageList(otherStageList);
        }

        // 初始化主阶段
        String stageId = mainRollBackStage.getStageId();
        String mainAddress = fileAddress + "/" + stageId;
        String stageInstanceId = initStageInstance(mainRollBackStage, instanceId, true,mainAddress);
        mainRollBackStage.setInstanceId(stageInstanceId);

        // 初始化并行阶段
        String otherStageInstanceId = initStageInstance(otherRollBackStage, stageInstanceId, false,fileAddress);
        otherRollBackStage.setInstanceId(otherStageInstanceId);

        // 初始化任务
        String otherAddress = mainAddress + "/"+otherStageInstanceId;
        String taskInstanceId = tasksExecService.createTaskExecInstance(deployTask, otherStageInstanceId, 2, otherAddress);
        tasksExecService.createDeployInstance(deployTask, taskInstanceId);
        deployTask.setInstanceId(taskInstanceId);
        tasksRollBackList.add(deployTask);

        // 并行阶段
        otherRollBackStage.setTaskValues(tasksRollBackList);
        otherRollBackStageList.add(otherRollBackStage);

        // 主阶段
        mainRollBackStage.setStageList(otherRollBackStageList);
        mainRollBackStageList.add(mainRollBackStage);


        return mainRollBackStageList;
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











































