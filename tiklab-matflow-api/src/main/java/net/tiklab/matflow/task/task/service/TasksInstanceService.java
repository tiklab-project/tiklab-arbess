package net.tiklab.matflow.task.task.service;

import net.tiklab.join.annotation.FindAll;
import net.tiklab.join.annotation.FindList;
import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.task.task.model.TaskInstance;

import java.util.List;
/**
 * 任务执行实例服务接口
 */
@JoinProvider(model = TaskInstance.class)
public interface TasksInstanceService {

    /**
     * 创建任务日志
     * @param taskInstance 日志实体
     * @return 任务日志id
     */
     String createTaskInstance(TaskInstance taskInstance);

    /**
     * 删除实例下的运行实例
     * @param instanceId 历史id
     */
    void deleteAllInstanceInstance(String instanceId);

    /**
     * 删除阶段下的运行实例
     * @param stageId 阶段id
     */
    void deleteAllStageInstance(String stageId);

    /**
     * 查询单个任务运行实例
     * @param taskInstanceId 任务实例id
     * @return 任务运行实例
     */
    TaskInstance findOneTaskInstance(String taskInstanceId);

    /**
     * 更新任务运行实例
     * @param taskInstance 任务实例模型
     */
    void updateTaskInstance(TaskInstance taskInstance);

    /**
     *  查询流水线实例下的任务实例
     * @param instanceId 实例id
     * @return 任务运行实例集合
     */
    List<TaskInstance> findAllInstanceInstance(String instanceId);

    /**
     * 查询阶段下的任务运行实例
     * @param stageId 阶段id
     * @return 任务运行实例集合
     */
    List<TaskInstance> findAllStageInstance(String stageId);

    /**
     * 添加任务执行日志
     * @param taskId 任务id
     * @param log 日志
     */
    void writeExecLog(String taskId, String log);

    /**
     * 获取Command执行结果
     * @param process Command执行实例
     * @param enCode Command编码
     * @param error Command错误编码
     * @param taskId 任务id
     * @return 执行结果 true:执行完成 false:执行失败
     */
    boolean readCommandExecResult(Process process , String enCode, String[] error,String taskId);

    /**
     * 查询所有任务日志
     * @return 任务日志列表
     */
    @FindAll
     List<TaskInstance> findAllTaskInstance();


    @FindList
    List<TaskInstance> findAllInstanceList(List<String> idList);
}
