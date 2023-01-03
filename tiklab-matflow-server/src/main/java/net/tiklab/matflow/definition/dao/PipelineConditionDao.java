package net.tiklab.matflow.definition.dao;

import net.tiklab.beans.BeanMapper;
import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.PipelineConditionEntity;
import net.tiklab.matflow.definition.model.PipelineCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class PipelineConditionDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createCond(PipelineCondition condition){
        PipelineConditionEntity conditionEntity = BeanMapper.map(condition, PipelineConditionEntity.class);
        return jpaTemplate.save(conditionEntity, String.class);
    };

    public void deleteCond(String condId){
        jpaTemplate.delete(PipelineConditionEntity.class,condId);
    };

    public void updateCond(PipelineCondition condition){
        PipelineConditionEntity conditionEntity = BeanMapper.map(condition, PipelineConditionEntity.class);
        jpaTemplate.update(conditionEntity);
    };

    public PipelineCondition findOneCond(String condId){
        PipelineConditionEntity conditionEntity = jpaTemplate.findOne(PipelineConditionEntity.class, condId);
        return BeanMapper.map(conditionEntity, PipelineCondition.class);
    };

    public List<PipelineCondition> findAllCond(){
        List<PipelineConditionEntity> conditionEntities = jpaTemplate.findAll(PipelineConditionEntity.class);
        List<PipelineCondition> conditionList = BeanMapper.mapList(conditionEntities, PipelineCondition.class);
        if (conditionList == null ){
            return Collections.emptyList();
        }
        return conditionList;
    };

}

















































