package io.tiklab.arbess.support.message.service;

import io.tiklab.arbess.support.message.model.*;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.version.service.PipelineVersionService;
import io.tiklab.arbess.support.message.dao.TaskMessageDao;
import io.tiklab.arbess.support.message.entity.TaskMessageEntity;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.core.utils.UuidGenerator;
import io.tiklab.message.mail.modal.MailCfg;
import io.tiklab.message.mail.service.MailCfgService;
import io.tiklab.message.webhook.modal.WebHook;
import io.tiklab.message.webhook.modal.WebHookQuery;
import io.tiklab.message.webhook.service.WebHookService;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class TaskMessageServiceImpl implements TaskMessageService {

    @Autowired
    TaskMessageDao messageDao;

    @Autowired
    PipelineVersionService versionService;

    @Autowired
    WebHookService webHookService;

    @Autowired
    MailCfgService mailCfgService;

    @Autowired
    TaskMessageTypeService messageTypeService;

    @Autowired
    TaskMessageUserService messageUserService;

    @Autowired
    JoinTemplate joinTemplate;

    @Override
    @Transactional
    public String createTaskMessage(TaskMessage message) {
        String messageId = message.getId();
        if (StringUtils.isEmpty(messageId)){
            messageId = UuidGenerator.getRandomIdByUUID(12);
        }

        List<TaskMessageType> typeList = message.getTypeList();
        if (!Objects.isNull(typeList)){
            for (TaskMessageType messageType : typeList){
                messageType.setMessageId(messageId);
                messageTypeService.createMessageType(messageType);
            }
        }

        List<TaskMessageUser> userList = message.getUserList();
        if (!Objects.isNull(userList)){
            for (TaskMessageUser messageUser : userList){
                messageUser.setMessageId(messageId);
                messageUserService.createMessageUser(messageUser);
            }
        }

        message.setCreateTime(new Timestamp(System.currentTimeMillis()));
        TaskMessageEntity messageEntity = BeanMapper.map(message, TaskMessageEntity.class);
        messageEntity.setId(messageId);
        return messageDao.createTaskMessage(messageEntity);
    }

    @Override
    public void deleteTaskMessage(String id) {

        TaskMessageTypeQuery taskMessageTypeQuery = new TaskMessageTypeQuery();
        taskMessageTypeQuery.setMessageId(id);
        List<TaskMessageType> messageTypeList = messageTypeService.findMessageTypeList(taskMessageTypeQuery);
        for (TaskMessageType taskMessageType : messageTypeList) {
            messageTypeService.deleteMessageType(taskMessageType.getId());
        }
        TaskMessageUserQuery taskMessageUserQuery = new TaskMessageUserQuery();
        taskMessageUserQuery.setMessageId(id);
        List<TaskMessageUser> messageUserList = messageUserService.findMessageUserList(taskMessageUserQuery);
        for (TaskMessageUser messageUser : messageUserList) {
            messageUserService.deleteMessageUser(messageUser.getId());
        }

        messageDao.deleteTaskMessage(id);
    }

    @Transactional
    @Override
    public void updateTaskMessage(TaskMessage message) {
        String messageId = message.getId();

        TaskMessageTypeQuery taskMessageTypeQuery = new TaskMessageTypeQuery();
        taskMessageTypeQuery.setMessageId(messageId);
        List<TaskMessageType> messageTypeList = messageTypeService.findMessageTypeList(taskMessageTypeQuery);
        for (TaskMessageType taskMessageType : messageTypeList) {
            messageTypeService.deleteMessageType(taskMessageType.getId());
        }
        TaskMessageUserQuery taskMessageUserQuery = new TaskMessageUserQuery();
        taskMessageUserQuery.setMessageId(messageId);
        List<TaskMessageUser> messageUserList = messageUserService.findMessageUserList(taskMessageUserQuery);
        for (TaskMessageUser messageUser : messageUserList) {
            messageUserService.deleteMessageUser(messageUser.getId());
        }


        List<TaskMessageType> typeList = message.getTypeList();
        if (!Objects.isNull(typeList)){
            for (TaskMessageType messageType : typeList){
                messageType.setMessageId(messageId);
                messageTypeService.createMessageType(messageType);
            }
        }

        List<TaskMessageUser> userList = message.getUserList();
        if (!Objects.isNull(userList)){
            for (TaskMessageUser messageUser : userList){
                messageUser.setMessageId(messageId);
                messageUserService.createMessageUser(messageUser);
            }
        }



        TaskMessageEntity messageEntity = BeanMapper.map(message, TaskMessageEntity.class);
        messageDao.updateTaskMessage(messageEntity);
    }

    @Override
    public TaskMessage findTaskMessage(String id) {
        TaskMessageEntity messageEntity = messageDao.findTaskMessage(id);
        TaskMessage taskMessage = BeanMapper.map(messageEntity, TaskMessage.class);
        queryMessageDetail(taskMessage);
        return taskMessage;
    }

    @Override
    public TaskMessage findTaskMessageNoQuery(String id) {
        TaskMessageEntity messageEntity = messageDao.findTaskMessage(id);
        TaskMessage taskMessage = BeanMapper.map(messageEntity, TaskMessage.class);
        queryMessageDetailNoQuery(taskMessage);
        return taskMessage;
    }


    @Override
    public void cloneMessage(String id,String cloneId){
        TaskMessageQuery taskMessageQuery = new TaskMessageQuery();
        taskMessageQuery.setPipelineId(id);
        taskMessageQuery.setType(1);
        List<TaskMessage> taskMessageList = findTaskMessageList(taskMessageQuery);
        for (TaskMessage taskMessage : taskMessageList) {
            taskMessage.setPipelineId(cloneId);
            taskMessage.setId(null);
            createTaskMessage(taskMessage);
        }
    }

    @Override
    public void cloneTaskMessage(String id,String cloneId){
        TaskMessageQuery taskMessageQuery = new TaskMessageQuery();
        taskMessageQuery.setTaskId(id);
        taskMessageQuery.setType(2);
        List<TaskMessage> taskMessageList = findTaskMessageList(taskMessageQuery);
        for (TaskMessage taskMessage : taskMessageList) {
            taskMessage.setPipelineId(" ");
            taskMessage.setTaskId(cloneId);
            taskMessage.setId(null);
            createTaskMessage(taskMessage);
        }
    }

    @Override
    public List<TaskMessage> findAllTaskMessage() {
        List<TaskMessageEntity> messageEntityList = messageDao.findAllTaskMessage();
        return BeanMapper.mapList(messageEntityList, TaskMessage.class);
    }

    @Override
    public List<TaskMessage> findTaskMessageList(List<String> idList) {
        List<TaskMessageEntity> messageEntityList = messageDao.findTaskMessageList(idList);
        return BeanMapper.mapList(messageEntityList, TaskMessage.class);
    }

    @Override
    public List<TaskMessage> findTaskMessageList(TaskMessageQuery messageQuery) {
        List<TaskMessageEntity> messageEntityList = messageDao.findTaskMessageList(messageQuery);
        List<TaskMessage> taskMessageList = BeanMapper.mapList(messageEntityList, TaskMessage.class);
        for (TaskMessage taskMessage : taskMessageList) {
            queryMessageDetail(taskMessage);
        }
        return taskMessageList;
    }

    @Override
    public Pagination<TaskMessage> findTaskMessagePage(TaskMessageQuery messageQuery) {
        Pagination<TaskMessageEntity> messageEntityPage = messageDao.findTaskMessagePage(messageQuery);
        List<TaskMessageEntity> dataList = messageEntityPage.getDataList();
        if (Objects.isNull(dataList) || dataList.isEmpty()){
            return PaginationBuilder.build(messageEntityPage, new ArrayList<>());
        }
        List<TaskMessage> messageList = BeanMapper.mapList(dataList, TaskMessage.class);

        for (TaskMessage taskMessage : messageList) {
            queryMessageDetail(taskMessage);
        }

        return PaginationBuilder.build(messageEntityPage, messageList);
    }

    @Override
    public List<String> findMessageSendTypeList(){
        List<String> list = new ArrayList<>();
        if (!versionService.isVip()){
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

    public void queryMessageDetail(TaskMessage message){
        if (Objects.isNull(message)){
            return;
        }

        String messageId = message.getId();

        TaskMessageTypeQuery typeQuery = new TaskMessageTypeQuery();
        typeQuery.setMessageId(messageId);
        List<TaskMessageType> typeList = messageTypeService.findMessageTypeList(typeQuery);
        message.setTypeList(typeList);

        TaskMessageUserQuery userQuery = new TaskMessageUserQuery();
        userQuery.setMessageId(messageId);
        List<TaskMessageUser> userList = messageUserService.findMessageUserList(userQuery);
        joinTemplate.joinQuery(userList,new String[]{"user"});
        message.setUserList(userList);

    }

    public void queryMessageDetailNoQuery(TaskMessage message){
        if (Objects.isNull(message)){
            return;
        }

        String messageId = message.getId();

        TaskMessageTypeQuery typeQuery = new TaskMessageTypeQuery();
        typeQuery.setMessageId(messageId);
        List<TaskMessageType> typeList = messageTypeService.findMessageTypeList(typeQuery);
        message.setTypeList(typeList);

        TaskMessageUserQuery userQuery = new TaskMessageUserQuery();
        userQuery.setMessageId(messageId);
        List<TaskMessageUser> userList = messageUserService.findMessageUserList(userQuery);
        message.setUserList(userList);

    }


}
