package io.tiklab.arbess.support.agent.service;

import io.tiklab.arbess.support.agent.model.AgentRole;
import io.tiklab.arbess.support.agent.model.AgentRoleQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.FindOne;

import java.util.List;

/**
 * 代理agent规则接口
 */
public interface AgentRoleService {

    
    /**
     * 创建agent规则
     * @param agentRole agent规则信息
     * @return ID
     */
    String createAgentRole(AgentRole agentRole) ;

    /**
     * 更新agent规则
     * @param agentRole agent规则信息
     */
    void updateAgentRole(AgentRole agentRole);
    
    /**
     * 删除agent规则
     * @param id agent规则ID
     */
    void deleteAgentRole(String id);

    /**
     * 查询流水线执行agent规则
     * @param id agent规则ID
     * @return agent规则
     */
    @FindOne
    AgentRole findAgentRole(String id);

    /**
     * 条件查询环流水线执行agent规则
     * @param agentRoleQuery 条件
     * @return 执行agent规则列表
     */
    List<AgentRole> findAgentRoleList(AgentRoleQuery agentRoleQuery) ;

    /**
     * 条件查询环流水线执行agent规则
     * @param agentRoleIdList id列表
     * @return 执行agent规则列表
     */
    @FindList
    List<AgentRole> findAgentRoleList(List<String> agentRoleIdList) ;

    /**
     * 查询所有流水线执行agent规则
     * @return 执行agent规则列表
     */
    @FindAll
    List<AgentRole> findAllAgentRole() ;

    /**
     * 分页条件查询环流水线执行agent规则
     * @param agentRoleQuery 条件
     * @return 执行agent规则列表
     */
    Pagination<AgentRole> findAgentRolePage(AgentRoleQuery agentRoleQuery);




}
