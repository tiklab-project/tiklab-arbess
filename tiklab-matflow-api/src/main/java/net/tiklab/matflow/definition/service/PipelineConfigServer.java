package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.PipelineConfig;

import java.util.List;

public interface PipelineConfigServer {

    List<Object> findAllConfig(String pipelineId);


    /**
     * 查询所有任务配置
     * @param pipelineId 流水线Id
     * @return 配置
     */
    List<Object> findAllTaskConfig(String pipelineId);

    /**
     * 创建配置及任务配置
     * @param config 配置信息
     * @return 配置id
     */
    String createTaskConfig(PipelineConfig config);


    /**
     * 删除配置及任务
     * @param config 配置信息
     */
    void deleteTaskConfig(PipelineConfig config);

    /**
     * 更新配置及任务
     * @param config 配置信息
     */
    void updateTaskConfig(PipelineConfig config);

}
