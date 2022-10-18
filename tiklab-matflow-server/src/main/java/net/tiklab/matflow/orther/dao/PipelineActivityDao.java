package net.tiklab.matflow.orther.dao;


import net.tiklab.dal.jdbc.JdbcTemplate;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.orther.entity.PipelineActivityEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineActivityDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param pipelineActivityEntity 操作
     * @return 操作id
     */
    public  String createActivity(PipelineActivityEntity pipelineActivityEntity){

        return jpaTemplate.save(pipelineActivityEntity, String.class);
    }

    /**
     * 删除操作
     * @param activityId 操作id
     */
    public  void deleteActivity(String activityId){
        jpaTemplate.delete(PipelineActivityEntity.class, activityId);
    }

    /**
     * 更新操作
     * @param pipelineActivityEntity 更新信息
     */
    public  void updateActivity(PipelineActivityEntity pipelineActivityEntity){
        jpaTemplate.update(pipelineActivityEntity);
    }

    /**
     * 查询单个操作信息87
     * @param activityId 操作id
     * @return 操作信息
     */
    public PipelineActivityEntity findOneActivity(String activityId){
        return jpaTemplate.findOne(PipelineActivityEntity.class,activityId);
    }

    /**
     * 查询所有操作
     * @return 操作集合
     */
    public  List<PipelineActivityEntity> findAllActivity(){
        return jpaTemplate.findAll(PipelineActivityEntity.class);
    }


    public List<PipelineActivityEntity> findAllActivityList(List<String> idList){
        return jpaTemplate.findList(PipelineActivityEntity.class,idList);
    }

    /**
     * 获取用户动态
     * @param s 条件
     * @return 动态信息
     */
    public List<PipelineActivityEntity> findUserActivity(String s){
        String sql = "select * from pipeline_activity ";
        sql = sql.concat(s);
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineActivityEntity.class));
    }

    /**
     * 根据流水线id删除所有动态
     * @param pipelineId 流水线id
     */
    public void deleteAllActivity(String pipelineId){
        String sql = "delete from pipeline_activity ";
        sql = sql.concat(" where pipeline_activity.pipeline_id = '"+pipelineId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        jdbcTemplate.execute(sql);
    }






}
