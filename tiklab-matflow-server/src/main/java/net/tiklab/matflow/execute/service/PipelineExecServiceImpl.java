package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.join.JoinTemplate;
import net.tiklab.matflow.definition.model.*;
import net.tiklab.matflow.definition.service.PipelinePostServer;
import net.tiklab.matflow.definition.service.PipelineService;
import net.tiklab.matflow.definition.service.PipelineStagesServer;
import net.tiklab.matflow.definition.service.PipelineTasksService;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.model.PipelineRun;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static net.tiklab.matflow.orther.until.PipelineFinal.*;

@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    JoinTemplate joinTemplate;

    @Autowired
    PipelineExecTaskService taskExecService;

    @Autowired
    PipelineExecCommonService commonService;

    @Autowired
    PipelineHomeService homeService;

    @Autowired
    PipelineTasksService tasksService;

    @Autowired
    PipelinePostServer postServer;

    @Autowired
    PipelineStagesServer stagesServer;

    @Autowired
    PipelineExecLogService execLogService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线运行历史(流水线id:历史)
    public static Map<String,PipelineExecHistory> historyMap = new HashMap<>();

    //流水线运行历史(日志Id:日志)
    public static Map<String,PipelineExecLog> logMap = new HashMap<>();

    //运行日志信息(历史id:日志id集合)
    public static Map<String,List<String>> logListMap = new HashMap<>();

    //运行日志时间(日志id:运行时间)
    public static  Map<String,Integer> runTime = new HashMap<>();

    //流水线运行历史(配置Id:日志Id)
    Map<String ,String> configLogMap = new HashMap<>();

    //运行的配置
    Map<String,List<String>> configMap = new HashMap<>();

    //创建线程池
    public static ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 流水线开始运行
     * @param pipelineId 流水线id
     * @param startWAy 运行方式
     * @return 是否正在运行
     */
    @Override
    public boolean start(String pipelineId,int startWAy){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        int threadCount = bean.getThreadCount();

        logger.info("初始化线程池线程数量："+threadCount);

        // 判断同一任务是否在运行
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        if (pipeline.getState() == 2){
            return false;
        }
        pipeline.setState(2);
        pipelineService.updatePipeline(pipeline);

        HashMap<String,Object> maps =homeService.initMap(pipeline);

        // homeService.log(LOG_PIPELINE_RUN,LOG_MD_PIPELINE_RUN,LOG_TEM_PIPELINE_EXEC, maps);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new ApplicationException(e);
        }
        executorService.submit(() -> {
            Thread.currentThread().setName(pipelineId);
            PipelineExecHistory pipelineExecHistory = commonService.initializeHistory(pipelineId,startWAy);
            begin(pipeline,pipelineExecHistory);
        });

        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            throw new ApplicationException(e);
        }

        ThreadMXBean beans = ManagementFactory.getThreadMXBean();
        int count = beans.getThreadCount();
        logger.info("执行线程池线程数量："+count);
        if (count <= threadCount ){
            logger.info("再次启动："+count);
        }

        return true;
    }

    /**
     * 查询流水线运行信息
     * @param pipelineId 流水线id
     * @return 运行状态
     */
    @Override
    public PipelineRun pipelineRunStatus(String pipelineId){
        PipelineExecHistory history = historyMap.get(pipelineId);
        joinTemplate.joinQuery(history);
        if (history == null){
            return null;
        }
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int pipelineType = pipeline.getType();

        PipelineRun execRun = new PipelineRun();
        execRun.setRunWay(history.getRunWay());
        execRun.setCreateTime(history.getCreateTime());
        User user = history.getUser();
        execRun.setExecUser(user.getName());
        if (user.getNickname() != null){
            execRun.setExecUser(user.getNickname());
        }

        List<PipelineRun> runList = new ArrayList<>();

        //多任务
        if (pipelineType == 1){
            List<PipelineExecLog> logList = new ArrayList<>();
            List<PipelineTasks> tasks = tasksService.finAllTasks(pipelineId);
            for (PipelineTasks task : tasks) {
                String configId = task.getConfigId();
                PipelineExecLog pipelineExecLog = findRunPipelineExecLog(configId);
                pipelineExecLog.setTaskType(task.getTaskType());
                logList.add(pipelineExecLog);
            }

            List<PipelinePost> allPost = postServer.findAllPost(pipelineId);
            if (allPost == null || allPost.size() == 0){
                execRun.setRunLogList(logList);
                runList.add(execRun);
            }else {
                for (PipelinePost pipelinePost : allPost) {
                    String configId = pipelinePost.getConfigId();
                    PipelineExecLog pipelineExecLog = findRunPipelineExecLog(configId);
                    logList.add(pipelineExecLog);
                }
            }
            execRun.setRunLogList(logList);
        }
        //多阶段
        if (pipelineType == 2){
            List<PipelineStages> stagesStageTask = stagesServer.findAllStagesStageTask(pipelineId);
            //并行阶段
            for (PipelineStages stages : stagesStageTask) {
                PipelineRun pipelineRun = new PipelineRun();
                List<PipelineStages> stagesList = stages.getStagesList();
                List<PipelineRun> runStagesList = new ArrayList<>();

                //并行任务
                for (PipelineStages pipelineStages : stagesList) {
                    PipelineRun run = new PipelineRun();
                    //获取阶段任务执行时间

                    String stagesId = pipelineStages.getStagesId();
                    //阶段任务
                    List<PipelineExecLog> logList = new ArrayList<>();
                    List<PipelineStagesTask> allStagesTask = stagesServer.findAllStagesTask(stagesId);
                    for (PipelineStagesTask stagesTask : allStagesTask) {
                        String configId = stagesTask.getConfigId();
                        PipelineExecLog pipelineExecLog = findRunPipelineExecLog(configId);
                        pipelineExecLog.setTaskType(stagesTask.getTaskType());
                        logList.add(pipelineExecLog);
                    }
                    run.setRunLogList(logList);
                    int time = 0;
                    if (logList.size() != 0){
                        for (PipelineExecLog log : logList) {
                            time = log.getRunTime();
                        }
                    }
                    run.setRunTime(time);
                    runStagesList.add(run);
                }
                pipelineRun.setRunList(runStagesList);
                runList.add(pipelineRun);
            }
            //后置任务
            List<PipelinePost> allPost = postServer.findAllPost(pipelineId);
            if (allPost != null && allPost.size() != 0){
                for (PipelinePost pipelinePost : allPost) {
                    PipelineRun pipelineRun = new PipelineRun();
                    List<PipelineRun> runs = new ArrayList<>();
                    String configId = pipelinePost.getConfigId();
                    PipelineRun run = new PipelineRun();
                    List<PipelineExecLog> logList = new ArrayList<>();

                    PipelineExecLog pipelineExecLog = findRunPipelineExecLog(configId);
                    pipelineExecLog.setTaskType(pipelinePost.getTaskType());
                    logList.add(pipelineExecLog);

                    run.setRunLogList(logList);
                    runs.add(run);
                    pipelineRun.setRunList(runs);

                    runList.add(pipelineRun);
                }
            }
        }

        //所有任务执行时长
        int times = 0;
        List<String> list = logListMap.get(history.getHistoryId());
        if (list != null){
            for (String s : list) {
                Integer integer = runTime.get(s);
                if (integer != null){
                    times = times +integer;
                }
            }
        }
        execRun.setRunTime(times);
        execRun.setRunList(runList);

        return execRun;
    }
    
    /**
     * 停止流水线运行
     * @param pipelineId 流水线id
     */
    @Override
    public void killInstance(String pipelineId) {
        PipelineExecHistory history = historyMap.get(pipelineId);

        if (history == null){
            updateStatus(pipelineId,PIPELINE_RUN_HALT);
            return;
        }

        String historyId = history.getHistoryId();

        List<String> list = configMap.get(pipelineId);
        if (list == null || list.size() == 0){
            updateStatus(pipelineId,PIPELINE_RUN_HALT);
            return;
        }

        for (String s : list) {
            String logId = configLogMap.get(s);
            if (logId == null){
                continue;
            }
            commonService.updateState(historyId,logId,PIPELINE_RUN_HALT);
        }
        updateStatus(pipelineId,PIPELINE_RUN_HALT);
    }

    /**
     * 查询流水线是否正在运行
     * @param pipelineId 流水线ID
     * @return 结果
     */
    @Override
    public int findPipelineState(String pipelineId){
        PipelineExecHistory pipelineExecHistory = historyMap.get(pipelineId);
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int state = pipeline.getState();

        if (pipelineExecHistory != null){
            if (state == 1){
                historyMap.remove(pipelineId);
                stop(pipelineId);
                return 1;
            }
            return 2;
        }else {
            if (state == 2){
                pipeline.setState(1);
                pipelineService.updatePipeline(pipeline);
                stop(pipelineId);
                return 2;
            }
        }
        return 1;
    }

    //构建开始
    private void begin(Pipeline pipeline,PipelineExecHistory pipelineExecHistory) {

        String pipelineId = pipeline.getId();

        String historyId = pipelineExecHistory.getHistoryId();

        //获取配置信息
        PipelineProcess pipelineProcess = new PipelineProcess();

        pipelineProcess.setPipeline(pipeline);
        pipelineProcess.setHistoryId(historyId);
        historyMap.put(pipelineId,pipelineExecHistory);

        //流程任务
        boolean courseState = beginCourse(pipelineProcess);

        //后置任务
        boolean afterState = beginAfter(pipelineProcess);

        if (courseState && afterState){
            updateStatus(pipelineId,PIPELINE_RUN_SUCCESS);
            return;
        }
        updateStatus(pipelineId,PIPELINE_RUN_ERROR);
    }

    // <--执行任务 -->
    public boolean beginCourse(PipelineProcess pipelineProcess){
        Pipeline pipeline = pipelineProcess.getPipeline();
        String pipelineId = pipeline.getId();
        int pipelineType = pipeline.getType();
        //多任务
        if (pipelineType == 1 ){
            List<PipelineTasks> allCourseConfig = tasksService.finAllTasks(pipelineId);
            if (allCourseConfig == null){
                return true;
            }
            for (PipelineTasks tasks : allCourseConfig) {
                pipelineProcess.setTaskSort(tasks.getTaskSort());
                pipelineProcess.setConfigId(tasks.getConfigId());
                pipelineProcess.setTaskType(tasks.getTaskType());
                boolean b = execTask(pipelineProcess);
                if (!b){
                    return false;
                }
            }
          return true;
        }

        //多阶段
        if (pipelineType == 2){
            List<PipelineStages> allStagesTask = stagesServer.findAllStagesStageTask(pipelineId);
            if (allStagesTask == null || allStagesTask.size() == 0 ){
                return true;
            }
            for (PipelineStages stages : allStagesTask) {

                Map<String , Future<Boolean> > map = new HashMap<>();

                //并行阶段
                List<PipelineStages> stagesList = stages.getStagesList();
                ExecutorService threadPool = Executors.newCachedThreadPool();
                for (PipelineStages stage : stagesList) {
                    //放入线程执行
                    Future<Boolean> future = threadPool.submit(() -> {
                        Thread.currentThread().setName(stage.getStagesId());
                        //获取任务，执行
                        List<PipelineStagesTask> stagesTaskList = stagesServer.findAllStagesTask(stage.getStagesId());
                        for (PipelineStagesTask stagesTask : stagesTaskList) {
                            pipelineProcess.setTaskSort(stagesTask.getTaskSort());
                            pipelineProcess.setTaskType(stagesTask.getTaskType());
                            pipelineProcess.setConfigId(stagesTask.getConfigId());
                            pipelineProcess.setStagesId(stagesTask.getStagesId());
                            boolean state = execTask(pipelineProcess);
                            if (!state){
                                return  false;
                            }
                        }
                        return true;
                    });
                    map.put(stage.getStagesId(),future);
                }
                //获取线程执行结果
                for (PipelineStages pipelineStages : stagesList) {
                    try {
                        Boolean aBoolean = map.get(pipelineStages.getStagesId()).get();
                        if (!aBoolean){
                            return false;
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
                threadPool.shutdown();
            }
        }
        return true;
    }

    public boolean beginAfter(PipelineProcess pipelineProcess){
        Pipeline pipeline = pipelineProcess.getPipeline();
        String pipelineId = pipeline.getId();
        List<PipelinePost> allAfterConfig = postServer.findAllPost(pipelineId);
        if (allAfterConfig == null){
            return true;
        }

        commonService.updateExecHistory(pipelineId,PipelineUntil.date(4)+"开始执行后置任务。");

        for (PipelinePost pipelinePost : allAfterConfig) {

            int taskSort = 0;
            List<PipelineTasks> courseConfig = tasksService.finAllTasks(pipelineId);
            if (courseConfig != null){
                taskSort = courseConfig.size() ;
            }

            pipelineProcess.setTaskSort(taskSort);
            pipelineProcess.setConfigId(pipelinePost.getConfigId());
            pipelineProcess.setTaskType(pipelinePost.getTaskType());
            pipelineProcess.setStagesId(null);

            boolean b = execTask(pipelineProcess);

            if (!b){
                return false;
            }
        }
        return true;
    }

    private boolean execTask(PipelineProcess pipelineProcess){
        String configId = pipelineProcess.getConfigId();
        String stagesId = pipelineProcess.getStagesId();
        int taskType = pipelineProcess.getTaskType();
        int taskSort = pipelineProcess.getTaskSort();
        String historyId = pipelineProcess.getHistoryId();
        Pipeline pipeline = pipelineProcess.getPipeline();

        PipelineExecLog pipelineExecLog = new PipelineExecLog(historyId,taskType,taskSort,stagesId);

        String logId = execLogService.createLog(pipelineExecLog);
        pipelineExecLog.setLogId(logId);
        logMap.put(logId,pipelineExecLog);

        configLogMap.put(configId,logId);
        List<String> list = configMap.get(pipeline.getId());
        if (list == null){
            list = new ArrayList<>();
        }
        list.add(configId);
        configMap.put(pipeline.getId(),list);

        //执行时间
        time(logId,historyId);
        PipelineProcess process = new PipelineProcess();
        process.setPipeline(pipeline);
        process.setLogId(logId);

        boolean b = taskExecService.beginCourseState(process, configId,taskType);
        stop(logId);

        if (!b){
            commonService.updateState(historyId,logId,PIPELINE_RUN_ERROR);
            return false;
        }
        commonService.updateState(historyId,logId,PIPELINE_RUN_SUCCESS);
        return true;
    }

    //查询正在运行的日志
    private PipelineExecLog findRunPipelineExecLog(String configId){
        String logId = configLogMap.get(configId);
        PipelineExecLog pipelineExecLog = logMap.get(logId);
        if (pipelineExecLog != null){
            //运行完成
            Integer integer = runTime.get(logId);
            if(integer == null){
                integer = 0;
            }
            pipelineExecLog.setRunTime(integer);
        }else {
            pipelineExecLog = execLogService.findOneLog(logId);
        }
        //等待运行
        if (pipelineExecLog == null){
            pipelineExecLog = new PipelineExecLog();
            pipelineExecLog.setRunState(3);
        }
        return pipelineExecLog;
    }

    //更新运行状态
    private void updateStatus(String pipelineId,int status){

        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        commonService.runEnd(pipelineId,status);


        //日志，消息
        HashMap<String,Object> maps = homeService.initMap(pipeline);
        maps.put("title","流水线运行信息");
        if (status == PIPELINE_RUN_SUCCESS){
            maps.put("message","成功");
        }
        if (status == PIPELINE_RUN_ERROR){
            maps.put("message","失败");
        }
        if (status == PIPELINE_RUN_HALT){
            maps.put("message","停止执行");
        }

        //清除历史与时间信息
        String historyId = historyMap.get(pipelineId).getHistoryId();
        List<String> list = logListMap.get(historyId);
        if (list != null){
            //清除日志与时间的关系
            for (String s : list) {
                runTime.remove(s);
                stop(s);
            }
        }
        logListMap.remove(historyId);
        historyMap.remove(pipelineId);

        //清除正在运行的配置及日志信息
        List<String> stringList = configMap.get(pipelineId);
        if (stringList != null){
            for (String s : stringList) {
                configLogMap.remove(s);
            }
        }
        configMap.remove(pipelineId);



        //更新流水线状态
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);

        //创建日志
        // homeService.log(LOG_PIPELINE_RUN,LOG_MD_PIPELINE_RUN,LOG_TEM_PIPELINE_RUN, maps);

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        int threadCount = bean.getThreadCount();
        logger.info("执行结束，线程池线程数量："+threadCount);
        //停止运行
        stop(pipelineId);
    }

    //停止线程
    private void stop(String threadName){
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            String nm = lstThreads[i].getName();
            if (!nm.equals(threadName)) {
                continue;
            }
            lstThreads[i].stop();
        }
    }

    //时间增加
    private void time(String logId ,String historyId){
        runTime.put(logId,0);
        List<String> list = logListMap.get(historyId);
        if (list == null){
            list = new ArrayList<>();
        }
        list.add(logId);
        logListMap.put(historyId,list);

        //创建线程池
        // ExecutorService threadPool = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            while (true){
                Thread.currentThread().setName(logId);
                int integer = runTime.get(logId);
                try {
                    Thread.sleep(1000);
                }catch (RuntimeException e){
                    throw new RuntimeException();
                }
                integer = integer +1;
                runTime.put(logId,integer);
            }
        });
    }
}


































