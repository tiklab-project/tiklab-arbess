package io.thoughtware.arbess.support.postprocess.service;

import io.thoughtware.arbess.support.postprocess.model.Postprocess;

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






}
