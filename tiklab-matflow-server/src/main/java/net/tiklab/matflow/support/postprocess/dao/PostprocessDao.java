package net.tiklab.matflow.support.postprocess.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.support.postprocess.entity.PostprocessEntity;
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
