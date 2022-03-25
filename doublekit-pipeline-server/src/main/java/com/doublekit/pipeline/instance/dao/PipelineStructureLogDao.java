package com.doublekit.pipeline.instance.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.instance.entity.PipelineStructureLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineStructureLogDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineStructureLogEntity structureLog信息
     * @return structureLogId
     */
    public String createStructureLog(PipelineStructureLogEntity pipelineStructureLogEntity){
        return jpaTemplate.save(pipelineStructureLogEntity,String.class);
    }


    /**
     * 删除structureLog
     * @param structureLogId structureLogId
     */
    public void deleteStructureLog(String structureLogId){
        jpaTemplate.delete(PipelineStructureLogEntity.class,structureLogId);
    }


    /**
     * 更新structureLog
     * @param pipelineStructureLogEntity 更新信息
     */
    public void updateStructureLog(PipelineStructureLogEntity pipelineStructureLogEntity){
        jpaTemplate.update(pipelineStructureLogEntity);
    }

    /**
     * 查询单个structureLog信息
     * @param structureLogId structureLogId
     * @return structureLog信息
     */
    public PipelineStructureLogEntity findOneStructureLog(String structureLogId){
        return jpaTemplate.findOne(PipelineStructureLogEntity.class,structureLogId);
    }

    /**
     * 查询所有structureLog信息
     * @return structureLog信息集合
     */
    public List<PipelineStructureLogEntity> findAllStructureLog(){
        return jpaTemplate.findAll(PipelineStructureLogEntity.class);
    }


    public List<PipelineStructureLogEntity> findAllStructureLogList(List<String> idList){
        return jpaTemplate.findList(PipelineStructureLogEntity.class,idList);
    }
}
