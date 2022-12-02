package net.tiklab.matflow.definition.service;

import net.tiklab.matflow.definition.model.PipelineBeforeConfig;
import net.tiklab.matflow.definition.model.task.PipelineTime;

public interface PipelineBeforeConfigTaskServer {

    /**
     * 查询任务
     * @param config 配置信息
     */
    void createBeforeConfig(PipelineBeforeConfig config);

    /**
     * 删除任务
     * @param config 配置
     */
    void deleteBeforeConfig(PipelineBeforeConfig config);

    /**
     * 更新任务
     * @param config 配置
     */
    void updateBeforeConfig(PipelineBeforeConfig config);

    /**
     * 查询任务
     * @param config 配置
     * @return 任务
     */
    PipelineTime findBeforeConfig(PipelineBeforeConfig config);


}
