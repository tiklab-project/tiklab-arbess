package io.tiklab.arbess.support.trigger.quartz;

import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.arbess.pipeline.execute.model.PipelineRunMsg;
import io.tiklab.arbess.pipeline.execute.service.PipelineExecService;
import io.tiklab.arbess.pipeline.instance.model.PipelineInstance;
import io.tiklab.arbess.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.arbess.support.approve.model.ApprovePipeline;
import io.tiklab.arbess.support.approve.model.ApprovePipelineQuery;
import io.tiklab.arbess.support.approve.service.ApprovePipelineService;
import io.tiklab.arbess.support.approve.service.ApproveService;
import io.tiklab.eam.common.context.LoginContext;
import io.tiklab.arbess.support.trigger.service.TriggerService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.*;

@Component
public  class RunJob implements org.quartz.Job {

    public static PipelineExecService execService;

    public static Job job;

    public static TriggerService triggerService;

    private static ApprovePipelineService approvePipelineService;

    private static PipelineInstanceService pipelineInstanceService;

    private static ApproveService approveService;

    private static final Logger logger = LoggerFactory.getLogger(RunJob.class);

    @Autowired
    public void setExecService(PipelineExecService execService) {
        RunJob.execService = execService;
    }

    @Autowired(required = false)
    public void setApprovePipelineService(ApprovePipelineService approvePipelineService) {
        RunJob.approvePipelineService = approvePipelineService;
    }

    @Autowired
    public void setPipelineInstanceService(PipelineInstanceService pipelineInstanceService) {
        RunJob.pipelineInstanceService = pipelineInstanceService;
    }

    @Autowired(required = false)
    public void setApproveService(ApproveService approveService) {
        RunJob.approveService = approveService;
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
        String approveId = (String)map.get("approveId");
        String type = (String)map.get("type");

        String triggerName = jobExecutionContext.getTrigger().getKey().getName();

        logger.warn("定时任务触发，组：{}，流水线：{} 时间：{}，",group,pipelineId,weekTime);

        String loginId = LoginContext.getLoginId();
        // 审批
        if (type.equals(APPROVE)){
            if (!Objects.isNull(approvePipelineService)){
                ApprovePipelineQuery approvePipelineQuery = new ApprovePipelineQuery();
                approvePipelineQuery.setApproveId(approveId);
                int  i = 0;
                List<ApprovePipeline> approveUserList  = approvePipelineService.findApprovePipelineList(approvePipelineQuery);
                for (ApprovePipeline approvePipeline : approveUserList){
                    Pipeline pipeline = approvePipeline.getPipeline();
                    PipelineRunMsg runMsg = new PipelineRunMsg(pipeline.getId(),loginId,2);
                    runMsg.setPipeline(pipeline);
                    runMsg.setRunWay(2);
                    if (i == 0){
                        approvePipeline.setStatus(RUN_RUN);
                    }
                    runMsg.setRunStatus(RUN_WAIT);
                    PipelineInstance pipelineInstance = pipelineInstanceService.initializeInstance(runMsg);

                    String instanceId = pipelineInstance.getInstanceId();
                    runMsg.setInstanceId(instanceId);
                    approvePipeline.setInstanceId(instanceId);
                    approvePipelineService.updateApprovePipeline(approvePipeline);
                    if (i == 0){
                        // 运行
                        approvePipeline.setStatus(RUN_RUN);
                        try {
                            execService.validWailExecPipeline(runMsg);
                            execService.start(runMsg);
                        }catch (Exception e){
                            e.printStackTrace();
                            approvePipeline.setStatus(RUN_ERROR);
                            approveService.updateApprovePipeline(approvePipeline);
                        }
                    }
                    i++;
                }
            }
        }else{

            PipelineRunMsg pipelineRunMsg = new PipelineRunMsg(pipelineId,loginId,2);

            execService.validWailExecPipeline(pipelineRunMsg);
            execService.start(pipelineRunMsg);

            triggerService.updateTrigger(triggerName.split("_")[2]);
        }
        logger.warn("组：{}，流水线：{}，类型：{},定时任务触发完成",group,pipelineId,type);
        job.removeJob(group,triggerName);
    }

}



























