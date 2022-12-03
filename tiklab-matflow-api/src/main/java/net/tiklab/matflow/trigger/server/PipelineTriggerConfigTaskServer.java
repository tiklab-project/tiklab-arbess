package net.tiklab.matflow.trigger.server;

import net.tiklab.matflow.trigger.model.PipelineTriggerConfig;
import net.tiklab.matflow.trigger.model.PipelineTime;

public interface PipelineTriggerConfigTaskServer {

    /**
     * 查询任务
     * @param config 配置信息
     */
    void createTriggerConfig(PipelineTriggerConfig config);

    /**
     * 删除任务
     * @param config 配置
     */
    void deleteTriggerConfig(PipelineTriggerConfig config);

    /**
     * 更新任务
     * @param config 配置
     */
    void updateTriggerConfig(PipelineTriggerConfig config);

    /**
     * 查询任务
     * @param config 配置
     * @return 任务
     */
    PipelineTime findTriggerConfig(PipelineTriggerConfig config);


}
