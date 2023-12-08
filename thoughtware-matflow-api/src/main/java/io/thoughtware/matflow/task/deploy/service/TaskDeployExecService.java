package io.thoughtware.matflow.task.deploy.service;

import io.thoughtware.matflow.task.task.model.Tasks;
import io.thoughtware.core.exception.ApplicationException;

/**
 * 部署执行服务接口
 */
public interface TaskDeployExecService {

    /**
     * 部署
     * @param pipelineId 流水线id
     * @param task 任务信息
     * @param taskType 任务类型
     * @return 执行状态 true:成功 false:失败
     * @throws ApplicationException 运行失败
     */
    boolean deploy(String pipelineId, Tasks task , String taskType);


}
