package net.tiklab.matflow.task.codescan.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.task.task.model.Tasks;

/**
 * 代码扫描执行服务接口
 */
public interface TaskCodeScanExecService {

    /**
     * 代码扫描
     * @param pipelineId 流水线id
     * @param task 任务信息
     * @param taskType 任务类型
     * @return 执行状态 true:成功 false:失败
     * @throws ApplicationException 运行失败
     */
    boolean codeScan(String pipelineId, Tasks task , int taskType);

}
