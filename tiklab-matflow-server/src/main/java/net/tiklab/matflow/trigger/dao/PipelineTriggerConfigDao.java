package net.tiklab.matflow.trigger.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.trigger.entity.PipelineTriggerConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineTriggerConfigDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineTriggerConfigEntity 后置配置
     * @return TriggerConfigId
     */
    public String createTriggerConfig(PipelineTriggerConfigEntity pipelineTriggerConfigEntity){
        return jpaTemplate.save(pipelineTriggerConfigEntity,String.class);
    }

    /**
     * 删除TriggerConfig
     * @param TriggerConfigId TriggerConfigId
     */
    public void deleteTriggerConfig(String TriggerConfigId){
        jpaTemplate.delete(PipelineTriggerConfigEntity.class,TriggerConfigId);
    }

    /**
     * 更新TriggerConfig
     * @param pipelineTriggerConfigEntity 更新信息
     */
    public void updateTriggerConfig(PipelineTriggerConfigEntity pipelineTriggerConfigEntity){
        jpaTemplate.update(pipelineTriggerConfigEntity);
    }

    /**
     * 查询单个后置配置
     * @param TriggerConfigId TriggerConfigId
     * @return 后置配置
     */
    public PipelineTriggerConfigEntity findOneTriggerConfig(String TriggerConfigId){
        return jpaTemplate.findOne(PipelineTriggerConfigEntity.class,TriggerConfigId);
    }

    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<PipelineTriggerConfigEntity> findAllTriggerConfig(){
        return jpaTemplate.findAll(PipelineTriggerConfigEntity.class);
    }


    public List<PipelineTriggerConfigEntity> findAllTriggerConfigList(List<String> idList){
        return jpaTemplate.findList(PipelineTriggerConfigEntity.class,idList);
    }

}
