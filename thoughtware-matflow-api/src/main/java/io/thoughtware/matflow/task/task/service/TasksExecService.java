package io.thoughtware.matflow.task.task.service;

import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.matflow.task.task.model.TaskExecMessage;
import io.thoughtware.matflow.task.task.model.Tasks;

import java.util.List;

/**
 * 任务执行服务接口
 */
public interface TasksExecService {

    /**
     * 执行任务
     * @param pipelineId 流水线id
     * @param taskType 任务类型
     * @param taskId 任务id
     * @return 执行状态
     */
    boolean execTask(String pipelineId , String taskType,String taskId) ;

    /**
     * 执行发送消息任务
     * @param taskExecMessage 执行信息
     * @return 执行状态
     */
    boolean execSendMessageTask(TaskExecMessage taskExecMessage);


    /**
     * 停止任务执行
     * @param taskId 任务id
     */
    void stopTask(String taskId);


    void stop(String instanceId,String stageInstanceId,String postProcessId);

    /**
     * 创建任务或阶段运行实例
     * @param task 任务运行信息
     * @param id 流水线实例id或阶段实例id或后置处理实例id
     * @param type 1.实例id 2.阶段id 3.后置处理实例id
     */
    void createTaskExecInstance(Tasks task, String id, int type,String logPath);

    /**
     * 停止指定线程
     * @param threadName 线程名称
     */
    void stopThread(String threadName);


    void runError( List<Tasks> tasks);


}
