package io.thoughtware.matflow.task.message.service;

import io.thoughtware.join.JoinTemplate;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.matflow.task.message.model.TaskMessageType;
import io.thoughtware.matflow.task.message.model.TaskMessageUser;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.matflow.home.service.PipelineHomeService;
import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.task.task.model.TaskExecMessage;
import io.thoughtware.matflow.task.task.model.Tasks;
import io.thoughtware.matflow.task.task.service.TasksInstanceService;
import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.user.user.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.thoughtware.matflow.support.util.util.PipelineFinal.*;

/**
 * 消息
 */


@Service
@Exporter
public class TaskMessageExecServiceImpl implements TaskMessageExecService {

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    PipelineHomeService homeService;

    @Autowired
    JoinTemplate joinTemplate;


    public boolean message(TaskExecMessage taskExecMessage) {
        Tasks task = taskExecMessage.getTasks();
        String taskId = task.getTaskId();

        Pipeline pipeline = taskExecMessage.getPipeline();

        boolean runState = taskExecMessage.isExecState();

        boolean isPipeline = taskExecMessage.isExecPipeline();

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行任务：消息通知.....");
        TaskMessageType messageType = (TaskMessageType) task.getValues();

        //不存在接收人
        List<TaskMessageUser> userList = messageType.getUserList();
        if (userList == null || userList.isEmpty()){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"接收人为空！");
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务：消息通知执行完成。");
            return true;
        }

        Map<String, Object> map = homeService.initMap(pipeline);

        //添加接收人
        List<TaskMessageUser> list = new ArrayList<>();
        for (TaskMessageUser userMessage : userList) {
            int type = userMessage.getReceiveType();
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

        joinTemplate.joinQuery(list);
        //执行成功或失败
        if (runState){
            map.put("message","执行成功");
        }else {
            map.put("message","执行失败");
        }

        //任务消息还是流水线消息
        map.put("mesType", PipelineFinal.MES_PIPELINE_RUN);

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
    public void messageType(String taskId, String type, List<TaskMessageUser> userList,
                             Map<String, Object> map) throws ApplicationException {
        switch (type){
            case MES_SEND_SITE ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：站内信");
                map.put("sendWay", MES_SEND_SITE);
                map.put("link",PipelineFinal.RUN_LINK);
                sendSite(taskId,userList,map);
            }
            case MES_SEND_WECHAT ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：微信机器人消息");
                map.put("sendWay", MES_SEND_WECHAT);
                homeService.message(map,new ArrayList<>());
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "微信机器人消息发送成功");
            }
            case MES_SEND_SMS ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：短信消息");
                map.put("sendWay", MES_SEND_SMS);
                sendSSM(taskId,userList,map);
                homeService.smsMessage(map);
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "短信发送成功");
            }
            case MES_SEND_EMAIL ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：邮箱消息");
                map.put("sendWay", MES_SEND_EMAIL);
                sendMail(taskId,userList,map);
                homeService.message(map,new ArrayList<>());
            }
            case MES_SEND_DINGDING ->{
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "发送消息，类型：钉钉机器人消息");
                map.put("sendWay", MES_SEND_DINGDING);
                sendDingDing(taskId,userList,map);
            }
            default -> {
                throw new ApplicationException("没有该类型的消息提醒:"+ type);
            }
        }
    }


    public void  sendMail (String taskId,List<TaskMessageUser> userList,Map<String, Object> map){
        List<String> list = new ArrayList<>();
        for (TaskMessageUser message : userList) {
            User user = message.getUser();
            if (StringUtils.isEmpty(user.getEmail())){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "用户："+user.getNickname()+"邮箱为空！");
                continue;
            }
            list.add(user.getEmail());
        }
        homeService.message(map,list);
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "邮箱消息发送成功!");


    }

    public void  sendSite (String taskId,List<TaskMessageUser> userList,Map<String, Object> map){
        List<String> list = new ArrayList<>();
        for (TaskMessageUser message : userList) {
            User user = message.getUser();
            list.add(user.getId());
        }
        map.put("mesType",MES_RUN);
        homeService.message(map,list);
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "站内信发送成功!");
    }

    public void  sendSSM (String taskId,List<TaskMessageUser> userList,Map<String, Object> map){

    }


    public void  sendDingDing (String taskId,List<TaskMessageUser> userList,Map<String, Object> map){
        homeService.message(map,new ArrayList<>());
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "钉钉机器人消息成功");
    }

}
































