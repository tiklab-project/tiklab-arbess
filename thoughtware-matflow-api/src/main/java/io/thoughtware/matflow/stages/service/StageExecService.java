package io.thoughtware.matflow.stages.service;

import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.pipeline.execute.model.PipelineDetails;
import io.thoughtware.matflow.stages.model.Stage;

import java.util.List;

/**
 * 阶段执行服务接口
 */
public interface StageExecService {


    /**
     * 创建阶段运行实例
     * @param pipelineId 流水线id
     * @param instanceId 流水线实例id
     */
    List<Stage> createStageExecInstance(String pipelineId , String instanceId);


    /**
     * 运行流水线阶段
     * @param pipelineDetails 流水线执行详情
     * @return 阶段运行状态
     */
    boolean execStageTask(PipelineDetails pipelineDetails);






}
