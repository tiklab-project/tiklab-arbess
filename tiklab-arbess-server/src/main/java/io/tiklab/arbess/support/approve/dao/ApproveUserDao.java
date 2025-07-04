package io.tiklab.arbess.support.approve.dao;

import io.tiklab.arbess.support.approve.entity.ApproveUserEntity;
import io.tiklab.arbess.support.approve.model.ApproveUserQuery;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApproveUserDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createApproveUser(ApproveUserEntity approveUserEntity) {
        approveUserEntity.setCreateTime(PipelineUtil.date(1));
        return jpaTemplate.save(approveUserEntity,String.class);
    }

    public void updateApproveUser(ApproveUserEntity approveUserEntity) {
        jpaTemplate.update(approveUserEntity);
    }

    public void deleteApproveUser(String id) {
        jpaTemplate.delete(ApproveUserEntity.class,id);
    }

    public ApproveUserEntity findApproveUser(String id) {
        return jpaTemplate.findOne(ApproveUserEntity.class,id);
    }

    public List<ApproveUserEntity> findApproveUserList(){
        return jpaTemplate.findAll(ApproveUserEntity.class);
    }

    public List<ApproveUserEntity> findApproveUserList(ApproveUserQuery approveUserQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ApproveUserEntity.class)
                .eq("status", approveUserQuery.getStatus())
                .eq("userId", approveUserQuery.getUserId())
                .eq("approveId", approveUserQuery.getApproveId())
                .eq("pipelineId", approveUserQuery.getPipelineId())
                .orders(approveUserQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ApproveUserEntity.class);
    }

    public Pagination<ApproveUserEntity> findApproveUserPage(ApproveUserQuery approveUserQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(ApproveUserEntity.class)
                .eq("status", approveUserQuery.getStatus())
                .eq("userId", approveUserQuery.getUserId())
                .eq("approveId", approveUserQuery.getApproveId())
                .eq("pipelineId", approveUserQuery.getPipelineId())
                .orders(approveUserQuery.getOrderParams())
                .pagination(approveUserQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ApproveUserEntity.class);
    }


}
