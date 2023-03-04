package net.tiklab.matflow.stages.service;

public interface StageExecService {


    /**
     * 创建阶段运行实例
     * @param pipelineId 流水线id
     * @param instanceId 流水线实例id
     */
    void createStageExecInstance(String pipelineId , String instanceId);


    /**
     * 运行流水线阶段
     * @param pipelineId 流水线id
     * @param instanceId 流水线实例
     * @return 阶段运行状态
     */
    boolean execStageTask(String pipelineId , String instanceId);





}
