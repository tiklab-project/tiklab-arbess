package net.tiklab.matflow.trigger.quartz;

import net.tiklab.matflow.trigger.server.PipelineTriggerServer;
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
public class PipelineInitJob implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        run();
    }

    private static final Logger logger = LoggerFactory.getLogger(PipelineInitJob.class);

    @Autowired
    PipelineJob job;

    @Autowired
    PipelineTriggerServer triggerConfigServer;

    public void run(){
        logger.info("加载定时任务。。。。。。");
        // List<Object> allConfig = triggerConfigServer.findAllConfig(null);
        // if (allConfig == null){
        //     return;
        // }
        // for (Object o : allConfig) {
        //     PipelineTime time = (PipelineTime) o;
        //     String configId = time.getConfigId();
        //     PipelineTrigger oneTriggerConfig = triggerConfigServer.findOneTriggerConfig(configId);
        //     String pipelineId = oneTriggerConfig.getPipeline().getId();
        //     try {
        //         job.addJob(pipelineId, PipelineRunJob.class,time.getCron());
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
