package net.tiklab.matflow.pipeline.definition.dao;


import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.pipeline.definition.entity.PipelineStagesTaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流水线顺序配置
 */

@Repository
public class PipelineStagesTaskDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineStagesTaskEntity 后置配置
     * @return StagesTaskId
     */
    public String createStagesTask(PipelineStagesTaskEntity pipelineStagesTaskEntity){
        return jpaTemplate.save(pipelineStagesTaskEntity,String.class);
    }

    /**
     * 删除StagesTask
     * @param StagesTaskId StagesTaskId
     */
    public void deleteStagesTask(String StagesTaskId){
        jpaTemplate.delete(PipelineStagesTaskEntity.class,StagesTaskId);
    }

    /**
     * 更新StagesTask
     * @param pipelineStagesTaskEntity 更新信息
     */
    public void updateStagesTask(PipelineStagesTaskEntity pipelineStagesTaskEntity){
        jpaTemplate.update(pipelineStagesTaskEntity);
    }

    /**
     * 查询单个后置配置
     * @param StagesTaskId StagesTaskId
     * @return 后置配置
     */
    public PipelineStagesTaskEntity findOneStagesTask(String StagesTaskId){
        return jpaTemplate.findOne(PipelineStagesTaskEntity.class,StagesTaskId);
    }

    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<PipelineStagesTaskEntity> findAllStagesTask(){
        return jpaTemplate.findAll(PipelineStagesTaskEntity.class);
    }


    public List<PipelineStagesTaskEntity> findAllStagesTaskList(List<String> idList){
        return jpaTemplate.findList(PipelineStagesTaskEntity.class,idList);
    }
}
