package net.tiklab.matflow.task.task.service;

import net.tiklab.matflow.task.task.model.TaskFacade;
import net.tiklab.matflow.task.task.model.Tasks;

import java.util.List;

/**
 * 流水线流多任务服务接口
 */
public interface TasksService {

    /**
     * 创建配置及任务
     * @param taskFacade 配置信息
     * @return 配置id
     */
    String createTasksOrTask(TaskFacade taskFacade);

    /**
     * 更新配置及任务
     * @param taskFacade 配置信息
     */
    void updateTasksTask(TaskFacade taskFacade);

    /**
     * 删除配置及任务
     * @param tasksId 配置id
     */
    void deleteTasksOrTask(String tasksId);

    /**
     * 删除流水线所有配置及任务
     * @param pipelineId 流水线id
     */
    void deleteAllTasksOrTask(String pipelineId,int pipelineType);

    /**
     * 查询流水线或阶段所有任务
     * @param id 流水线id或阶段id
     * @return 配置
     */
    List<Tasks> finAllPipOrStages(String id, int pipelineType);

    /**
     * 查询流水线或阶段的所有任务
     * @param id 流水线id或阶段id
     * @return 任务集合
     */
    List<Object> findAllTasksOrTask(String id , int type);

    /**
     * 查询单个任务详情
     * @param tasksId 配置id
     * @return 详情
     */
    Object findOneTasksTask(String tasksId);

    /**
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    List<String> validTasksMustField(String pipelineId,int type);

    /**
     * 查询单个任务
     * @param tasksId 任务id
     * @return 任务
     */
    Tasks findOneTasks(String tasksId);

}

















