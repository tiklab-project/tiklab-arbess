package io.tiklab.matflow.task.message.service;

import io.tiklab.beans.BeanMapper;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.matflow.task.message.dao.TaskMessageTypeDao;
import io.tiklab.matflow.task.message.entity.TaskMessageTypeEntity;
import io.tiklab.matflow.task.message.model.TaskMessageType;
import io.tiklab.matflow.task.message.model.TaskMessageUser;
import io.tiklab.message.mail.modal.MailCfg;
import io.tiklab.message.mail.service.MailCfgService;
import io.tiklab.message.webhook.modal.WebHook;
import io.tiklab.message.webhook.modal.WebHookQuery;
import io.tiklab.message.webhook.service.WebHookService;
import io.tiklab.rpc.annotation.Exporter;
import io.tiklab.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static io.tiklab.matflow.support.util.PipelineFinal.*;

@Service
@Exporter
public class TaskMessageTypeServiceImpl implements TaskMessageTypeService {

    @Autowired
    private TaskMessageTypeDao taskMessageTypeDao;
    
    @Autowired
    private TaskMessageUserService messageUserServer;

    @Autowired
    private WebHookService webHookService;

    @Autowired
    private MailCfgService mailCfgService;

    //创建
    @Override
    public void createMessage(TaskMessageType message) {
        List<String> typeList = message.getTypeList() ;
        List<TaskMessageUser> userList = message.getUserList();

        if (typeList == null){
            typeList = new ArrayList<>();
            typeList.add(MES_SEND_SITE);
        }

        if (userList == null){
            userList = new ArrayList<>();
            TaskMessageUser taskMessageUser = new TaskMessageUser();
            taskMessageUser.setReceiveType(1);
            User user = new User();
            user.setId(LoginContext.getLoginId());
            taskMessageUser.setUser(user);
            userList.add(taskMessageUser);
        }

        String taskId = message.getTaskId();
        for (String s : typeList) {
            TaskMessageTypeEntity messageTypeEntity = BeanMapper.map(message, TaskMessageTypeEntity.class);
            messageTypeEntity.setTaskType(s);
            taskMessageTypeDao.createMessage(messageTypeEntity);
            messageUserServer.createAllMessage(userList,taskId);
        }

    }

    @Override
    public void deleteAllMessage(String taskId){
        List<TaskMessageType> allMessage = findAllMessage();
        if (allMessage == null){
            return;
        }
        for (TaskMessageType messageType : allMessage) {
            if (messageType.getTaskId().equals(taskId)){
                String messageTaskId = messageType.getId();
                deleteMessage(messageTaskId);
                messageUserServer.deleteAllMessage(taskId);
            }
        }
    }

    @Override
    public List<String> messageSendType(){
        List<String> list = new ArrayList<>();
        MailCfg oneMail = mailCfgService.findOneMail();
        if (oneMail == null){
            list.add(MES_SEND_EMAIL);
        }
        WebHookQuery webHookQuery = new WebHookQuery();
        webHookQuery.setType(1);
        WebHook hookByType = webHookService.findWebHookByType(webHookQuery);
        if (hookByType == null){
            list.add(MES_SEND_DINGDING);
        }
        webHookQuery.setType(2);
        hookByType = webHookService.findWebHookByType(webHookQuery);
        if (hookByType == null){
            list.add(MES_SEND_WECHAT);
        }
        list.add(MES_SEND_SMS);
        return list;
    }

    @Override
    public TaskMessageType findMessage(String taskId){
        List<TaskMessageType> allMessage = findAllMessage();
        if (allMessage == null){
            return null;
        }
        TaskMessageType taskMessageType = new TaskMessageType();


        //添加消息发送类型
        List<String> list = new ArrayList<>();
        for (TaskMessageType messageType : allMessage) {
            String typeTaskId = messageType.getTaskId();
            if (!typeTaskId.equals(taskId)){
                continue;
            }
            String taskType = messageType.getTaskType();
            list.add(taskType);
        }

        List<TaskMessageUser>  userList = messageUserServer.findAllUserMessage(taskId);
        taskMessageType.setTypeList(list);
        taskMessageType.setTaskId(taskId);
        taskMessageType.setUserList(userList);

        return taskMessageType;
    }


    @Override
    public void deleteMessage(String messageId) {
        taskMessageTypeDao.deleteMessage(messageId);
    }


    @Override
    public void updateMessage(TaskMessageType taskMessageType) {
        TaskMessageTypeEntity taskMessageTypeEntity = BeanMapper.map(taskMessageType, TaskMessageTypeEntity.class);
        taskMessageTypeDao.updateMessage(taskMessageTypeEntity);
    }


    @Override
    public TaskMessageType findOneMessage(String messageId) {
        TaskMessageTypeEntity messageTypeEntity = taskMessageTypeDao.findOneMessage(messageId);
        return BeanMapper.map(messageTypeEntity, TaskMessageType.class);

    }

    @Override
    public List<TaskMessageType> findAllMessage() {
        return BeanMapper.mapList(taskMessageTypeDao.findAllMessage(), TaskMessageType.class);
    }

    @Override
    public List<TaskMessageType> findAllMessageList(List<String> idList) {
        return BeanMapper.mapList(taskMessageTypeDao.findAllMessageTypeList(idList), TaskMessageType.class);
    }
    
    
}
