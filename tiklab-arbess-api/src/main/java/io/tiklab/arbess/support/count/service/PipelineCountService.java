package io.tiklab.arbess.support.count.service;

import io.tiklab.arbess.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.arbess.support.count.model.*;
import io.tiklab.arbess.support.count.model.*;

import java.util.List;

/**
 * 流水线统计服务接口
 */
public interface PipelineCountService {

    /**
     * 统计流水线最近的运行时间分布
     * @param countQuery 条件
     * @return 运行时间分布
     */
    List<PipelineRunDayCount> findPipelineRunTimeSpan(PipelineRunCountQuery countQuery);


    /**
     * 统计流水线最近运行时间分布
     * @param countQuery 条件
     * @return 运行时间分布
     */
    List<PipelineDayCount> findRunTimeSpan(PipelineRunCountQuery countQuery);


    /**
     * 统计流水线最近运行次数统计
     * @param countQuery 条件
     * @return 运行次数统计
     */
    List<PipelineDayResultCount> findRunResultSpan(PipelineRunCountQuery countQuery);


    /**
     * 统计流水线最近运行次数
     * @param countQuery 条件
     * @return 运行次数
     */
    PipelineDayNumberCount findRunNumberSpan(PipelineRunCountQuery countQuery);

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
     * 统计流水线最近运行结果
     * @param countQuery 条件
     * @return 运行结果
     */
    PipelineRunResultCount findRunResultCount(PipelineRunCountQuery countQuery);


    /**
     * 统计流水线最近运行结果 (全部，成功，失败，成功率)
     * @param countQuery 条件
     * @return 运行结果
     */
    List<PipelineDayRateCount> findDayRateCount(PipelineRunCountQuery countQuery);

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


    /**
     * 获取最近的天数(yyyy-MM-dd 23:59:59)
     * @param days 最近几天
     * @return 天数
     */
    List<String> findDaysFormatted(int days);

    /**
     * 获取流水线运行结果统计
     * @param query 流水线id
     * @return 运行结果
     */
    PipelineInstanceCount findPipelineInstanceCount(PipelineInstanceQuery query);

}
