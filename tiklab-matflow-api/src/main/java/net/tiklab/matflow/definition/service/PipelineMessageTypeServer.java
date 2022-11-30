package net.tiklab.matflow.definition.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.PipelineMessageType;

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
