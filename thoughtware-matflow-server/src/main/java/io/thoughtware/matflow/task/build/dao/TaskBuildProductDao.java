package io.thoughtware.matflow.task.build.dao;

import io.thoughtware.matflow.task.build.model.TaskBuildProductQuery;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.annotation.Column;
import io.thoughtware.dal.jpa.annotation.Entity;
import io.thoughtware.dal.jpa.annotation.Id;
import io.thoughtware.dal.jpa.annotation.Table;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.matflow.task.build.entity.TaskBuildProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskBuildProductDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param taskBuildProductEntity build信息
     * @return buildId
     */
    public String createBuildProduct(TaskBuildProductEntity taskBuildProductEntity){
        return jpaTemplate.save(taskBuildProductEntity,String.class);
    }

    /**
     * 删除
     * @param buildId buildId
     */
    public void deleteBuildProduct(String buildId){
        jpaTemplate.delete(TaskBuildProductEntity.class,buildId);
    }

    /**
     * 更新build
     * @param taskBuildProductEntity 更新信息
     */
    public void updateBuildProduct(TaskBuildProductEntity taskBuildProductEntity){
        jpaTemplate.update(taskBuildProductEntity);
    }

    /**
     * 查询单个build信息
     * @param buildId buildId
     * @return build信息
     */
    public TaskBuildProductEntity findOneBuildProduct(String buildId){
        return jpaTemplate.findOne(TaskBuildProductEntity.class,buildId);
    }

    /**
     * 查询所有build信息
     * @return build信息集合
     */
    public List<TaskBuildProductEntity> findAllBuildProduct(){
        return jpaTemplate.findAll(TaskBuildProductEntity.class);
    }

    public List<TaskBuildProductEntity> findBuildProductList(TaskBuildProductQuery taskBuildProductQuery){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskBuildProductEntity.class)
                .eq("instanceId", taskBuildProductQuery.getInstanceId())
                .eq("type", taskBuildProductQuery.getType())
                .eq("key", taskBuildProductQuery.getKey())
                .get();
        return jpaTemplate.findList(queryCondition, TaskBuildProductEntity.class);
    }

    public List<TaskBuildProductEntity> findAllBuildProductList(List<String> idList){
        return jpaTemplate.findList(TaskBuildProductEntity.class,idList);
    }




}
