package net.tiklab.matflow.pipeline.overview.service;

import net.tiklab.matflow.pipeline.definition.model.PipelineOverview;

public interface PipelineOverviewService {


    /**
     * 流水线执行信息统计
     * @param pipelineId 流水线id
     * @return 统计信息
     */
    PipelineOverview pipelineCensus(String pipelineId);



}
