package com.doublekit.pipeline.execute.dao;

import com.doublekit.dal.jpa.JpaTemplate;
import com.doublekit.pipeline.execute.entity.PipelineStructureEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineStructureDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineStructureEntity structure信息
     * @return structureId
     */
    public String createStructure(PipelineStructureEntity pipelineStructureEntity){
        return jpaTemplate.save(pipelineStructureEntity,String.class);
    }

    /**
     * 删除
     * @param structureId structureId
     */
    public void deleteStructure(String structureId){
        jpaTemplate.delete(PipelineStructureEntity.class,structureId);
    }

    /**
     * 更新structure
     * @param pipelineStructureEntity 更新信息
     */
    public void updateStructure(PipelineStructureEntity pipelineStructureEntity){
        jpaTemplate.update(pipelineStructureEntity);
    }

    /**
     * 查询单个structure信息
     * @param structureId structureId
     * @return structure信息
     */
    public PipelineStructureEntity findOneStructure(String structureId){
        return jpaTemplate.findOne(PipelineStructureEntity.class,structureId);
    }

    /**
     * 查询所有structure信息
     * @return structure信息集合
     */
    public List<PipelineStructureEntity> findAllStructure(){
        return jpaTemplate.findAll(PipelineStructureEntity.class);
    }


    public List<PipelineStructureEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(PipelineStructureEntity.class,idList);
    }
}
