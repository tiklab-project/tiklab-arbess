package io.thoughtware.matflow.task.message.service;

import io.thoughtware.join.annotation.FindAll;
import io.thoughtware.join.annotation.FindList;
import io.thoughtware.join.annotation.FindOne;
import io.thoughtware.join.annotation.JoinProvider;
import io.thoughtware.matflow.task.message.model.TaskMessageUser;

import java.util.List;

/**
 * 消息接收人服务接口
 */
@JoinProvider(model = TaskMessageUser.class)
public interface TaskMessageUserService {

    /**
     * 创建
     * @param taskMessageUser message信息
     * @return messageId
     */
    String createMessage(TaskMessageUser taskMessageUser) ;


    /**
     * 添加所有接收人
     * @param userMessages 接收人信息
     * @param taskId 任务id
     */
    void createAllMessage(List<TaskMessageUser> userMessages, String taskId);


    /**
     * 查询所有发送人
     * @param taskId 任务id
     * @return 发送人
     */
    List<TaskMessageUser> findAllUserMessage(String taskId);


    /**
     * 删除任务
     * @param taskId 配置id
     */
     void deleteAllMessage(String taskId);

    /**
     * 删除
     * @param messageId messageId
     */
    void deleteMessage(String messageId) ;


    /**
     * 更新信息
     * @param taskMessageUser 信息
     */
    void updateMessage(TaskMessageUser taskMessageUser);

    /**
     * 查询单个信息
     * @param messageId pipelineMessageId
     * @return message信息
     */
    @FindOne
    TaskMessageUser findOneMessage(String messageId) ;

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<TaskMessageUser> findAllMessage() ;

    @FindList
    List<TaskMessageUser> findAllMessageList(List<String> idList);

}
