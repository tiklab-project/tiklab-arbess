package com.doublekit.pipeline.definition.dao;


import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.dal.jpa.criterial.condition.QueryCondition;
import com.doublekit.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import com.doublekit.pipeline.definition.entity.PipelineEntity;
import com.doublekit.user.user.entity.DmUserEntity;
import com.doublekit.user.user.model.DmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * pipelineDao
 */

@Repository
public class PipelineDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线
     * @param pipelineEntity 流水线信息
     * @return 流水线id
     */
    public String createPipeline(PipelineEntity pipelineEntity){
        return jpaTemplate.save(pipelineEntity, String.class);
    }

    /**
     * 删除流水线
     * @param id 流水线id
     */
    public void deletePipeline(String id){
        jpaTemplate.delete(PipelineEntity.class,id);
    }

    /**
     * 更新流水线
     * @param pipelineEntity 更新后流水线信息
     */
    public void updatePipeline(PipelineEntity pipelineEntity){

        jpaTemplate.update(pipelineEntity);

    }

    /**
     * 查询单个流水线
     * @param id 流水线id
     * @return 流水线信息
     */
    public PipelineEntity findPipeline(String id){
        return jpaTemplate.findOne(PipelineEntity.class,id);
    }

    /**
     * 查询所有流水线
     * @return 流水线列表
     */
    public List<PipelineEntity> findAllPipeline(){
        return jpaTemplate.findAll(PipelineEntity.class);
    }


    public List<PipelineEntity> findAllPipelineList(List<String> idList){
        return jpaTemplate.findList(PipelineEntity.class,idList);
    }

    //获取用户拥有的流水线
    public List<PipelineEntity> findUserPipeline(String userId){
        String sql = "select p.* from orc_dm_user d,pipeline p";
        sql = sql.concat(" where (d.domain_id  COLLATE utf8mb4_general_ci ) = (p.pipeline_id COLLATE utf8mb4_general_ci ) "
                + " and  (d.user_id COLLATE utf8mb4_general_ci ) =  (? COLLATE utf8mb4_general_ci ) ");
        return jpaTemplate.getJdbcTemplate().query(sql, new String[]{userId}, new BeanPropertyRowMapper(PipelineEntity.class));
    }



    /**
     * 根据名称模糊查询
     * @param pipelineName 查询条件
     * @return 流水线集合
     */
    public List<PipelineEntity> findName(String pipelineName){
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineEntity.class)
                .like("pipelineName", pipelineName).get();
        return jpaTemplate.findList(queryCondition,PipelineEntity.class);
    }

    //查询拥有此流水线的用户
    public List<DmUserEntity> findPipelineUser(String PipelineId){
        QueryCondition queryCondition = QueryBuilders.createQuery(DmUserEntity.class)
                .eq("domainId",PipelineId)
                .get();
        return jpaTemplate.findList(queryCondition, DmUserEntity.class);
    }
















}
