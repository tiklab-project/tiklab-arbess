package io.tiklab.arbess.support.trigger.quartz;

import io.tiklab.arbess.support.trigger.model.TriggerJob;
import io.tiklab.arbess.support.trigger.service.CronUtils;
import io.tiklab.arbess.support.util.util.PipelineUtil;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.APPROVE;
import static io.tiklab.arbess.support.util.util.PipelineFinal.DEFAULT;

@Component
@Scope("singleton")
public class Job {

    private static final Logger logger = LoggerFactory.getLogger(Job.class);

    private static final SchedulerFactory schedulerFactory =  new StdSchedulerFactory();

    private Scheduler scheduler = null;

    /**
     * @param job  执行不同的任务
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addJob(TriggerJob job) throws SchedulerException {

        String cron = job.getCron();
        String group = job.getGroup();
        String pipelineId = job.getPipelineId();
        Class jobClass = job.getJobClass();
        String triggerId = job.getTriggerId();


        Map<String, String> map = CronUtils.cronWeek(cron);
        String weekTime = map.get("weekTime");
        Date date = PipelineUtil.StringChengeDate(weekTime);
        if (date.getTime() <= new Date().getTime()){
            logger.warn("定时任务时间已过，跳过添加：{}，执行流水线id：{}，执行时间：{}，cron：{}",group,pipelineId, weekTime,cron);
            return;
        }

        // 任务名，任务组，任务执行类
        Scheduler scheduler = schedulerFactory.getScheduler();

        boolean isNewTrigger = false;

        JobKey jobKey = JobKey.jobKey(group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (Objects.isNull(jobDetail)){
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            //添加pipelineId执行信息
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("group",group);
            jobDataMap.put("triggerId",triggerId);
            jobDataMap.put("pipelineId",pipelineId);
            jobDataMap.put("cron",cron);
            jobDataMap.put("type",DEFAULT);
            jobDataMap.put("weekTime",map.get("weekTime"));
            jobBuilder.setJobData(jobDataMap);

            jobDetail = jobBuilder.withIdentity(group).build();

            isNewTrigger = true;

        }

        String triggerName = pipelineId + "_" + cron + "_" + triggerId;

        logger.warn("添加定时任务，triggerName：{}，执行流水线id：{}，执行时间：{}，cron：{}",triggerName,pipelineId, weekTime,cron);

        // 添加触发器
        addTrigger(scheduler,jobDetail,group,triggerName,cron,isNewTrigger);

    }

    public void addJob(String group, String pipelineId, Class jobClass, String cron,String triggerId,String approveId) throws SchedulerException {
        Map<String, String> map = CronUtils.cronWeek(cron);
        String weekTime = map.get("weekTime");
        Date date = PipelineUtil.StringChengeDate(weekTime);
        if (date.getTime() <= new Date().getTime()){
            logger.warn("定时任务时间已过，跳过添加：{}，执行流水线id：{}，执行时间：{}，cron：{}",group,pipelineId, weekTime,cron);
            return;
        }

        logger.warn("添加定时任务，定时任务组：{}，执行流水线id：{}，执行时间：{}，cron：{}",group,pipelineId, weekTime,cron);

        // 任务名，任务组，任务执行类
        Scheduler scheduler = schedulerFactory.getScheduler();

        boolean isNewTrigger = false;

        JobKey jobKey = JobKey.jobKey(group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);

        if (Objects.isNull(jobDetail)){
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass);
            //添加pipelineId执行信息
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("group",group);
            jobDataMap.put("triggerId",triggerId);
            jobDataMap.put("pipelineId",pipelineId);
            jobDataMap.put("cron",cron);
            jobDataMap.put("type",APPROVE);
            jobDataMap.put("approveId",approveId);
            jobDataMap.put("weekTime",map.get("weekTime"));
            jobBuilder.setJobData(jobDataMap);

            jobDetail = jobBuilder.withIdentity(group).build();

            isNewTrigger = true;

        }

        String triggerName = approveId + "_" + cron;

        // 添加触发器
        addTrigger(scheduler,jobDetail,group,triggerName,cron,isNewTrigger);

    }

    /**
     *  添加触发器
     * @param scheduler 定时器
     * @param jobDetail JobDetail
     * @param state 是否为新建trigger
     * @param triggerName 流水线ID
     * @param cron 时间
     * @throws SchedulerException  添加失败
     */
    private void addTrigger( Scheduler scheduler,JobDetail jobDetail,String group,String triggerName,String cron,Boolean state) throws SchedulerException {

        //触发器名
        // String triggerName = pipelineId + "_" + cron;

        // 触发器
        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, group) // 触发器名,触发器组
                .withSchedule(CronScheduleBuilder.cronSchedule(cron));// 触发器时间设定

        // Job存在则指定job
        if (!state){
            triggerBuilder.forJob(jobDetail);
        }

        CronTrigger trigger = triggerBuilder.build();

        // 调度容器设置JobDetail和Trigger
        if (state){
            scheduler.scheduleJob(jobDetail, trigger);
        }else {
            scheduler.scheduleJob(trigger);
        }

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
                logger.warn("任务："+jobName+"被修改");
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
                Class<? extends org.quartz.Job> jobClass = jobDetail.getJobClass();
                removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
                // addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass,cron);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void removeJob(String group,String triggerName){
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,group);
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器

            logger.warn("移除触发器，定时任务组：{}，triggerName：{}",group , triggerName );
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
