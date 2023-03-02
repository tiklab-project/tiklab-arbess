package net.tiklab.matflow.pipeline.overview.service;

import net.tiklab.matflow.pipeline.overview.model.PipelineOverview;

/**
 * 流水线统计服务接口
 */

public interface PipelineOverviewService {


    /**
     * 流水线执行信息统计
     * @param pipelineId 流水线id
     * @return 统计信息
     */
    PipelineOverview pipelineOverview(String pipelineId);



}
