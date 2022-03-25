package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jpa.JpaTemplate;

import com.doublekit.pipeline.instance.entity.PipelineCodeLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineCodeLogDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineCodeLogEntity codeLog信息
     * @return codeLogId
     */
    public String createCodeLog(PipelineCodeLogEntity pipelineCodeLogEntity){
        return jpaTemplate.save(pipelineCodeLogEntity,String.class);
    }


    /**
     * 删除codeLog
     * @param codeLogId codeLogId
     */
    public void deleteCodeLog(String codeLogId){
        jpaTemplate.delete(PipelineCodeLogEntity.class,codeLogId);
    }


    /**
     * 更新codeLog
     * @param pipelineCodeLogEntity 更新信息
     */
    public void updateCodeLog(PipelineCodeLogEntity pipelineCodeLogEntity){
        jpaTemplate.update(pipelineCodeLogEntity);
    }

    /**
     * 查询单个codeLog信息
     * @param codeLogId codeLogId
     * @return codeLog信息
     */
    public PipelineCodeLogEntity findOneCodeLog(String codeLogId){
        return jpaTemplate.findOne(PipelineCodeLogEntity.class,codeLogId);
    }

    /**
     * 查询所有codeLog信息
     * @return codeLog信息集合
     */
    public List<PipelineCodeLogEntity> findAllCodeLog(){
        return jpaTemplate.findAll(PipelineCodeLogEntity.class);
    }


    public List<PipelineCodeLogEntity> findAllCodeLogList(List<String> idList){
        return jpaTemplate.findList(PipelineCodeLogEntity.class,idList);
    }
}
