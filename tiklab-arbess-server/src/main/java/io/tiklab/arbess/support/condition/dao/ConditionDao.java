package io.tiklab.arbess.support.condition.dao;

import io.tiklab.arbess.support.condition.model.Condition;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.arbess.support.condition.entity.ConditionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class ConditionDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createCond(Condition condition){
        ConditionEntity conditionEntity = BeanMapper.map(condition, ConditionEntity.class);
        return jpaTemplate.save(conditionEntity, String.class);
    };

    public void deleteCond(String condId){
        jpaTemplate.delete(ConditionEntity.class,condId);
    };

    public void updateCond(Condition condition){
        ConditionEntity conditionEntity = BeanMapper.map(condition, ConditionEntity.class);
        jpaTemplate.update(conditionEntity);
    };

    public Condition findOneCond(String condId){
        ConditionEntity conditionEntity = jpaTemplate.findOne(ConditionEntity.class, condId);
        return BeanMapper.map(conditionEntity, Condition.class);
    };

    public List<Condition> findAllCond(){
        List<ConditionEntity> conditionEntities = jpaTemplate.findAll(ConditionEntity.class);
        List<Condition> conditionList = BeanMapper.mapList(conditionEntities, Condition.class);
        if (conditionList == null ){
            return Collections.emptyList();
        }
        return conditionList;
    };

}

















































