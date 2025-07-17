package io.tiklab.arbess.support.agent.dao;


import io.tiklab.arbess.support.agent.entity.AgentRoleEntity;
import io.tiklab.arbess.support.agent.model.AgentRoleQuery;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgentRoleDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createAgentRole(AgentRoleEntity agentRoleEntity) {
        return jpaTemplate.save(agentRoleEntity,String.class);
    }

    public void updateAgentRole(AgentRoleEntity agentRoleEntity) {
        jpaTemplate.update(agentRoleEntity);
    }

    public void deleteAgentRole(String id) {
        jpaTemplate.delete(AgentRoleEntity.class,id);
    }

    public AgentRoleEntity findAgentRole(String id) {
        return jpaTemplate.findOne(AgentRoleEntity.class,id);
    }

    public List<AgentRoleEntity> findAgentRoleList(){
        return jpaTemplate.findAll(AgentRoleEntity.class);
    }

    public List<AgentRoleEntity> findAgentRoleList(List<String> idList) {
        return jpaTemplate.findList(idList,AgentRoleEntity.class);
    }

    public List<AgentRoleEntity> findAllAgentRole() {
        return jpaTemplate.findAll(AgentRoleEntity.class);
    }

    public List<AgentRoleEntity> findAgentRoleList(AgentRoleQuery agentRoleQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(AgentRoleEntity.class)
                .eq("status", agentRoleQuery.getStatus())
                .eq("type", agentRoleQuery.getType())
                .orders(agentRoleQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,AgentRoleEntity.class);
    }

    public Pagination<AgentRoleEntity> findAgentRolePage(AgentRoleQuery agentRoleQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(AgentRoleEntity.class)
                .eq("status", agentRoleQuery.getStatus())
                .eq("type", agentRoleQuery.getType())
                .orders(agentRoleQuery.getOrderParams())
                .pagination(agentRoleQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,AgentRoleEntity.class);
    }
}
