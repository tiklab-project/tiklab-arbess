package io.tiklab.arbess.support.trigger.quartz;

import io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
import io.tiklab.arbess.pipeline.execute.service.PipelineExecService;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.arbess.support.trigger.service.TriggerService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public  class RunJob implements org.quartz.Job {

    public static PipelineExecService execService;

    public static Job job;

    public static TriggerService triggerService;

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
    public void setTriggerConfigServer(TriggerService triggerService) {
        RunJob.triggerService = triggerService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String pipelineId = (String)map.get("pipelineId");
        String group = (String)map.get("group");
        String weekTime = (String)map.get("weekTime");
        String cron = (String)map.get("cron");

        String triggerName = jobExecutionContext.getTrigger().getKey().getName();

        logger.warn("定时任务触发，组：{}，流水线：{} 时间：{}，",group,pipelineId,weekTime);
        String loginId = LoginContext.getLoginId();
        PipelineRunMsg pipelineRunMsg = new PipelineRunMsg(pipelineId,loginId,2);
        execService.start(pipelineRunMsg);
        triggerService.updateTrigger(triggerName.split("_")[2]);
        logger.warn("组：{}，流水线：{},定时任务触发完成",group,pipelineId);
        job.removeJob(group,triggerName);
    }

}



























