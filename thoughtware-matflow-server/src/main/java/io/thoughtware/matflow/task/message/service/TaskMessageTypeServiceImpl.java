package io.thoughtware.matflow.task.message.service;

import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.version.service.PipelineVersionService;
import io.thoughtware.matflow.task.message.model.TaskMessageType;
import io.thoughtware.matflow.task.message.model.TaskMessageUser;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.eam.common.context.LoginContext;
import io.thoughtware.matflow.task.message.dao.TaskMessageTypeDao;
import io.thoughtware.matflow.task.message.entity.TaskMessageTypeEntity;
import io.thoughtware.message.mail.modal.MailCfg;
import io.thoughtware.message.mail.service.MailCfgService;
import io.thoughtware.message.webhook.modal.WebHook;
import io.thoughtware.message.webhook.modal.WebHookQuery;
import io.thoughtware.message.webhook.service.WebHookService;
import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Exporter
public class TaskMessageTypeServiceImpl implements TaskMessageTypeService {

    @Autowired
    TaskMessageTypeDao taskMessageTypeDao;
    
    @Autowired
    TaskMessageUserService messageUserServer;

    @Autowired
    WebHookService webHookService;

    @Autowired
    MailCfgService mailCfgService;

    @Autowired
    PipelineVersionService versionService;


    //创建
    @Override
    public void createMessage(TaskMessageType message) {
        List<String> typeList = message.getTypeList() ;
        List<TaskMessageUser> userList = message.getUserList();

        if (Objects.isNull(typeList)){
            typeList = new ArrayList<>();
            typeList.add(PipelineFinal.MES_SEND_SITE);
        }

        if (Objects.isNull(userList)){
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
        }
        messageUserServer.createAllMessage(userList,taskId);
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

        if (!versionService.isVip()){
            list.add(PipelineFinal.MES_SEND_EMAIL);
            list.add(PipelineFinal.MES_SEND_WECHAT);
            list.add(PipelineFinal.MES_SEND_DINGDING);
            list.add(PipelineFinal.MES_SEND_SMS);
            return list;
        }

        MailCfg oneMail = mailCfgService.findOneMail();
        if (Objects.isNull(oneMail)){
            list.add(PipelineFinal.MES_SEND_EMAIL);
        }
        WebHookQuery webHookQuery = new WebHookQuery();
        webHookQuery.setType(1);
        webHookQuery.setType(2);
        WebHook hookByType = webHookService.findWebHookByType(webHookQuery);
        if (Objects.isNull(hookByType)){
            list.add(PipelineFinal.MES_SEND_WECHAT);
        }
        list.add(PipelineFinal.MES_SEND_SMS);
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

        List<TaskMessageUser> userList = messageUserServer.findAllUserMessage(taskId);
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
