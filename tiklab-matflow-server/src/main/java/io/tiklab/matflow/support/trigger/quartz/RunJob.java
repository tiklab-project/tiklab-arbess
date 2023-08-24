package io.tiklab.matflow.support.trigger.quartz;

import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.matflow.pipeline.execute.service.PipelineExecService;
import io.tiklab.matflow.support.trigger.service.TriggerService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public  class RunJob implements org.quartz.Job {

    private static PipelineExecService execService;

    private static Job job;

    private static TriggerService triggerConfigServer;

    private static final Logger logger = LoggerFactory.getLogger(RunJob.class);

    @Autowired
    public void setExecService(PipelineExecService execService) {
        RunJob.execService = execService;
    }

    @Autowired
    public void setQuartzManager(Job job) {
        RunJob.job = job;
    }

    @Autowired
    public void setTriggerConfigServer(TriggerService triggerConfigServer) {
        RunJob.triggerConfigServer = triggerConfigServer;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String pipelineId = (String)map.get("pipelineId");
        String weekTime = (String)map.get("weekTime");
        String cron = (String)map.get("cron");
        logger.warn("执行定时任务，时间："+ pipelineId +"    时间："+ weekTime+"     cron"+cron);
        String loginId = LoginContext.getLoginId();
        execService.start(pipelineId,loginId, 2);
        triggerConfigServer.deleteCronConfig(pipelineId,cron);
        job.removeJob(pipelineId,cron);
        logger.warn("定时任务执行完成，移除定时任务，流水线id："+pipelineId +"    时间："+ weekTime+"     cron"+cron);
    }

}



























