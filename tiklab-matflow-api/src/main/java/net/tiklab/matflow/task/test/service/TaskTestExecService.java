package net.tiklab.matflow.task.test.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;

public interface TaskTestExecService {

    /**
     * 测试
     * @param pipelineProcess 配置信息
     * @return 执行状态
     */
    boolean test(PipelineProcess pipelineProcess, String configId ,int taskType);


}
