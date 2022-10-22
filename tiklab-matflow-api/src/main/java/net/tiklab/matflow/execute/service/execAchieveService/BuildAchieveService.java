package net.tiklab.matflow.execute.service.execAchieveService;

import net.tiklab.matflow.definition.model.PipelineBuild;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface BuildAchieveService {

    /**
     * 构建
     * @param pipelineProcess 构建信息
     * @return 构建状态
     */
    boolean build(PipelineProcess pipelineProcess,  PipelineBuild pipelineBuild);
}
