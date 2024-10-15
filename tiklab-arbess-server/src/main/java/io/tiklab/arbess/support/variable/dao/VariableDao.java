package io.tiklab.arbess.support.variable.dao;

import io.tiklab.arbess.support.variable.model.Variable;
import io.tiklab.arbess.support.variable.model.VariableQuery;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.support.variable.entity.VariableEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VariableDao {

    @Autowired
    JpaTemplate jpaTemplate;


    /**
     * 创建变量
     * @param variable 变量信息
     * @return 变量id
     */
    public String createVariable(Variable variable){
        VariableEntity variableEntity = BeanMapper.map(variable, VariableEntity.class);
        return jpaTemplate.save(variableEntity, String.class);
    }

    /**
     * 删除变量
     * @param varId 变量id
     */
    public void deleteVariable(String varId){
        jpaTemplate.delete(VariableEntity.class,varId);
    }

    /**
     * 更新变量
     * @param variable 变量信息
     */
    public void updateVariable(Variable variable){
        VariableEntity variableEntity = BeanMapper.map(variable, VariableEntity.class);
        jpaTemplate.update(variableEntity);
    }

    /**
     * 查询单个变量
     * @param varId 变量id
     * @return 变量信息
     */
    public Variable findOneVariable(String varId){
        VariableEntity variableEntity = jpaTemplate.findOne(VariableEntity.class, varId);
        return BeanMapper.map(variableEntity, Variable.class);
    }

    /**
     * 查询所有变量
     * @return 变量集合
     */
    public List<Variable> findAllVariable(){
        List<VariableEntity> variableEntity = jpaTemplate.findAll(VariableEntity.class);
        return BeanMapper.mapList(variableEntity, Variable.class);
    }


    public List<VariableEntity> findVariableList(VariableQuery query){
        QueryBuilders queryBuilders = QueryBuilders.createQuery(VariableEntity.class)
                .eq("taskId",query.getTaskId())
                .eq("varKey",query.getVarKey())
                .eq("varValue",query.getVarValue());
        if (query.getTaskType() != 0){
            queryBuilders.eq("taskType", query.getTaskType());
        }

        if ( query.getType() != 0){
            queryBuilders.eq("type",  query.getType());
        }
        QueryCondition queryCondition = queryBuilders
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition, VariableEntity.class);
    }


}






























