package io.tiklab.matflow.task.task.dao;


import io.tiklab.matflow.task.task.entity.TasksEntity;
import io.tiklab.dal.jpa.JpaTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 流水线流多任务数据访问
 */

@Repository
public class TasksDao {

    @Autowired
    JpaTemplate jpaTemplate;


    private static final Logger logger = LoggerFactory.getLogger(TasksDao.class);

    /**
     * 添加流水线配置信息
     * @param pipelineConfigureEntity 配置信息实体
     * @return 配置信息id
     */
    public String createConfigure(TasksEntity pipelineConfigureEntity){
        return jpaTemplate.save(pipelineConfigureEntity,String.class);
    }

    /**
     * 删除流水线配置
     * @param id 配置id
     */
    public void deleteConfigure(String id){
        jpaTemplate.delete(TasksEntity.class,id);
    }

    /**
     * 更新流水线配置
     * @param pipelineConfigureEntity 配置信息实体
     */
    public void updateConfigure(TasksEntity pipelineConfigureEntity){
        jpaTemplate.update(pipelineConfigureEntity);
    }

    /**
     * 查询配置信息
     * @param configureId 查询id
     * @return 配置信息
     */
    public TasksEntity findOneConfigure(String configureId){
        return jpaTemplate.findOne(TasksEntity.class, configureId);
    }

    /**
     * 查询所有配置信息
     * @return 配置信息实体集合
     */
    public List<TasksEntity> findAllConfigure(){
        return jpaTemplate.findAll(TasksEntity.class);
    }

    public List<TasksEntity> findAllConfigureList(List<String> idList){
        return jpaTemplate.findList(TasksEntity.class,idList);
    }
}
