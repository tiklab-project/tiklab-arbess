package io.tiklab.matflow.task.message.service;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.home.service.PipelineHomeService;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.task.message.model.TaskMessageType;
import io.tiklab.matflow.task.message.model.TaskUserSendMessageType;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 消息
 */


@Service
@Exporter
public class TaskMessageExecServiceImpl implements TaskMessageExecService {

    @Autowired
    private TasksInstanceService tasksInstanceService;

    @Autowired
    private PipelineHomeService homeService;


    public boolean message(Pipeline pipeline, Tasks task, boolean runState, boolean isPipeline) {
        String taskId = task.getTaskId();

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行任务：消息通知.....");
        TaskMessageType messageType = (TaskMessageType) task.getValues();

        //不存在接收人
        List<TaskUserSendMessageType> userList = messageType.getUserList();
        if (userList == null || userList.size() == 0){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务：消息通知执行完成。");
            return true;
        }

        HashMap<String, Object> map = homeService.initMap(pipeline);

        //添加接收人
        List<TaskUserSendMessageType> list = new ArrayList<>();
        for (TaskUserSendMessageType userMessage : userList) {
            int type = userMessage.getMessageType();
            //添加全部通知人
            if (type == 1){
                list.add(userMessage);
            }
            //添加成功通知人
            if (runState && type == 2){
                list.add(userMessage);
            }
            //添加失败通知人
            if (!runState && type == 3){
                list.add(userMessage);
            }
        }

        //执行成功或失败
        if (runState){
            map.put("message","执行成功");
        }else {
            map.put("message","执行失败");
        }

        //任务消息还是流水线消息
        if (isPipeline){
            map.put("mesType", PipelineFinal.MES_PIPELINE_RUN);
        }else {
            map.put("mesType", PipelineFinal.MES_PIPELINE_TASK_RUN);
        }

        //消息发送类型
        List<String> typeList = messageType.getTypeList();
        try {
            for (String type : typeList) {
                if (!PipelineUtil.isNoNull(type)){
                    continue;
                }
                messageType(taskId,type,list,map);
            }
        }catch (ApplicationException e){
            String message = e.getMessage();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+message);
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务：消息通知执行完成。");
        return true;
    }

    /**
     * 消息发送方式
     * @param type 类型
     * @throws ApplicationException 消息发送失败
     */
    private void messageType(String taskId, String type, List<TaskUserSendMessageType> userList,
                             HashMap<String, Object> map) throws ApplicationException {

        switch (type){
            case PipelineFinal.MES_SENT_SITE ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：站内信");
                map.put("sendWay", PipelineFinal.MES_SENT_SITE);
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
                map.put("sendWay", PipelineFinal.MES_SENT_WECHAT);
                homeService.message(map,new ArrayList<>());
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "微信机器人消息发送成功");
            }
            case "mail" ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：邮箱消息");
                map.put("sendWay", PipelineFinal.MES_SENT_EMAIL);
                List<String> list = new ArrayList<>();
                for (TaskUserSendMessageType message : userList) {
                    list.add(message.getUser().getEmail());
                }
                homeService.message(map,list);
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "邮箱消息发送成功");
            }
            case PipelineFinal.MES_SENT_DINGDING ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：钉钉机器人消息");
                map.put("sendWay", PipelineFinal.MES_SENT_DINGDING);
                homeService.message(map,new ArrayList<>());
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "钉钉机器人消息成功");
            }

            default -> {
                throw new ApplicationException("没有该类型的消息提醒:"+ type);
            }
        }


    }

}
































