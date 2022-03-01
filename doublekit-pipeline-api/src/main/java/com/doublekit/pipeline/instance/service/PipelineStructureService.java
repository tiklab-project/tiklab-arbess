package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.instance.model.PipelineLog;

public interface PipelineStructureService {

    /**
     * 开始构建
     * @param pipelineId 流水线id
     * @return 构建开始
     * @throws InterruptedException 等待超时
     */
    String  pipelineStructure(String pipelineId) throws InterruptedException;

    /**
     * 查询构建状态
     * @return 状态信息
     */
    PipelineLog selectStructureState();

    /**
     * 添加历史信息
     * @param pipelineId 流水线id
     * @return 历史部分信息
     */
    PipelineHistory pipelineHistoryTwo(String pipelineId);
}
