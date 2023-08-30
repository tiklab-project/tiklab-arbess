package io.tiklab.matflow.support.trigger.quartz;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.support.trigger.model.Trigger;
import io.tiklab.matflow.support.trigger.model.TriggerTime;
import io.tiklab.matflow.support.trigger.service.TriggerService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

@Configuration
public class InitJob implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(InitJob.class);

    @Autowired
    Job job;

    @Autowired
    TriggerService triggerConfigServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // run();
    }

    public void run(){
        logger.info("Load scheduled tasks......");
        List<Object> allConfig = triggerConfigServer.findAllTrigger(null);
        if (allConfig != null){
            for (Object o : allConfig) {
                TriggerTime time = (TriggerTime) o;
                String configId = time.getTriggerId();
                Trigger oneTriggerConfig = triggerConfigServer.findOneTriggerById(configId);
                String pipelineId = oneTriggerConfig.getPipeline().getId();
                try {
                    job.addJob(pipelineId, RunJob.class,time.getCron());
                } catch (SchedulerException e) {
                    throw new ApplicationException(e);
                }
            }
        }
        logger.info("Timed task loading completed!");
    }

    /**
     * 初始注入scheduler
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }


}
