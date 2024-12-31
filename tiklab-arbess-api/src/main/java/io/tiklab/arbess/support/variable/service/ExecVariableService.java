package io.tiklab.arbess.support.variable.service;

import io.tiklab.arbess.support.variable.model.ExecVariable;

/**
 * 执行变量服务接口
 */
public interface ExecVariableService {

    /**
     * 初始化流水线变量
     * @param pipelineId 流水线ID
     * @param taskId 任务ID
     */
    void initPipelineVariable(String pipelineId,String taskId);

    /**
     * 添加执行变量
     * @param variable 执行变量
     */
    void addExecVariable(ExecVariable variable);

}
