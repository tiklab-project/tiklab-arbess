package io.thoughtware.arbess.support.postprocess.dao;

import io.thoughtware.arbess.support.postprocess.entity.PostprocessEntity;
import io.thoughtware.arbess.support.postprocess.model.PostprocessQuery;
import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.dal.jpa.JpaTemplate;
import io.thoughtware.dal.jpa.criterial.condition.QueryCondition;
import io.thoughtware.dal.jpa.criterial.conditionbuilder.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostprocessDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param postprocessEntity 后置配置
     * @return postId
     */
    public String createPost(PostprocessEntity postprocessEntity){
        return jpaTemplate.save(postprocessEntity,String.class);
    }

    /**
     * 删除post
     * @param postId postId
     */
    public void deletePost(String postId){
        jpaTemplate.delete(PostprocessEntity.class,postId);
    }

    /**
     * 更新post
     * @param postprocessEntity 更新信息
     */
    public void updatePost(PostprocessEntity postprocessEntity){
        jpaTemplate.update(postprocessEntity);
    }

    /**
     * 查询单个后置配置
     * @param postId postId
     * @return 后置配置
     */
    public PostprocessEntity findOnePost(String postId){
        return jpaTemplate.findOne(PostprocessEntity.class,postId);
    }


    /**
     * 获取流水线的后置任务
     * @param pipelineId 流水线id
     * @return 流水线的后置任务
     */
    public List<PostprocessEntity> findPipelinePost(String pipelineId){
        List<Order> orderList = OrderBuilders.instance().desc("taskSort").get();
        QueryCondition queryCondition = QueryBuilders.createQuery(PostprocessEntity.class)
                .eq("pipelineId", pipelineId)
                .orders(orderList)
                .get();
        return jpaTemplate.findList(queryCondition,PostprocessEntity.class);
    }


    /**
     * 获取任务的后置任务
     * @param taskId 任务id
     * @return 任务的后置任务
     */
    public List<PostprocessEntity> findTaskPost(String taskId){
        List<Order> orderList = OrderBuilders.instance().desc("taskSort").get();
        QueryCondition queryCondition = QueryBuilders.createQuery(PostprocessEntity.class)
                .eq("taskId", taskId)
                .orders(orderList)
                .get();
        return jpaTemplate.findList(queryCondition,PostprocessEntity.class);
    }


    public List<PostprocessEntity> findPostTaskList(PostprocessQuery query){
        QueryCondition queryCondition = QueryBuilders.createQuery(PostprocessEntity.class)
                .eq("taskId", query.getTaskId())
                .eq("pipelineId", query.getPipelineId())
                .get();
        return jpaTemplate.findList(queryCondition,PostprocessEntity.class);
    }


    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<PostprocessEntity> findAllPost(){
        return jpaTemplate.findAll(PostprocessEntity.class);
    }


    public List<PostprocessEntity> findAllPostList(List<String> idList){
        return jpaTemplate.findList(PostprocessEntity.class,idList);
    }

}
