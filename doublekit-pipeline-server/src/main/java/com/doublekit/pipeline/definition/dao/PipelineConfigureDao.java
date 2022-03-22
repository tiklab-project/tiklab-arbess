package com.doublekit.pipeline.definition.dao;


import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.definition.entity.PipelineConfigureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PipelineConfigureDao
 */

@Repository
public class PipelineConfigureDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 添加流水线配置信息
     * @param pipelineConfigureEntity 配置信息
     * @return 配置信息id
     */
    public String createConfigure(PipelineConfigureEntity pipelineConfigureEntity){
        return jpaTemplate.save(pipelineConfigureEntity,String.class);
    }

    /**
     * 删除流水线配置
     * @param id 配置id
     */
    public void deleteConfigure(String id){
        jpaTemplate.delete(PipelineConfigureEntity.class,id);
    }

    /**
     * 更新流水线配置
     * @param pipelineConfigureEntity 配置信息
     */
    public void updateConfigure(PipelineConfigureEntity pipelineConfigureEntity){
        jpaTemplate.update(pipelineConfigureEntity);
    }

    /**
     * 查询配置信息
     * @param id 查询id
     * @return 配置信息
     */
    public PipelineConfigureEntity findConfigure(String id){
        return jpaTemplate.findOne(PipelineConfigureEntity.class, id);
    }

    /**
     * 查询所有配置信息
     * @return 配置信息集合
     */
    public List<PipelineConfigureEntity> findAllConfigure(){
        return jpaTemplate.findAll(PipelineConfigureEntity.class);
    }

    public List<PipelineConfigureEntity> findAllConfigureList(List<String> idList){

        return jpaTemplate.findList(PipelineConfigureEntity.class,idList);
    }
}
