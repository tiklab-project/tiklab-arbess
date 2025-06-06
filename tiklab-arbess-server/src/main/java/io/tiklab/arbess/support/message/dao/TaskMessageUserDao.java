package io.tiklab.arbess.support.message.dao;

import io.tiklab.arbess.support.message.entity.TaskMessageUserEntity;
import io.tiklab.arbess.support.message.model.TaskMessageUserQuery;
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
public class TaskMessageUserDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param messageUserEntity message信息
     * @return messageId
     */
    public String createMessageUser(TaskMessageUserEntity messageUserEntity){
        return jpaTemplate.save(messageUserEntity,String.class);
    }

    /**
     * 删除message
     * @param id messageId
     */
    public void deleteMessageUser(String id){
        jpaTemplate.delete(TaskMessageUserEntity.class,id);
    }

    /**
     * 更新message
     * @param messageUserEntity 更新信息
     */
    public void updateMessageUser(TaskMessageUserEntity messageUserEntity){
        jpaTemplate.update(messageUserEntity);
    }

    /**
     * 查询单个message信息
     * @param id messageId
     * @return message信息
     */
    public TaskMessageUserEntity findMessageUser(String id){
        return jpaTemplate.findOne(TaskMessageUserEntity.class,id);
    }

    /**
     * 查询所有message信息
     * @return message信息集合
     */
    public List<TaskMessageUserEntity> findAllMessageUser(){
        return jpaTemplate.findAll(TaskMessageUserEntity.class);
    }


    public List<TaskMessageUserEntity> findMessageUserList(List<String> idList){
        return jpaTemplate.findList(TaskMessageUserEntity.class,idList);
    }


    public List<TaskMessageUserEntity> findMessageUserList(TaskMessageUserQuery UserQuery){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskMessageUserEntity.class)
                .eq("messageId", UserQuery.getMessageId())
                .eq("userId", UserQuery.getUserId())
                .orders(UserQuery.getOrderParams())
                .get();
        List<TaskMessageUserEntity> UserEntityList = jpaTemplate.findList(queryCondition, TaskMessageUserEntity.class);
        if (Objects.isNull(UserEntityList)){
            return new ArrayList<>();
        }
        return UserEntityList;
    }

    public Pagination<TaskMessageUserEntity> findMessageUserPage(TaskMessageUserQuery UserQuery){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskMessageUserEntity.class)
                .eq("messageId", UserQuery.getMessageId())
                .eq("userId", UserQuery.getUserId())
                .orders(UserQuery.getOrderParams())
                .pagination(UserQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition, TaskMessageUserEntity.class);
    }


}
