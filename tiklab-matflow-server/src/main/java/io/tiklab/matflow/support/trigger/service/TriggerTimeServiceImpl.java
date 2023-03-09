package io.tiklab.matflow.support.trigger.service;

import io.tiklab.matflow.support.trigger.dao.TriggerTimeDao;
import io.tiklab.matflow.support.trigger.entity.TriggerTimeEntity;
import io.tiklab.matflow.support.trigger.model.TriggerTime;
import io.tiklab.matflow.support.trigger.quartz.Job;
import io.tiklab.matflow.support.trigger.quartz.RunJob;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.rpc.annotation.Exporter;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Service
@Exporter
public class TriggerTimeServiceImpl implements TriggerTimeService {


    @Autowired
    private TriggerTimeDao triggerTimeDao;

    @Autowired
    Job manager;


    @Override
    public String createTime(TriggerTime triggerTime) {
        TriggerTimeEntity triggerTimeEntity = BeanMapper.map(triggerTime, TriggerTimeEntity.class);
        return triggerTimeDao.createTime(triggerTimeEntity);
    }

    @Override
    public String createTimeConfig(TriggerTime pipelineTriggerTime, String pipelineId){
        List<Integer> timeList = pipelineTriggerTime.getTimeList();
        if (timeList == null || timeList.size() == 0){
            throw new ApplicationException(50001,"无法获取到执行时间");
        }
        String time = pipelineTriggerTime.getTime();
        String id = null;
        for (Integer integer : timeList) {
            pipelineTriggerTime.setDate(integer);
            String cron = CronUtils.weekCron(time, integer);
            pipelineTriggerTime.setCron(cron);
            try {
                manager.addJob(pipelineId, RunJob.class,cron);
            } catch (SchedulerException e) {
                throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
            }
            id = createTime(pipelineTriggerTime);
        }
        return id;
    }

    @Override
    public TriggerTime findTimeConfig(String configId){
        List<TriggerTime> allTriggerTime = findAllTimeConfig(configId);
        if (allTriggerTime == null || allTriggerTime.size() == 0){
            return null;
        }

        //获取时间
        List<Integer> allDataConfig = findAllDataConfig(configId);

        Integer integer = allDataConfig.get(0);
        TriggerTime triggerTime = findOneConfig(configId, integer);
        triggerTime.setTimeList(allDataConfig);
        //获取具体时间
        Map<String, String> map = CronUtils.cronWeek(triggerTime.getCron());
        triggerTime.setExecTime(map.get("cron"));
        triggerTime.setTime(map.get("time"));
        triggerTime.setWeekTime(map.get("weekTime"));
        return triggerTime;
    }

    @Override
    public List<TriggerTime> findAllTimeConfig(String configId){
        List<TriggerTime> allTriggerTime = findAllTime();
        if (allTriggerTime == null){
            return null;
        }
        List<TriggerTime> list = new ArrayList<>();
        for (TriggerTime triggerTime : allTriggerTime) {
            if (triggerTime.getTriggerId().equals(configId)){
                list.add(triggerTime);
            }
        }
        return list;
    }

    public TriggerTime findOneConfig(String configId, int day){
        List<TriggerTime> allTriggerTime = findAllTime();
        if (allTriggerTime == null){
            return null;
        }
        for (TriggerTime triggerTime : allTriggerTime) {
            String timeConfigId = triggerTime.getTriggerId();
            int date = triggerTime.getDate();
            if (timeConfigId.equals(configId) && date == day){
                return triggerTime;
            }
        }
        return null;
    }

    @Override
    public TriggerTime fondCronConfig(String configId, String cron){
        List<TriggerTime> allTriggerTime = findAllTimeConfig(configId);
        if (allTriggerTime == null){
            return null;
        }
        for (TriggerTime triggerTime : allTriggerTime) {
            String configId1 = triggerTime.getTriggerId();
            String cron1 = triggerTime.getCron();
            if (configId1.equals(configId) && cron1.equals(cron)){
                return triggerTime;
            }
        }
        return null;
    }

    @Override
    public List<Integer> findAllDataConfig(String configId){
        List<TriggerTime> allTriggerTime = findAllTime();
        if (allTriggerTime == null){
            return null;
        }
        List<Integer> list = new ArrayList<>();
        List<Integer> afterList = new ArrayList<>();
        for (TriggerTime triggerTime : allTriggerTime) {
            int date = triggerTime.getDate();
            if (triggerTime.getTriggerId().equals(configId) && date != 0){
                int week = PipelineUtil.week();
                if (date > week){
                    list.add(date);
                }else {
                    afterList.add(date);
                }
            }
            Collections.sort(list);
            Collections.sort(afterList);
        }
        list.addAll(afterList);
        return list;
    }

    @Override
    public void deleteAllTime(String configId,String pipelineId){
        List<TriggerTime> triggerTimeConfig = findAllTimeConfig(configId);
        if (triggerTimeConfig == null){
            return;
        }
        for (TriggerTime triggerTime : triggerTimeConfig) {
            String cron = triggerTime.getCron();
            manager.removeJob(pipelineId, cron);
            deleteTime(triggerTime.getTimeId());
        }
    }

    @Override
    public void deleteCronTime(String pipelineId,String timeId){
        TriggerTime oneTriggerTime = findOneTime(timeId);
        if (oneTriggerTime.getTaskType() == 1){
            deleteTime(timeId);
            return;
        }
        String cron = oneTriggerTime.getCron();
        String[] s = cron.split(" ");
        String time = s[2] + ":" + s[1];
        int date = oneTriggerTime.getDate();
        String weekCron = CronUtils.weekCron(time, date);
        oneTriggerTime.setCron(weekCron);
        try {
            manager.addJob(pipelineId, RunJob.class,weekCron);
        } catch (SchedulerException e) {
            throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
        }
        updateTime(oneTriggerTime);
    }

    @Override
    public void deleteTime(String timeId) {
        triggerTimeDao.deleteTime(timeId);
    }

    @Override
    public void updateTime(TriggerTime triggerTime) {
        TriggerTimeEntity triggerTimeEntity = BeanMapper.map(triggerTime, TriggerTimeEntity.class);
        triggerTimeDao.updateTime(triggerTimeEntity);
    }

    @Override
    public TriggerTime findOneTime(String timeId) {
        TriggerTimeEntity timeEntity = triggerTimeDao.findOneTime(timeId);
        return BeanMapper.map(timeEntity, TriggerTime.class);

    }

    @Override
    public List<TriggerTime> findAllTime() {
        return BeanMapper.mapList(triggerTimeDao.findAllTime(), TriggerTime.class);
    }

    @Override
    public List<TriggerTime> findAllTimeList(List<String> idList) {
        return BeanMapper.mapList(triggerTimeDao.findAllTimeList(idList), TriggerTime.class);
    }


}
