package net.tiklab.matflow.task.code.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;

public interface TaskCodeExecService {

    /**
     * 源码管理
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
    boolean clone(PipelineProcess pipelineProcess,  String configId ,int taskType);


}
