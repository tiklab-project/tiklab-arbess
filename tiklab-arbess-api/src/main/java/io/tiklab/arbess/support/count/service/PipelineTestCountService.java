package io.tiklab.arbess.support.count.service;

import io.tiklab.arbess.support.count.model.*;

import java.util.List;

/**
 * 流水线测试统计服务接口
 */
public interface PipelineTestCountService {

    /**
     * 查询流水线测试统计信息
     * @param pipelineId 流水线ID
     * @return 流水线测试统计信息
     */
    PipelineTestCount  findTestCount(String pipelineId);

    /**
     * 查询流水线自动化测试统计信息
     * @param pipelineId 流水线ID
     * @return 流水线测试统计信息
     */
    PipelineTestTestHuboCount findTestTestHuboCount(String pipelineId);

    /**
     * 查询流水线代码扫描统计信息
     * @param pipelineId 流水线ID
     * @return 流水线测试统计信息
     */
    PipelineTestTestHuboCount findTestCodeScanCount(String pipelineId);


}
