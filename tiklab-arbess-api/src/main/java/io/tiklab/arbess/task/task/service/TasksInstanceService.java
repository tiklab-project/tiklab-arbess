package io.tiklab.arbess.task.task.service;

import io.tiklab.arbess.task.task.model.TaskInstance;
import io.tiklab.arbess.task.task.model.TaskInstanceQuery;
import io.tiklab.toolkit.join.annotation.FindAll;
import io.tiklab.toolkit.join.annotation.FindList;
import io.tiklab.toolkit.join.annotation.JoinProvider;

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


    List<String> findAllInstanceLogs(String instanceId);

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
     * 获取流水线实例的后置任务信息
     * @param instanceId 任务实例id
     * @return 后置任务信息
     */
    TaskInstance findPostPipelineRunMessage(String instanceId);


    /**
     * 查询阶段下的后置任务实例
     * @param id 后置任务阶段id
     * @return 后置任务实例集合
     */
    List<TaskInstance> findStagePostRunMessage(String id);

    /**
     * 查询阶段下的任务运行实例
     * @param stageId 阶段id
     * @return 任务运行实例集合
     */
    List<TaskInstance> findAllStageInstance(String stageId);

    /**
     * 查询所有任务日志
     * @return 任务日志列表
     */
    @FindAll
    List<TaskInstance> findAllTaskInstance();


    List<TaskInstance> findTaskInstanceList(TaskInstanceQuery query);


    @FindList
    List<TaskInstance> findAllInstanceList(List<String> idList);
}
