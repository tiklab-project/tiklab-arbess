package net.tiklab.matflow.support.trigger.service;

import net.tiklab.matflow.support.trigger.model.Trigger;
import net.tiklab.matflow.support.trigger.model.TriggerTime;
/**
 * 流水线触发器任务服务接口
 */
public interface TriggerTaskService {

    /**
     * 查询任务
     * @param config 配置信息
     */
    void createTriggerConfig(Trigger config);

    /**
     * 删除任务
     * @param config 配置
     */
    void deleteTriggerConfig(Trigger config);

    /**
     * 更新任务
     * @param config 配置
     */
    void updateTriggerConfig(Trigger config);


    /**
     * 删除一个定时任务
     * @param configId 配置id
     * @param cron 表达式
     */
    void deleteCronConfig(String pipelineId,String configId,String cron);

    /**
     * 查询任务
     * @param config 配置
     * @return 任务
     */
    TriggerTime findTriggerConfig(Trigger config);


}
