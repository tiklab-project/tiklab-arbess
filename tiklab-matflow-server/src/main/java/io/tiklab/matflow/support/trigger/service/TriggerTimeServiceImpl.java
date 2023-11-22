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

import java.util.*;

import static io.tiklab.matflow.support.util.PipelineFinal.DEFAULT;


@Service
@Exporter
public class TriggerTimeServiceImpl implements TriggerTimeService {

    @Autowired
    TriggerTimeDao triggerTimeDao;

    @Autowired
    Job manager;

    @Override
    public String createTriggerTime(TriggerTime triggerTime, String pipelineId){
        List<Integer> timeList = triggerTime.getTimeList();
        if (timeList == null || timeList.isEmpty()){
            throw new ApplicationException(50001,"无法获取到执行时间");
        }
        String time = triggerTime.getTime();
        triggerTime.setDate(triggerTime.getDayTime());
        String cron = CronUtils.weekCron(time, triggerTime.getDayTime());
        triggerTime.setCron(cron);
        try {
            manager.addJob(DEFAULT,pipelineId, RunJob.class,cron);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
        }
        TriggerTimeEntity triggerTimeEntity = BeanMapper.map(triggerTime, TriggerTimeEntity.class);
        return triggerTimeDao.createTime(triggerTimeEntity);
    }

    @Override
    public TriggerTime findTriggerTime(String configId){
        List<TriggerTime> allTriggerTime = findAllTriggerTime(configId);
        if (allTriggerTime == null || allTriggerTime.isEmpty()){
            return null;
        }

        //获取时间
        List<Integer> allDataConfig = findAllDataConfig(configId);

        StringBuilder execTime = new StringBuilder();
        for (Integer integer : allDataConfig) {
            TriggerTime triggerTime = findOneConfig(configId, integer);
            triggerTime.setTimeList(allDataConfig);
            Map<String, String> map = CronUtils.cronWeek(triggerTime.getCron());
            execTime.append(map.get("cron")).append(" | ");
        }

        Integer integer = allDataConfig.get(0);
        TriggerTime triggerTime = findOneConfig(configId, integer);
        triggerTime.setTimeList(allDataConfig);
        //获取具体时间
        Map<String, String> map = CronUtils.cronWeek(triggerTime.getCron());

        triggerTime.setExecTime(execTime + map.get("time"));
        triggerTime.setTime(map.get("time"));
        triggerTime.setWeekTime(map.get("weekTime"));

        return triggerTime;
    }

    @Override
    public List<TriggerTime> findAllTriggerTime(String configId){
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
        List<TriggerTime> allTriggerTime = findAllTriggerTime(configId);
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
        List<TriggerTime> allTriggerTime = findAllTriggerTime(configId);
        if (allTriggerTime == null){
            return null;
        }
        List<Integer> list = new ArrayList<>();
        for (TriggerTime triggerTime : allTriggerTime) {
            String s = CronUtils.weekTime(triggerTime.getCron());
            triggerTime.setTime(s);
        }
        allTriggerTime.sort(Comparator.comparing(TriggerTime::getTime));

        for (TriggerTime triggerTime : allTriggerTime) {
            list.add(triggerTime.getDate());
        }
        return list;
    }

    @Override
    public void deleteAllTime(String triggerId,String pipelineId){
        List<TriggerTime> triggerTimeConfig = findAllTriggerTime(triggerId);
        if (triggerTimeConfig == null){
            return;
        }
        for (TriggerTime triggerTime : triggerTimeConfig) {
            String cron = triggerTime.getCron();
            manager.removeJob(DEFAULT,pipelineId, cron);
            deleteTime(triggerTime.getTimeId());
        }
    }

    @Override
    public Boolean deleteCronTime(String pipelineId,String timeId){
        TriggerTime oneTriggerTime = findOneTime(timeId);
        if (oneTriggerTime.getTaskType() == 1){
            // deleteTime(timeId);
            return true;
        }
        String cron = oneTriggerTime.getCron();
        String[] s = cron.split(" ");
        String time = s[2] + ":" + s[1];
        int date = oneTriggerTime.getDate();
        String weekCron = CronUtils.weekCron(time, date);
        oneTriggerTime.setCron(weekCron);
        try {
            manager.addJob(DEFAULT,pipelineId, RunJob.class,weekCron);
        } catch (SchedulerException e) {
            throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
        }
        updateTime(oneTriggerTime);
        return false;
    }

    @Override
    public void deleteTime(String timeId) {
        triggerTimeDao.deleteTime(timeId);
    }

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
