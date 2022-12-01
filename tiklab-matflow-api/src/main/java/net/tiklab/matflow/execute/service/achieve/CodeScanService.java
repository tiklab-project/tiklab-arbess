package net.tiklab.matflow.execute.service.achieve;

import net.tiklab.matflow.definition.model.task.PipelineCodeScan;
import net.tiklab.matflow.execute.model.PipelineProcess;

public interface CodeScanService {

    /**
     * 源码管理
     * @param pipelineProcess 执行信息
     * @param pipelineCodeScan 配置信息
     * @return 执行状态
     */
    boolean codeScan(PipelineProcess pipelineProcess, PipelineCodeScan pipelineCodeScan);

}
