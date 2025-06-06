package io.tiklab.arbess.support.message.dao;

import io.tiklab.arbess.support.message.entity.TaskMessageEntity;
import io.tiklab.arbess.support.message.model.TaskMessageQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Repository
public class TaskMessageDao {


    @Autowired
    JpaTemplate jpaTemplate;

    public String createTaskMessage(TaskMessageEntity messageEntity){
        return jpaTemplate.save(messageEntity, String.class);
    }

    public void deleteTaskMessage(String id){
        jpaTemplate.delete(TaskMessageEntity.class, id);
    }

    public void updateTaskMessage(TaskMessageEntity messageEntity){
        jpaTemplate.update(messageEntity);
    }

    public TaskMessageEntity findTaskMessage(String id){
        return jpaTemplate.findOne(TaskMessageEntity.class, id);
    }

    public List<TaskMessageEntity> findAllTaskMessage(){
        List<TaskMessageEntity> entityList = jpaTemplate.findAll(TaskMessageEntity.class);
        if (Objects.isNull(entityList) || entityList.isEmpty()){
            return new ArrayList<>();
        }
        return entityList;
    }


    public List<TaskMessageEntity> findTaskMessageList(List<String> idList){
        List<TaskMessageEntity> entityList = jpaTemplate.findList(TaskMessageEntity.class, idList);
        if (Objects.isNull(entityList) || entityList.isEmpty()){
            return new ArrayList<>();
        }
        return entityList;
    }


    public List<TaskMessageEntity> findTaskMessageList(TaskMessageQuery messageQuery){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskMessageEntity.class)
                .eq("pipelineId", messageQuery.getPipelineId())
                .eq("taskId", messageQuery.getTaskId())
                .eq("type", messageQuery.getType())
                .orders(messageQuery.getOrderParams())
                .get();
        List<TaskMessageEntity> entityList = jpaTemplate.findList(queryCondition, TaskMessageEntity.class);
        if (Objects.isNull(entityList) || entityList.isEmpty()){
            return new ArrayList<>();
        }
        return entityList;
    }


    public Pagination<TaskMessageEntity> findTaskMessagePage(TaskMessageQuery messageQuery){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskMessageEntity.class)
                .eq("pipelineId", messageQuery.getPipelineId())
                .eq("taskId", messageQuery.getTaskId())
                .eq("type", messageQuery.getType())
                .orders(messageQuery.getOrderParams())
                .pagination(messageQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition, TaskMessageEntity.class);
    }

}

















