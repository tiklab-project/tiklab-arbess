package io.tiklab.matflow.support.postprocess.service;

import io.tiklab.matflow.pipeline.definition.model.Pipeline;


/**
 * 流水线后置处理服务接口
 */
public interface PostprocessExecService {

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







}
