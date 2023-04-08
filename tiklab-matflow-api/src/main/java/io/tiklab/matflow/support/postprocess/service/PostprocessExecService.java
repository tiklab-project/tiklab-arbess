package io.tiklab.matflow.support.postprocess.service;

import io.tiklab.matflow.pipeline.definition.model.Pipeline;


/**
 * 流水线后置处理服务接口
 */
public interface PostprocessExecService {


    /**
     * 创建流水线后置任务实例
     * @param pipelineId 流水线id
     * @param instanceId 流水线实例id
     */
    void createPipelinePostInstance(String pipelineId,String instanceId);

    /**
     * 创建流水线任务后置任务实例
     * @param taskId 任务id
     */
    void createTaskPostInstance(String pipelineId, String instanceId, String taskId);


    /**
     * 执行流水线后置任务
     * @param pipeline 流水线信息
     * @param execStatus 流水线执行状态
     * @return 后置任务执行状态
     */
    boolean execPipelinePost(Pipeline pipeline , boolean execStatus);

    /**
     * 执行任务后置任务
     * @param pipeline 流水线信息
     * @param taskId 任务id
     * @param execStatus 执行状态
     * @return 后置任务执行状态
     */
    boolean execTaskPostTask(Pipeline pipeline , String taskId,boolean execStatus);


    /**
     * 停止后置任务的执行
     * @param pipelineId 流水线id
     */
    void stopTaskPostTask(String pipelineId);







}
