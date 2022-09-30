package net.tiklab.pipeline.execute.service.execAchieveService;

import net.tiklab.pipeline.definition.model.PipelineConfig;
import net.tiklab.pipeline.orther.model.PipelineProcess;

public interface BuildAchieveService {

    /**
     * 构建
     * @param pipelineProcess 构建信息
     * @return 构建状态
     */
    String build(PipelineProcess pipelineProcess, PipelineConfig pipelineConfig);
}
