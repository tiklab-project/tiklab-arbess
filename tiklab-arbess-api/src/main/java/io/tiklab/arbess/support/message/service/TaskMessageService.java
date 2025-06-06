package io.tiklab.arbess.support.message.service;

import io.tiklab.arbess.support.message.model.TaskMessage;
import io.tiklab.arbess.support.message.model.TaskMessageQuery;
import io.tiklab.core.page.Pagination;

import java.util.List;

public interface TaskMessageService {


    /**
     * 创建任务消息
     * @param message 消息
     * @return 任务消息ID
     */
    String createTaskMessage(TaskMessage message);

    /**
     * 删除任务消息
     * @param id 任务消息ID
     */
    void deleteTaskMessage(String id);

    /**
     * 更新任务消息
     * @param message 任务消息
     */
    void updateTaskMessage(TaskMessage message);

    /**
     * 查询任务消息
     * @param id 任务消息ID
     * @return 任务消息
     */
    TaskMessage findTaskMessage(String id);


    /**
     * 查询所有任务消息
     * @return 任务消息列表
     */
    List<TaskMessage> findAllTaskMessage();

    /**
     * 查询任务消息列表
     * @param idList id列表
     * @return 任务消息列表
     */
    List<TaskMessage> findTaskMessageList(List<String> idList);


    /**
     * 查询任务消息列表
     * @param messageQuery 查询条件
     * @return 任务消息列表
     */
    List<TaskMessage> findTaskMessageList(TaskMessageQuery messageQuery);


    /**
     * 查询任务消息分页
     * @param messageQuery 查询条件
     * @return 任务消息分页
     */
    Pagination<TaskMessage> findTaskMessagePage(TaskMessageQuery messageQuery);


    /**
     * 查询消息发送类型
     * @return 消息发送类型列表
     */
    List<String> findMessageSendTypeList();

}











