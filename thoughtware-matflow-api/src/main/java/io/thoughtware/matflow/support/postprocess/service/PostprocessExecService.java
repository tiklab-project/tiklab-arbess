package io.thoughtware.matflow.support.postprocess.service;

import io.thoughtware.matflow.pipeline.execute.model.PipelineDetails;
import io.thoughtware.matflow.support.postprocess.model.Postprocess;
import io.thoughtware.matflow.task.task.model.TaskExecMessage;

import java.util.List;


/**
 * 流水线后置处理服务接口
 */
public interface PostprocessExecService {


    /**
     * 创建流水线后置任务实例
     * @param pipelineId 流水线id
     * @param instanceId 流水线实例id
     */
    List<Postprocess> createPipelinePostInstance(String pipelineId, String instanceId);

    /**
     * 执行流水线后置任务
     * @param pipelineDetails 执行信息
     * @return 后置任务执行状态
     */
    boolean execPipelinePost(PipelineDetails pipelineDetails);


    /**
     * 停止后置任务的执行
     * @param pipelineId 流水线id
     */
    void stopTaskPostTask(String pipelineId);







}
