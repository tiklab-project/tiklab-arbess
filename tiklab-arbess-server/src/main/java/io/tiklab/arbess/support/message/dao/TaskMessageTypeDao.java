package io.tiklab.arbess.support.message.dao;

import io.tiklab.arbess.support.message.entity.TaskMessageTypeEntity;
import io.tiklab.arbess.support.message.model.TaskMessageTypeQuery;
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
public class TaskMessageTypeDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param messageTypeEntity message信息
     * @return messageId
     */
    public String createMessageType(TaskMessageTypeEntity messageTypeEntity){
        return jpaTemplate.save(messageTypeEntity,String.class);
    }

    /**
     * 删除message
     * @param id messageId
     */
    public void deleteMessageType(String id){
        jpaTemplate.delete(TaskMessageTypeEntity.class,id);
    }

    /**
     * 更新message
     * @param messageTypeEntity 更新信息
     */
    public void updateMessageType(TaskMessageTypeEntity messageTypeEntity){
        jpaTemplate.update(messageTypeEntity);
    }

    /**
     * 查询单个message信息
     * @param id messageId
     * @return message信息
     */
    public TaskMessageTypeEntity findMessageType(String id){
        return jpaTemplate.findOne(TaskMessageTypeEntity.class,id);
    }

    /**
     * 查询所有message信息
     * @return message信息集合
     */
    public List<TaskMessageTypeEntity> findAllMessageType(){
        return jpaTemplate.findAll(TaskMessageTypeEntity.class);
    }


    public List<TaskMessageTypeEntity> findMessageTypeList(List<String> idList){
        return jpaTemplate.findList(TaskMessageTypeEntity.class,idList);
    }


    public List<TaskMessageTypeEntity> findMessageTypeList(TaskMessageTypeQuery typeQuery){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskMessageTypeEntity.class)
                .eq("messageId", typeQuery.getMessageId())
                .eq("sendType", typeQuery.getSendType())
                .orders(typeQuery.getOrderParams())
                .get();
        List<TaskMessageTypeEntity> typeEntityList = jpaTemplate.findList(queryCondition, TaskMessageTypeEntity.class);
        if (Objects.isNull(typeEntityList)){
            return new ArrayList<>();
        }
        return typeEntityList;
    }

    public Pagination<TaskMessageTypeEntity> findMessageTypePage(TaskMessageTypeQuery typeQuery){
        QueryCondition queryCondition = QueryBuilders.createQuery(TaskMessageTypeEntity.class)
                .eq("messageId", typeQuery.getMessageId())
                .eq("sendType", typeQuery.getSendType())
                .orders(typeQuery.getOrderParams())
                .pagination(typeQuery.getPageParam())
                .get();
        return jpaTemplate.findPage(queryCondition, TaskMessageTypeEntity.class);
    }

    
}
