package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.execute.model.PipelineRun;

import java.util.List;

/**
 * 流水线构建
 */
public interface PipelineExecService {

    /**
     * 开始构建
     * @param pipelineId 流水线id
     * @return 构建开始
     */
    boolean  start(String pipelineId,int startWAy);

    List<PipelineRun> pipelineRunStatus(String pipelineId);


    /**
     * 判断运行状态
     * @param pipelineId 流水线ID
     * @return 状态
     */
    int findPipelineState(String pipelineId);

    /**
     * 关闭运行
     * @param pipelineId 流水线id
     */
    void killInstance(String pipelineId);



}
