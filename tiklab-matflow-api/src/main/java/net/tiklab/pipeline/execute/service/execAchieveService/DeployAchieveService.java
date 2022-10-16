package net.tiklab.pipeline.execute.service.execAchieveService;

import net.tiklab.pipeline.definition.model.PipelineDeploy;
import net.tiklab.pipeline.execute.model.PipelineProcess;

public interface DeployAchieveService {

    /**
     * 部署
     * @param pipelineProcess 部署信息
     * @return 部署状态
     */
    String deploy(PipelineProcess pipelineProcess, PipelineDeploy pipelineDeploy);
}
