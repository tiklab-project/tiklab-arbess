package net.tiklab.matflow.support.trigger.quartz;

import net.tiklab.matflow.support.trigger.service.TriggerService;
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
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class InitJob implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        run();
    }

    private static final Logger logger = LoggerFactory.getLogger(InitJob.class);

    @Autowired
    Job job;

    @Autowired
    TriggerService triggerConfigServer;

    public void run(){
        logger.info("加载定时任务。。。。。。");
        // List<Object> allConfig = triggerConfigServer.findAllConfig(null);
        // if (allConfig == null){
        //     return;
        // }
        // for (Object o : allConfig) {
        //     TriggerTime time = (TriggerTime) o;
        //     String configId = time.getConfigId();
        //     Trigger oneTriggerConfig = triggerConfigServer.findOneTriggerConfig(configId);
        //     String pipelineId = oneTriggerConfig.getPipeline().getId();
        //     try {
        //         job.addJob(pipelineId, RunJob.class,time.getCron());
        //     } catch (SchedulerException e) {
        //         throw new ApplicationException(e);
        //     }
        // }
        logger.info("定时任务加载完成");
    }

    /**
     * 初始注入scheduler
     *
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }


}