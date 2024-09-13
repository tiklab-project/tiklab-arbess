package io.thoughtware.arbess.task.message.dao;

import io.thoughtware.arbess.task.message.entity.TaskMessageTypeEntity;
import io.thoughtware.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskMessageTypeDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param taskMessageTypeEntity message信息
     * @return messageId
     */
    public String createMessage(TaskMessageTypeEntity taskMessageTypeEntity){
        return jpaTemplate.save(taskMessageTypeEntity,String.class);
    }

    /**
     * 删除message
     * @param messageId messageId
     */
    public void deleteMessage(String messageId){
        jpaTemplate.delete(TaskMessageTypeEntity.class,messageId);
    }

    /**
     * 更新message
     * @param taskMessageTypeEntity 更新信息
     */
    public void updateMessage(TaskMessageTypeEntity taskMessageTypeEntity){
        jpaTemplate.update(taskMessageTypeEntity);
    }

    /**
     * 查询单个message信息
     * @param messageId messageId
     * @return message信息
     */
    public TaskMessageTypeEntity findOneMessage(String messageId){
        return jpaTemplate.findOne(TaskMessageTypeEntity.class,messageId);
    }

    /**
     * 查询所有message信息
     * @return message信息集合
     */
    public List<TaskMessageTypeEntity> findAllMessage(){
        return jpaTemplate.findAll(TaskMessageTypeEntity.class);
    }


    public List<TaskMessageTypeEntity> findAllMessageTypeList(List<String> idList){
        return jpaTemplate.findList(TaskMessageTypeEntity.class,idList);
    }
    
    
}
