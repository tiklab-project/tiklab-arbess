package com.doublekit.pipeline.definition.dao;


import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.definition.entity.PipelineConfigureEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineConfigureDao {

    private static Logger logger = LoggerFactory.getLogger(PipelineDao.class);

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 添加流水线配置信息
     * @param pipelineConfigureEntity 配置信息
     * @return 配置信息id
     */
    public String createPipelineConfigure(PipelineConfigureEntity pipelineConfigureEntity){
        return jpaTemplate.save(pipelineConfigureEntity,String.class);
    }

    /**
     * 删除流水线配置
     * @param id 配置id
     */
    public void deletePipelineConfigure(String id){
        jpaTemplate.delete(PipelineConfigureEntity.class,id);
    }

    /**
     * 更新流水线配置
     * @param pipelineConfigureEntity 配置信息
     */
    public void updatePipelineConfigure(PipelineConfigureEntity pipelineConfigureEntity){
        jpaTemplate.update(pipelineConfigureEntity);
    }

    /**
     * 查询配置信息
     * @param id 查询id
     * @return 配置信息
     */
    public PipelineConfigureEntity selectPipelineConfigure(String id){
        return jpaTemplate.findOne(PipelineConfigureEntity.class, id);
    }

    /**
     * 查询所有配置信息
     * @return 配置信息集合
     */
    public List<PipelineConfigureEntity> selectAllPipelineConfigure(){
        return jpaTemplate.findAll(PipelineConfigureEntity.class);
    }
}
