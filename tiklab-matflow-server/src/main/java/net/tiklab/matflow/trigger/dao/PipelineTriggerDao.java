package net.tiklab.matflow.trigger.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.trigger.entity.PipelineTriggerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineTriggerDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineTriggerEntity 后置配置
     * @return TriggerConfigId
     */
    public String createTriggerConfig(PipelineTriggerEntity pipelineTriggerEntity){
        return jpaTemplate.save(pipelineTriggerEntity,String.class);
    }

    /**
     * 删除TriggerConfig
     * @param TriggerConfigId TriggerConfigId
     */
    public void deleteTriggerConfig(String TriggerConfigId){
        jpaTemplate.delete(PipelineTriggerEntity.class,TriggerConfigId);
    }

    /**
     * 更新TriggerConfig
     * @param pipelineTriggerEntity 更新信息
     */
    public void updateTriggerConfig(PipelineTriggerEntity pipelineTriggerEntity){
        jpaTemplate.update(pipelineTriggerEntity);
    }

    /**
     * 查询单个后置配置
     * @param TriggerConfigId TriggerConfigId
     * @return 后置配置
     */
    public PipelineTriggerEntity findOneTriggerConfig(String TriggerConfigId){
        return jpaTemplate.findOne(PipelineTriggerEntity.class,TriggerConfigId);
    }

    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<PipelineTriggerEntity> findAllTriggerConfig(){
        return jpaTemplate.findAll(PipelineTriggerEntity.class);
    }


    public List<PipelineTriggerEntity> findAllTriggerConfigList(List<String> idList){
        return jpaTemplate.findList(PipelineTriggerEntity.class,idList);
    }

}
