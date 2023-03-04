package net.tiklab.matflow.pipeline.execute.service;

/**
 * 流水线运行服务接口
 */
public interface PipelineExecService {

    /**
     * 开始构建
     * @param pipelineId 流水线id
     * @param startWAy 执行方式（1.手动执行 2.定时器触发）
     * @return 开始构建（true:开始运行 false:正在运行）
     */
    boolean start(String pipelineId,int startWAy);


    // void stop(String pipelineId);


}


































