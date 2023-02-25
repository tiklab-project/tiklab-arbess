package net.tiklab.matflow.task.artifact.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;

public interface TaskProductExecService {

    /**
     * 推送制品代码
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
     boolean product(PipelineProcess pipelineProcess, String configId ,int taskType);


}
