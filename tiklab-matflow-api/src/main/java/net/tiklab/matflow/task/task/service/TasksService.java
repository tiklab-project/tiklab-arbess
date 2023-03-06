package net.tiklab.matflow.task.task.service;

import net.tiklab.matflow.task.task.model.Tasks;

import java.util.List;

/**
 * 流水线流任务服务接口
 */
public interface TasksService {



    /**
     * 创建任务及详情
     * @param tasks 任务详情
     * @return 任务id
     */
    String createTasksOrTask(Tasks tasks);

    /**
     * 更新任务信息
     * @param tasks 更新内容
     */
    void updateTasksTask(Tasks tasks);

    /**
     * 更新任务名称
     * @param tasks 任务id
     */
    void updateTaskName(Tasks tasks);

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
     * 获取流水线任务
     * @param pipelineId 流水线id
     * @return 任务列表
     */
    List<Tasks> finAllPipelineTask(String pipelineId);

    /**
     * 获取阶段任务
     * @param stageId 阶段id
     * @return 任务列表
     */
    List<Tasks> finAllStageTask(String stageId);

    /**
     * 获取后置任务
     * @param postId 阶段id
     * @return 任务列表
     */
    Tasks findOnePostTask(String postId);

    /**
     * 获取后置任务及任务详情
     * @param postId 阶段id
     * @return 任务列表
     */
    Tasks findOnePostTaskOrTask(String postId);

    /**
     * 获取流水线任务及任务详情
     * @param pipelineId 流水线
     * @return 任务列表
     */
    List<Tasks> finAllPipelineTaskOrTask(String pipelineId);

    /**
     * 获取阶段任务及任务详情
     * @param stageId 流水线
     * @return 任务列表
     */
    List<Tasks> finAllStageTaskOrTask(String stageId);

    /**
     * 查询单个任务详情
     * @param tasksId 配置id
     * @return 详情
     */
    Object findOneTasksTask(String tasksId);

    /**
     * 效验配置必填字段
     * @param id 流水线id或阶段id
     * @return 配置id集合
     */
    List<String> validTasksMustField(String id,int type);

    /**
     * 查询单个任务
     * @param tasksId 任务id
     * @return 任务
     */
    Tasks findOneTasks(String tasksId);

    /**
     * 查询单个任务及任务详情
     * @param tasksId 任务id
     * @return 任务详细信息
     */
    Tasks findOneTasksOrTask(String tasksId);

}

















