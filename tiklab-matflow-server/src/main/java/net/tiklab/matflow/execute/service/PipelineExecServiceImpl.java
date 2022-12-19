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
import net.tiklab.matflow.execute.model.PipelineRunLog;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
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
    PipelineExecLogService logService;
    
    @Autowired
    PipelineExecHistoryService historyService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线运行历史(流水线id:历史)
    public static Map<String,PipelineExecHistory> historyMap = new HashMap<>();

    //流水线运行历史(日志Id:日志)
    public static Map<String,PipelineExecLog> logMap = new HashMap<>();

    //运行日志时间(日志id:运行时间)
    public static  Map<String,Integer> runTime = new HashMap<>();

    //流水线运行历史(配置Id:日志Id)
    Map<String ,String> configLogMap = new HashMap<>();

    //创建线程池
    public static ExecutorService executorService = Executors.newCachedThreadPool();

    //时间线程池
    public static ExecutorService threadPool = Executors.newCachedThreadPool();

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

        executorService.submit(() -> {
            Thread.currentThread().setName(pipelineId);
            PipelineExecHistory pipelineExecHistory = commonService.initializeHistory(pipelineId,startWAy);
            String historyId = pipelineExecHistory.getHistoryId();
            historyMap.put(pipelineId,pipelineExecHistory);
            time(historyId);
            begin(pipeline,historyId);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new ApplicationException(e);
        }

        return true;
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
    private void begin(Pipeline pipeline,String historyId) {

        String pipelineId = pipeline.getId();

        //流程任务
        boolean courseState = beginCourse(pipeline,historyId);

        //后置任务
        boolean afterState = beginAfter(pipeline,historyId);

        if (courseState && afterState){
            updateStatus(pipelineId,PIPELINE_RUN_SUCCESS);
            return;
        }
        updateStatus(pipelineId,PIPELINE_RUN_ERROR);
    }

    private void initPipelineLog(String pipelineId,String historyId){
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int type = pipeline.getType();
        int sort = 0;
        if (type == 1){
            List<PipelineTasks> list = tasksService.finAllTasks(pipelineId);
            sort = list.size()+1;
            for (PipelineTasks tasks : list) {
                createPipelineLog(type,tasks,historyId);
            }
        }

        if (type == 2){
            List<PipelineStages> stagesMainStage = stagesServer.findAllStagesMainStage(pipelineId);
            sort = stagesMainStage.size()+1;
            for (PipelineStages stages : stagesMainStage) {
                String stagesId = stages.getStagesId();
                List<PipelineStages> mainStage = stagesServer.findAllMainStage(stagesId);
                for (PipelineStages pipelineStages : mainStage) {
                    String id = pipelineStages.getStagesId();
                    List<PipelineStagesTask> allStagesTask = stagesServer.findAllStagesTask(id);
                    for (PipelineStagesTask stagesTask : allStagesTask) {
                        createPipelineLog(type,stagesTask,historyId);
                    }
                }
            }
        }

        List<PipelinePost> allPost = postServer.findAllPost(pipelineId);
        if (allPost == null || allPost.size() == 0){
            return;
        }
        for (PipelinePost post : allPost) {
            post.setTaskSort(post.getTaskSort()+sort);
            createPipelineLog(0,post,historyId);
        }
    }

    private void createPipelineLog(int type,Object o,String historyId){
        int taskSort = 0;
        int taskType = 0;
        String stagesId = null;
        String configId = null;
        String name = null;

        if (type == 0){
            PipelinePost post = (PipelinePost) o;
            taskSort = post.getTaskSort();
            taskType = post.getTaskType();
            configId = post.getConfigId();
            name = post.getName();
        }

        if (type == 1){
            PipelineTasks tasks = (PipelineTasks) o;
            taskSort = tasks.getTaskSort();
            taskType = tasks.getTaskType();
            configId = tasks.getConfigId();
            name = tasks.getName();
        }

        if (type == 2){
            PipelineStagesTask stagesTask = (PipelineStagesTask) o;
            taskSort = stagesTask.getTaskSort();
            taskType = stagesTask.getTaskType();
            configId = stagesTask.getConfigId();
            stagesId = stagesTask.getStagesId();
            name = stagesTask.getName();
        }

        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setHistoryId(historyId);
        pipelineExecLog.setTaskSort(taskSort);
        pipelineExecLog.setTaskType(taskType);
        pipelineExecLog.setStagesId(stagesId);
        pipelineExecLog.setRunState(3);
        pipelineExecLog.setTaskName(name);
        pipelineExecLog.setRunLog("");
        String logId = logService.createLog(pipelineExecLog);
        logMap.put(logId,pipelineExecLog);
        configLogMap.put(configId,logId);
    }

    // <--执行任务 -->
    public boolean beginCourse(Pipeline pipeline, String historyId) throws ApplicationException{
        String pipelineId = pipeline.getId();
        int pipelineType = pipeline.getType();

        //初始化日志信息
        initPipelineLog(pipelineId,historyId);
        //多任务
        if (pipelineType == 1 ){
            List<PipelineTasks> allCourseConfig = tasksService.finAllTasks(pipelineId);
            if (allCourseConfig == null){
                return true;
            }
            for (PipelineTasks tasks : allCourseConfig) {
                String configId = tasks.getConfigId();
                String s = configLogMap.get(configId);
                PipelineExecLog pipelineExecLog = logMap.get(s);
                pipelineExecLog.setLogId(s);
                pipelineExecLog.setRunState(0);
                logMap.put(s,pipelineExecLog);
                PipelineProcess process = new PipelineProcess(pipelineId);
                pipelineExecLog.setLogId(s);
                process.setConfigId(configId);
                process.setHistoryId(historyId);
                boolean b = execTask(process,pipelineExecLog);
                if (!b){
                    return false;
                }
            }
          return true;
        }

        //多阶段
        if (pipelineType == 2){
            List<PipelineStages> allStagesTask = stagesServer.findAllStagesMainStage(pipelineId);
            if (allStagesTask == null || allStagesTask.size() == 0 ){
                return true;
            }
            for (PipelineStages stages : allStagesTask) {
                String stagesId = stages.getStagesId();
                Map<String , Future<Boolean> > map = new HashMap<>();

                //并行阶段
                List<PipelineStages> stagesList = stagesServer.findAllMainStage(stagesId);
                ExecutorService threadPool = Executors.newCachedThreadPool();
                for (PipelineStages stage : stagesList) {
                    //放入线程执行
                    Future<Boolean> future = threadPool.submit(() -> {
                        Thread.currentThread().setName(stage.getStagesId());
                        //获取任务，执行
                        List<PipelineStagesTask> stagesTaskList = stagesServer.findAllStagesTask(stage.getStagesId());
                        for (PipelineStagesTask stagesTask : stagesTaskList) {
                            String configId = stagesTask.getConfigId();
                            PipelineProcess process = new PipelineProcess(pipelineId);
                            String logId = configLogMap.get(configId);
                            PipelineExecLog pipelineExecLog = logMap.get(logId);
                            pipelineExecLog.setLogId(logId);
                            pipelineExecLog.setRunState(0);
                            logMap.put(logId,pipelineExecLog);
                            process.setConfigId(configId);
                            process.setHistoryId(historyId);
                            boolean state = execTask(process,pipelineExecLog);
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
                    } catch (InterruptedException | ExecutionException | ApplicationException e) {
                        throw new ApplicationException(e);
                    }
                }
                threadPool.shutdown();
            }
        }

        return true;
    }

    public boolean beginAfter(Pipeline pipeline, String historyId){
        String pipelineId = pipeline.getId();
        List<PipelinePost> allAfterConfig = postServer.findAllPost(pipelineId);
        if (allAfterConfig == null){
            return true;
        }

        commonService.updateExecHistory(pipelineId,PipelineUntil.date(4)+"开始执行后置任务。");

        for (PipelinePost pipelinePost : allAfterConfig) {
            String configId = pipelinePost.getConfigId();
            String s = configLogMap.get(configId);
            PipelineExecLog pipelineExecLog = logMap.get(s);
            pipelineExecLog.setLogId(s);
            PipelineProcess process = new PipelineProcess(pipelineId);
            process.setConfigId(configId);
            process.setHistoryId(historyId);
            boolean b = execTask(process,pipelineExecLog);
            if (!b){
                return false;
            }
        }
        return true;
    }

    private boolean execTask(PipelineProcess pipelineProcess,PipelineExecLog pipelineExecLog){
        String id = pipelineProcess.getPipeline().getId();
        Pipeline pipeline = pipelineService.findOnePipeline(id);

        pipelineProcess.setPipeline(pipeline);
        String configId = pipelineProcess.getConfigId();

        String logId = pipelineExecLog.getLogId();

        pipelineProcess.setLogId(logId);
        int taskType = pipelineExecLog.getTaskType();

        //执行时间
        time(logId);

        boolean b = taskExecService.beginCourseState(pipelineProcess, configId,taskType);
        stop(logId);
        if (!b){
            commonService.updateState(pipeline.getId(),logId,PIPELINE_RUN_ERROR);
            return false;
        }
        commonService.updateState(pipeline.getId(),logId,PIPELINE_RUN_SUCCESS);
        return true;
    }

    /**
     * 查询流水线运行信息
     * @param pipelineId 流水线id
     * @return 运行状态
     */
    @Override
    public PipelineRunLog findRunState(String pipelineId){
        PipelineExecHistory history = historyMap.get(pipelineId);

        PipelineRunLog execRunLog = new PipelineRunLog();

        if (history == null){
            PipelineExecHistory lastHistory = historyService.findLastHistory(pipelineId);
            if (lastHistory == null){
                return null;
            }
            String historyId = lastHistory.getHistoryId();
            return historyService.findAll(historyId);
        }

        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int type = pipeline.getType();

        List<PipelineRunLog> list = new ArrayList<>();

        if (type == 1){
            List<PipelineTasks> allCourseConfig = tasksService.finAllTasks(pipelineId);
            for (PipelineTasks tasks : allCourseConfig) {
                String configId = tasks.getConfigId();
                PipelineRunLog runLog = findOneRunLog(configId);
                list.add(runLog);
            }
            Map<String, Object> timeState = findTimeState(list);
            execRunLog.setRunLog((String) timeState.get("runLog"));
        }

        if (type == 2){
            List<PipelineStages> stagesMainStage = stagesServer.findAllStagesMainStage(pipelineId);
            //阶段
            for (PipelineStages stages : stagesMainStage) {

                List<PipelineRunLog> runLogList = new ArrayList<>();
                //并行阶段
                String stagesId = stages.getStagesId();
                List<PipelineStages> allMainStage = stagesServer.findAllMainStage(stagesId);
                for (PipelineStages pipelineStages : allMainStage) {
                    String id = pipelineStages.getStagesId();
                    //并行阶段任务
                    List<PipelineStagesTask> allStagesTask = stagesServer.findAllStagesTask(id);
                    List<PipelineRunLog> taskList = new ArrayList<>();
                    for (PipelineStagesTask stagesTask : allStagesTask) {
                        String configId = stagesTask.getConfigId();
                        PipelineRunLog logs = findOneRunLog(configId);
                        taskList.add(logs);
                    }
                    PipelineRunLog pipelineRunLog = initRunLog(pipelineStages, taskList);
                    runLogList.add(pipelineRunLog);
                }
                PipelineRunLog runLog = initRunLog(stages, runLogList);
                list.add(runLog);
            }

            //添加消息阶段
            List<PipelinePost> allPost = postServer.findAllPost(pipelineId);
            if (allPost.size() == 0){
                execRunLog.setAllState(1);
                execRunLog.setAllTime(runTime.get(history.getHistoryId()));
                execRunLog.setRunLogList(list);
                return execRunLog;
            }

            List<PipelineRunLog> logs = new ArrayList<>();
            for (PipelinePost post : allPost) {
                String configId = post.getConfigId();
                PipelineRunLog pipelineRunLog = findOneRunLog(configId);
                logs.add(pipelineRunLog);
            }
            PipelineStages stages = new PipelineStages();
            stages.setName("后置处理");
            stages.setStagesId("后置处理");
            PipelineRunLog runLog = initRunLog(stages, logs);

            logs.add(runLog);
        }

        execRunLog.setAllState(1);
        execRunLog.setAllTime(runTime.get(history.getHistoryId()));
        execRunLog.setRunLogList(list);

        return execRunLog;
    }

    public PipelineRunLog initRunLog(PipelineStages pipelineStages,List<PipelineRunLog> taskList){
        PipelineRunLog pipelineRunLog = new PipelineRunLog();
        pipelineRunLog.setName(pipelineStages.getName());
        pipelineRunLog.setId(pipelineStages.getStagesId());
        Map<String, Object> timeState = findTimeState(taskList);
        pipelineRunLog.setRunLog((String) timeState.get("runLog"));
        pipelineRunLog.setTime((Integer) timeState.get("time"));
        pipelineRunLog.setState((Integer) timeState.get("state"));
        pipelineRunLog.setRunLogList(taskList);
        return pipelineRunLog;
    }

    public Map<String,Object> findTimeState(List<PipelineRunLog> logs){
        int time = 0;
        int runState = 3;
        int state = 0;
        StringBuilder runLog  = new StringBuilder();
        Map<String,Object> map = new HashMap<>();
        for (PipelineRunLog log : logs) {
            time = time + log.getTime();
            state = state + log.getState();
            if (log.getState() == 0){
                runState = 0;
            }
            if (!PipelineUntil.isNoNull(log.getRunLog())){
                continue;
            }
            runLog.append(log.getRunLog());
        }

        if (runState != 0 ){
            runState = state/logs.size();
        }

        map.put("state",runState);
        map.put("time",time);
        map.put("runLog", runLog.toString());
        return map;
    }

    public PipelineRunLog findOneRunLog(String configId){
        PipelineRunLog runLog = new PipelineRunLog();
        String logId = configLogMap.get(configId);
        PipelineExecLog log = logMap.get(logId);
        if (log == null){
            log = logService.findOneLog(logId);
        }
        Integer integer = runTime.get(logId);
        if (integer == null){
            integer = 0 ;
        }
        runLog.setName(log.getTaskName());
        runLog.setRunLog(log.getRunLog());
        runLog.setState(log.getRunState());
        runLog.setTime(integer);
        runLog.setType(log.getTaskType());
        runLog.setId(logId);
        return runLog;
    }

    /**
     * 停止流水线运行
     * @param pipelineId 流水线id
     */
    @Override
    public void killInstance(String pipelineId) {
        PipelineExecHistory history = historyMap.get(pipelineId);
        if (history == null){
            PipelineExecHistory lastHistory = historyService.findLastHistory(pipelineId);
            lastHistory.setRunStatus(PIPELINE_RUN_HALT);
            historyService.updateHistory(lastHistory);
            return;
        }
        String historyId = history.getHistoryId();
        List<PipelineExecLog> allLog = logService.findAllLog(historyId);
        for (PipelineExecLog execLog : allLog) {
            String logId = execLog.getLogId();
            PipelineExecLog log = logMap.get(logId);
            if (log == null){
                continue;
            }
            commonService.updateState(pipelineId,logId,PIPELINE_RUN_HALT);
        }
        updateStatus(pipelineId,PIPELINE_RUN_HALT);
    }

    //更新运行状态
    private void updateStatus(String pipelineId,int status){
        PipelineExecHistory history = historyMap.get(pipelineId);
        String historyId = history.getHistoryId();
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        commonService.runEnd(pipelineId,status);

        List<PipelineStages> allStagesMainStage = stagesServer.findAllStagesMainStage(pipelineId);
        for (PipelineStages stages : allStagesMainStage) {
            String stagesId = stages.getStagesId();
            List<PipelineStages> allMainStage = stagesServer.findAllMainStage(stagesId);
            for (PipelineStages pipelineStages : allMainStage) {
                String id = pipelineStages.getStagesId();
                List<PipelineStagesTask> allStagesTask = stagesServer.findAllStagesTask(id);
                for (PipelineStagesTask stagesTask : allStagesTask) {
                    String configId = stagesTask.getConfigId();
                    stop(configId);
                }
            }
        }

        //清除历史与时间信息
        historyMap.remove(pipelineId);

        //更新流水线状态
        pipeline.setState(1);
        pipelineService.updatePipeline(pipeline);

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        int threadCount = bean.getThreadCount();
        logger.info("执行结束，线程池线程数量："+threadCount);

        //停止历史时间运行
        stop(historyId);

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
    private void time(String logId){
        runTime.put(logId,0);
        threadPool.submit(() -> {
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


































