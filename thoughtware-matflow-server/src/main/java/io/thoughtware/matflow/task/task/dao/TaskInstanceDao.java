package io.thoughtware.matflow.task.task.dao;

import io.thoughtware.matflow.task.task.model.TaskInstanceQuery;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.thoughtware.matflow.task.task.entity.TaskInstanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 任务日志数据访问
 */

@Repository
public class TaskInstanceDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建流水线日志
     * @param taskInstanceEntity 流水线历史日志
     * @return 流水线日志id
     */
    public String createInstance(TaskInstanceEntity taskInstanceEntity){
        return jpaTemplate.save(taskInstanceEntity, String.class);
    }

    /**
     * 删除流水线日志
     * @param id 流水线日志id
     */
    public void deleteInstance(String id){
        jpaTemplate.delete(TaskInstanceEntity.class, id);
    }

    /**
     * 更新流水线日志
     * @param taskInstanceEntity 更新后流水线日志信息
     */
    public void updateInstance(TaskInstanceEntity taskInstanceEntity){
        jpaTemplate.update(taskInstanceEntity);
    }

    /**
     * 查询流水线日志
     * @param id 查询id
     * @return 流水线日志信息
     */
    public TaskInstanceEntity findOne(String id){
        return jpaTemplate.findOne(TaskInstanceEntity.class,id);
    }

    /**
     * 查询所有流水线日志
     * @return 流水线日志列表
     */
    public List<TaskInstanceEntity> findAllInstance(){ return jpaTemplate.findAll(TaskInstanceEntity.class); }

    public List<TaskInstanceEntity> findStageInstance(String stageId){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskInstanceEntity.class)
                .eq("stagesId", stageId)
                .get();
        return jpaTemplate.findList(queryCondition,TaskInstanceEntity.class);
    }

    public List<TaskInstanceEntity> findPipelineInstance(String instanceId){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskInstanceEntity.class)
                .eq("instanceId", instanceId)
                .get();
        return jpaTemplate.findList(queryCondition,TaskInstanceEntity.class);
    }

    public List<TaskInstanceEntity> findTaskInstanceList(TaskInstanceQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskInstanceEntity.class)
                .eq("instanceId", query.getInstanceId())
                .eq("taskType", query.getTaskType())
                .eq("runState", query.getRunState())
                .eq("stagesId", query.getStagesId())
                .eq("postprocessId", query.getPostprocessId())
                .get();
        return jpaTemplate.findList(queryCondition,TaskInstanceEntity.class);
    }


    public List<TaskInstanceEntity> findPostInstance(String postprocessId){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskInstanceEntity.class)
                .eq("postprocessId", postprocessId)
                .get();
        return jpaTemplate.findList(queryCondition,TaskInstanceEntity.class);
    }


    public List<TaskInstanceEntity> findAllInstanceList(List<String> idList){
        return jpaTemplate.findList(TaskInstanceEntity.class,idList);
    }



}
