package io.tiklab.arbess.support.variable.service;

import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.support.variable.model.VariableQuery;
import io.tiklab.core.page.Pagination;

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
     * 获取所以变量
     * @return 变量
     */
    List<Variable> findAllVariable();

    /**
     * 条件查询变量
     * @param query 条件
     * @return 变量列表
     */
    List<Variable> findVariableList(VariableQuery query);


    Pagination<Variable> findVariablePage(VariableQuery query);


    // 克隆变量
    void cloneVariable(String id,String cloneId);

}
