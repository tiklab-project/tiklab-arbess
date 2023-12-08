package io.thoughtware.matflow.support.postprocess.service;

import io.thoughtware.matflow.support.postprocess.model.PostprocessInstance;

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


    /**
     * 后置任务实例开始运行
     * @param postInstanceId 后置任务实例id
     */
    void postInstanceRunTime(String postInstanceId);

    /**
     * 获取后置任务实例运行时间
     * @param postInstanceId 后置任务实例id
     * @return 运行时间
     */
    Integer findPostInstanceRunTime(String postInstanceId);

    /**
     * 移除后置任务实例
     * @param postInstanceId 后置任务实例id
     */
    void removePostInstanceRunTime(String postInstanceId);


}










































