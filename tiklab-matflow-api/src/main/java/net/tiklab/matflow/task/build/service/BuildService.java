package net.tiklab.matflow.task.build.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;

public interface BuildService {

    /**
     * 构建
     * @param pipelineProcess 构建信息
     * @return 构建状态
     */
    boolean build(PipelineProcess pipelineProcess, String configId ,int taskType);


}
