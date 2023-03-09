package io.tiklab.matflow.support.trigger.service;

import com.alibaba.fastjson.JSON;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.support.trigger.model.TriggerTime;
import io.tiklab.matflow.support.trigger.model.Trigger;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Exporter
public class TriggerTaskServiceImpl implements TriggerTaskService {

    @Autowired
    TriggerTimeService timeServer;

    /**
     * 查询任务
     * @param trigger 配置信息
     */
    @Override
    public void createTaskTrigger(Trigger trigger) throws ApplicationException {
        String triggerId = trigger.getTriggerId();
        int taskType = trigger.getTaskType();
        Pipeline pipeline = trigger.getPipeline();
        String pipelineId = pipeline.getId();
        String object = JSON.toJSONString(trigger.getValues());
        if (taskType == 81){
            TriggerTime triggerTime = JSON.parseObject(object, TriggerTime.class);
            triggerTime.setTriggerId(triggerId);
            timeServer.createTimeConfig(triggerTime,pipelineId);
        }
    }

    /**
     * 删除任务
     * @param trigger 配置
     */
    @Override
    public void deleteTrigger(Trigger trigger){
        String triggerId = trigger.getTriggerId();
        Pipeline pipeline = trigger.getPipeline();
        String pipelineId = pipeline.getId();
        timeServer.deleteAllTime(triggerId,pipelineId);
    }

    /**
     * 更新任务
     * @param trigger 配置
     */
    @Override
    public void updateTrigger(Trigger trigger){
        String triggerId = trigger.getTriggerId();
        int taskType = trigger.getTaskType();
        Pipeline pipeline = trigger.getPipeline();
        String pipelineId = pipeline.getId();
        String object = JSON.toJSONString(trigger.getValues());
        if (taskType == 81){
            TriggerTime triggerTime = JSON.parseObject(object, TriggerTime.class);
            triggerTime.setTriggerId(triggerId);
            timeServer.deleteAllTime(triggerId,pipelineId);
            timeServer.createTimeConfig(triggerTime,pipelineId);
        }
    }

    /**
     * 删除一个定时任务
     * @param triggerId 配置id
     * @param cron 表达式
     */
    public void deleteCron(String pipelineId,String triggerId,String cron){
        TriggerTime triggerTime = timeServer.fondCronConfig(triggerId, cron);
        if (triggerTime == null){
            return;
        }
        timeServer.deleteCronTime(pipelineId, triggerTime.getTimeId());
    }

    /**
     * 查询任务
     * @param trigger 配置
     * @return 任务
     */
    @Override
    public TriggerTime findTrigger(Trigger trigger){
        String configId = trigger.getTriggerId();
        int taskType = trigger.getTaskType();
        TriggerTime triggerTimeConfig = timeServer.findTimeConfig(configId);
        if (triggerTimeConfig == null){
            return null;
        }
        triggerTimeConfig.setType(taskType);
        return triggerTimeConfig;
    }




}
