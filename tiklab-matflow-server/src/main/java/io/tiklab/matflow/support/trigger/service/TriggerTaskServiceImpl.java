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
     * @param config 配置信息
     */
    @Override
    public void createTriggerConfig(Trigger config)throws ApplicationException {
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        Pipeline pipeline = config.getPipeline();
        String pipelineId = pipeline.getId();
        String object = JSON.toJSONString(config.getValues());
        if (taskType == 81){
            TriggerTime triggerTime = JSON.parseObject(object, TriggerTime.class);
            triggerTime.setConfigId(configId);
            timeServer.createTimeConfig(triggerTime,pipelineId);
        }
    }

    /**
     * 删除任务
     * @param config 配置
     */
    @Override
    public void deleteTriggerConfig(Trigger config){
        String configId = config.getConfigId();
        Pipeline pipeline = config.getPipeline();
        String pipelineId = pipeline.getId();
        timeServer.deleteAllTime(configId,pipelineId);
    }

    /**
     * 更新任务
     * @param config 配置
     */
    @Override
    public void updateTriggerConfig(Trigger config){
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        Pipeline pipeline = config.getPipeline();
        String pipelineId = pipeline.getId();
        String object = JSON.toJSONString(config.getValues());
        if (taskType == 81){
            TriggerTime triggerTime = JSON.parseObject(object, TriggerTime.class);
            triggerTime.setConfigId(configId);
            timeServer.deleteAllTime(configId,pipelineId);
            timeServer.createTimeConfig(triggerTime,pipelineId);
        }
    }

    /**
     * 删除一个定时任务
     * @param configId 配置id
     * @param cron 表达式
     */
    public void deleteCronConfig(String pipelineId,String configId,String cron){
        TriggerTime triggerTime = timeServer.fondCronConfig(configId, cron);
        if (triggerTime == null){
            return;
        }
        timeServer.deleteCronTime(pipelineId, triggerTime.getTimeId());
    }

    /**
     * 查询任务
     * @param config 配置
     * @return 任务
     */
    @Override
    public TriggerTime findTriggerConfig(Trigger config){
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        TriggerTime triggerTimeConfig = timeServer.findTimeConfig(configId);
        if (triggerTimeConfig == null){
            return null;
        }
        triggerTimeConfig.setType(taskType);
        return triggerTimeConfig;
    }




}
