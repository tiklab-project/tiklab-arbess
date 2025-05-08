package io.tiklab.arbess.support.approve.dao;

import io.tiklab.arbess.support.approve.entity.ApprovePipelineEntity;
import io.tiklab.arbess.support.approve.model.ApprovePipelineQuery;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApprovePipelineDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createApprovePipeline(ApprovePipelineEntity approvePipelineEntity) {
        approvePipelineEntity.setCreateTime(PipelineUtil.date(1));
        return jpaTemplate.save(approvePipelineEntity,String.class);
    }

    public void updateApprovePipeline(ApprovePipelineEntity approvePipelineEntity) {
        jpaTemplate.update(approvePipelineEntity);
    }

    public void deleteApprovePipeline(String id) {
        jpaTemplate.delete(ApprovePipelineEntity.class,id);
    }

    public ApprovePipelineEntity findApprovePipeline(String id) {
        return jpaTemplate.findOne(ApprovePipelineEntity.class,id);
    }

    public List<ApprovePipelineEntity> findApprovePipelineList(){
        return jpaTemplate.findAll(ApprovePipelineEntity.class);
    }

    public List<ApprovePipelineEntity> findApprovePipelineList(ApprovePipelineQuery approvePipelineQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ApprovePipelineEntity.class)
                .eq("status", approvePipelineQuery.getStatus())
                .eq("pipelineId", approvePipelineQuery.getPipelineId())
                .eq("approveId", approvePipelineQuery.getApproveId())
                .eq("instanceId", approvePipelineQuery.getInstanceId())
                .orders(approvePipelineQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ApprovePipelineEntity.class);
    }

    public Pagination<ApprovePipelineEntity> findApprovePipelinePage(ApprovePipelineQuery approvePipelineQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ApprovePipelineEntity.class)
                .eq("status", approvePipelineQuery.getStatus())
                .eq("pipelineId", approvePipelineQuery.getPipelineId())
                .eq("approveId", approvePipelineQuery.getApproveId())
                .eq("instanceId", approvePipelineQuery.getInstanceId())
                .orders(approvePipelineQuery.getOrderParams())
                .pagination(approvePipelineQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ApprovePipelineEntity.class);
    }


}
