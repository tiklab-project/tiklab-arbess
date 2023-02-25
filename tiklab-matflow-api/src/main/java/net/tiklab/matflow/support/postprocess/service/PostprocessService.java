package net.tiklab.matflow.support.postprocess.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.FindOne;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.support.postprocess.model.Postprocess;

import java.util.List;

@JoinProvider(model = Postprocess.class)
public interface PostprocessService {


    /**
     * 创建
     * @param postprocess message信息
     * @return messageId
     */
    String createPostTask(Postprocess postprocess) ;


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
     * @param postprocess 信息
     */
    void updatePostTask(Postprocess postprocess);

    /**
     * 查询单个信息
     * @param messageId pipelineMessageId
     * @return message信息
     */
    @FindOne
    Postprocess findOnePost(String messageId);

    /**
     * 根据流水线id查询后置配置
     * @param taskId 流水线id
     * @return 配置
     */
     List<Postprocess> findAllPost(String taskId);

    /**
     * 查询所有信息
     * @return message信息集合
     */
    @FindAll
    List<Postprocess> findAllPost() ;

    @FindList
    List<Postprocess> findAllPostList(List<String> idList);
    
}
