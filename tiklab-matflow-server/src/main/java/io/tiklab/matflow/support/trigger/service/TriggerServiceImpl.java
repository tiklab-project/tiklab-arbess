package io.tiklab.matflow.support.trigger.service;

import com.alibaba.fastjson.JSON;
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
import java.util.Objects;

@Service
@Exporter
public class TriggerServiceImpl implements TriggerService {

    @Autowired
    TriggerDao triggerDao;

    @Autowired
    JoinTemplate joinTemplate;
    
    @Autowired
    TriggerTimeService timeServer;


    /**
     * 创建配置及任务
     * @param trigger 配置
     * @return 配置id
     */
    @Override
    public String createTrigger(Trigger trigger) {
        trigger.setCreateTime(PipelineUtil.date(1));
        String triggerId = createTriggerConfig(trigger);
        List<String> list = new ArrayList<>();
        try {
            int taskType = trigger.getTaskType();
            Pipeline pipeline = trigger.getPipeline();
            String pipelineId = pipeline.getId();
            if (taskType == 81){
                String object = JSON.toJSONString(trigger.getValues());
                TriggerTime triggerTime = JSON.parseObject(object, TriggerTime.class);
                List<Integer> timeList = triggerTime.getTimeList();
                for (Integer integer : timeList) {
                    list.add(triggerId);
                    triggerTime.setDayTime(integer);
                    triggerTime.setTriggerId(triggerId);
                    timeServer.createTriggerTime(triggerTime,pipelineId);
                }
            }
        }catch (ApplicationException e){
            for (String s : list) {
                deleteTrigger(s);
            }
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
            String triggerId = trigger.getTriggerId();
            TriggerTime triggerTime = timeServer.findTriggerTime(triggerId);
            if (triggerTime == null){
                deleteTrigger(trigger.getTriggerId());
                continue;
            }
            int taskType = trigger.getTaskType();
            triggerTime.setType(taskType);
            triggerTimeList.add(triggerTime);
        }
        triggerTimeList.sort(Comparator.comparing(TriggerTime::getWeekTime));

        return new ArrayList<>(triggerTimeList);
    }


    public void cloneTrigger(String pipelineId,String clonePipelineId){
        List<Trigger> allTrigger = findAllPipelineTrigger(pipelineId);
        if (allTrigger == null){
            return;
        }
        for (Trigger trigger : allTrigger) {
            String triggerId = trigger.getTriggerId();
            trigger.setPipeline(new Pipeline(clonePipelineId));
            TriggerEntity triggerEntity = BeanMapper.map(trigger, TriggerEntity.class);
            String triggerEntityId = triggerDao.createTriggerConfig(triggerEntity);

            TriggerTime triggerTime = timeServer.findTriggerTime(triggerId);
            List<Integer> timeList = triggerTime.getTimeList();
            for (Integer integer : timeList) {
                triggerTime.setDayTime(integer);
                triggerTime.setTriggerId(triggerEntityId);
                timeServer.createTriggerTime(triggerTime,clonePipelineId);
            }
        }

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
        for (Trigger trigger : allTriggerConfig) {
            String triggerId = trigger.getTriggerId();
            deleteTrigger(triggerId);
            timeServer.deleteAllTime(triggerId,pipelineId);
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
        for (Trigger trigger : allTriggerConfig) {
            String triggerId = trigger.getTriggerId();

            TriggerTime triggerTime = timeServer.fondCronConfig(triggerId, cron);
            if (triggerTime == null){
                return;
            }
            timeServer.deleteCronTime(pipelineId, triggerTime.getTimeId());
        }
    }


    @Override
    public void updateTrigger(Trigger trigger){
        String triggerId = trigger.getTriggerId();
        if (triggerId == null){
            triggerId = createTrigger(trigger);
        }
        trigger.setTriggerId(triggerId);

        int taskType = trigger.getTaskType();
        Pipeline pipeline = trigger.getPipeline();
        String pipelineId = pipeline.getId();
        String object = JSON.toJSONString(trigger.getValues());
        if (taskType == 81){
            TriggerTime triggerTime = JSON.parseObject(object, TriggerTime.class);
            triggerTime.setTriggerId(triggerId);
            timeServer.deleteAllTime(triggerId,pipelineId);

            List<Integer> timeList = triggerTime.getTimeList();
            for (Integer integer : timeList) {
                triggerTime.setDayTime(integer);
                timeServer.createTriggerTime(triggerTime,pipelineId);
            }

        }
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
        if (Objects.isNull(pipelineId)){
            return allTriggerConfig;
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
        return triggerDao.createTriggerConfig(configEntity);
    }

    
    public Trigger findOneTriggerById(String triggerId) {
        TriggerEntity triggerConfigEntity = triggerDao.findOneTriggerConfig(triggerId);
        Trigger config = BeanMapper.map(triggerConfigEntity, Trigger.class);
        joinTemplate.joinQuery(config);
        return config;
    }
    
    @Override
    public void deleteTrigger(String triggerId) {
        Trigger trigger = findOneTriggerById(triggerId);
        String pipelineId = trigger.getPipeline().getId();
        timeServer.deleteAllTime(triggerId,pipelineId);
        triggerDao.deleteTriggerConfig(triggerId);
    }


    public List<Trigger> findAllTrigger() {
        return BeanMapper.mapList(triggerDao.findAllTriggerConfig(), Trigger.class);
    }

    @Override
    public List<Trigger> findAllTriggerConfigList(List<String> idList) {
        return BeanMapper.mapList(triggerDao.findAllTriggerConfigList(idList), Trigger.class);
    }

}
