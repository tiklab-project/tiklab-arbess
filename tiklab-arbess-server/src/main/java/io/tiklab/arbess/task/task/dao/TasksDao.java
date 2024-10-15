package io.tiklab.arbess.task.task.dao;


import io.tiklab.arbess.task.task.model.TasksQuery;
import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.dal.jpa.JpaTemplate;
import io.tiklab.dal.jpa.criterial.condition.QueryCondition;
import io.tiklab.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import io.tiklab.arbess.task.task.entity.TasksEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
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
     * @param tasksEntity 配置信息实体
     * @return 配置信息id
     */
    public String createConfigure(TasksEntity tasksEntity){
        return jpaTemplate.save(tasksEntity,String.class);
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

    public List<TasksEntity> findTaskList(TasksQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(TasksEntity.class)
                .eq("pipelineId", query.getPipelineId())
                .eq("stageId", query.getStageId())
                .eq("postprocessId", query.getPostprocessId())
                .orders(query.getOrderParams())
                .get();
        List<TasksEntity> list = jpaTemplate.findList(queryCondition, TasksEntity.class);
        list.sort(Comparator.comparing(TasksEntity::getTaskSort));
        return list;
    }

    /**
     * 查询后置任务
     * @param postId 后置任务id
     * @return 任务
     */
    public List<TasksEntity> findPostTask(String postId){
        List<Order> orderList = OrderBuilders.instance().asc("taskSort").get();
        QueryCondition queryCondition = QueryBuilders.createQuery(TasksEntity.class)
                .eq("postprocessId", postId)
                .orders(orderList)
                .get();
        return jpaTemplate.findList(queryCondition,TasksEntity.class);
    }



    public List<TasksEntity> findAllConfigureList(List<String> idList){
        return jpaTemplate.findList(TasksEntity.class,idList);
    }
}




































