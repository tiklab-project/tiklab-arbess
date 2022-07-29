package com.tiklab.matflow.instance.dao;



import com.tiklab.dal.jdbc.JdbcTemplate;
import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.matflow.definition.entity.MatFlowEntity;
import com.tiklab.matflow.instance.entity.MatFlowFollowEntity;
import com.tiklab.matflow.instance.model.MatFlowFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatFlowFollowDao {


    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 创建次数
     * @param matFlowFollowEntity 次数
     * @return 次数id
     */
    public String createFollow(MatFlowFollowEntity matFlowFollowEntity){
        return jpaTemplate.save(matFlowFollowEntity, String.class);
    }

    /**
     * 删除次数
     * @param followId 次数id
     */
    public void deleteFollow(String followId){
        jpaTemplate.delete(MatFlowFollowEntity.class, followId);
    }

    /**
     * 更新次数
     * @param matFlowFollowEntity 更新信息
     */
    public void updateFollow(MatFlowFollowEntity matFlowFollowEntity){
        jpaTemplate.update(matFlowFollowEntity);
    }

    /**
     * 查询单个次数信息
     * @param followId 次数id
     * @return 次数信息
     */

    public MatFlowFollowEntity findOneFollow(String followId){
        return jpaTemplate.findOne(MatFlowFollowEntity.class,followId);
    }

    /**
     * 查询所有次数
     * @return 次数集合
     */
    public List<MatFlowFollowEntity> findAllFollow(){
        return jpaTemplate.findAll(MatFlowFollowEntity.class);
    }


    public List<MatFlowFollowEntity> findAllFollowList(List<String> idList){
        return jpaTemplate.findList(MatFlowFollowEntity.class,idList);
    }

    public List<MatFlowEntity> findMatFlowFollow(String userId, StringBuilder s){
        String sql = "select p.* from matflow p ";
        sql = sql.concat(" where p.matflow_id  "
                + " in ("+ s +")"
                + " and p.matflow_id "
                + " in (select matflow_follow.matflow_id from matflow_follow"
                + " where matflow_follow.user_id  =  '"+userId+"'  )");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowEntity.class));
    }

    public List<MatFlowEntity> findMatFlowNotFollow(String userId, StringBuilder s){
        String sql = "select p.* from matflow p ";
        sql = sql.concat(" where p.matflow_id  "
                + " in ("+ s +")"
                + " and p.matflow_id "
                + " not in (select matflow_follow.matflow_id from matflow_follow"
                + " where matflow_follow.user_id  =  '" + userId + "'  )");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowEntity.class));
    }

    public List<MatFlowFollow> updateFollow(String userId, String matFlowId){
        String sql = "select matflow_follow.* from matflow_follow ";
        sql = sql.concat(" where matflow_follow.user_id  = '"+userId+"' "
                + " and matflow_follow.matflow_id  = '"+matFlowId+"' ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowFollow.class));
    }











}
