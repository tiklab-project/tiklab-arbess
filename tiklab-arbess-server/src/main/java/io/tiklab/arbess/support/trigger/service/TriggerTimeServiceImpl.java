package io.tiklab.arbess.support.trigger.service;

import io.tiklab.arbess.support.trigger.dao.TriggerTimeDao;
import io.tiklab.arbess.support.trigger.entity.TriggerTimeEntity;
import io.tiklab.arbess.support.trigger.model.TriggerJob;
import io.tiklab.arbess.support.trigger.model.TriggerTime;
import io.tiklab.arbess.support.trigger.model.TriggerTimeQuery;
import io.tiklab.arbess.support.trigger.quartz.Job;
import io.tiklab.arbess.support.trigger.quartz.RunJob;
import io.tiklab.core.page.Pagination;
import io.tiklab.core.page.PaginationBuilder;
import io.tiklab.toolkit.beans.BeanMapper;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.lang3.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static io.tiklab.arbess.support.util.util.PipelineFinal.DEFAULT;


@Service
@Exporter
public class TriggerTimeServiceImpl implements TriggerTimeService {

    @Autowired
    TriggerTimeDao triggerTimeDao;

    @Autowired
    Job manager;

    private final Logger logger = LoggerFactory.getLogger(TriggerTimeServiceImpl.class);

    @Override
    public String createTriggerTime(TriggerTime triggerTime, String pipelineId){
        String cron = triggerTime.getCron();
        if (StringUtils.isEmpty(cron)){
            Integer dayTime = triggerTime.getDayTime();
            triggerTime.setDate(dayTime);
            cron = CronUtils.weekCron(triggerTime.getTime(), dayTime);
        }

        String triggerId = triggerTime.getTriggerId();
        TriggerTimeEntity triggerTimeEntity = BeanMapper.map(triggerTime, TriggerTimeEntity.class);
        triggerTimeEntity.setCron(cron);
        String timeId = triggerTimeDao.createTime(triggerTimeEntity);
        TriggerJob triggerJob = new TriggerJob()
                .setTriggerId(triggerId)
                .setCron(cron)
                .setJobClass(RunJob.class)
                .setTimeId(timeId)
                .setPipelineId(pipelineId)
                .setGroup(DEFAULT);
        try {
            manager.addJob(triggerJob);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
        }
        return timeId;
    }

    @Override
    public void updateExecTriggerTime(String timeId,String pipelineId){
        TriggerTime triggerTime = findTriggerTime(timeId);
        if (Objects.isNull(triggerTime)){
            logger.error("查询定时任务信息失败，无法更新定时任务执行结果！");
            return;
        }
        if (triggerTime.getTaskType() == 2){
            TriggerTime time = triggerTime;
            String cron = CronUtils.toCron(triggerTime.getCron(), 7);
            time.setCron(cron);
            createTriggerTime(time, pipelineId);

            // String triggerId = triggerTime.getTriggerId();
            // TriggerJob triggerJob = new TriggerJob()
            //         .setTriggerId(triggerId)
            //         .setJobClass(RunJob.class)
            //         .setCron(cron)
            //         .setTimeId(newTimeId)
            //         .setPipelineId(pipelineId)
            //         .setGroup(DEFAULT);
            //
            // logger.warn("timeId: {}",newTimeId);
            // try {
            //     manager.addJob(triggerJob);
            // } catch (SchedulerException e) {
            //     e.printStackTrace();
            //     throw new ApplicationException(50001,"当前时间已经添加过，无需重复添加。");
            // }
        }
        triggerTime.setExecStatus("2");
        updateTriggerTime(triggerTime);
    }

    @Override
    public void removeTriggerTime(String triggerId,String pipelineId){
        TriggerTimeQuery triggerTimeQuery = new TriggerTimeQuery();
        triggerTimeQuery.setTriggerId(triggerId);
        List<TriggerTime> triggerTimeList = findTriggerTimeList(triggerTimeQuery);

        if (triggerTimeList.isEmpty()){
            return;
        }
        for (TriggerTime triggerTime : triggerTimeList) {
            String cron = triggerTime.getCron();
            String triggerName = pipelineId + "_" + cron + "_" + triggerId;
            manager.removeJob(DEFAULT,triggerName);
            deleteTriggerTime(triggerTime.getTimeId());
        }
    }

    @Override
    public void deleteTriggerTime(String timeId) {
        triggerTimeDao.deleteTime(timeId);
    }

    public void updateTriggerTime(TriggerTime triggerTime) {
        TriggerTimeEntity triggerTimeEntity = BeanMapper.map(triggerTime, TriggerTimeEntity.class);
        triggerTimeDao.updateTime(triggerTimeEntity);
    }

    @Override
    public TriggerTime findTriggerTime(String timeId) {
        TriggerTimeEntity timeEntity = triggerTimeDao.findOneTime(timeId);
        return BeanMapper.map(timeEntity, TriggerTime.class);

    }

    @Override
    public List<TriggerTime> findAllTriggerTime() {
        List<TriggerTimeEntity> allTime = triggerTimeDao.findAllTime();
        return BeanMapper.mapList(allTime, TriggerTime.class);
    }

    @Override
    public List<TriggerTime> findTriggerTimeList(List<String> idList) {
        List<TriggerTimeEntity> triggerTimeList = triggerTimeDao.findTriggerTimeList(idList);
        return BeanMapper.mapList(triggerTimeList, TriggerTime.class);
    }

    @Override
    public List<TriggerTime> findTriggerTimeList(TriggerTimeQuery query) {

        List<TriggerTimeEntity> triggerTimeEntityList = triggerTimeDao.findTriggerTimeList(query);
        if (triggerTimeEntityList.isEmpty()){
            return new ArrayList<>();
        }
        return BeanMapper.mapList(triggerTimeEntityList, TriggerTime.class);
    }

    @Override
    public Pagination<TriggerTime> findTriggerTimePage(TriggerTimeQuery query) {

        Pagination<TriggerTimeEntity> pagination = triggerTimeDao.findTriggerTimePage(query);

        List<TriggerTimeEntity> dataList = pagination.getDataList();
        if (dataList == null || dataList.isEmpty()){
            return PaginationBuilder.build(pagination,new ArrayList<>());
        }

        List<TriggerTime> triggerTimeList = BeanMapper.mapList(dataList, TriggerTime.class);

        return PaginationBuilder.build(pagination,triggerTimeList);
    }


}
