package net.tiklab.matflow.task.deploy.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;

public interface TaskDeployExecService {

    /**
     * 部署
     * @param pipelineProcess 部署信息
     * @return 部署状态
     */
    boolean deploy(PipelineProcess pipelineProcess,String configId ,int taskType);


}
