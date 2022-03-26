package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;

/**
 * 流水线构建
 */
public interface PipelineExecService {

    /**
     * 开始构建
     * @param pipelineId 流水线id
     * @return 构建开始
     * @throws InterruptedException 等待超时
     */
    int  start(String pipelineId) throws Exception;

    /**
     * 查询构建状态
     * @return 状态信息
     */
    PipelineExecLog findStructureState(String pipelineId) ;

    /**
     * 添加历史信息
     * @param pipelineId 流水线id
     * @return 历史部分信息
     */
    PipelineExecHistory addHistoryTwo(String pipelineId);
}
