package net.tiklab.matflow.trigger.service.quartz;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.trigger.model.PipelineTime;
import net.tiklab.matflow.trigger.model.PipelineTriggerConfig;
import net.tiklab.matflow.trigger.server.PipelineTriggerConfigServer;
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

import java.util.List;

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
    PipelineTriggerConfigServer triggerConfigServer;

    public void run(){
        logger.info("加载定时任务。。。。。。");
        List<Object> allConfig = triggerConfigServer.findAllConfig(null);
        if (allConfig == null){
            return;
        }
        for (Object o : allConfig) {
            PipelineTime time = (PipelineTime) o;
            String configId = time.getConfigId();
            PipelineTriggerConfig oneTriggerConfig = triggerConfigServer.findOneTriggerConfig(configId);
            String pipelineId = oneTriggerConfig.getPipeline().getPipelineId();
            // logger.info("添加定时任务，时间："+ pipelineId +"  时间："+ time.getWeekTime()+" cron："+time.getCron());
            try {
                job.addJob(pipelineId, PipelineRunJob.class,time.getCron());
            } catch (SchedulerException e) {
                throw new ApplicationException(e);
            }
        }
        logger.info("定时任务加载完成");
    }

    /**
     * 初始注入scheduler
     *
     * @return
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactoryBean = new StdSchedulerFactory();
        return schedulerFactoryBean.getScheduler();
    }


}
