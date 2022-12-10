package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.PipelineConfig;
import net.tiklab.matflow.definition.model.PipelineTasks;

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
    String createTasksTask(PipelineConfig config);

    /**
     * 更新配置及任务
     * @param config 配置信息
     */
    void updateTasksTask(PipelineConfig config);

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
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    List<String> validAllConfig(String pipelineId);

}
