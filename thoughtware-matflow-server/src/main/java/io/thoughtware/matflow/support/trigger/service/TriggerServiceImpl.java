package io.thoughtware.matflow.support.trigger.service;

import com.alibaba.fastjson.JSON;
import io.thoughtware.matflow.support.trigger.dao.TriggerDao;
import io.thoughtware.matflow.support.trigger.entity.TriggerEntity;
import io.thoughtware.toolkit.beans.BeanMapper;
import io.thoughtware.toolkit.join.JoinTemplate;
import io.thoughtware.matflow.pipeline.definition.model.Pipeline;
import io.thoughtware.matflow.support.trigger.model.TriggerQuery;
import io.thoughtware.matflow.support.trigger.model.TriggerTime;
import io.thoughtware.matflow.support.trigger.model.Trigger;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        int taskType = trigger.getTaskType();
        String pipelineId = trigger.getPipeline().getId();
        if (taskType == 81){
            String object = JSON.toJSONString(trigger.getValues());
            TriggerTime triggerTime = JSON.parseObject(object, TriggerTime.class);
            List<Integer> timeList = triggerTime.getTimeList();
            for (Integer integer : timeList) {
                triggerTime.setDayTime(integer);
                triggerTime.setTriggerId(triggerId);
                timeServer.createTriggerTime(triggerTime,pipelineId);
            }
        }
        return triggerId;
    }

    @Override
    public List<Object> findAllTrigger(TriggerQuery triggerQuery){
        List<Trigger> triggerList = findTriggerList(triggerQuery);
        if (triggerList.isEmpty()){
            return Collections.emptyList();
        }
        List<TriggerTime> triggerTimeList = new ArrayList<>();
        for (Trigger trigger : triggerList) {
            String triggerId = trigger.getTriggerId();
            TriggerTime triggerTime = timeServer.findTriggerTime(triggerId);
            if (triggerTime == null){
                deleteTrigger(trigger.getTriggerId());
                continue;
            }
            int taskType = trigger.getTaskType();
            triggerTime.setType(taskType);
            triggerTime.setState(trigger.getState());
            triggerTimeList.add(triggerTime);
        }

        triggerTimeList.sort(Comparator.comparing(TriggerTime::getWeekTime));

        return new ArrayList<>(triggerTimeList);
    }

    @Override
    public void cloneTrigger(String pipelineId,String clonePipelineId){
        TriggerQuery triggerQuery = new TriggerQuery();
        triggerQuery.setPipelineId(pipelineId);
        List<Trigger> allTrigger = findTriggerList(triggerQuery);
        if (allTrigger.isEmpty()){
            return;
        }
        for (Trigger trigger : allTrigger) {
            String triggerId = trigger.getTriggerId();
            trigger.setPipeline(new Pipeline(clonePipelineId));
            TriggerEntity triggerEntity = BeanMapper.map(trigger, TriggerEntity.class);
            String triggerEntityId = triggerDao.createTrigger(triggerEntity);

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
    @Override
    public void deleteAllTrigger(String pipelineId){
        TriggerQuery triggerQuery = new TriggerQuery();
        triggerQuery.setPipelineId(pipelineId);
        List<Trigger> allTriggerConfig = findTriggerList(triggerQuery);
        if ( allTriggerConfig.isEmpty()){
            return;
        }
        for (Trigger trigger : allTriggerConfig) {
            String triggerId = trigger.getTriggerId();
            deleteTrigger(triggerId);
            timeServer.deleteAllTime(triggerId,pipelineId);
        }
    }

    /**
     * 更新定时任务
     */
    @Override
    public void updateTrigger(String triggerId){

        // List<TriggerTime> triggerTimeList = timeServer.fondCronTimeList(cron);
        // if (triggerTimeList.isEmpty()){
        //     return;
        // }
        // List<TriggerTime> timeList = triggerTimeList.stream()
        //         .filter(triggerTime -> {
        //     String triggerId = triggerTime.getTriggerId();
        //     Trigger trigger = findOneTriggerById(triggerId);
        //     String id = trigger.getPipeline().getId();
        //     return id.equals(pipelineId);
        // }).toList();
        //
        // if (timeList.isEmpty()){
        //     return;
        // }

        // String triggerId = timeList.get(0).getTriggerId();

        boolean b = true;
        List<TriggerTime> allTriggerTime = timeServer.findAllTriggerTime(triggerId);
        for (TriggerTime time : allTriggerTime) {
            String weekTime = CronUtils.weekTime(time.getCron());
            Date date = PipelineUtil.StringChengeDate(weekTime);

            if (date.getTime() < new Date().getTime()){
                continue;
            }
            b = false;
        }
        if (b){
            Trigger trigger = findOneTriggerById(triggerId);
            System.out.println("更新状态："+trigger.getState() + " 更新ID：" + trigger.getTriggerId());
            TriggerEntity triggerEntity = BeanMapper.map(trigger.setState("2"), TriggerEntity.class);
            triggerDao.updateTrigger(triggerEntity);
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
            if (Objects.isNull(triggerTime)){
                return;
            }
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
     * @param triggerQuery 条件
     * @return 配置
     */
    @Override
    public List<Trigger> findTriggerList(TriggerQuery triggerQuery) {
        List<TriggerEntity> triggerEntityList = triggerDao.findTriggerList(triggerQuery);

        if ( triggerEntityList.isEmpty()){
            return Collections.emptyList();
        }

        return BeanMapper.mapList(triggerEntityList, Trigger.class);
    }

    //创建
    public String createTriggerConfig(Trigger trigger){
        TriggerEntity configEntity = BeanMapper.map(trigger, TriggerEntity.class);
        return triggerDao.createTrigger(configEntity);
    }

    
    public Trigger findOneTriggerById(String triggerId) {
        TriggerEntity triggerConfigEntity = triggerDao.findOneTrigger(triggerId);
        Trigger trigger = BeanMapper.map(triggerConfigEntity, Trigger.class);
        joinTemplate.joinQuery(trigger);
        return trigger;
    }
    
    @Override
    public void deleteTrigger(String triggerId) {
        Trigger trigger = findOneTriggerById(triggerId);
        String pipelineId = trigger.getPipeline().getId();
        timeServer.deleteAllTime(triggerId,pipelineId);
        triggerDao.deleteTrigger(triggerId);
    }


    public List<Trigger> findAllTrigger() {
        return BeanMapper.mapList(triggerDao.findAllTrigger(), Trigger.class);
    }

    @Override
    public List<Trigger> findAllTriggerConfigList(List<String> idList) {
        return BeanMapper.mapList(triggerDao.findAllTriggerList(idList), Trigger.class);
    }

}
