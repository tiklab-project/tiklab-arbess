package io.thoughtware.matflow.task.message.dao;

import io.thoughtware.matflow.task.message.entity.TaskMessageUserEntity;
import io.thoughtware.dal.jpa.JpaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskMessageUserDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param taskMessageUserEntity message信息
     * @return messageId
     */
    public String createMessage(TaskMessageUserEntity taskMessageUserEntity){
        return jpaTemplate.save(taskMessageUserEntity,String.class);
    }

    /**
     * 删除message
     * @param messageId messageId
     */
    public void deleteMessage(String messageId){
        jpaTemplate.delete(TaskMessageUserEntity.class,messageId);
    }

    /**
     * 更新message
     * @param taskMessageUserEntity 更新信息
     */
    public void updateMessage(TaskMessageUserEntity taskMessageUserEntity){
        jpaTemplate.update(taskMessageUserEntity);
    }

    /**
     * 查询单个message信息
     * @param messageId messageId
     * @return message信息
     */
    public TaskMessageUserEntity findOneMessage(String messageId){
        return jpaTemplate.findOne(TaskMessageUserEntity.class,messageId);
    }

    /**
     * 查询所有message信息
     * @return message信息集合
     */
    public List<TaskMessageUserEntity> findAllMessage(){
        return jpaTemplate.findAll(TaskMessageUserEntity.class);
    }


    public List<TaskMessageUserEntity> findAllMessageUserList(List<String> idList){
        return jpaTemplate.findList(TaskMessageUserEntity.class,idList);
    }


}
