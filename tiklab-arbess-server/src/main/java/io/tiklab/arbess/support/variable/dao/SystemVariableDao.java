package io.tiklab.arbess.support.variable.dao;

import io.tiklab.arbess.support.variable.entity.SystemVariableEntity;
import io.tiklab.arbess.support.variable.model.SystemVariable;
import io.tiklab.arbess.support.variable.model.SystemVariableQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.toolkit.beans.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SystemVariableDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建变量
     * @param systemVariable 变量信息
     * @return 变量id
     */
    public String createSystemVariable(SystemVariable systemVariable){
        SystemVariableEntity systemVariableEntity = BeanMapper.map(systemVariable, SystemVariableEntity.class);
        return jpaTemplate.save(systemVariableEntity, String.class);
    }

    /**
     * 删除变量
     * @param varId 变量id
     */
    public void deleteSystemVariable(String varId){
        jpaTemplate.delete(SystemVariableEntity.class,varId);
    }

    /**
     * 更新变量
     * @param systemVariable 变量信息
     */
    public void updateSystemVariable(SystemVariable systemVariable){
        SystemVariableEntity systemVariableEntity = BeanMapper.map(systemVariable, SystemVariableEntity.class);
        jpaTemplate.update(systemVariableEntity);
    }

    /**
     * 查询单个变量
     * @param varId 变量id
     * @return 变量信息
     */
    public SystemVariable findOneSystemVariable(String varId){
        SystemVariableEntity systemVariableEntity = jpaTemplate.findOne(SystemVariableEntity.class, varId);
        return BeanMapper.map(systemVariableEntity, SystemVariable.class);
    }

    /**
     * 查询所有变量
     * @return 变量集合
     */
    public List<SystemVariable> findAllSystemVariable(){
        List<SystemVariableEntity> systemVariableEntity = jpaTemplate.findAll(SystemVariableEntity.class);
        return BeanMapper.mapList(systemVariableEntity, SystemVariable.class);
    }


    public List<SystemVariableEntity> findSystemVariableList(SystemVariableQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(SystemVariableEntity.class)
                .like("varKey",query.getVarKey(),false)
                .eq("type",query.getType())
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition, SystemVariableEntity.class);
    }


    public Pagination<SystemVariableEntity> findSystemVariablePage(SystemVariableQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(SystemVariableEntity.class)
                .like("varKey",query.getVarKey(),false)
                .eq("type",query.getType())
                .pagination(query.getPageParam())
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findPage(queryCondition, SystemVariableEntity.class);
    }

}



