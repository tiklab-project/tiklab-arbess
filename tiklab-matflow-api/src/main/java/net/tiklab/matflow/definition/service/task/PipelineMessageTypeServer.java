package net.tiklab.matflow.definition.service.task;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.task.PipelineMessage;
import net.tiklab.matflow.definition.model.task.PipelineMessageType;

import java.util.List;

@JoinProvider(model = PipelineMessageType.class)
public interface PipelineMessageTypeServer {

    /**
     * 创建
     * @param pipelineMessageType message信息
     * @return messageId
     */
    String createMessage(PipelineMessageType pipelineMessageType) ;

    /**
     * 查询消息发送信息
     * @param configId 配置id
     * @return 信息
     */
    PipelineMessage findConfigMessage(String configId);

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
    PipelineMessageType findMessage(String configId);

    /**
     * 删除
     * @param messageId messageId
     */
    void deleteMessage(String messageId) ;


    /**
     * 更新信息
     * @param pipelineMessageType 信息
     */
    void updateMessage(PipelineMessageType pipelineMessageType);

    /**
     * 查询单个信息
     * @param messageId pipelineMessageId
     * @return message信息
     */
    @FindOne
    PipelineMessageType findOneMessage(String messageId) ;

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<PipelineMessageType> findAllMessage() ;

    @FindList
    List<PipelineMessageType> findAllMessageList(List<String> idList);
    
    
}
