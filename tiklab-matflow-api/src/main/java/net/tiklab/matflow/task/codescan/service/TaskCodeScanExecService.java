package net.tiklab.matflow.task.codescan.service;

import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;

public interface TaskCodeScanExecService {

    /**
     * 源码管理
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
    boolean codeScan(PipelineProcess pipelineProcess,String configId ,int taskType);

}
