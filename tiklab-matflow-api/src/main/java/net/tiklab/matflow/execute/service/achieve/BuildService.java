package net.tiklab.matflow.execute.service.achieve;

import net.tiklab.matflow.definition.model.PipelineCourseConfig;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface BuildService {

    /**
     * 构建
     * @param pipelineProcess 构建信息
     * @return 构建状态
     */
    boolean build(PipelineProcess pipelineProcess,  PipelineCourseConfig config);


}