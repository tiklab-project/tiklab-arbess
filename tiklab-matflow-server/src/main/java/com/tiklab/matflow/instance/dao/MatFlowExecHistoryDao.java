package com.tiklab.matflow.instance.dao;


import com.tiklab.core.page.Pagination;
import com.tiklab.dal.jdbc.JdbcTemplate;
import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.dal.jpa.criterial.condition.QueryCondition;
import com.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import com.tiklab.matflow.instance.entity.MatFlowExecHistoryEntity;
import com.tiklab.matflow.instance.model.MatFlowHistoryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatFlowExecHistoryDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线历史
     * @param matFlowExecHistoryEntity 流水线历史信息
     * @return 流水线id
     */
    public String createHistory(MatFlowExecHistoryEntity matFlowExecHistoryEntity) {

        return jpaTemplate.save(matFlowExecHistoryEntity, String.class);

    }

    /**
     * 删除流水线历史
     * @param id 流水线历史id
     */
    public void deleteHistory(String id) {
        jpaTemplate.delete(MatFlowExecHistoryEntity.class, id);
    }

    /**
     * 更新流水线历史
     * @param matFlowExecHistoryEntity 更新后流水线历史信息
     */
    public void updateHistory(MatFlowExecHistoryEntity matFlowExecHistoryEntity) {

        jpaTemplate.update(matFlowExecHistoryEntity);

    }

    /**
     * 查询流水线历史
     * @param id 查询id
     * @return 流水线历史信息
     */
    public MatFlowExecHistoryEntity findOneHistory(String id) {

        return jpaTemplate.findOne(MatFlowExecHistoryEntity.class, id);
    }


    public List<MatFlowExecHistoryEntity> findHistoryList(List<String> idList) {

        return jpaTemplate.findList(MatFlowExecHistoryEntity.class, idList);
    }

    /**
     * 查询所有流水线历史
     * @return 流水线历史列表
     */
    public List<MatFlowExecHistoryEntity> findAllHistory() {

        return jpaTemplate.findAll(MatFlowExecHistoryEntity.class);
    }

    /**
     * 筛选历史
     * @param matFlowHistoryQuery 筛选条件
     * @return 历史
     */
    public Pagination<MatFlowExecHistoryEntity> findPageHistory(MatFlowHistoryQuery matFlowHistoryQuery){
        QueryBuilders builders = QueryBuilders.createQuery(MatFlowExecHistoryEntity.class)
                    .eq("matflowId", matFlowHistoryQuery.getMatflowId());
            if (matFlowHistoryQuery.getState() != 0) {
                builders.eq("runStatus", matFlowHistoryQuery.getState());
            }
            if (matFlowHistoryQuery.getType() != 0){
                builders.eq("runWay", matFlowHistoryQuery.getType() );
            }
            if (matFlowHistoryQuery.getUserId() != null){
                builders.eq("userId", matFlowHistoryQuery.getUserId() );
            }
        QueryCondition matFlowExecHistoryList =  builders.pagination(matFlowHistoryQuery.getPageParam())
                .orders(matFlowHistoryQuery.getOrderParams())
                .get();
        return jpaTemplate.findPage(matFlowExecHistoryList, MatFlowExecHistoryEntity.class);
    }

    /**
     * 查询用户所有流水线历史
     * @return 流水线历史列表
     */
    public List<MatFlowExecHistoryEntity> findAllUserHistory(String lastTime, String nowTime, StringBuilder s) {
        String sql = "select matflow_history.* from matflow_history ";
        sql = sql.concat(" where matFlow_history.matflow_id "
                + " in("+ s +" )"
                + " and matflow_history.create_time > '"+ lastTime +"'"
                + " and matflow_history.create_time < '"+nowTime + "'"
                + " order by matflow_history.create_time desc");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowExecHistoryEntity.class));
    }


    /**
     * 根据流水线id查询所有历史
     * @param matFlowId 流水线id
     * @return 历史集合
     */
    public List<MatFlowExecHistoryEntity> findAllHistory(String matFlowId){
        String sql = "select matflow_history.* from matflow_history  ";
        sql = sql.concat(" where matflow_history.matflow_id   = '"+matFlowId+"' " +
                " and matflow_history.find_state = 1 ");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowExecHistoryEntity.class));
    }

    /**
     * 获取最近成功的历史信息
     * @param matFlowId 流水线id
     * @return 成功列表
     */
    public List<MatFlowExecHistoryEntity> findLatelySuccess(String matFlowId){
        String sql = "select matflow_history.* from matflow_history  ";
        sql = sql.concat(" where matflow_history.matflow_id   = '"+matFlowId+"' " +
                " and matflow_history.run_status = '30'  " +
                " order by matflow_history.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowExecHistoryEntity.class));
    }

    /**
     * 获取最近一次构建信息
     * @param matFlowId 流水线id
     * @return 构建信息
     */
    public List<MatFlowExecHistoryEntity> findLatelyHistory(String matFlowId){
        String sql = "select matflow_history.* from matflow_history  ";
        sql = sql.concat(" where matflow_history.matflow_id = '"+matFlowId+"' " +
                " order by matflow_history.create_time desc" +
                " limit 0 ,1");
        JdbcTemplate jdbcTemplate = jpaTemplate.getJdbcTemplate();
        return  jdbcTemplate.query(sql, new BeanPropertyRowMapper(MatFlowExecHistoryEntity.class));
    }


}