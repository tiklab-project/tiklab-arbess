package io.tiklab.arbess.support.variable.service;


import io.tiklab.arbess.support.variable.model.SystemVariable;
import io.tiklab.arbess.support.variable.model.SystemVariableQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.JoinProvider;

import java.util.List;

/**
 * 流水线条件服务接口
 */
@JoinProvider(model = SystemVariable.class)
public interface SystemVariableService {

    /**
     * 创建变量
     * @param variable 变量信息
     * @return 变量id
     */
    String createSystemVariable(SystemVariable variable);

    /**
     * 删除变量
     * @param id 变量id
     */
    void deleteSystemVariable(String id);

    /**
     * 更新变量
     * @param variable 变量信息
     */
    void updateSystemVariable(SystemVariable variable);

    /**
     * 查询单个变量
     * @param id 变量id
     * @return 变量信息
     */
    SystemVariable findSystemVariable(String id);

    /**
     * 获取所以变量
     * @return 变量
     */
    List<SystemVariable> findAllSystemVariable();

    /**
     * 条件查询变量
     * @param query 条件
     * @return 变量列表
     */
    List<SystemVariable> findSystemVariableList(SystemVariableQuery query);

    /**
     * 分页条件查询变量
     * @param query 条件
     * @return 变量列表
     */
    Pagination<SystemVariable> findSystemVariablePage(SystemVariableQuery query);

}
