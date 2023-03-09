package io.tiklab.matflow.support.trigger.service;

import io.tiklab.matflow.support.trigger.model.Trigger;
import io.tiklab.matflow.support.trigger.model.TriggerTime;
/**
 * 流水线触发器任务服务接口
 */
public interface TriggerTaskService {

    /**
     * 查询任务
     * @param trigger 配置信息
     */
    void createTaskTrigger(Trigger trigger);

    /**
     * 删除任务
     * @param trigger 配置
     */
    void deleteTrigger(Trigger trigger);

    /**
     * 更新任务
     * @param trigger 配置
     */
    void updateTrigger(Trigger trigger);


    /**
     * 删除一个定时任务
     * @param triggerId 配置id
     * @param cron 表达式
     */
    void deleteCron(String pipelineId,String triggerId,String cron);

    /**
     * 查询任务
     * @param trigger 配置
     * @return 任务
     */
    TriggerTime findTrigger(Trigger trigger);


}
