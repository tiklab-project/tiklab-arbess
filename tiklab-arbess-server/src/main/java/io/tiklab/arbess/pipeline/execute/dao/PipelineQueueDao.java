package io.tiklab.arbess.pipeline.execute.dao;


import io.tiklab.arbess.pipeline.execute.entity.PipelineQueueEntity;
import io.tiklab.arbess.pipeline.execute.model.PipelineQueueQuery;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineQueueDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createPipelineQueue(PipelineQueueEntity pipelineQueueEntity) {
        pipelineQueueEntity.setCreateTime(PipelineUtil.date(1));
        return jpaTemplate.save(pipelineQueueEntity,String.class);
    }

    public void updatePipelineQueue(PipelineQueueEntity pipelineQueueEntity) {
        jpaTemplate.update(pipelineQueueEntity);
    }

    public void deletePipelineQueue(String id) {
        jpaTemplate.delete(PipelineQueueEntity.class,id);
    }

    public PipelineQueueEntity findPipelineQueue(String id) {
        return jpaTemplate.findOne(PipelineQueueEntity.class,id);
    }

    public List<PipelineQueueEntity> findPipelineQueueList(){
        return jpaTemplate.findAll(PipelineQueueEntity.class);
    }

    public List<PipelineQueueEntity> findPipelineQueueList(PipelineQueueQuery pipelineQueueQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineQueueEntity.class)
                .eq("status", pipelineQueueQuery.getStatus())
                .eq("userId", pipelineQueueQuery.getUserId())
                .eq("pipelineId", pipelineQueueQuery.getPipelineId())
                .eq("instanceId", pipelineQueueQuery.getInstanceId())
                .eq("agentId", pipelineQueueQuery.getAgentId())
                .orders(pipelineQueueQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,PipelineQueueEntity.class);
    }

    public Pagination<PipelineQueueEntity> findPipelineQueuePage(PipelineQueueQuery pipelineQueueQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineQueueEntity.class)
                .eq("status", pipelineQueueQuery.getStatus())
                .eq("userId", pipelineQueueQuery.getUserId())
                .eq("pipelineId", pipelineQueueQuery.getPipelineId())
                .eq("instanceId", pipelineQueueQuery.getInstanceId())
                .eq("agentId", pipelineQueueQuery.getAgentId())
                .orders(pipelineQueueQuery.getOrderParams())
                .pagination(pipelineQueueQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,PipelineQueueEntity.class);
    }


}
