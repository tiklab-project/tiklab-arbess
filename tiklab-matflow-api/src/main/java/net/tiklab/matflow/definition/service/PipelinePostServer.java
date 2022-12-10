package net.tiklab.matflow.definition.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.PipelinePost;

import java.util.List;

@JoinProvider(model = PipelinePost.class)
public interface PipelinePostServer {


    /**
     * 创建
     * @param pipelinePost message信息
     * @return messageId
     */
    String createPostTask(PipelinePost pipelinePost) ;


    /**
     * 查询配置
     * @param pipelineId 流水线id
     * @return 配置
     */
     List<Object> findAllPostTask(String pipelineId);

    /**
     * 删除
     * @param postId messageId
     */
    void deletePostTask(String postId) ;


    /**
     * 更新信息
     * @param pipelinePost 信息
     */
    void updatePostTask(PipelinePost pipelinePost);

    /**
     * 查询单个信息
     * @param messageId pipelineMessageId
     * @return message信息
     */
    @FindOne
    PipelinePost findOnePost(String messageId);

    /**
     * 根据流水线id查询后置配置
     * @param pipelineId 流水线id
     * @return 配置
     */
     List<PipelinePost> findAllPost(String pipelineId);

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<PipelinePost> findAllPost() ;

    @FindList
    List<PipelinePost> findAllPostList(List<String> idList);
    
}
