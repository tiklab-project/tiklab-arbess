package net.tiklab.matflow.definition.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.PipelineMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelineMessageDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelineMessageEntity message信息
     * @return messageId
     */
    public String createMessage(PipelineMessageEntity pipelineMessageEntity){
        return jpaTemplate.save(pipelineMessageEntity,String.class);
    }

    /**
     * 删除message
     * @param messageId messageId
     */
    public void deleteMessage(String messageId){
        jpaTemplate.delete(PipelineMessageEntity.class,messageId);
    }

    /**
     * 更新message
     * @param pipelineMessageEntity 更新信息
     */
    public void updateMessage(PipelineMessageEntity pipelineMessageEntity){
        jpaTemplate.update(pipelineMessageEntity);
    }

    /**
     * 查询单个message信息
     * @param messageId messageId
     * @return message信息
     */
    public PipelineMessageEntity findOneMessage(String messageId){
        return jpaTemplate.findOne(PipelineMessageEntity.class,messageId);
    }

    /**
     * 查询所有message信息
     * @return message信息集合
     */
    public List<PipelineMessageEntity> findAllMessage(){
        return jpaTemplate.findAll(PipelineMessageEntity.class);
    }


    public List<PipelineMessageEntity> findAllCodeList(List<String> idList){
        return jpaTemplate.findList(PipelineMessageEntity.class,idList);
    }
    
    
}
