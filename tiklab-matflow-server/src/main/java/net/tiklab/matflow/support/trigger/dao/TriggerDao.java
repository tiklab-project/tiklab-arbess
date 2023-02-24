package net.tiklab.matflow.support.trigger.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.support.trigger.entity.TriggerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
