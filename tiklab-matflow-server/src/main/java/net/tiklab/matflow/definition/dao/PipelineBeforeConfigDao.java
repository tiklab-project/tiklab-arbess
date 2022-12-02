package net.tiklab.matflow.definition.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.PipelineBeforeConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineBeforeConfigDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineBeforeConfigEntity 后置配置
     * @return BeforeConfigId
     */
    public String createBeforeConfig(PipelineBeforeConfigEntity pipelineBeforeConfigEntity){
        return jpaTemplate.save(pipelineBeforeConfigEntity,String.class);
    }

    /**
     * 删除BeforeConfig
     * @param BeforeConfigId BeforeConfigId
     */
    public void deleteBeforeConfig(String BeforeConfigId){
        jpaTemplate.delete(PipelineBeforeConfigEntity.class,BeforeConfigId);
    }

    /**
     * 更新BeforeConfig
     * @param pipelineBeforeConfigEntity 更新信息
     */
    public void updateBeforeConfig(PipelineBeforeConfigEntity pipelineBeforeConfigEntity){
        jpaTemplate.update(pipelineBeforeConfigEntity);
    }

    /**
     * 查询单个后置配置
     * @param BeforeConfigId BeforeConfigId
     * @return 后置配置
     */
    public PipelineBeforeConfigEntity findOneBeforeConfig(String BeforeConfigId){
        return jpaTemplate.findOne(PipelineBeforeConfigEntity.class,BeforeConfigId);
    }

    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<PipelineBeforeConfigEntity> findAllBeforeConfig(){
        return jpaTemplate.findAll(PipelineBeforeConfigEntity.class);
    }


    public List<PipelineBeforeConfigEntity> findAllBeforeConfigList(List<String> idList){
        return jpaTemplate.findList(PipelineBeforeConfigEntity.class,idList);
    }

}
