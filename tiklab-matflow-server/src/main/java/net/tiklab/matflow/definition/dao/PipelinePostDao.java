package net.tiklab.matflow.definition.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.definition.entity.PipelinePostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PipelinePostDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param pipelinePostEntity 后置配置
     * @return postId
     */
    public String createPost(PipelinePostEntity pipelinePostEntity){
        return jpaTemplate.save(pipelinePostEntity,String.class);
    }

    /**
     * 删除post
     * @param postId postId
     */
    public void deletePost(String postId){
        jpaTemplate.delete(PipelinePostEntity.class,postId);
    }

    /**
     * 更新post
     * @param pipelinePostEntity 更新信息
     */
    public void updatePost(PipelinePostEntity pipelinePostEntity){
        jpaTemplate.update(pipelinePostEntity);
    }

    /**
     * 查询单个后置配置
     * @param postId postId
     * @return 后置配置
     */
    public PipelinePostEntity findOnePost(String postId){
        return jpaTemplate.findOne(PipelinePostEntity.class,postId);
    }

    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<PipelinePostEntity> findAllPost(){
        return jpaTemplate.findAll(PipelinePostEntity.class);
    }


    public List<PipelinePostEntity> findAllPostList(List<String> idList){
        return jpaTemplate.findList(PipelinePostEntity.class,idList);
    }

}
