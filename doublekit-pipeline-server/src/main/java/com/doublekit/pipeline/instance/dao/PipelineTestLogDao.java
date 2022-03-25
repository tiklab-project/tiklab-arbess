package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.instance.entity.PipelineTestLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineTestLogDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineTestLogEntity testLog信息
     * @return testLogId
     */
    public String createTestLog(PipelineTestLogEntity pipelineTestLogEntity){
        return jpaTemplate.save(pipelineTestLogEntity,String.class);
    }


    /**
     * 删除testLog
     * @param testLogId testLogId
     */
    public void deleteTestLog(String testLogId){
        jpaTemplate.delete(PipelineTestLogEntity.class,testLogId);
    }


    /**
     * 更新testLog
     * @param pipelineTestLogEntity 更新信息
     */
    public void updateTestLog(PipelineTestLogEntity pipelineTestLogEntity){
        jpaTemplate.update(pipelineTestLogEntity);
    }

    /**
     * 查询单个testLog信息
     * @param testLogId testLogId
     * @return testLog信息
     */
    public PipelineTestLogEntity findOneTestLog(String testLogId){
        return jpaTemplate.findOne(PipelineTestLogEntity.class,testLogId);
    }

    /**
     * 查询所有testLog信息
     * @return testLog信息集合
     */
    public List<PipelineTestLogEntity> findAllTestLog(){
        return jpaTemplate.findAll(PipelineTestLogEntity.class);
    }


    public List<PipelineTestLogEntity> findAllTestLogList(List<String> idList){
        return jpaTemplate.findList(PipelineTestLogEntity.class,idList);
    }
}
