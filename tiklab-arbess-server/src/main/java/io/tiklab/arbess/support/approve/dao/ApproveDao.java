package io.tiklab.arbess.support.approve.dao;


import io.tiklab.arbess.support.approve.entity.ApproveEntity;
import io.tiklab.arbess.support.approve.model.ApproveQuery;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.utils.UuidGenerator;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApproveDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createApprove(ApproveEntity approveEntity) {
        approveEntity.setCreateTime(PipelineUtil.date(1));
        if (StringUtils.isEmpty(approveEntity.getId())){
            approveEntity.setId(UuidGenerator.getRandomIdByUUID(12));
        }
        return jpaTemplate.save(approveEntity,String.class);
    }

    public void updateApprove(ApproveEntity approveEntity) {
        jpaTemplate.update(approveEntity);
    }

    public void deleteApprove(String id) {
        jpaTemplate.delete(ApproveEntity.class,id);
    }

    public ApproveEntity findApprove(String id) {
        return jpaTemplate.findOne(ApproveEntity.class,id);
    }

    public List<ApproveEntity> findApproveList(){
        return jpaTemplate.findAll(ApproveEntity.class);
    }

    public List<ApproveEntity> findApproveList(ApproveQuery approveQuery) {
        QueryBuilders builders = QueryBuilders.createQuery(ApproveEntity.class)
                .eq("status", approveQuery.getStatus())
                .eq("userId", approveQuery.getUserId());

        if (!StringUtils.isEmpty(approveQuery.getLoginId())){
            builders = builders.like("userIds", approveQuery.getLoginId());
        }
        QueryCondition queryCondition = builders.orders(approveQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition,ApproveEntity.class);
    }

    public Pagination<ApproveEntity> findApprovePage(ApproveQuery approveQuery) {
        QueryBuilders builders = QueryBuilders.createQuery(ApproveEntity.class)
                .eq("status", approveQuery.getStatus())
                .eq("userId", approveQuery.getUserId());

        if (!StringUtils.isEmpty(approveQuery.getLoginId())){
            builders = builders.like("userIds", approveQuery.getLoginId());
        }
        QueryCondition queryCondition = builders.orders(approveQuery.getOrderParams())
                .pagination(approveQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition,ApproveEntity.class);
    }


}
