package io.tiklab.arbess.task.task.service;

import io.tiklab.arbess.task.task.model.Tasks;

/**
 * 任务执行服务接口
 */
public interface TasksExecService {


    /**
     * 创建任务或阶段运行实例
     * @param task 任务运行信息
     * @param id 流水线实例id或阶段实例id或后置处理实例id
     * @param type 1.实例id 2.阶段id 3.后置处理实例id
     */
    String createTaskExecInstance(Tasks task, String id, int type,String logPath);


    /**
     * 创建部署任务实例
     * @param task 任务运行信息
     * @param taskInstanceId 任务实例id
     */
    void createDeployInstance(Tasks task,String taskInstanceId);

}
