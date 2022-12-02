package net.tiklab.matflow.definition.service;

import com.alibaba.fastjson.JSON;
import net.tiklab.matflow.definition.model.PipelineBeforeConfig;
import net.tiklab.matflow.definition.model.task.PipelineTime;
import net.tiklab.matflow.definition.service.task.PipelineTimeServer;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Exporter
public class PipelineBeforeConfigTaskServerImpl implements PipelineBeforeConfigTaskServer {

    @Autowired
    PipelineTimeServer timeServer;

    /**
     * 查询任务
     * @param config 配置信息
     */
    @Override
    public void createBeforeConfig(PipelineBeforeConfig config){
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        if (taskType == 81){
            PipelineTime pipelineTime = new PipelineTime();
            pipelineTime.setConfigId(configId);
            timeServer.createTimeConfig(pipelineTime);
        }
    }

    /**
     * 删除任务
     * @param config 配置
     */
    @Override
    public void deleteBeforeConfig(PipelineBeforeConfig config){
        String configId = config.getConfigId();
        timeServer.deleteAllTime(configId);
    }

    /**
     * 更新任务
     * @param config 配置
     */
    @Override
    public void updateBeforeConfig(PipelineBeforeConfig config){
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        String object = JSON.toJSONString(config.getValues());
        if (taskType == 81){
            PipelineTime pipelineTime = JSON.parseObject(object, PipelineTime.class);
            pipelineTime.setConfigId(configId);
            timeServer.deleteAllTime(configId);
            timeServer.createTimeConfig(pipelineTime);
        }
    }

    /**
     * 查询任务
     * @param config 配置
     * @return 任务
     */
    @Override
    public PipelineTime findBeforeConfig(PipelineBeforeConfig config){
        String configId = config.getConfigId();
        int taskType = config.getTaskType();
        if (taskType == 81){
            PipelineTime timeConfig = timeServer.findTimeConfig(configId);
            timeConfig.setType(taskType);
            return timeConfig;
        }
        return null;
    }




}
