package io.tiklab.arbess.task.strategy.dao;

import io.tiklab.arbess.task.strategy.entity.TaskHostStrategyEntity;
import io.tiklab.arbess.task.strategy.model.TaskHostStrategyQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskHostStrategyDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createTaskStrategy(TaskHostStrategyEntity taskStrategy) {
        return jpaTemplate.save(taskStrategy, String.class);
    }

    public void updateTaskStrategy(TaskHostStrategyEntity taskStrategy) {
        jpaTemplate.update(taskStrategy);
    }

    public void deleteTaskStrategy(String id) {
        jpaTemplate.delete(TaskHostStrategyEntity.class, id);
    }

    public TaskHostStrategyEntity findTaskStrategy(String id) {
        return jpaTemplate.findOne(TaskHostStrategyEntity.class, id);
    }

    public List<TaskHostStrategyEntity> findTaskStrategyList(List<String> idList) {
        return jpaTemplate.findList(TaskHostStrategyEntity.class, idList);
    }

    public List<TaskHostStrategyEntity> findAllTaskStrategy() {
        return jpaTemplate.findAll(TaskHostStrategyEntity.class);
    }

    public List<TaskHostStrategyEntity> findTaskStrategyList(TaskHostStrategyQuery query) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskHostStrategyEntity.class)
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition, TaskHostStrategyEntity.class);
    }


    public Pagination<TaskHostStrategyEntity> findTaskStrategyPage(TaskHostStrategyQuery query) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskHostStrategyEntity.class)
                .orders(query.getOrderParams())
                .pagination(query.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition, TaskHostStrategyEntity.class);
    }

}













