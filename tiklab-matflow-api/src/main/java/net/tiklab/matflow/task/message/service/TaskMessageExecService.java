package net.tiklab.matflow.task.message.service;

import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.task.task.model.Tasks;

/**
 * 流水线执行消息任务服务接口
 */
public interface TaskMessageExecService {


    /**
     * 执行不同的任务实现
     * @param pipeline 配置信息
     * @return 状态
     */
    boolean message(Pipeline pipeline, Tasks task, int taskType);

}
