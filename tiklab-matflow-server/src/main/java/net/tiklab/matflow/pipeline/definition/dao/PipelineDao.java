package net.tiklab.matflow.pipeline.definition.dao;
import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.dal.jpa.criterial.condition.QueryCondition;
import net.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import net.tiklab.matflow.pipeline.definition.entity.PipelineEntity;
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
    public List<PipelineEntity> findUserPipeline(StringBuilder s){
        String sql = " select p.* from pip_pipeline p";
        sql = sql.concat(" where p.id in ("+s+")");
        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(PipelineEntity.class));
    }

    //获取所有全局项目
    public List<PipelineEntity> findUserPipeline(){
        String sql = " select p.* from pip_pipeline p";
        sql = sql.concat(" where p.power = 1");
        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(PipelineEntity.class));
    }

    //获取用户收藏的流水线
    public List<PipelineEntity> findPipelineFollow(String userId, StringBuilder s){
        String sql = "select p.* from pip_pipeline p ";
        sql = sql.concat(" where p.id  "
                + " in ("+ s +")"
                + " and p.id "
                + " in (select pip_pipeline_other_follow.pipeline_id from pip_pipeline_other_follow"
                + " where pip_pipeline_other_follow.user_id  =  '"+userId+"'  )");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineEntity.class));
    }

    //获取用户收藏的流水线
    public List<PipelineEntity> findAllPipeline(StringBuilder s){
        String sql = "select p.* from pip_pipeline p ";
        sql = sql.concat(" where p.id  "
                + " in ("+ s +")");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineEntity.class));
    }


    //获取用户未收藏的流水线
    public List<PipelineEntity> findPipelineNotFollow(String userId, StringBuilder s){
        String sql = "select p.* from pip_pipeline p ";
        sql = sql.concat(" where p.id  "
                + " in ("+ s +")"
                + " and p.id "
                + " not in (select pip_pipeline_other_follow.pipeline_id from pip_pipeline_other_follow"
                + " where pip_pipeline_other_follow.user_id  =  '" + userId + "'  )");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineEntity.class));
    }

    /**
     * 根据名称模糊查询
     * @param pipelineName 查询条件
     * @return 流水线集合
     */
    public List<PipelineEntity> findLikeName(String pipelineName){
        QueryCondition queryCondition = QueryBuilders.createQuery(PipelineEntity.class)
                .like("name", pipelineName).get();
        return jpaTemplate.findList(queryCondition, PipelineEntity.class);
    }
















}
