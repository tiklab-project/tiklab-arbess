package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.instance.entity.PipelineDeployLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineDeployLogDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineDeployLogEntity deployLog信息
     * @return deployLogId
     */
    public String createDeployLog(PipelineDeployLogEntity pipelineDeployLogEntity){
        return jpaTemplate.save(pipelineDeployLogEntity,String.class);
    }


    /**
     * 删除deployLog
     * @param deployLogId deployLogId
     */
    public void deleteDeployLog(String deployLogId){
        jpaTemplate.delete(PipelineDeployLogEntity.class,deployLogId);
    }


    /**
     * 更新deployLog
     * @param pipelineDeployLogEntity 更新信息
     */
    public void updateDeployLog(PipelineDeployLogEntity pipelineDeployLogEntity){
        jpaTemplate.update(pipelineDeployLogEntity);
    }

    /**
     * 查询单个deployLog信息
     * @param deployLogId deployLogId
     * @return deployLog信息
     */
    public PipelineDeployLogEntity findOneDeployLog(String deployLogId){
        return jpaTemplate.findOne(PipelineDeployLogEntity.class,deployLogId);
    }

    /**
     * 查询所有deployLog信息
     * @return deployLog信息集合
     */
    public List<PipelineDeployLogEntity> findAllDeployLog(){
        return jpaTemplate.findAll(PipelineDeployLogEntity.class);
    }


    public List<PipelineDeployLogEntity> findAllDeployLogList(List<String> idList){
        return jpaTemplate.findList(PipelineDeployLogEntity.class,idList);
    }
}
