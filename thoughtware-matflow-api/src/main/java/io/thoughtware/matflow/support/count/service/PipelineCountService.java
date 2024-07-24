package io.thoughtware.matflow.support.count.service;

import io.thoughtware.matflow.support.count.model.*;

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


    /**
     * 统计单个流水线日志类型分布
     * @param pipelineId 流水线id
     * @return 日志类型分布
     */
    List<PipelineLogTypeCount> findPipelineLogTypeCount(String pipelineId);

    /**
     * 统计单个流水线日志用户分布
     * @param pipelineId 流水线id
     * @return 日志用户分布
     */
    List<PipelineLogUserCount> findPipelineLogUserCount(String pipelineId);


    /**
     * 统计流水线日志用户分布
     * @return 日志用户分布
     */
    List<PipelineLogUserCount> findLogUserCount();

    /**
     * 统计流水线日志类型分布
     * @return 日志类型分布
     */
    List<PipelineLogTypeCount> findLogTypeCount();

    /**
     * 统计流水线概况
     * @param pipelineId 流水线id
     * @return 概况
     */
    PipelineSurveyCount findPipelineSurveyCount(String pipelineId);

    /**
     * 统计流水线运行结果
     * @param pipelineId 流水线id
     * @return 运行结果
     */
    PipelineSurveyResultCount findPipelineSurveyResultCount(String pipelineId);

    /**
     * 统计流水线概况
     * @return 概况
     */
    PipelineSurveyCount findSurveyCount();


    /**
     * 统计流水线运行结果
     * @return 运行结果
     */
    PipelineSurveyResultCount findSurveyResultCount();

}
