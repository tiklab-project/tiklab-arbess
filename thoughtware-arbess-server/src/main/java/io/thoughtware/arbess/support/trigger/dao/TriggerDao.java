package io.thoughtware.arbess.support.trigger.dao;

import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.arbess.support.trigger.entity.TriggerEntity;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.arbess.support.trigger.model.TriggerQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class TriggerDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param triggerEntity 后置配置
     * @return TriggerId
     */
    public String createTrigger(TriggerEntity triggerEntity){
        return jpaTemplate.save(triggerEntity,String.class);
    }

    /**
     * 删除Trigger
     * @param TriggerId TriggerId
     */
    public void deleteTrigger(String TriggerId){
        jpaTemplate.delete(TriggerEntity.class,TriggerId);
    }

    /**
     * 更新Trigger
     * @param triggerEntity 更新信息
     */
    public void updateTrigger(TriggerEntity triggerEntity){
        jpaTemplate.update(triggerEntity);
    }

    /**
     * 查询单个后置配置
     * @param TriggerId TriggerId
     * @return 后置配置
     */
    public TriggerEntity findOneTrigger(String TriggerId){
        return jpaTemplate.findOne(TriggerEntity.class,TriggerId);
    }

    /**
     *  查询定时任务
     * @param triggerQuery 查询条件
     * @return 定时任务
     */
    public List<TriggerEntity> findTriggerList(TriggerQuery triggerQuery){
        QueryCondition queryCondition = QueryBuilders.createQuery(TriggerEntity.class)
                .eq("pipelineId", triggerQuery.getPipelineId())
                .eq("state", triggerQuery.getState())
                .get();
        List<TriggerEntity> triggerEntities = jpaTemplate.findList(queryCondition, TriggerEntity.class);
        if (triggerEntities == null || triggerEntities.isEmpty()){
            return Collections.emptyList();
        }
        return  triggerEntities;
    }

    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<TriggerEntity> findAllTrigger(){
        return jpaTemplate.findAll(TriggerEntity.class);
    }


    public List<TriggerEntity> findAllTriggerList(List<String> idList){
        return jpaTemplate.findList(TriggerEntity.class,idList);
    }

}
