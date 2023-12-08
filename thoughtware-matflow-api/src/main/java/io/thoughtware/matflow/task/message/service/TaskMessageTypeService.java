package io.thoughtware.matflow.task.message.service;

import io.thoughtware.matflow.task.message.model.TaskMessageType;
import io.thoughtware.join.annotation.FindAll;
import io.thoughtware.join.annotation.FindList;
import io.thoughtware.join.annotation.FindOne;
import io.thoughtware.join.annotation.JoinProvider;

import java.util.List;

/**
 * 任务消息类型服务接口
 */
@JoinProvider(model = TaskMessageType.class)
public interface TaskMessageTypeService {

    /**
     * 创建
     * @param taskMessageType message信息
     */
    void createMessage(TaskMessageType taskMessageType) ;

    /**
     * 删除任务
     * @param taskId 任务id
     */
    void deleteAllMessage(String taskId);


    /**
     * 判断是否存在消息配置
     * @return 不存在的消息配置
     */
    List<String> messageSendType();


    /**
     * 根据配置id查询消息类型
     * @param taskId 配置id
     * @return 消息
     */
    TaskMessageType findMessage(String taskId);

    /**
     * 删除
     * @param messageId messageId
     */
    void deleteMessage(String messageId) ;


    /**
     * 更新信息
     * @param taskMessageType 信息
     */
    void updateMessage(TaskMessageType taskMessageType);

    /**
     * 查询单个信息
     * @param messageId pipelineMessageId
     * @return message信息
     */
    @FindOne
    TaskMessageType findOneMessage(String messageId) ;

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<TaskMessageType> findAllMessage() ;

    @FindList
    List<TaskMessageType> findAllMessageList(List<String> idList);
    
    
}
