package net.tiklab.matflow.definition.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.PipelineMessageUser;

import java.util.List;

@JoinProvider(model = PipelineMessageUser.class)
public interface PipelineMessageUserServer {

    /**
     * 创建
     * @param pipelineMessageUser message信息
     * @return messageId
     */
    String createMessage(PipelineMessageUser pipelineMessageUser) ;

    /**
     * 删除
     * @param messageId messageId
     */
    void deleteMessage(String messageId) ;


    /**
     * 更新信息
     * @param pipelineMessageUser 信息
     */
    void updateMessage(PipelineMessageUser pipelineMessageUser);

    /**
     * 查询单个信息
     * @param messageId pipelineMessageId
     * @return message信息
     */
    @FindOne
    PipelineMessageUser findOneMessage(String messageId) ;

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<PipelineMessageUser> findAllMessage() ;

    @FindList
    List<PipelineMessageUser> findAllMessageList(List<String> idList);

}
