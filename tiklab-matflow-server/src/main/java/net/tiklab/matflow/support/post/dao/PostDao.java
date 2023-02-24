package net.tiklab.matflow.support.post.dao;

import net.tiklab.dal.jpa.JpaTemplate;
import net.tiklab.matflow.support.post.entity.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDao {

    @Autowired
    JpaTemplate jpaTemplate;

    /**
     * 创建
     * @param postEntity 后置配置
     * @return postId
     */
    public String createPost(PostEntity postEntity){
        return jpaTemplate.save(postEntity,String.class);
    }

    /**
     * 删除post
     * @param postId postId
     */
    public void deletePost(String postId){
        jpaTemplate.delete(PostEntity.class,postId);
    }

    /**
     * 更新post
     * @param postEntity 更新信息
     */
    public void updatePost(PostEntity postEntity){
        jpaTemplate.update(postEntity);
    }

    /**
     * 查询单个后置配置
     * @param postId postId
     * @return 后置配置
     */
    public PostEntity findOnePost(String postId){
        return jpaTemplate.findOne(PostEntity.class,postId);
    }

    /**
     * 查询所有后置配置
     * @return 后置配置集合
     */
    public List<PostEntity> findAllPost(){
        return jpaTemplate.findAll(PostEntity.class);
    }


    public List<PostEntity> findAllPostList(List<String> idList){
        return jpaTemplate.findList(PostEntity.class,idList);
    }

}
