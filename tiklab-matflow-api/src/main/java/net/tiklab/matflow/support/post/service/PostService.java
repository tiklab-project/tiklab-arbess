package net.tiklab.matflow.support.post.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.support.post.model.Post;

import java.util.List;

@JoinProvider(model = Post.class)
public interface PostService {


    /**
     * 创建
     * @param post message信息
     * @return messageId
     */
    String createPostTask(Post post) ;


    /**
     * 查询配置
     * @param taskId 流水线id
     * @return 配置
     */
     List<Object> findAllPostTask(String taskId);

    /**
     * 删除
     * @param postId messageId
     */
    void deletePostTask(String postId) ;


    /**
     * 更新信息
     * @param post 信息
     */
    void updatePostTask(Post post);

    /**
     * 查询单个信息
     * @param messageId pipelineMessageId
     * @return message信息
     */
    @FindOne
    Post findOnePost(String messageId);

    /**
     * 根据流水线id查询后置配置
     * @param taskId 流水线id
     * @return 配置
     */
     List<Post> findAllPost(String taskId);

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<Post> findAllPost() ;

    @FindList
    List<Post> findAllPostList(List<String> idList);
    
}
