package io.tiklab.arbess.task.test.dao;

import io.tiklab.arbess.task.test.entity.RelevanceTestOnEntity;
import io.tiklab.arbess.task.test.model.RelevanceTestOnQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelevanceTestOnDao {


    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param relevanceTestOnEntity relevanceTestOn信息
     * @return relevanceTestOnId
     */
    public String createRelevanceTestOn(RelevanceTestOnEntity relevanceTestOnEntity){
        return jpaTemplate.save(relevanceTestOnEntity,String.class);
    }

    /**
     * 删除
     * @param relevanceTestOnId relevanceTestOnId
     */
    public void deleteRelevanceTestOn(String relevanceTestOnId){
        jpaTemplate.delete(RelevanceTestOnEntity.class,relevanceTestOnId);
    }

    /**
     * 查询流水线的关联关系
     * @param pipelineId 流水线id
     * @return 所有关联关系
     */
    public List<RelevanceTestOnEntity> findAllRelevance(String pipelineId){
        String sql = "select * from pip_task_relevance_teston where pipeline_id = '"+pipelineId+"'";

        return jpaTemplate.getJdbcTemplate().query(sql,new BeanPropertyRowMapper(RelevanceTestOnEntity.class) );
    }

    /**
     * 获取流水线的关联关系
     * @param relevanceTestOnQuery 查询条件
     * @return 关联关系
     */
    public Pagination<RelevanceTestOnEntity> findAllRelevancePage(RelevanceTestOnQuery relevanceTestOnQuery){
        QueryBuilders builders = QueryBuilders.createQuery(RelevanceTestOnEntity.class);

        QueryCondition queryCondition = builders.eq("pipelineId", relevanceTestOnQuery.getPipelineId())
                .pagination(relevanceTestOnQuery.getPageParam())
                .orders(relevanceTestOnQuery.getOrderParams())
                .get();
        return jpaTemplate.findPage(queryCondition, RelevanceTestOnEntity.class);
    }

    /**
     * 更新relevanceTestOn
     * @param relevanceTestOnEntity 更新信息
     */
    public void updateRelevanceTestOn(RelevanceTestOnEntity relevanceTestOnEntity){
        jpaTemplate.update(relevanceTestOnEntity);
    }

    /**
     * 查询单个relevanceTestOn信息
     * @param relevanceTestOnId relevanceTestOnId
     * @return relevanceTestOn信息
     */
    public RelevanceTestOnEntity findOneRelevanceTestOn(String relevanceTestOnId){
        return jpaTemplate.findOne(RelevanceTestOnEntity.class,relevanceTestOnId);
    }

    /**
     * 查询所有relevanceTestOn信息
     * @return relevanceTestOn信息集合
     */
    public List<RelevanceTestOnEntity> findAllRelevanceTestOn(){
        return jpaTemplate.findAll(RelevanceTestOnEntity.class);
    }

    public List<RelevanceTestOnEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(RelevanceTestOnEntity.class,idList);
    }

}
