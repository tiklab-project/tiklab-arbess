package io.tiklab.arbess.task.task.service;

import io.tiklab.arbess.task.task.model.Tasks;

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
     * 获取阶段任务及任务详情
     * @param stageId 流水线
     * @return 任务列表
     */
    List<Tasks> finStageTaskOrTask(String stageId);

    /**
     * 获取阶段任务及任务详情
     * @param stageId 流水线
     * @return 任务列表
     */
    List<Tasks> finStageTaskOrTaskNoAuth(String stageId);

    /**
     * 效验配置必填字段
     * @param tasksList 任务
     * @return 配置id集合
     */
    List<String> validTasksMustField(List<Tasks> tasksList);


    /**
     * 创建任务模板
     * @param pipelineId 流水线id
     * @param template 模板
     */
    void createTaskTemplate(String pipelineId,String[] template);

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


    /**
     * 划分任务类型
     * @param taskType 任务类型
     * @return 任务类型
     */
    String findTaskType(String taskType);


    String initDifferentTaskName(String taskType);


    List<Tasks> findAllTasks();


    /**
     * 更新任务
     * @param tasks 任务
     */
    void updateTasks(Tasks tasks);

}

















