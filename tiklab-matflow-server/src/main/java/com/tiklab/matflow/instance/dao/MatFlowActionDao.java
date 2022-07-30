package com.tiklab.matflow.instance.dao;


import com.tiklab.dal.jdbc.JdbcTemplate;
import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.matflow.instance.entity.MatFlowActionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatFlowActionDao {

    @Autowired
    JpaTemplate jpaTemplate ;

    /**
     * 创建动态
     * @param matFlowActionEntity 操作
     * @return 操作id
     */
    public  String createAction(MatFlowActionEntity matFlowActionEntity){

        return jpaTemplate.save(matFlowActionEntity, String.class);
    }

    /**
     * 删除操作
     * @param actionId 操作id
     */
    public  void deleteAction(String actionId){
        jpaTemplate.delete(MatFlowActionEntity.class, actionId);
    }

    /**
     * 更新操作
     * @param matFlowActionEntity 更新信息
     */
    public  void updateAction(MatFlowActionEntity matFlowActionEntity){
        jpaTemplate.update(matFlowActionEntity);
    }

    /**
     * 查询单个操作信息87
     * @param actionId 操作id
     * @return 操作信息
     */
    public MatFlowActionEntity findOneAction(String actionId){
        return jpaTemplate.findOne(MatFlowActionEntity.class,actionId);
    }

    /**
     * 查询所有操作
     * @return 操作集合
     */
    public  List<MatFlowActionEntity> findAllAction(){
        return jpaTemplate.findAll(MatFlowActionEntity.class);
    }


    public List<MatFlowActionEntity> findAllActionList(List<String> idList){
        return jpaTemplate.findList(MatFlowActionEntity.class,idList);
    }

    /**
     * 获取用户动态
     * @param s 条件
     * @return 动态信息
     */
    public List<MatFlowActionEntity> findUserAction(String s){
        String sql = "select * from matflow_action ";
        sql = sql.concat(s);
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowActionEntity.class));
    }

    /**
     * 根据流水线id删除所有动态
     * @param matFlowId 流水线id
     */
    public void deleteAllAction(String matFlowId){
        String sql = "delete  from matflow_action ";
        sql = sql.concat(" where matflow_action.matflow_id = '"+matFlowId +"'");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        jdbcTemplate.execute(sql);
    }






}
