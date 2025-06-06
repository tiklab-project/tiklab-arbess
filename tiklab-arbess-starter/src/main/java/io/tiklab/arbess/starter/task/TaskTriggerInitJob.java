package io.tiklab.arbess.starter.task;

import io.tiklab.arbess.support.trigger.model.*;
import io.tiklab.arbess.support.trigger.quartz.Job;
import io.tiklab.arbess.support.trigger.quartz.RunJob;
import io.tiklab.arbess.support.trigger.service.CronUtils;
import io.tiklab.arbess.support.trigger.service.TriggerService;
import io.tiklab.arbess.support.trigger.service.TriggerTimeService;
import io.tiklab.arbess.support.util.util.PipelineFinal;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.eam.client.author.config.TiklabApplicationRunner;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.DEFAULT;

@Configuration
public class TaskTriggerInitJob implements TiklabApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(TaskTriggerInitJob.class);

    @Autowired
    Job job;

    @Autowired
    TriggerService triggerServer;


    @Autowired
    TriggerTimeService triggerTimeService;


    @Override
    public void run(){
        logger.info(" load scheduled tasks......");
        addTriggerJob();
        logger.info(" timed task loading completed!");
    }


    public void addTriggerJob(){
        try {
            TriggerQuery triggerQuery = new TriggerQuery();
            triggerQuery.setState(1);
            List<Trigger> triggerList = triggerServer.findTriggerList(triggerQuery);

            for (Trigger trigger : triggerList) {

                String cron = trigger.getCron();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date inputTime = sdf.parse(CronUtils.weekTime(cron));
                    boolean after = inputTime.after(new Date());
                    if (!after){
                        continue;
                    }
                } catch (Exception e) {
                    logger.error("corn转换时间失败：{}",cron);
                    return;
                }

                TriggerJob triggerJob = new TriggerJob()
                        .setTriggerId(trigger.getId())
                        .setCron(cron)
                        .setJobClass( RunJob.class)
                        .setPipelineId(trigger.getPipelineId())
                        .setGroup(DEFAULT);
                try {
                    job.addJob(triggerJob);
                } catch (SchedulerException e) {
                    throw new ApplicationException(e);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("timed task loading  error : {}", e.getMessage());
        }
    }

}
