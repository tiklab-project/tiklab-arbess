package net.tiklab.matflow.definition.service;

import net.tiklab.join.annotation.JoinProvider;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineConfig;

import java.util.List;

@JoinProvider(model = PipelineConfig.class)
public interface PipelineConfigServer {

    /**
     * 查询所有配置（包括后置任务）
     * @param pipelineId 流水线id
     * @return 任务
     */
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
     * 删除流水线所有配置
     * @param pipelineId 流水线id
     * @param pipelineType 流水线类型
     */
    void deleteAllTaskConfig(String pipelineId,int pipelineType);

    /**
     * 更新配置及任务
     * @param config 配置信息
     */
    void updateTaskConfig(PipelineConfig config);


    /**
     * 效验配置必填字段
     * @param pipelineId 流水线id
     * @return 配置id集合
     */
    List<String> validAllConfig(String pipelineId);


    /**
     * 创建流水线模板
     * @param pipeline 流水线信息
     */
    void createTemplate(Pipeline pipeline);

}
