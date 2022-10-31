package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.PipelineCode;
import net.tiklab.matflow.definition.model.PipelineCodeScan;
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
