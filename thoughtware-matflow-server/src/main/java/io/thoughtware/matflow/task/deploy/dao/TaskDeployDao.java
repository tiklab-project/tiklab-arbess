package io.thoughtware.matflow.task.deploy.dao;


import io.thoughtware.matflow.task.deploy.entity.TaskDeployEntity;
import io.thoughtware.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDeployDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param taskDeployEntity deploy信息
     * @return deployId
     */
    public String createDeploy(TaskDeployEntity taskDeployEntity){
        return jpaTemplate.save(taskDeployEntity,String.class);
    }

    /**
     * 删除deploy
     * @param deployId deployId
     */
    public void deleteDeploy(String deployId){
        jpaTemplate.delete(TaskDeployEntity.class,deployId);
    }

    /**
     * 更新deploy
     * @param taskDeployEntity 更新信息
     */
    public void updateDeploy(TaskDeployEntity taskDeployEntity){
        jpaTemplate.update(taskDeployEntity);
    }

    /**
     * 查询单个deploy信息
     * @param deployId deployId
     * @return deploy信息
     */
    public TaskDeployEntity findOneDeploy(String deployId){
        return jpaTemplate.findOne(TaskDeployEntity.class,deployId);
    }

    /**
     * 查询所有deploy信息
     * @return deploy信息集合
     */
    public List<TaskDeployEntity> findAllDeploy(){
        return jpaTemplate.findAll(TaskDeployEntity.class);
    }


    public List<TaskDeployEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(TaskDeployEntity.class,idList);
    }

}
