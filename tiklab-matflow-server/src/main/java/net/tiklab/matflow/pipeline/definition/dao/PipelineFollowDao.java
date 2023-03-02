package net.tiklab.matflow.pipeline.definition.dao;

import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.pipeline.definition.entity.PipelineFollowEntity;
import net.tiklab.matflow.pipeline.definition.model.PipelineFollow;
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
    public List<PipelineFollow> findUserFollowPipeline(String userId, String pipelineId){
        String sql = "select pip_pipeline_other_follow.* from pip_pipeline_other_follow ";
        sql = sql.concat(" where pip_pipeline_other_follow.user_id  = '"+userId+"' "
                + " and pip_pipeline_other_follow.pipeline_id  = '"+pipelineId+"' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineFollow.class));
    }











}
