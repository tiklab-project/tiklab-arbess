package com.doublekit.pipeline.execute.dao;

import com.doublekit.dal.jpa.JpaTemplate;

import com.doublekit.pipeline.execute.entity.PipelineDeployEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineDeployDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineDeployEntity deploy信息
     * @return deployId
     */
    public String createDeploy(PipelineDeployEntity pipelineDeployEntity){
        return jpaTemplate.save(pipelineDeployEntity,String.class);
    }

    /**
     * 删除deploy
     * @param deployId deployId
     */
    public void deleteDeploy(String deployId){
        jpaTemplate.delete(PipelineDeployEntity.class,deployId);
    }

    /**
     * 更新deploy
     * @param pipelineDeployEntity 更新信息
     */
    public void updateDeploy(PipelineDeployEntity pipelineDeployEntity){
        jpaTemplate.update(pipelineDeployEntity);
    }

    /**
     * 查询单个deploy信息
     * @param deployId deployId
     * @return deploy信息
     */
    public PipelineDeployEntity findOneDeploy(String deployId){
        return jpaTemplate.findOne(PipelineDeployEntity.class,deployId);
    }

    /**
     * 查询所有deploy信息
     * @return deploy信息集合
     */
    public List<PipelineDeployEntity> findAllDeploy(){
        return jpaTemplate.findAll(PipelineDeployEntity.class);
    }


    public List<PipelineDeployEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(PipelineDeployEntity.class,idList);
    }

}
