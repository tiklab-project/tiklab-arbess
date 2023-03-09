package io.tiklab.matflow.support.trigger.service;

import io.tiklab.matflow.support.trigger.dao.TriggerDao;
import io.tiklab.matflow.support.trigger.entity.TriggerEntity;
import io.tiklab.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.join.JoinTemplate;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.matflow.support.trigger.model.TriggerTime;
import io.tiklab.matflow.support.trigger.model.Trigger;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class TriggerServiceImpl implements TriggerService {

    @Autowired
    TriggerDao triggerConfigDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    TriggerTaskService triggerTaskServer;


    /**
     * 创建配置及任务
     * @param trigger 配置
     * @return 配置id
     */
    @Override
    public String createTrigger(Trigger trigger) {
        trigger.setCreateTime(PipelineUtil.date(1));
        String triggerId = createTriggerConfig(trigger);
        trigger.setTriggerId(triggerId);
        try {
            triggerTaskServer.createTaskTrigger(trigger);
        }catch (ApplicationException e){
            deleteTrigger(triggerId);
        }
        return triggerId;
    }

    /**
     * 查询所有配置
     * @param pipelineId 流水线id
     * @return 配置列表
     */
    @Override
    public List<Object> findAllTrigger(String pipelineId){
        List<Trigger> allTriggerConfig = findAllPipelineTrigger(pipelineId);
        if (allTriggerConfig == null){
            return null;
        }
        List<TriggerTime> triggerTimeList = new ArrayList<>();
        for (Trigger trigger : allTriggerConfig) {
            TriggerTime triggerConfig = triggerTaskServer.findTrigger(trigger);
            if (triggerConfig == null){
                deleteTrigger(trigger.getTriggerId());
                continue;
            }
            triggerTimeList.add(triggerConfig);
        }
        triggerTimeList.sort(Comparator.comparing(TriggerTime::getWeekTime));

        return new ArrayList<>(triggerTimeList);
    }

    /**
     * 删除流水线所有定时任务
     * @param pipelineId 流水线id
     */
    public void deleteAllTrigger(String pipelineId){
        List<Trigger> allTriggerConfig = findAllPipelineTrigger(pipelineId);
        if (allTriggerConfig == null || allTriggerConfig.size() == 0){
            return;
        }
        for (Trigger config : allTriggerConfig) {
            triggerTaskServer.deleteTrigger(config);
            deleteTrigger(config.getTriggerId());
        }
    }

    /**
     * 删除单个定时任务
     * @param pipelineId 流水线id
     * @param cron 表达式
     */
    @Override
    public void deleteCronConfig(String pipelineId,String cron){
        List<Trigger> allTriggerConfig = findAllPipelineTrigger(pipelineId);
        if (allTriggerConfig == null){
            return;
        }
        for (Trigger config : allTriggerConfig) {
            String configId = config.getTriggerId();
            triggerTaskServer.deleteCron(pipelineId,configId,cron);
        }
    }

    /**
     * 查询单个任务
     * @param triggerId 配置id
     * @return 任务
     */
    @Override
    public Object findOneTrigger(String triggerId){
        Trigger oneTriggerConfig = findOneTriggerConfig(triggerId);
        TriggerTime triggerConfig = triggerTaskServer.findTrigger(oneTriggerConfig);
        if (triggerConfig == null){
            deleteTrigger(triggerId);
            return null;
        }
        return triggerConfig;
    }

    @Override
    public void updateTrigger(Trigger trigger){
        String configId = trigger.getTriggerId();
        if (configId == null){
            configId = createTrigger(trigger);
        }
        trigger.setTriggerId(configId);
        triggerTaskServer.updateTrigger(trigger);
    }

    /**
     * 根据流水线id查询触发器配置
     * @param pipelineId 流水线id
     * @return 配置
     */
    @Override
    public List<Trigger> findAllPipelineTrigger(String pipelineId) {
        List<Trigger> allTriggerConfig = findAllTrigger();
        List<Trigger> list = new ArrayList<>();

        if (allTriggerConfig == null || allTriggerConfig.size() == 0){
            return null;
        }

        for (Trigger trigger : allTriggerConfig) {
            Pipeline pipeline = trigger.getPipeline();
            if (!pipeline.getId().equals(pipelineId)){
                continue;
            }
            list.add(trigger);
        }
        return list;
    }

    //创建
    public String createTriggerConfig(Trigger trigger){
        TriggerEntity configEntity = BeanMapper.map(trigger, TriggerEntity.class);
        return triggerConfigDao.createTriggerConfig(configEntity);
    }

    //更新
    public void updateTriggerConfig(Trigger trigger) {
        triggerTaskServer.updateTrigger(trigger);
    }

    
    public Trigger findOneTriggerConfig(String triggerId) {
        TriggerEntity triggerConfigEntity = triggerConfigDao.findOneTriggerConfig(triggerId);
        Trigger config = BeanMapper.map(triggerConfigEntity, Trigger.class);
        joinTemplate.joinQuery(config);
        return config;
    }
    
    @Override
    public void deleteTrigger(String triggerId) {
        Trigger oneTriggerConfig = findOneTriggerConfig(triggerId);
        triggerTaskServer.deleteTrigger(oneTriggerConfig);
        triggerConfigDao.deleteTriggerConfig(triggerId);
    }


    public List<Trigger> findAllTrigger() {
        return BeanMapper.mapList(triggerConfigDao.findAllTriggerConfig(), Trigger.class);
    }

    @Override
    public List<Trigger> findAllTriggerConfigList(List<String> idList) {
        return BeanMapper.mapList(triggerConfigDao.findAllTriggerConfigList(idList), Trigger.class);
    }

}
