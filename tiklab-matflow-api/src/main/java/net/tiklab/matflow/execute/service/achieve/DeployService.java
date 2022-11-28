package net.tiklab.matflow.execute.service.achieve;

import net.tiklab.matflow.definition.model.PipelineDeploy;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface DeployService {

    /**
     * 部署
     * @param pipelineProcess 部署信息
     * @return 部署状态
     */
    boolean deploy(PipelineProcess pipelineProcess, PipelineDeploy pipelineDeploy);


}
