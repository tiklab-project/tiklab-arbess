package net.tiklab.matflow.task.test.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.task.task.model.Tasks;

/**
 * 任务测试执行服务接口
 */
public interface TaskTestExecService {

    /**
     * 测试
     * @param pipelineId 流水线id
     * @param task 任务信息
     * @param taskType 任务类型
     * @return 执行状态 true:成功 false:失败
     * @throws ApplicationException 运行失败
     */
    boolean test(String pipelineId, Tasks task , int taskType);


}
