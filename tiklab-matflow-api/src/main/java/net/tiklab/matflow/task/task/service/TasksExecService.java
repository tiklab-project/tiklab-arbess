package net.tiklab.matflow.task.task.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.task.task.model.Tasks;

public interface TasksExecService {

    /**
     * 执行任务
     * @param pipelineId 流水线id
     * @param taskType 任务类型
     * @param taskId 任务id
     * @return 执行状态
     */
    boolean execTask(String pipelineId , int taskType,String taskId) throws ApplicationException;

    /**
     * 创建任务或阶段运行实例
     * @param task 任务运行信息
     * @param id 实例id或阶段id
     * @param type 1.实例id 2.阶段id
     */
    void createTaskExecInstance(Tasks task,String id,int type);

    /**
     * 任务执行时长
     * @param taskId 任务id
     */
    void time(String taskId);

    /**
     * 停止指定线程
     * @param threadName 线程名称
     */
    void stopThread(String threadName);


}
