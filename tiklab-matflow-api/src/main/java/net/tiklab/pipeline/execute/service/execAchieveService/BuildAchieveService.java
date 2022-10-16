package net.tiklab.pipeline.execute.service.execAchieveService;

import net.tiklab.pipeline.definition.model.PipelineBuild;
import net.tiklab.pipeline.execute.model.PipelineProcess;

public interface BuildAchieveService {

    /**
     * 构建
     * @param pipelineProcess 构建信息
     * @return 构建状态
     */
    String build(PipelineProcess pipelineProcess,  PipelineBuild pipelineBuild);
}
