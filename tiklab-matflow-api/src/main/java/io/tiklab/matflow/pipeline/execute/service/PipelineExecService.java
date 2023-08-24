package io.tiklab.matflow.pipeline.execute.service;

import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;

/**
 * 流水线运行服务接口
 */
public interface PipelineExecService {

    /**
     * 开始构建
     *
     * @param pipelineId 流水线id
     * @param startWAy   执行方式（1.手动执行 2.定时器触发）
     * @return 开始构建（true:开始运行 false:正在运行）
     */
    PipelineInstance start(String pipelineId,String userId, int startWAy);

    /**
     * 停止流水线运行
     * @param pipelineId 流水线id
     */
    void stop(String pipelineId);




}


































