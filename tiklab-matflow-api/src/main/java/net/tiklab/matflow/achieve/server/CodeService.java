package net.tiklab.matflow.achieve.server;

import net.tiklab.matflow.execute.model.PipelineProcess;

public interface CodeService {

    /**
     * 源码管理
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
    // boolean clone(PipelineProcess pipelineProcess, PipelineTasks config);
    boolean clone(PipelineProcess pipelineProcess,  String configId ,int taskType);
}
