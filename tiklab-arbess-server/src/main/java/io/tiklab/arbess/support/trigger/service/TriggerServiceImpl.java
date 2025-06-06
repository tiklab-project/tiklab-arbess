package io.tiklab.arbess.support.trigger.service;

import io.tiklab.arbess.support.trigger.dao.TriggerDao;
import io.tiklab.arbess.support.trigger.entity.TriggerEntity;
import io.tiklab.arbess.support.trigger.model.*;
import io.tiklab.arbess.support.trigger.quartz.Job;
import io.tiklab.arbess.support.trigger.quartz.RunJob;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.toolkit.join.JoinTemplate;
import io.tiklab.rpc.annotation.Exporter;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

import static io.tiklab.arbess.support.util.util.PipelineFinal.DEFAULT;

@Service
@Exporter
public class TriggerServiceImpl implements TriggerService {

    @Autowired
    TriggerDao triggerDao;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    Job manager;

    private final Logger logger = LoggerFactory.getLogger(TriggerServiceImpl.class);

    /**
     * 创建配置及任务
     * @param trigger 配置
     * @return 配置id
     */
    @Override
    public String createTrigger(Trigger trigger) {
        trigger.setCreateTime(new Timestamp(System.currentTimeMillis()));
        String cron = CronUtils.weekCron(trigger.getData(), trigger.getWeekTime());
        trigger.setCron(cron);

        TriggerEntity triggerEntity = BeanMapper.map(trigger, TriggerEntity.class);
        String triggerId = triggerDao.createTrigger(triggerEntity);

        if (trigger.getStatus() == 2){
            return triggerId;
        }

        TriggerJob triggerJob = new TriggerJob()
                .setTriggerId(triggerId)
                .setCron(cron)
                .setJobClass(RunJob.class)
                .setPipelineId(trigger.getPipelineId())
                .setGroup(DEFAULT);
        try {
            manager.addJob(triggerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
        }
        return triggerId;
    }

    @Override
    public void cloneTrigger(String pipelineId,String clonePipelineId){
        TriggerQuery triggerQuery = new TriggerQuery();
        triggerQuery.setPipelineId(pipelineId);
        List<Trigger> triggerList = findTriggerList(triggerQuery);
        for (Trigger trigger : triggerList) {
            trigger.setPipelineId(clonePipelineId);
            createTrigger(trigger);
        }
    }

    @Override
    public void deletePipelineTrigger(String pipelineId){
        TriggerQuery triggerQuery = new TriggerQuery();
        triggerQuery.setPipelineId(pipelineId);
        List<Trigger> triggerList = findTriggerList(triggerQuery);
        for (Trigger trigger : triggerList) {
            deleteTrigger(trigger.getId());
        }
    }

    @Override
    public void updateTrigger(Trigger trigger){
        String triggerId = trigger.getId();
        Trigger oldTrigger = findOneTriggerById(triggerId);
        String pipelineId = oldTrigger.getPipelineId();
        String oldCron = oldTrigger.getCron();
        String triggerName = pipelineId + "_" + oldCron + "_" + triggerId;
        manager.removeJob(DEFAULT,triggerName);

        if (trigger.getStatus() == 2){
            triggerDao.updateTrigger(BeanMapper.map(trigger, TriggerEntity.class));
            return;
        }

        String newCron = CronUtils.weekCron(trigger.getData(), trigger.getWeekTime());
        TriggerJob triggerJob = new TriggerJob()
                .setCron(newCron)
                .setTriggerId(triggerId)
                .setJobClass(RunJob.class)
                .setPipelineId(trigger.getPipelineId())
                .setGroup(DEFAULT);
        try {
            manager.addJob(triggerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
        }
        trigger.setCron(newCron);

        triggerDao.updateTrigger(BeanMapper.map(trigger, TriggerEntity.class));
    }

    @Override
    public Trigger findPipelineTrigger(String pipelineId){
        TriggerQuery triggerQuery = new TriggerQuery();
        triggerQuery.setPipelineId(pipelineId);
        List<Trigger> triggerList = findTriggerList(triggerQuery);
        if (triggerList.isEmpty()){
            Trigger trigger = new Trigger();
            trigger.setPipelineId(pipelineId);
            trigger.setExecType(1);
            trigger.setWeekTime(1);
            trigger.setData("02:00");
            trigger.setStatus(2);
            createTrigger(trigger);
        }
        return triggerList.get(0);
    }

    @Override
    public void updateTrigger(String triggerId){
        Trigger trigger = findOneTriggerById(triggerId);
        if (trigger.getExecType() == 1){
            return;
        }

        String cron = CronUtils.toCron(trigger.getCron(), 7);
        trigger.setCron(cron);
        TriggerJob triggerJob = new TriggerJob()
                .setJobClass(RunJob.class)
                .setCron(cron)
                .setTriggerId(triggerId)
                .setPipelineId(trigger.getPipelineId())
                .setGroup(DEFAULT);
        try {
            manager.addJob(triggerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
        }

        triggerDao.updateTrigger(BeanMapper.map(trigger, TriggerEntity.class));
    }

    @Override
    public List<Trigger> findTriggerList(TriggerQuery triggerQuery) {
        List<TriggerEntity> triggerEntityList = triggerDao.findTriggerList(triggerQuery);
        if (triggerEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return BeanMapper.mapList(triggerEntityList, Trigger.class);
    }

    @Override
    public Trigger findOneTriggerById(String triggerId) {
        TriggerEntity triggerConfigEntity = triggerDao.findOneTrigger(triggerId);
        Trigger trigger = BeanMapper.map(triggerConfigEntity, Trigger.class);
        joinTemplate.joinQuery(trigger);
        return trigger;
    }
    
    @Override
    public void deleteTrigger(String triggerId) {
        triggerDao.deleteTrigger(triggerId);
    }

    public List<Trigger> findAllTrigger() {
        List<TriggerEntity> allTrigger = triggerDao.findAllTrigger();
        return BeanMapper.mapList(allTrigger, Trigger.class);
    }

    @Override
    public List<Trigger> findTriggerList(List<String> idList) {
        List<TriggerEntity> triggerList = triggerDao.findTriggerList(idList);
        return BeanMapper.mapList(triggerList, Trigger.class);
    }

}
