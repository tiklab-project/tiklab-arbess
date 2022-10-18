package net.tiklab.matflow.execute.service.execAchieveService;

import net.tiklab.matflow.definition.model.PipelineDeploy;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface DeployAchieveService {

    /**
     * 部署
     * @param pipelineProcess 部署信息
     * @return 部署状态
     */
    String deploy(PipelineProcess pipelineProcess, PipelineDeploy pipelineDeploy);
}
