package net.tiklab.matflow.definition.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.PipelineMessageTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineMessageTypeDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineMessageTypeEntity message信息
     * @return messageId
     */
    public String createMessage(PipelineMessageTypeEntity pipelineMessageTypeEntity){
        return jpaTemplate.save(pipelineMessageTypeEntity,String.class);
    }

    /**
     * 删除message
     * @param messageId messageId
     */
    public void deleteMessage(String messageId){
        jpaTemplate.delete(PipelineMessageTypeEntity.class,messageId);
    }

    /**
     * 更新message
     * @param pipelineMessageTypeEntity 更新信息
     */
    public void updateMessage(PipelineMessageTypeEntity pipelineMessageTypeEntity){
        jpaTemplate.update(pipelineMessageTypeEntity);
    }

    /**
     * 查询单个message信息
     * @param messageId messageId
     * @return message信息
     */
    public PipelineMessageTypeEntity findOneMessage(String messageId){
        return jpaTemplate.findOne(PipelineMessageTypeEntity.class,messageId);
    }

    /**
     * 查询所有message信息
     * @return message信息集合
     */
    public List<PipelineMessageTypeEntity> findAllMessage(){
        return jpaTemplate.findAll(PipelineMessageTypeEntity.class);
    }


    public List<PipelineMessageTypeEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(PipelineMessageTypeEntity.class,idList);
    }
    
    
}
