package net.tiklab.matflow.achieve.server;

import net.tiklab.matflow.execute.model.PipelineProcess;

public interface CodeScanService {

    /**
     * 源码管理
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
    boolean codeScan(PipelineProcess pipelineProcess,String configId ,int taskType);

}
