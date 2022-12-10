package net.tiklab.matflow.task.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.task.entity.PipelineMessageUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineMessageUserDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineMessageUserEntity message信息
     * @return messageId
     */
    public String createMessage(PipelineMessageUserEntity pipelineMessageUserEntity){
        return jpaTemplate.save(pipelineMessageUserEntity,String.class);
    }

    /**
     * 删除message
     * @param messageId messageId
     */
    public void deleteMessage(String messageId){
        jpaTemplate.delete(PipelineMessageUserEntity.class,messageId);
    }

    /**
     * 更新message
     * @param pipelineMessageUserEntity 更新信息
     */
    public void updateMessage(PipelineMessageUserEntity pipelineMessageUserEntity){
        jpaTemplate.update(pipelineMessageUserEntity);
    }

    /**
     * 查询单个message信息
     * @param messageId messageId
     * @return message信息
     */
    public PipelineMessageUserEntity findOneMessage(String messageId){
        return jpaTemplate.findOne(PipelineMessageUserEntity.class,messageId);
    }

    /**
     * 查询所有message信息
     * @return message信息集合
     */
    public List<PipelineMessageUserEntity> findAllMessage(){
        return jpaTemplate.findAll(PipelineMessageUserEntity.class);
    }


    public List<PipelineMessageUserEntity> findAllMessageUserList(List<String> idList){
        return jpaTemplate.findList(PipelineMessageUserEntity.class,idList);
    }


}
