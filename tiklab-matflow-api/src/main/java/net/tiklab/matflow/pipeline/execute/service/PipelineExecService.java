package net.tiklab.matflow.pipeline.execute.service;

import net.tiklab.matflow.pipeline.execute.model.TaskRunLog;

/**
 * 流水线运行服务接口
 */
public interface PipelineExecService {

    /**
     * 开始构建
     * @param pipelineId 流水线id
     * @param startWAy 执行方式（1.手动执行 2.定时器触发）
     * @return 开始构建（true:开始运行 false:正在运行）
     */
    boolean  start(String pipelineId,int startWAy);

    /**
     * 查询流水线运行信息
     * @param pipelineId 流水线id
     * @return 运行信息
     */
    TaskRunLog findPipelineRunMessage(String pipelineId);

    /**
     * 判断运行状态(1.未运行 2.正在运行)
     * @param pipelineId 流水线Id
     * @return 状态
     */
    int findPipelineState(String pipelineId);

    /**
     * 关闭运行
     * @param pipelineId 流水线id
     */
    void killInstance(String pipelineId);



}


































