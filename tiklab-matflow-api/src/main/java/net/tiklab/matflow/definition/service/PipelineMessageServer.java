package net.tiklab.matflow.definition.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.PipelineMessage;

import java.util.List;

@JoinProvider(model = PipelineMessage.class)
public interface PipelineMessageServer {

    /**
     * 创建
     * @param pipelineMessage message信息
     * @return messageId
     */
    String createMessage(PipelineMessage pipelineMessage) ;

    /**
     * 删除
     * @param messageId messageId
     */
    void deleteMessage(String messageId) ;


    /**
     * 更新信息
     * @param pipelineMessage 信息
     */
    void updateMessage(PipelineMessage pipelineMessage);

    /**
     * 查询单个信息
     * @param messageId pipelineMessageId
     * @return message信息
     */
    @FindOne
    PipelineMessage findOneMessage(String messageId) ;

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<PipelineMessage> findAllMessage() ;

    @FindList
    List<PipelineMessage> findAllMessageList(List<String> idList);
    
    
}
