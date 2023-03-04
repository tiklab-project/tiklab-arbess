package net.tiklab.matflow.task.codescan.service;

import net.tiklab.matflow.task.task.model.Tasks;

public interface TaskCodeScanExecService {

    /**
     * 源码管理
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
    boolean codeScan(String pipelineId, Tasks task , int taskType);

}
