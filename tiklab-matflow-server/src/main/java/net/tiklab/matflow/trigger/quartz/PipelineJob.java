package net.tiklab.matflow.trigger.quartz;

import net.tiklab.matflow.orther.until.PipelineCronUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Scope("singleton")
public class PipelineJob {

    private static final Logger logger = LoggerFactory.getLogger(PipelineJob.class);

    private static final SchedulerFactory schedulerFactory =  new StdSchedulerFactory();

    private Scheduler scheduler = null;

    /**
     * @param jobClass  执行不同的任务
     * @param cron   时间设置，参考quartz说明文档
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addJob(String pipelineId, Class jobClass, String cron) throws SchedulerException {
        Map<String, String> map = PipelineCronUtils.cronWeek(cron);
        logger.info("添加定时任务，流水线id:"+pipelineId+"    时间："+ map.get("weekTime") +"  cron:" +cron );

        //任务名
        String jobName =pipelineId + "_"+cron;
        //任务组名
        // String jobGroupName ="group"+"_"+ pipelineId +"_"+cron;
        //触发器名
        String triggerName = pipelineId + "_"+cron;
        //触发器组名
        // String triggerGroupName =pipelineId;

        // 任务名，任务组，任务执行类
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobBuilder jobBuilder = JobBuilder.newJob(jobClass);

        //添加pipelineId执行信息
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("pipelineId",pipelineId);
        jobDataMap.put("cron",cron);
        jobDataMap.put("weekTime",map.get("weekTime"));
        jobBuilder.setJobData(jobDataMap);

        //任务
        // JobDetail jobDetail= jobBuilder.withIdentity(jobName, jobGroupName).build();
        JobDetail jobDetail= jobBuilder.withIdentity(jobName).build();
        // JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();

        // 触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        // 触发器名,触发器组
        triggerBuilder.withIdentity(triggerName, pipelineId);
        triggerBuilder.startNow();

        // 触发器时间设定
        triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));

        // 创建Trigger对象
        CronTrigger trigger = (CronTrigger) triggerBuilder.build();

        // 调度容器设置JobDetail和Trigger
        scheduler.scheduleJob(jobDetail, trigger);

        // 启动
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    public boolean findTask(String triggerName ,String triggerGroupName){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            JobKey jobKey = JobKey.jobKey(" ", " ");
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return true;
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * @Description: 修改一个任务的触发时间
     * @param jobName
     * @param jobGroupName
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名
     * @param cron   时间设置，参考quartz说明文档
     */
    public  void modifyJobTime(String jobName,String jobGroupName, String triggerName, String triggerGroupName, String cron) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                System.out.println("任务："+jobName+"被修改");
                /** 方式一 ：调用 rescheduleJob 开始 */
               /* // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);*/
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
                Class<? extends Job> jobClass = jobDetail.getJobClass();
                removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                // addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass,cron);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void removeJob(String pipelineId ,String cron){
        String jobName =pipelineId + "_"+cron;
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName);
            JobKey jobKey = JobKey.jobKey(jobName);
            scheduler.getJobDetail(jobKey);
            logger.info("移除触发器，流水线id："+pipelineId+"     时间：" + PipelineCronUtils.weekTime(cron) +"   cron:"+ cron  );
            scheduler.deleteJob(jobKey);
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description: 移除一个任务
     *
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public void removeJob(String jobName, String jobGroupName,String triggerName, String triggerGroupName) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     */
    public   void startJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     */
    public void shutdownJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前正在执行的任务
     * @return
     */
    public  boolean getCurrentJobs(String name){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            List<JobExecutionContext> jobContexts = scheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext context : jobContexts) {
                if (name.equals(context.getTrigger().getJobKey().getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


}
