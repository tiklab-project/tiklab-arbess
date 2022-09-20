package net.tiklab.matflow.orther.dao;


import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.orther.entity.MatFlowActivityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatFlowActivityDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param matFlowActivityEntity 操作
     * @return 操作id
     */
    public  String createActivity(MatFlowActivityEntity matFlowActivityEntity){

        return jpaTemplate.save(matFlowActivityEntity, String.class);
    }

    /**
     * 删除操作
     * @param activityId 操作id
     */
    public  void deleteActivity(String activityId){
        jpaTemplate.delete(MatFlowActivityEntity.class, activityId);
    }

    /**
     * 更新操作
     * @param matFlowActivityEntity 更新信息
     */
    public  void updateActivity(MatFlowActivityEntity matFlowActivityEntity){
        jpaTemplate.update(matFlowActivityEntity);
    }

    /**
     * 查询单个操作信息87
     * @param activityId 操作id
     * @return 操作信息
     */
    public MatFlowActivityEntity findOneActivity(String activityId){
        return jpaTemplate.findOne(MatFlowActivityEntity.class,activityId);
    }

    /**
     * 查询所有操作
     * @return 操作集合
     */
    public  List<MatFlowActivityEntity> findAllActivity(){
        return jpaTemplate.findAll(MatFlowActivityEntity.class);
    }


    public List<MatFlowActivityEntity> findAllActivityList(List<String> idList){
        return jpaTemplate.findList(MatFlowActivityEntity.class,idList);
    }

    /**
     * 获取用户动态
     * @param s 条件
     * @return 动态信息
     */
    public List<MatFlowActivityEntity> findUserActivity(String s){
        String sql = "select * from matflow_activity ";
        sql = sql.concat(s);
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowActivityEntity.class));
    }

    /**
     * 根据流水线id删除所有动态
     * @param matFlowId 流水线id
     */
    public void deleteAllActivity(String matFlowId){
        String sql = "delete from matflow_activity ";
        sql = sql.concat(" where matflow_activity.matflow_id = '"+matFlowId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        jdbcTemplate.execute(sql);
    }






}
