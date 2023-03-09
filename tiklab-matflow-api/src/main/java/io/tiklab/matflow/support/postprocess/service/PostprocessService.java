package io.tiklab.matflow.support.postprocess.service;

import io.tiklab.join.annotation.FindAll;
import io.tiklab.join.annotation.FindList;
import io.tiklab.join.annotation.FindOne;
import io.tiklab.join.annotation.JoinProvider;
import io.tiklab.matflow.support.postprocess.model.Postprocess;

import java.util.List;
/**
 * 流水线后置处理服务接口
 */
@JoinProvider(model = Postprocess.class)
public interface PostprocessService {


    /**
     * 创建
     * @param postprocess message信息
     * @return messageId
     */
    String createPostTask(Postprocess postprocess) ;


    /**
     * 查询流水线后置任务
     * @param pipelineId 流水线id
     * @return 任务
     */
    List<Postprocess> findAllPipelinePostTask(String pipelineId);

    /**
     * 查询任务后置处理
     * @param taskId 任务id
     * @return 任务列表
     */
    List<Postprocess> findAllTaskPostTask(String taskId);


    /**
     * 删除
     * @param postprocessId 后置任务id
     */
    void deletePostTask(String postprocessId) ;


    /**
     * 更新信息
     * @param postprocess 信息
     */
    void updatePostTask(Postprocess postprocess);

    /**
     * 查询单个后置任务及详情
     * @param postprocessId 后置任务id
     * @return 后置任务及详情
     */
    Postprocess findOnePostOrTask(String postprocessId);

    /**
     * 查询单个信息
     * @param postprocessId 后置任务id
     * @return message信息
     */
    @FindOne
    Postprocess findOnePost(String postprocessId);

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
