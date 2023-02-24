package net.tiklab.matflow.support.variable.service;

import net.tiklab.matflow.support.variable.model.Variable;

import java.util.List;

public interface VariableService {

    /**
     * 创建变量
     * @param variable 变量信息
     * @return 变量id
     */
    String createVariable(Variable variable);

    /**
     * 删除变量
     * @param varId 变量id
     */
    void deleteVariable(String varId);

    /**
     * 更新变量
     * @param variable 变量信息
     */
    void updateVariable(Variable variable);

    /**
     * 查询单个变量
     * @param varId 变量id
     * @return 变量信息
     */
    Variable findOneVariable(String varId);


    /**
     * 查询流水线所有变量
     * @param taskId 任务id
     * @return 变量
     */
    List<Variable> findAllVariable(String taskId);

}
