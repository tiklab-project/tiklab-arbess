package net.tiklab.matflow.orther.dao;



import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.PipelineEntity;
import net.tiklab.matflow.orther.entity.PipelineFollowEntity;
import net.tiklab.matflow.orther.model.PipelineFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineFollowDao {


    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 创建次数
     * @param pipelineFollowEntity 次数
     * @return 次数id
     */
    public String createFollow(PipelineFollowEntity pipelineFollowEntity){
        return jpaTemplate.save(pipelineFollowEntity, String.class);
    }

    /**
     * 删除次数
     * @param followId 次数id
     */
    public void deleteFollow(String followId){
        jpaTemplate.delete(PipelineFollowEntity.class, followId);
    }

    /**
     * 更新次数
     * @param pipelineFollowEntity 更新信息
     */
    public void updateFollow(PipelineFollowEntity pipelineFollowEntity){
        jpaTemplate.update(pipelineFollowEntity);
    }

    /**
     * 查询单个次数信息
     * @param followId 次数id
     * @return 次数信息
     */

    public PipelineFollowEntity findOneFollow(String followId){
        return jpaTemplate.findOne(PipelineFollowEntity.class,followId);
    }

    /**
     * 查询所有次数
     * @return 次数集合
     */
    public List<PipelineFollowEntity> findAllFollow(){
        return jpaTemplate.findAll(PipelineFollowEntity.class);
    }


    public List<PipelineFollowEntity> findAllFollowList(List<String> idList){
        return jpaTemplate.findList(PipelineFollowEntity.class,idList);
    }

    public List<PipelineEntity> findPipelineFollow(String userId, StringBuilder s){
        String sql = "select p.* from pipeline p ";
        sql = sql.concat(" where p.pipeline_id  "
                + " in ("+ s +")"
                + " and p.pipeline_id "
                + " in (select pip_pipeline_other_follow.pipeline_id from pip_pipeline_other_follow"
                + " where pip_pipeline_other_follow.user_id  =  '"+userId+"'  )");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineEntity.class));
    }

    public List<PipelineEntity> findPipelineNotFollow(String userId, StringBuilder s){
        String sql = "select p.* from pipeline p ";
        sql = sql.concat(" where p.pipeline_id  "
                + " in ("+ s +")"
                + " and p.pipeline_id "
                + " not in (select pip_pipeline_other_follow.pipeline_id from pip_pipeline_other_follow"
                + " where pip_pipeline_other_follow.user_id  =  '" + userId + "'  )");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineEntity.class));
    }

    /**
     *
     * @param userId 用户id
     * @param pipelineId 流水线id
     * @return 收藏信息
     */
    public List<PipelineFollow> updateFollow(String userId, String pipelineId){
        String sql = "select pip_pipeline_other_follow.* from pip_pipeline_other_follow ";
        sql = sql.concat(" where pip_pipeline_other_follow.user_id  = '"+userId+"' "
                + " and pip_pipeline_other_follow.pipeline_id  = '"+pipelineId+"' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineFollow.class));
    }











}
