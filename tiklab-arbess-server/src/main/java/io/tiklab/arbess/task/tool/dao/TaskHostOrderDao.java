package io.tiklab.arbess.task.tool.dao;

import io.tiklab.arbess.task.tool.entity.TaskHostOrderEntity;
import io.tiklab.arbess.task.tool.model.TaskHostOrderQuery;
import io.tiklab.core.page.Pagination;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskHostOrderDao {

    @Autowired
    JpaTemplate jpaTemplate;

    public String createTaskHostOrder(TaskHostOrderEntity taskHostOrder) {
        return jpaTemplate.save(taskHostOrder, String.class);
    }

    public void updateTaskHostOrder(TaskHostOrderEntity taskHostOrder) {
        jpaTemplate.update(taskHostOrder);
    }

    public void deleteTaskHostOrder(String id) {
        jpaTemplate.delete(TaskHostOrderEntity.class, id);
    }

    public TaskHostOrderEntity findTaskHostOrder(String id) {
        return jpaTemplate.findOne(TaskHostOrderEntity.class, id);
    }

    public List<TaskHostOrderEntity> findTaskHostOrderList(List<String> idList) {
        return jpaTemplate.findList(TaskHostOrderEntity.class, idList);
    }

    public List<TaskHostOrderEntity> findAllTaskHostOrder() {
        return jpaTemplate.findAll(TaskHostOrderEntity.class);
    }

    public List<TaskHostOrderEntity> findTaskHostOrderList(TaskHostOrderQuery query) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskHostOrderEntity.class)
                .orders(query.getOrderParams())
                .get();
        return jpaTemplate.findList(queryCondition, TaskHostOrderEntity.class);
    }


    public Pagination<TaskHostOrderEntity> findTaskHostOrderPage(TaskHostOrderQuery query) {
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskHostOrderEntity.class)
                .orders(query.getOrderParams())
                .pagination(query.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition, TaskHostOrderEntity.class);
    }

}













