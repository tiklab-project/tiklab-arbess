package net.tiklab.matflow.pipeline.definition.service;

import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.pipeline.definition.model.PipelineTasks;

import java.util.List;

/**
 * 流水线流程设计任务配置
 */
public interface PipelineTasksService {

    /**
     * 创建配置及任务
     * @param config 配置信息
     * @return 配置id
     */
    String createTasksTask(Tasks config);

    /**
     * 更新配置及任务
     * @param config 配置信息
     */
    void updateTasksTask(Tasks config);

    /**
     * 删除配置及任务
     * @param tasksId 配置id
     */
    void deleteTasksTask(String tasksId);

    /**
     * 删除流水线所有配置
     * @param pipelineId 流水线id
     */
    void deleteAllTasksTask(String pipelineId);

    /**
     * 查询所有配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    List<PipelineTasks> finAllTasks(String pipelineId);

    /**
     * 查询所有任务
     * @param pipelineId 流水线id
     * @return 任务集合
     */
    List<Object>  findAllTasksTask(String pipelineId);


    /**
     * 获取配置详情
     * @param configId 配置id
     * @return 详情
     */
    Object findOneTasksTask(String configId);

    /**
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    List<String> validAllConfig(String pipelineId);

}
