package net.tiklab.matflow.support.trigger.quartz;

import net.tiklab.matflow.pipeline.execute.service.PipelineExecService;
import net.tiklab.matflow.support.trigger.service.PipelineTriggerService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public  class PipelineRunJob implements Job {

    private static PipelineExecService execService;

    private static PipelineJob pipelineJob;

    private static PipelineTriggerService triggerConfigServer;

    private static final Logger logger = LoggerFactory.getLogger(PipelineRunJob.class);

    @Autowired
    public void setExecService(PipelineExecService execService) {
        PipelineRunJob.execService = execService;
    }

    @Autowired
    public void setQuartzManager(PipelineJob pipelineJob) {
        PipelineRunJob.pipelineJob = pipelineJob;
    }

    @Autowired
    public void setTriggerConfigServer(PipelineTriggerService triggerConfigServer) {
        PipelineRunJob.triggerConfigServer = triggerConfigServer;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String pipelineId = (String)map.get("pipelineId");
        String weekTime = (String)map.get("weekTime");
        String cron = (String)map.get("cron");
        logger.info("执行定时任务，时间："+ pipelineId +"    时间："+ weekTime+"     cron"+cron);
        execService.start(pipelineId, 2);
        triggerConfigServer.deleteCronConfig(pipelineId,cron);
        pipelineJob.removeJob(pipelineId,cron);
        logger.info("执行定时任务执行完成，移除定时任务，流水线id："+pipelineId +"    时间："+ weekTime+"     cron"+cron);
    }

}



























