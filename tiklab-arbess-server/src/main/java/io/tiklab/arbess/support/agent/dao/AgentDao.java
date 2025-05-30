package io.tiklab.arbess.support.agent.dao;

import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.support.agent.entity.AgentEntity;
import io.tiklab.arbess.support.agent.model.AgentQuery;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgentDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createAgent(AgentEntity agentEntity) {
        agentEntity.setCreateTime(PipelineUtil.date(1));
        return jpaTemplate.save(agentEntity,String.class);
    }

    public void updateAgent(AgentEntity agentEntity) {
        jpaTemplate.update(agentEntity);
    }

    public void deleteAgent(String id) {
        jpaTemplate.delete(AgentEntity.class,id);
    }

    public AgentEntity findAgent(String id) {
        return jpaTemplate.findOne(AgentEntity.class,id);
    }

    public List<AgentEntity> findAgentList(){
        return jpaTemplate.findAll(AgentEntity.class);
    }

    public List<AgentEntity> findAgentList(AgentQuery agentQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(AgentEntity.class)
                .eq("address", agentQuery.getAddress())
                .eq("ip", agentQuery.getIp())
                .eq("tenantId", agentQuery.getTenantId())
                .eq("businessType", agentQuery.getBusinessType())
                .eq("displayType", agentQuery.getDisplayType())
                .like("name", agentQuery.getName())
                .orders(agentQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,AgentEntity.class);
    }

    public Pagination<AgentEntity> findAgentPage(AgentQuery agentQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(AgentEntity.class)
                .eq("address", agentQuery.getAddress())
                .eq("ip", agentQuery.getIp())
                .eq("tenantId", agentQuery.getTenantId())
                .eq("businessType", agentQuery.getBusinessType())
                .eq("displayType", agentQuery.getDisplayType())
                .like("name", agentQuery.getName())
                .orders(agentQuery.getOrderParams())
                .pagination(agentQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,AgentEntity.class);
    }


}
