package io.tiklab.arbess.task.deploy.dao;

import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.task.deploy.entity.TaskDeployInstanceEntity;
import io.tiklab.arbess.task.deploy.model.TaskDeployInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDeployInstanceDao {


    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建任务部署策略
     * @param strategyEntity strategyEntity
     * @return id
     */
    public String createTaskDeployInstance(TaskDeployInstanceEntity strategyEntity) {
        return jpaTemplate.save(strategyEntity,String.class);
    }

    /**
     * 更新任务部署策略
     * @param strategyEntity strategyEntity
     */
    public void updateTaskDeployInstance(TaskDeployInstanceEntity strategyEntity) {
        jpaTemplate.update(strategyEntity);
    }

    /**
     * 删除任务部署策略
     * @param id id
     */
    public void deleteTaskDeployInstance(String id) {
        jpaTemplate.delete(TaskDeployInstanceEntity.class,id);
    }

    /**
     * 查找任务部署策略
     * @param id id
     * @return strategyEntity
     */
    public TaskDeployInstanceEntity findTaskDeployInstance(String id) {
        return jpaTemplate.findOne(TaskDeployInstanceEntity.class,id);
    }

    /**
     * 查找任务部署策略
     * @param deployInstanceQuery deployInstanceQuery
     * @return strategyEntity
     */
    public List<TaskDeployInstanceEntity> findTaskDeployInstanceList(TaskDeployInstanceQuery deployInstanceQuery) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskDeployInstanceEntity.class)
                .eq("taskInstanceId", deployInstanceQuery.getTaskInstanceId())
                .get();
        return jpaTemplate.findList(queryCondition,TaskDeployInstanceEntity.class);
    }


    /**
     * 查找所以任务部署策略
     * @return strategyEntity
     */
    public List<TaskDeployInstanceEntity> findAllTaskDeployInstance() {
        return jpaTemplate.findAll(TaskDeployInstanceEntity.class);
    }














}
