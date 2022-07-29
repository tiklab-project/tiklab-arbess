package com.tiklab.matflow.definition.dao;



import com.tiklab.dal.jpa.JpaTemplate;
import com.tiklab.dal.jpa.criterial.condition.QueryCondition;
import com.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import com.tiklab.matflow.definition.entity.MatFlowEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * matFlowDao
 */

@Repository
public class MatFlowDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线
     * @param matFlowEntity 流水线信息
     * @return 流水线id
     */
    public String createMatFlow(MatFlowEntity matFlowEntity){
        return jpaTemplate.save(matFlowEntity, String.class);
    }

    /**
     * 删除流水线
     * @param id 流水线id
     */
    public void deleteMatFlow(String id){
        jpaTemplate.delete(MatFlowEntity.class,id);
    }

    /**
     * 更新流水线
     * @param matFlowEntity 更新后流水线信息
     */
    public void updateMatFlow(MatFlowEntity matFlowEntity){

        jpaTemplate.update(matFlowEntity);

    }

    /**
     * 查询单个流水线
     * @param id 流水线id
     * @return 流水线信息
     */
    public MatFlowEntity findMatFlow(String id){
        return jpaTemplate.findOne(MatFlowEntity.class,id);
    }

    /**
     * 查询所有流水线
     * @return 流水线列表
     */
    public List<MatFlowEntity> findAllMatFlow(){
        return jpaTemplate.findAll(MatFlowEntity.class);
    }


    public List<MatFlowEntity> findAllMatFlowList(List<String> idList){
        return jpaTemplate.findList(MatFlowEntity.class,idList);
    }

    //获取用户拥有的流水线
    public List<MatFlowEntity> findUserMatFlow(StringBuilder s){
        String sql = " select p.* from matflow p";
        sql = sql.concat(" where p.matflow_id in ("+s+")");
        return jpaTemplate.getJdbcTemplate().query(sql, new BeanPropertyRowMapper(MatFlowEntity.class));
    }



    /**
     * 根据名称模糊查询
     * @param matFlowName 查询条件
     * @return 流水线集合
     */
    public List<MatFlowEntity> findName(String matFlowName){
        QueryCondition queryCondition = QueryBuilders.createQuery(MatFlowEntity.class)
                .like("matFlowName", matFlowName).get();
        return jpaTemplate.findList(queryCondition, MatFlowEntity.class);
    }
















}
