package net.tiklab.matflow.pipeline.definition.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.pipeline.definition.entity.PipelineStagesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineStagesDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineStagesEntity 后置配置
     * @return StagesId
     */
    public String createStages(PipelineStagesEntity pipelineStagesEntity){
        return jpaTemplate.save(pipelineStagesEntity,String.class);
    }

    /**
     * 删除Stages
     * @param StagesId StagesId
     */
    public void deleteStages(String StagesId){
        jpaTemplate.delete(PipelineStagesEntity.class,StagesId);
    }

    /**
     * 更新Stages
     * @param pipelineStagesEntity 更新信息
     */
    public void updateStages(PipelineStagesEntity pipelineStagesEntity){
        jpaTemplate.update(pipelineStagesEntity);
    }

    /**
     * 查询单个后置配置
     * @param StagesId StagesId
     * @return 后置配置
     */
    public PipelineStagesEntity findOneStages(String StagesId){
        return jpaTemplate.findOne(PipelineStagesEntity.class,StagesId);
    }

    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<PipelineStagesEntity> findAllStages(){
        return jpaTemplate.findAll(PipelineStagesEntity.class);
    }


    public List<PipelineStagesEntity> findAllStagesList(List<String> idList){
        return jpaTemplate.findList(PipelineStagesEntity.class,idList);
    }
    
    
}
