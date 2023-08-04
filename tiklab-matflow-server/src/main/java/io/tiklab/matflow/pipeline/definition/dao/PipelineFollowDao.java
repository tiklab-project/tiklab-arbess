package io.tiklab.matflow.pipeline.definition.dao;

import io.tiklab.dal.jdbc.JdbcTemplate;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.matflow.pipeline.definition.entity.PipelineFollowEntity;
import io.tiklab.matflow.pipeline.definition.model.PipelineFollowQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流水线收藏数据访问
 */

@Repository
public class PipelineFollowDao {


    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 创建收藏
     * @param pipelineFollowEntity 收藏
     * @return 收藏id
     */
    public String createFollow(PipelineFollowEntity pipelineFollowEntity){
        return jpaTemplate.save(pipelineFollowEntity, String.class);
    }

    /**
     * 删除收藏
     * @param followId 收藏id
     */
    public void deleteFollow(String followId){
        jpaTemplate.delete(PipelineFollowEntity.class, followId);
    }

    /**
     * 更新收藏
     * @param pipelineFollowEntity 更新信息
     */
    public void updateFollow(PipelineFollowEntity pipelineFollowEntity){
        jpaTemplate.update(pipelineFollowEntity);
    }

    /**
     * 查询单个收藏信息
     * @param followId 收藏id
     * @return 收藏信息
     */
    public PipelineFollowEntity findOneFollow(String followId){
        return jpaTemplate.findOne(PipelineFollowEntity.class,followId);
    }

    /**
     * 查询所有收藏
     * @return 收藏集合
     */
    public List<PipelineFollowEntity> findAllFollow(){
        return jpaTemplate.findAll(PipelineFollowEntity.class);
    }


    public List<PipelineFollowEntity> findAllFollowList(List<String> idList){
        return jpaTemplate.findList(PipelineFollowEntity.class,idList);
    }

    /**
     * 查询用户是否收藏该流水线
     * @param userId 用户id
     * @param pipelineId 流水线id
     * @return 收藏信息
     */
    public List<PipelineFollowEntity> findOneUserFollowPipeline(String userId, String pipelineId){
        String sql = "select pip_other_follow.* from pip_other_follow ";
        sql = sql.concat(" where pip_other_follow.user_id  = '"+userId+"' "
                + " and pip_other_follow.pipeline_id  = '"+pipelineId+"' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineFollowEntity.class));
    }

    /**
     * 查询用户是藏的流水线
     * @param userId 用户id
     * @return 收藏信息
     */
    public List<PipelineFollowEntity> findUserFollowPipeline(String userId){
        String sql = "select pip_other_follow.* from pip_other_follow ";
        sql = sql.concat(" where pip_other_follow.user_id  = '"+userId+"' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineFollowEntity.class));
    }


    public List<PipelineFollowEntity> findFollowQueryList(PipelineFollowQuery followQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineFollowEntity.class)
                .eq("userId", followQuery.getUserId())
                .eq("pipelineId", followQuery.getPipelineId())
                .orders(followQuery.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition, PipelineFollowEntity.class);
    }








}
