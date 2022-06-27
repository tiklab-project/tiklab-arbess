package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jdbc.JdbcTemplate;
import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.instance.entity.PipelineActionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineActionDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param pipelineActionEntity 操作
     * @return 操作id
     */
    public  String createAction(PipelineActionEntity pipelineActionEntity){

        return jpaTemplate.save(pipelineActionEntity, String.class);
    }

    /**
     * 删除操作
     * @param actionId 操作id
     */
    public  void deleteAction(String actionId){
        jpaTemplate.delete(PipelineActionEntity.class, actionId);
    }

    /**
     * 更新操作
     * @param pipelineActionEntity 更新信息
     */
    public  void updateAction(PipelineActionEntity pipelineActionEntity){
        jpaTemplate.update(pipelineActionEntity);
    }

    /**
     * 查询单个操作信息87
     * @param actionId 操作id
     * @return 操作信息
     */
    public  PipelineActionEntity findOneAction(String actionId){
        return jpaTemplate.findOne(PipelineActionEntity.class,actionId);
    }

    /**
     * 查询所有操作
     * @return 操作集合
     */
    public  List<PipelineActionEntity> findAllAction(){
        return jpaTemplate.findAll(PipelineActionEntity.class);
    }


    public List<PipelineActionEntity> findAllActionList(List<String> idList){
        return jpaTemplate.findList(PipelineActionEntity.class,idList);
    }

    /**
     * 获取用户动态
     * @param s 条件
     * @return 动态信息
     */
    public List<PipelineActionEntity> findUserAction(String s){
        String sql = "select * from pipeline_action ";
        sql = sql.concat(s);
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(PipelineActionEntity.class));
    }







}
