package com.doublekit.pipeline.instance.service;

import com.doublekit.pipeline.instance.model.PipelineExecHistory;

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
     * @param pipelineId 流水线id
     * @return 状态信息
     */
    PipelineExecHistory findInstanceState(String pipelineId) ;

    /**
     * 判断运行状态
     * @param pipelineId 流水线ID
     * @return 状态
     */
    int findState(String pipelineId);


}
