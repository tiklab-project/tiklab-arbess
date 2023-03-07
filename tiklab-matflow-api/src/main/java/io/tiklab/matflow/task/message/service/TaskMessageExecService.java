package io.tiklab.matflow.task.message.service;

import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.task.task.model.Tasks;

/**
 * 流水线执行消息任务服务接口
 */
public interface TaskMessageExecService {


    /**
     * 发送消息
     * @param pipeline 流水线id
     * @param task 任务信息
     * @param runState 运行状态
     * @param isPipeline 是否是为流水线消息
     * @return 执行状态
     */
    boolean message(Pipeline pipeline, Tasks task, boolean runState, boolean isPipeline);

}
