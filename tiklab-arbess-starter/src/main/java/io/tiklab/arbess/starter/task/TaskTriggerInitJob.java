package io.tiklab.arbess.starter.task;

import io.tiklab.arbess.support.trigger.model.Trigger;
import io.tiklab.arbess.support.trigger.model.TriggerTime;
import io.tiklab.arbess.support.trigger.quartz.Job;
import io.tiklab.arbess.support.trigger.quartz.RunJob;
import io.tiklab.arbess.support.trigger.service.CronUtils;
import io.tiklab.arbess.support.trigger.service.TriggerService;
import io.tiklab.arbess.support.trigger.service.TriggerTimeService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.exception.ApplicationException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Configuration
public class TaskTriggerInitJob implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        run();
    }

    private static final Logger logger = LoggerFactory.getLogger(TaskTriggerInitJob.class);

    @Autowired
    Job job;

    @Autowired
    TriggerService triggerServer;


    @Autowired
    TriggerTimeService triggerTimeService;

    // @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        run();
    }

    public void run(){
        try {
            List<TriggerTime> triggerList = triggerTimeService.findAllTime();
            if (Objects.isNull(triggerList) || triggerList.isEmpty()){
                return;
            }
            logger.info("Load scheduled tasks......");
            for (TriggerTime triggerTime : triggerList) {
                String timeId = triggerTime.getTimeId();
                String triggerId = triggerTime.getTriggerId();
                String time = CronUtils.weekTime(triggerTime.getCron());
                Date date = PipelineUtil.StringChengeDate(time);
                if (date.getTime() < new Date().getTime()){
                    continue;
                }
                Trigger trigger = triggerServer.findOneTriggerById(triggerId);
                String pipelineId = trigger.getPipeline().getId();
                try {
                    job.addJob(PipelineFinal.DEFAULT,pipelineId, RunJob.class,triggerTime.getCron(),triggerId);
                } catch (SchedulerException e) {
                    throw new ApplicationException(e);
                }
            }
            logger.info("Timed task loading completed!");
        }catch (Exception e) {
            logger.error(" Timed task loading  error : " + e.getMessage());
        }
    }

    @Scheduled(cron = "0 01 00 ? * 2")
    // @Bean
    public void refreshTrigger() {
        List<TriggerTime> triggerTimeList = triggerTimeService.findAllTime();
        if (triggerTimeList.isEmpty()){
            return;
        }

        List<TriggerTime> timeList = triggerTimeList.stream()
                .filter(triggerTime -> triggerTime.getTaskType() != 1).toList();

        for (TriggerTime triggerTime : timeList) {
            String cron = triggerTime.getCron();

            String triggerId = triggerTime.getTriggerId();
            Trigger trigger = triggerServer.findOneTriggerById(triggerId);
            String id = trigger.getPipeline().getId();

            String[] split = cron.split(" ");

            int[] ints = {
                    Integer.parseInt(split[6]),
                    Integer.parseInt(split[4]),
                    Integer.parseInt(split[3]),
                    Integer.parseInt(split[2]),
                    Integer.parseInt(split[1])
            };

            // 指定时间
            LocalDateTime specifiedDateTime = LocalDateTime.of(ints[0],ints[1], ints[2], ints[3], ints[4], 0);

            Instant instant = specifiedDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant();
            long timestamp = instant.toEpochMilli();

            if (timestamp > new Date().getTime()){
                continue;
            }

            // 获取指定时间的7天后
            LocalDateTime dateTime = specifiedDateTime.plusDays(7);

            int year = dateTime.getYear();
            int month = dateTime.getMonthValue();
            int day = dateTime.getDayOfMonth();
            int hour = ints[3];
            int minute = ints[4];
            String newCron = "00 " + minute + " " + hour + " " + day + " " + month + " ? " + year;

            triggerTime.setCron(newCron);
            triggerTimeService.updateTime(triggerTime);

            triggerServer.updateTrigger(trigger.setState("1"));

            try {
                job.addJob(PipelineFinal.DEFAULT,id, RunJob.class,newCron,triggerId);
            } catch (SchedulerException e) {
                throw new ApplicationException(e);
            }
        }

    }





}
