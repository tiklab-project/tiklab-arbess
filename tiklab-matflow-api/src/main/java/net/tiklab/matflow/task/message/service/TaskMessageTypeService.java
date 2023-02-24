package net.tiklab.matflow.task.message.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.message.model.TaskMessage;
import net.tiklab.matflow.task.message.model.TaskMessageType;

import java.util.List;

@JoinProvider(model = TaskMessageType.class)
public interface TaskMessageTypeService {

    /**
     * 创建
     * @param taskMessageType message信息
     * @return messageId
     */
    String createMessage(TaskMessageType taskMessageType) ;

    /**
     * 查询消息发送信息
     * @param configId 配置id
     * @return 信息
     */
    TaskMessage findConfigMessage(String configId);

    /**
     * 删除任务
     * @param configId 配置id
     */
    void deleteAllMessage(String configId);


    /**
     * 根据配置id查询消息类型
     * @param configId 配置id
     * @return 消息
     */
    TaskMessageType findMessage(String configId);

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
