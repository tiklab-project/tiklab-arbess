package io.thoughtware.matflow.task.message.service;

import io.thoughtware.matflow.task.task.model.TaskExecMessage;

/**
 * 流水线执行消息任务服务接口
 */
public interface TaskMessageExecService {


    /**
     * 发送消息
     * @param taskExecMessage 信息
     * @return 执行状态
     */
    boolean message(TaskExecMessage taskExecMessage);

}
