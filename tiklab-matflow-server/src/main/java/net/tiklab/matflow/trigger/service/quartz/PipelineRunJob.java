package net.tiklab.matflow.trigger.service.quartz;

import net.tiklab.matflow.execute.service.PipelineExecHistoryServiceImpl;
import net.tiklab.matflow.execute.service.PipelineExecService;
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

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecHistoryServiceImpl.class);

    @Autowired
    public void setExecService(PipelineExecService execService) {
        PipelineRunJob.execService = execService;
    }

    @Autowired
    public void setQuartzManager(PipelineJob pipelineJob) {
        PipelineRunJob.pipelineJob = pipelineJob;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String pipelineId = (String)map.get("pipelineId");
        String cron = (String)map.get("cron");
        logger.info("执行定时任务，时间："+ cron );
        execService.start(pipelineId);
        logger.info("执行定时任务执行完成，移除："+ cron);
        pipelineJob.removeJob(pipelineId,cron);
    }

}



























