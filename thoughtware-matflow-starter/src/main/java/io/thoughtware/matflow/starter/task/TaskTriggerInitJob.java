package io.thoughtware.matflow.starter.task;

import io.thoughtware.matflow.support.trigger.model.Trigger;
import io.thoughtware.matflow.support.trigger.model.TriggerQuery;
import io.thoughtware.matflow.support.trigger.model.TriggerTime;
import io.thoughtware.matflow.support.trigger.quartz.Job;
import io.thoughtware.matflow.support.trigger.quartz.RunJob;
import io.thoughtware.matflow.support.trigger.service.TriggerService;
import io.thoughtware.matflow.support.util.PipelineFinal;
import io.thoughtware.matflow.support.util.PipelineUtil;
import io.thoughtware.core.exception.ApplicationException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Configuration
public class TaskTriggerInitJob implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TaskTriggerInitJob.class);

    @Autowired
    Job job;

    @Autowired
    TriggerService triggerServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        run();
    }

    public void run(){
        logger.info("Load scheduled tasks......");
        TriggerQuery triggerQuery = new TriggerQuery();
        triggerQuery.setState("1");
        List<Object> triggerList = triggerServer.findAllTrigger(triggerQuery);
        
        if (!Objects.isNull(triggerList) && !triggerList.isEmpty()){
            for (Object o : triggerList) {
                TriggerTime triggerTime = (TriggerTime) o;
                String configId = triggerTime.getTriggerId();
                String time = triggerTime.getWeekTime();
                Date date = PipelineUtil.StringChengeDate(time);
                if (date.getTime() < new Date().getTime()){
                    continue;
                }
                Trigger trigger = triggerServer.findOneTriggerById(configId);
                String pipelineId = trigger.getPipeline().getId();
                try {
                    job.addJob(PipelineFinal.DEFAULT,pipelineId, RunJob.class,triggerTime.getCron());
                } catch (SchedulerException e) {
                    throw new ApplicationException(e);
                }
            }
        }
        logger.info("Timed task loading completed!");
    }



}
