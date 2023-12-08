package io.thoughtware.matflow.support.variable.service;

import io.thoughtware.matflow.support.variable.model.Variable;
import io.thoughtware.matflow.support.variable.model.VariableQuery;

import java.util.List;
/**
 * 流水线条件服务接口
 */
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
     * 替换变量
     * @param pipelineId 流水线id
     * @param taskId 任务id
     * @param order 需要替换的内容
     * @return 替换后的内容
     */
    String replaceVariable(String pipelineId,String taskId,String order);

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


    List<Variable> findVariableList(VariableQuery query);

    // 克隆变量
    void cloneVariable(String id,String cloneId);

}
