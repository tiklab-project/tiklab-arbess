package io.thoughtware.arbess.support.postprocess.service;

import io.thoughtware.arbess.support.postprocess.model.PostprocessInstance;

import java.util.List;

/**
 * 流水线后置处理实例服务接口
 */
public interface PostprocessInstanceService {


    /**
     * 创建后置任务执行实例
     * @param instance 实例模型
     * @return 实例id
     */
    String createPostInstance(PostprocessInstance instance);


    /**
     * 查询后置任务执行实例
     * @param postInstanceId 实例id
     * @return 后置任务实例
     */
    PostprocessInstance findPostInstance(String postInstanceId);


    /**
     * 删除实例下的所有后置处理
     * @param instanceId 实例id
     */
    void deletePipelinePostInstance(String instanceId);

    /**
     * 删除实例下的所有后置处理
     * @param taskInstanceId 任务实例id
     */
    void deleteTaskPostInstance(String taskInstanceId);

    /**
     * 更新后置任务实例信息
     * @param instance 后置任务实例模型
     */
    void updatePostInstance(PostprocessInstance instance);

    /**
     * 查询任务后置处理实例
     * @param taskInstanceId 任务执行实例
     * @return 后置处理集合实例
     */
    List<PostprocessInstance> findTaskPostInstance(String taskInstanceId);


    /**
     * 查询流水线后置处理实例
     * @param instanceId 流水线执行实例
     * @return 后置处理集合实例
     */
    List<PostprocessInstance> findPipelinePostInstance(String instanceId);


}










































