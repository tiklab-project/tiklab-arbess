package io.thoughtware.matflow.task.message.service;

import io.thoughtware.matflow.task.message.model.TaskMessageUser;
import io.thoughtware.beans.BeanMapper;
import io.thoughtware.join.JoinTemplate;
import io.thoughtware.matflow.task.message.dao.TaskMessageUserDao;
import io.thoughtware.matflow.task.message.entity.TaskMessageUserEntity;
import io.thoughtware.rpc.annotation.Exporter;
import io.thoughtware.user.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class TaskMessageUserServiceImpl implements TaskMessageUserService {

    @Autowired
    private TaskMessageUserDao taskMessageUserDao;

    @Autowired
    private JoinTemplate joinTemplate;

    //创建
    @Override
    public String createMessage(TaskMessageUser taskMessageUser) {
        TaskMessageUserEntity taskMessageUserEntity = BeanMapper.map(taskMessageUser, TaskMessageUserEntity.class);
        return taskMessageUserDao.createMessage(taskMessageUserEntity);
    }

    /**
     * 添加所有接收人
     * @param userMessages 接收人信息
     * @param taskId 任务id
     */
    public void createAllMessage(List<TaskMessageUser> userMessages, String taskId){
        if (userMessages == null){
            return;
        }
        for (TaskMessageUser userMessage : userMessages) {
            User user = userMessage.getUser();
            int type = userMessage.getReceiveType();
            TaskMessageUser messageUser = new TaskMessageUser();
            messageUser.setReceiveType(type);
            messageUser.setUser(user);
            messageUser.setTaskId(taskId);
            createMessage(messageUser);
        }
    }

    /**
     * 查询所有发送人
     * @param taskId 任务id
     * @return 发送人
     */
    public List<TaskMessageUser> findAllUserMessage(String taskId){
        List<TaskMessageUser> list = new ArrayList<>();
        for (TaskMessageUser messageUser : findAllMessage()) {
            String messageTaskId = messageUser.getTaskId();
            if (messageTaskId.equals(taskId)){
                joinTemplate.joinQuery(messageUser);
                User user = messageUser.getUser();
                int receiveType = messageUser.getReceiveType();
                TaskMessageUser taskUserSendMessageType = new TaskMessageUser();
                taskUserSendMessageType.setReceiveType(receiveType);
                taskUserSendMessageType.setUser(user);
                list.add(taskUserSendMessageType);
            }
        }
        return list;
    }

    /**
     * 删除任务
     * @param taskId 配置id
     */
    public void deleteAllMessage(String taskId){
        List<TaskMessageUser> allMessage = findAllMessage();
        for (TaskMessageUser messageUser : allMessage) {
            if (messageUser.getTaskId().equals(taskId)){
                deleteMessage(messageUser.getMessageId());
            }
        }
    }

    //删除
    @Override
    public void deleteMessage(String messageId) {
        taskMessageUserDao.deleteMessage(messageId);
    }

    //更新
    @Override
    public void updateMessage(TaskMessageUser taskMessageUser) {
        TaskMessageUserEntity taskMessageUserEntity = BeanMapper.map(taskMessageUser, TaskMessageUserEntity.class);
        taskMessageUserDao.updateMessage(taskMessageUserEntity);
    }

    //查询单个
    @Override
    public TaskMessageUser findOneMessage(String messageId) {
        TaskMessageUserEntity messageTypeEntity = taskMessageUserDao.findOneMessage(messageId);
        return BeanMapper.map(messageTypeEntity, TaskMessageUser.class);

    }

    //查询所有
    @Override
    public List<TaskMessageUser> findAllMessage() {
        return BeanMapper.mapList(taskMessageUserDao.findAllMessage(), TaskMessageUser.class);
    }

    @Override
    public List<TaskMessageUser> findAllMessageList(List<String> idList) {
        return BeanMapper.mapList(taskMessageUserDao.findAllMessageUserList(idList), TaskMessageUser.class);
    }
    
    
}
