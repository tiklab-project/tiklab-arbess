package net.tiklab.pipeline.execute.service.execAchieveService;

import net.tiklab.pipeline.definition.model.PipelineConfig;
import net.tiklab.pipeline.orther.model.PipelineProcess;

public interface DeployAchieveService {

    /**
     * 部署
     * @param pipelineProcess 部署信息
     * @return 部署状态
     */
    String deploy(PipelineProcess pipelineProcess, PipelineConfig pipelineConfig);
}
