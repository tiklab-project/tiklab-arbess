package io.tiklab.arbess.support.agent.service;

import io.tiklab.core.page.Pagination;
import io.tiklab.arbess.support.agent.model.Agent;
import io.tiklab.arbess.support.agent.model.AgentQuery;

import java.util.List;

/**
 * 代理服务接口
 */
public interface AgentService {

    /**
     * 初始化默认环境
     * @param agent 环境
     */
    void initAgent(Agent agent) ;

    /**
     * 更新agent状态
     * @param agentQuery 状态信息
     */
    void updateAgentStatus(AgentQuery agentQuery);

    /**
     * 创建环境
     * @param agent 环境信息
     * @return ID
     */
    String createAgent(Agent agent) ;

    /**
     * 更新环境
     * @param agent 环境信息
     */
    void updateAgent(Agent agent);

    /**
     * 获取默认执行环境
     * @return 执行环境
     */
    Agent findDefaultAgent();

    /**
     * 更新默认的执行环境
     * @param id 更新ID
     */
    void updateDefaultAgent(String id);

    /**
     * 删除环境
     * @param id 环境ID
     */
    void deleteAgent(String id);

    /**
     * 查询流水线执行环境
     * @param id 环境ID
     * @return 环境
     */
    Agent findAgent(String id);

    /**
     * 条件查询环流水线执行环境
     * @param agentQuery 条件
     * @return 执行环境列表
     */
    List<Agent> findAgentList(AgentQuery agentQuery) ;

    /**
     * 分页条件查询环流水线执行环境
     * @param agentQuery 条件
     * @return 执行环境列表
     */
    Pagination<Agent> findAgentPage(AgentQuery agentQuery);




}
