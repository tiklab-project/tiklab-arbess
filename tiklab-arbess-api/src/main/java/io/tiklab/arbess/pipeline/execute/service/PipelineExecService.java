package io.tiklab.arbess.pipeline.execute.service;

import io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;

/**
 * 流水线运行服务接口
 */
public interface PipelineExecService {

    /**
     * 开始构建
     *
     * @param runMsg 流水线id
     // * @param startWAy   执行方式（1.手动执行 2.定时器触发）
     * @return 开始构建（true:开始运行 false:正在运行）
     */
    PipelineInstance start(PipelineRunMsg runMsg);

    /**
     * 停止流水线运行
     * @param pipelineId 流水线id
     */
    void stop(String pipelineId);

    /**
     * 保持运行
     * @param pipelineId 流水线id
     */
    void keepOn(String pipelineId);




}


































