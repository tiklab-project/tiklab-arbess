package net.tiklab.matflow.definition.service.task;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.task.PipelineMessageUser;
import net.tiklab.matflow.definition.model.task.PipelineUserMessage;

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
     * 添加所有接收人
     * @param userMessages 接收人信息
     * @param taskId 任务id
     */
    void createAllMessage(List<PipelineUserMessage> userMessages, String taskId);


    /**
     * 查询所有发送人
     * @param taskId 任务id
     * @return 发送人
     */
    List<PipelineUserMessage> findAllUserMessage(String taskId);


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
