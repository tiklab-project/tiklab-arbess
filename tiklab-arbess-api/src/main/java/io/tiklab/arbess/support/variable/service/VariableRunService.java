package io.tiklab.arbess.support.variable.service;

import io.tiklab.arbess.support.variable.model.ExecVariable;

import java.util.List;

/**
 * 执行变量服务接口
 */
public interface VariableRunService {

    /**
     * 初始化流水线变量
     * @param pipelineId 流水线ID
     */
    List<ExecVariable> findPipelineVariable(String pipelineId);

    /**
     * 初始化流水线变量
     * @param pipelineId 流水线ID
     */
    List<ExecVariable> findTaskVariable(String pipelineId,String taskId);


}
