package io.tiklab.arbess.task.tool.dao;

import io.tiklab.arbess.task.tool.entity.TaskCheckPointEntity;
import io.tiklab.arbess.task.tool.model.TaskCheckPointQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskCheckPointDao {

    @Autowired
    JpaTemplate jpaTemplate;


    public String createTaskCheckPoint(TaskCheckPointEntity taskCheckPoint) {
        return jpaTemplate.save(taskCheckPoint, String.class);
    }

    public void updateTaskCheckPoint(TaskCheckPointEntity taskCheckPoint) {
        jpaTemplate.update(taskCheckPoint);
    }

    public void deleteTaskCheckPoint(String id) {
        jpaTemplate.delete(TaskCheckPointEntity.class, id);
    }

    public TaskCheckPointEntity findTaskCheckPoint(String id) {
        return jpaTemplate.findOne(TaskCheckPointEntity.class, id);
    }

    public List<TaskCheckPointEntity> findTaskCheckPointList(List<String> idList) {
        return jpaTemplate.findList(TaskCheckPointEntity.class, idList);
    }

    public List<TaskCheckPointEntity> findAllTaskCheckPoint() {
        return jpaTemplate.findAll(TaskCheckPointEntity.class);
    }

    public List<TaskCheckPointEntity> findTaskCheckPointList(TaskCheckPointQuery query) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskCheckPointEntity.class)
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition, TaskCheckPointEntity.class);
    }


    public Pagination<TaskCheckPointEntity> findTaskCheckPointPage(TaskCheckPointQuery query) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskCheckPointEntity.class)
                .orders(query.getOrderParams())
                .pagination(query.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition, TaskCheckPointEntity.class);
    }

}













