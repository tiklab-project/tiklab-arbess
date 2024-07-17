package io.thoughtware.matflow.support.count.service;

import io.thoughtware.matflow.support.count.model.PipelineRunCount;
import io.thoughtware.matflow.support.count.model.PipelineRunCountQuery;
import io.thoughtware.matflow.support.count.model.PipelineRunDayCount;
import io.thoughtware.matflow.support.count.model.PipelineRunResultCount;

import java.util.List;

public interface PipelineCountService {

    /**
     * 统计流水线最近的运行时间分布
     * @param countQuery 条件
     * @return 运行时间分布
     */
    List<PipelineRunDayCount> findPipelineRunTimeSpan(PipelineRunCountQuery countQuery);


    /**
     * 统计流水线最近运行信息
     * @param countQuery 条件
     * @return 运行信息
     */
    List<PipelineRunCount> findPipelineRunCount(PipelineRunCountQuery countQuery);


    /**
     * 流水线运行结果统计
     * @param countQuery 条件
     * @return 运行结果
     */
    List<PipelineRunResultCount> findPipelineRunResultCount(PipelineRunCountQuery countQuery);

}
