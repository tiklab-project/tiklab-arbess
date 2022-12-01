package net.tiklab.matflow.definition.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.PipelineAfterConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineAfterConfigDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineAfterConfigEntity 后置配置
     * @return afterConfigId
     */
    public String createAfterConfig(PipelineAfterConfigEntity pipelineAfterConfigEntity){
        return jpaTemplate.save(pipelineAfterConfigEntity,String.class);
    }

    /**
     * 删除afterConfig
     * @param afterConfigId afterConfigId
     */
    public void deleteAfterConfig(String afterConfigId){
        jpaTemplate.delete(PipelineAfterConfigEntity.class,afterConfigId);
    }

    /**
     * 更新afterConfig
     * @param pipelineAfterConfigEntity 更新信息
     */
    public void updateAfterConfig(PipelineAfterConfigEntity pipelineAfterConfigEntity){
        jpaTemplate.update(pipelineAfterConfigEntity);
    }

    /**
     * 查询单个后置配置
     * @param afterConfigId afterConfigId
     * @return 后置配置
     */
    public PipelineAfterConfigEntity findOneAfterConfig(String afterConfigId){
        return jpaTemplate.findOne(PipelineAfterConfigEntity.class,afterConfigId);
    }

    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<PipelineAfterConfigEntity> findAllAfterConfig(){
        return jpaTemplate.findAll(PipelineAfterConfigEntity.class);
    }


    public List<PipelineAfterConfigEntity> findAllAfterConfigList(List<String> idList){
        return jpaTemplate.findList(PipelineAfterConfigEntity.class,idList);
    }

}
