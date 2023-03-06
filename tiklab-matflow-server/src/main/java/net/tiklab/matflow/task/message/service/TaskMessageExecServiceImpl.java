package net.tiklab.matflow.task.message.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.home.service.PipelineHomeService;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.support.condition.service.ConditionService;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.support.variable.service.VariableService;
import net.tiklab.matflow.task.message.model.TaskMessageType;
import net.tiklab.matflow.task.message.model.TaskUserSendMessageType;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.task.task.service.TasksInstanceService;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static net.tiklab.matflow.support.util.PipelineFinal.*;

/**
 * 消息
 */


@Service
@Exporter
public class TaskMessageExecServiceImpl implements TaskMessageExecService {

    @Autowired
    private TasksInstanceService tasksInstanceService;

    @Autowired
    private VariableService variableServer;

    @Autowired
    private ConditionService conditionService;

    @Autowired
    PipelineHomeService homeService;

    @Autowired
    TaskMessageTypeService messageTypeServer;


    private static final Logger logger = LoggerFactory.getLogger(TaskMessageExecServiceImpl.class);

    /**
     * 部署
     * @param pipeline 配置信息
     * @return 状态
     */
    public boolean message(Pipeline pipeline, Tasks task, int taskType) {
        String pipelineId = pipeline.getId();
        String taskId = task.getTaskId();
        Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        if (!aBoolean){
            String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
            return true;
        }

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行任务：消息通知.....");

        //获取项目执行情况
        int i = 10;
        // for (TaskInstance taskInstance : allLog) {
        //     int state = taskInstance.getRunState();
        //     if (state == 1 || state == 20){
        //         i = state;
        //     }
        // }
        //
        TaskMessageType messageType = (TaskMessageType) task.getValues();

        //需要发送消息的人
        List<TaskUserSendMessageType> list = new ArrayList<>();
        List<TaskUserSendMessageType> userList = messageType.getUserList();

        if (userList == null || userList.size() == 0){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务：消息通知执行完成。");
            return true;
        }


        HashMap<String, Object> map = homeService.initMap(pipeline);

        for (TaskUserSendMessageType userMessage : userList) {
            int type = userMessage.getMessageType();
            if (type == 1){
                list.add(userMessage);
            }
            if (i == 10 && type == 2){
                list.add(userMessage);
            }
            if (i == 1 && type == 3){
                list.add(userMessage);
            }
        }

        if (i == 1){
            map.put("message","执行失败");
        }
        if (i == 10){
            map.put("message","执行成功");
        }
        if (i == 20){
            map.put("message","停止执行");
        }

        // String task = maps.get("task");

        // if (task == null || task.equals("false")){
        //     map.put("mesType",MES_PIPELINE_RUN);
        // }else {
        //     map.put("mesType",MES_PIPELINE_TASK_RUN);
        //     map.put("taskMessage", maps.get("taskMessage"));
        // }
        //
        // if (list.size() == 0){
        //     tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务：消息通知执行完成。");
        //     return true;
        // }
        //
        // //消息发送类型
        // List<String> typeList = configMessage.getTypeList();


        // try {
        //     for (String type : typeList) {
        //         if (!PipelineUtil.isNoNull(type)){
        //             continue;
        //         }
        //         messageType(taskId,type,list,map);
        //     }
        // }catch (ApplicationException e){
        //     String message = e.getMessage();
        //     tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+message);
        //     return false;
        // }
        // tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务：消息通知执行完成。");
        return true;
    }

    /**
     * 消息发送方式
     * @param type 类型
     * @throws ApplicationException 消息发送失败
     */
    private void messageType(String taskId, String type, List<TaskUserSendMessageType> userList, HashMap<String, Object> map) throws ApplicationException {

        switch (type){
            case MES_SENT_SITE ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：站内信");
                map.put("sendWay",MES_SENT_SITE);
                List<String> list = new ArrayList<>();
                for (TaskUserSendMessageType message : userList) {
                    list.add(message.getUser().getId());
                }
                homeService.message(map,list);
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "站内信发送成功");
            }
            case "sms" ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：短信");
                homeService.smsMessage(new HashMap<>());
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "短信发送成功。");
            }
            case "wechat" ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：微信机器人消息");
                map.put("sendWay",MES_SENT_WECHAT);
                homeService.message(map,new ArrayList<>());
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "微信机器人消息发送成功");
            }
            case "mail" ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：邮箱消息");
                map.put("sendWay",MES_SENT_EMAIL);
                List<String> list = new ArrayList<>();
                for (TaskUserSendMessageType message : userList) {
                    list.add(message.getUser().getEmail());
                }
                homeService.message(map,list);
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "邮箱消息发送成功");
            }
            case MES_SENT_DINGDING ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：钉钉机器人消息");
                map.put("sendWay",MES_SENT_DINGDING);
                homeService.message(map,new ArrayList<>());
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "钉钉机器人消息成功");
            }

            default -> {
                throw new ApplicationException("没有该类型的消息提醒:"+ type);
            }
        }


    }

}
































