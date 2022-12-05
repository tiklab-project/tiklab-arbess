package net.tiklab.matflow.trigger.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.trigger.model.PipelineTime;
import net.tiklab.matflow.trigger.model.PipelineTimeServer;
import net.tiklab.matflow.trigger.model.PipelineTriggerConfig;
import net.tiklab.matflow.trigger.server.PipelineTriggerConfigTaskServer;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Exporter
public class PipelineTriggerConfigTaskServerImpl implements PipelineTriggerConfigTaskServer {

    @Autowired
    PipelineTimeServer timeServer;

    /**
     * 查询任务
     * @param config 配置信息
     */
    @Override
    public void createTriggerConfig(PipelineTriggerConfig config)throws ApplicationException {
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        Pipeline pipeline = config.getPipeline();
        String pipelineId = pipeline.getPipelineId();
        String object = JSON.toJSONString(config.getValues());
        if (taskType == 81){
            PipelineTime pipelineTime = JSON.parseObject(object, PipelineTime.class);
            pipelineTime.setConfigId(configId);
            timeServer.createTimeConfig(pipelineTime,pipelineId);
        }
    }

    /**
     * 删除任务
     * @param config 配置
     */
    @Override
    public void deleteTriggerConfig(PipelineTriggerConfig config){
        String configId = config.getConfigId();
        Pipeline pipeline = config.getPipeline();
        String pipelineId = pipeline.getPipelineId();
        timeServer.deleteAllTime(configId,pipelineId);
    }

    /**
     * 更新任务
     * @param config 配置
     */
    @Override
    public void updateTriggerConfig(PipelineTriggerConfig config){
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        Pipeline pipeline = config.getPipeline();
        String pipelineId = pipeline.getPipelineId();
        String object = JSON.toJSONString(config.getValues());
        if (taskType == 81){
            PipelineTime pipelineTime = JSON.parseObject(object, PipelineTime.class);
            pipelineTime.setConfigId(configId);
            timeServer.deleteAllTime(configId,pipelineId);
            timeServer.createTimeConfig(pipelineTime,pipelineId);
        }
    }


    /**
     * 删除一个定时任务
     * @param configId 配置id
     * @param cron 表达式
     */
    public void deleteCronConfig(String pipelineId,String configId,String cron){
        PipelineTime pipelineTime = timeServer.fondCronConfig(configId, cron);
        if (pipelineTime == null){
            return;
        }
        timeServer.deleteCronTime(pipelineId,pipelineTime.getTimeId());
    }

    /**
     * 查询任务
     * @param config 配置
     * @return 任务
     */
    @Override
    public PipelineTime findTriggerConfig(PipelineTriggerConfig config){
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        PipelineTime timeConfig = timeServer.findTimeConfig(configId);
        if (timeConfig == null){
            return null;
        }
        timeConfig.setType(taskType);
        return timeConfig;
    }




}
