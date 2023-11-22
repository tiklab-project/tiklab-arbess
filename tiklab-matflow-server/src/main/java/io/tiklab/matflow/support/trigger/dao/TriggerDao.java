package io.tiklab.matflow.support.trigger.dao;

import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.matflow.support.trigger.entity.TriggerEntity;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.matflow.support.trigger.model.TriggerQuery;
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
     * @return TriggerConfigId
     */
    public String createTriggerConfig(TriggerEntity triggerEntity){
        return jpaTemplate.save(triggerEntity,String.class);
    }

    /**
     * 删除TriggerConfig
     * @param TriggerConfigId TriggerConfigId
     */
    public void deleteTriggerConfig(String TriggerConfigId){
        jpaTemplate.delete(TriggerEntity.class,TriggerConfigId);
    }

    /**
     * 更新TriggerConfig
     * @param triggerEntity 更新信息
     */
    public void updateTriggerConfig(TriggerEntity triggerEntity){
        jpaTemplate.update(triggerEntity);
    }

    /**
     * 查询单个后置配置
     * @param TriggerConfigId TriggerConfigId
     * @return 后置配置
     */
    public TriggerEntity findOneTriggerConfig(String TriggerConfigId){
        return jpaTemplate.findOne(TriggerEntity.class,TriggerConfigId);
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
    public List<TriggerEntity> findAllTriggerConfig(){
        return jpaTemplate.findAll(TriggerEntity.class);
    }


    public List<TriggerEntity> findAllTriggerConfigList(List<String> idList){
        return jpaTemplate.findList(TriggerEntity.class,idList);
    }

}
