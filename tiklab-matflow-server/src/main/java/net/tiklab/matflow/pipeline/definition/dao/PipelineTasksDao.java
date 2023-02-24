package net.tiklab.matflow.pipeline.definition.dao;


import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.pipeline.definition.entity.PipelineTasksEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * PipelineConfigureDao
 */

@Repository
public class PipelineTasksDao {

    @Autowired
    JpaTemplate jpaTemplate;


    private static final Logger logger = LoggerFactory.getLogger(PipelineTasksDao.class);

    /**
     * 添加流水线配置信息
     * @param pipelineConfigureEntity 配置信息
     * @return 配置信息id
     */
    public String createConfigure(PipelineTasksEntity pipelineConfigureEntity){
        return jpaTemplate.save(pipelineConfigureEntity,String.class);
    }

    /**
     * 删除流水线配置
     * @param id 配置id
     */
    public void deleteConfigure(String id){
        jpaTemplate.delete(PipelineTasksEntity.class,id);
    }

    /**
     * 更新流水线配置
     * @param pipelineConfigureEntity 配置信息
     */
    public void updateConfigure(PipelineTasksEntity pipelineConfigureEntity){
        jpaTemplate.update(pipelineConfigureEntity);
    }

    /**
     * 查询配置信息
     * @param configureId 查询id
     * @return 配置信息
     */
    public PipelineTasksEntity findOneConfigure(String configureId){
        return jpaTemplate.findOne(PipelineTasksEntity.class, configureId);
    }


    /**
     * 查询所有配置信息
     * @return 配置信息集合
     */
    public List<PipelineTasksEntity> findAllConfigure(){
        return jpaTemplate.findAll(PipelineTasksEntity.class);
    }

    public List<PipelineTasksEntity> findAllConfigureList(List<String> idList){
        return jpaTemplate.findList(PipelineTasksEntity.class,idList);
    }
}
