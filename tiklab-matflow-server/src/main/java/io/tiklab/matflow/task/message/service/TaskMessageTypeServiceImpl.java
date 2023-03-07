package io.tiklab.matflow.task.message.service;

import io.tiklab.matflow.task.message.dao.TaskMessageTypeDao;
import io.tiklab.matflow.task.message.entity.TaskMessageTypeEntity;
import io.tiklab.matflow.task.message.model.TaskMessageType;
import io.tiklab.matflow.task.message.model.TaskUserSendMessageType;
import io.tiklab.beans.BeanMapper;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Exporter
public class TaskMessageTypeServiceImpl implements TaskMessageTypeService {

    @Autowired
    private TaskMessageTypeDao taskMessageTypeDao;
    
    @Autowired
    private TaskMessageUserService messageUserServer;

    @Autowired
    private TaskMessageTypeService messageTypeService;

    //创建
    @Override
    public void createMessage(TaskMessageType message) {

        List<String> typeList = message.getTypeList() ;
        List<TaskUserSendMessageType> userList = message.getUserList();

        for (String s : typeList) {
            TaskMessageTypeEntity messageTypeEntity = BeanMapper.map(message, TaskMessageTypeEntity.class);
            messageTypeEntity.setTaskType(s);
            String taskId = taskMessageTypeDao.createMessage(messageTypeEntity);
            messageUserServer.createAllMessage(userList,taskId);
        }

    }

    /**
     * 根据配置查询发送方式
     * @param configId 配置id
     * @return 发送方式
     */
    public List<String> findAllMessage(String configId){
        List<TaskMessageType> allMessage = findAllMessage();
        if (allMessage == null){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (TaskMessageType messageType : allMessage) {
            if (messageType.getTaskId().equals(configId)){
                list.add(messageType.getTaskType());
            }
        }
        return list;
    }

    /**
     * 删除任务
     * @param taskId 配置id
     */
    public void deleteAllMessage(String taskId){
        List<TaskMessageType> allMessage = findAllMessage();
        if (allMessage == null){
            return;
        }
        for (TaskMessageType messageType : allMessage) {
            if (messageType.getTaskId().equals(taskId)){
                String messageTaskId = messageType.getId();
                deleteMessage(messageTaskId);
                messageUserServer.deleteAllMessage(messageTaskId);
            }
        }

    }

    @Override
    public TaskMessageType findMessage(String taskId){
        List<TaskMessageType> allMessage = findAllMessage();
        if (allMessage == null){
            return null;
        }
        TaskMessageType taskMessageType = new TaskMessageType();
        List<String> list = new ArrayList<>();

        for (TaskMessageType messageType : allMessage) {
            String typeTaskId = messageType.getTaskId();
            if (!typeTaskId.equals(taskId)){
                continue;
            }

            String taskType = messageType.getTaskType();
            list.add(taskType);
        }
        String id = allMessage.get(0).getId();
        List<TaskUserSendMessageType>  userList = messageUserServer.findAllUserMessage(id);
        taskMessageType.setTypeList(list);
        taskMessageType.setTaskId(taskId);
        taskMessageType.setUserList(userList);

        return taskMessageType;
    }

    //删除
    @Override
    public void deleteMessage(String messageId) {
        taskMessageTypeDao.deleteMessage(messageId);
    }

    //更新
    @Override
    public void updateMessage(TaskMessageType taskMessageType) {
        TaskMessageTypeEntity taskMessageTypeEntity = BeanMapper.map(taskMessageType, TaskMessageTypeEntity.class);
        taskMessageTypeDao.updateMessage(taskMessageTypeEntity);
    }

    //查询单个
    @Override
    public TaskMessageType findOneMessage(String messageId) {
        TaskMessageTypeEntity messageTypeEntity = taskMessageTypeDao.findOneMessage(messageId);
        return BeanMapper.map(messageTypeEntity, TaskMessageType.class);

    }

    //查询所有
    @Override
    public List<TaskMessageType> findAllMessage() {
        return BeanMapper.mapList(taskMessageTypeDao.findAllMessage(), TaskMessageType.class);
    }

    @Override
    public List<TaskMessageType> findAllMessageList(List<String> idList) {
        return BeanMapper.mapList(taskMessageTypeDao.findAllMessageTypeList(idList), TaskMessageType.class);
    }
    
    
}
