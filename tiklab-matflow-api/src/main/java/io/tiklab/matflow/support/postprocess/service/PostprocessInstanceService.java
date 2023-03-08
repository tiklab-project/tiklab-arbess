package io.tiklab.matflow.support.postprocess.service;

import io.tiklab.matflow.support.postprocess.model.PostprocessInstance;

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
     * 删除执行实例
     * @param postInstanceId 实例id
     */
    void deletePostInstance(String postInstanceId);

    /**
     * 删除实例下的所有后置处理
     * @param instanceId 实例id
     */
    void deleteAllPostInstance(String instanceId);







}










































