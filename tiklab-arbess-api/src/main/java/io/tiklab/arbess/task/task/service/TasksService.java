package io.tiklab.arbess.task.task.service;

import io.tiklab.arbess.task.task.model.Tasks;
import io.tiklab.toolkit.join.annotation.FindList;

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
     * 更新必填字段状态
     * @param taskId 任务id
     */
    void updateTasksMustField(String taskId);

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
    List<Tasks> findStageTask(String stageId);

    /**
     * 获取阶段任务及任务详情
     * @param stageId 流水线
     * @return 任务列表
     */
    List<Tasks> findStageTaskOrTask(String stageId);

    /**
     * 获取阶段任务及任务详情
     * @param stageId 流水线
     * @return 任务列表
     */
    List<Tasks> finStageTaskOrTaskNoAuth(String stageId);

    /**
     * 获取阶段任务及任务详情（返回所有任务）
     * @param pipelineId 流水线id列表
     * @return 任务列表
     */
    List<Tasks> findTaskList(String pipelineId);

    /**
     * 获取阶段任务及任务详情
     * @param pipelineId 流水线id列表
     * @return 任务列表
     */
    List<Tasks> findTaskListByDetails(String pipelineId);

    /**
     * 创建任务模板
     * @param pipelineId 流水线id
     * @param template 模板
     */
    void createTaskTemplate(String pipelineId,String[] template);

    /**
     * 删除任务
     * @param tasksId 任务id
     */
    void deleteTasks(String tasksId);

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

    @FindList
    List<Tasks> findTasksList(List<String> idList);

    /**
     * 更新任务
     * @param tasks 任务
     */
    void updateTasks(Tasks tasks);

    /**
     * 效验任务必填字段
     * @param taskType 任务类型
     * @param object 任务
     * @return 验证结果
     */
    Boolean validTaskMastField(String taskType,Object object);

}

















