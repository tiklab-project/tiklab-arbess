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

import java.util.*;
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
    PipelinePostServer afterConfigServer;

    @Autowired
    PipelineStagesServer stagesServer;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线运行历史
    public static Map<String,PipelineExecHistory> historyMap = new HashMap<>();

    public static Map<String,PipelineExecLog> execMap = new HashMap<>();

    //运行日志信息
    public static Map<String,List<String>> logMap = new HashMap<>();

    //创建线程池
    ExecutorService executorService = Executors.newCachedThreadPool();

    public static  Map<String,Integer>  runTime = new HashMap<>();


    /**
     * 流水线开始运行
     * @param pipelineId 流水线id
     * @param startWAy 运行方式
     * @return 是否正在运行
     */
    @Override
    public boolean start(String pipelineId,int startWAy){
        // 判断同一任务是否在运行
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        if (pipeline.getState() == 2){
            return false;
        }
        updatePipelineStatus(pipeline.getId(), true);
        Map<String, String> maps =homeService.initMap(pipeline);

        homeService.log(LOG_PIPELINE_RUN,LOG_MD_PIPELINE_RUN,LOG_TEM_PIPELINE_EXEC, maps);

        executorService.submit(() -> {
            Thread.currentThread().setName(pipelineId);
            begin(pipeline,startWAy);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new ApplicationException(e);
        }

        return true;
    }

    /**
     * 查询流水线运行状态
     * @param pipelineId 流水线id
     * @return 运行状态
     */
    public List<PipelineRun> pipelineRunStatus(String pipelineId){
        PipelineExecHistory history = historyMap.get(pipelineId);
        joinTemplate.joinQuery(history);
        if (history == null){
            return null;
        }
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int pipelineType = pipeline.getType();
        String historyId = history.getHistoryId();

        List<PipelineRun> runList = new ArrayList<>();

        if (pipelineType == 1){
            PipelineRun run = initPipelineRun(history);

            int runtime = 0;
            List<String>  timeList = new ArrayList<>();
            List<String> list = logMap.get(historyId);
            if (list != null){
                for (String s : list) {
                    int integer = runTime.get(s);
                    timeList.add(String.valueOf(integer));
                    runtime = runtime +integer;
                }
            }
            run.setRunTime(runtime);
            run.setTimeList(timeList);
            runList.add(run);
        }

        if (pipelineType == 2){

            List<PipelineStages> stagesStageTask = stagesServer.findAllStagesStageTask(pipelineId);
            for (PipelineStages stages : stagesStageTask) {
                PipelineRun pipelineRun = new PipelineRun();
                List<PipelineStages> stagesList = stages.getStagesList();
                List<PipelineRun> runStagesList = new ArrayList<>();

                for (PipelineStages pipelineStages : stagesList) {
                    PipelineRun run = initPipelineRun(history);
                    //获取阶段任务执行时间
                    int runtime = 0;
                    String stagesId = pipelineStages.getStagesId();
                    List<PipelineExecLog> allStagesLog = commonService.findAllStagesLog(historyId, stagesId);
                    if (allStagesLog == null || allStagesLog.size() == 0){
                        continue;
                    }
                    List<String> timeList = new ArrayList<>();
                    allStagesLog.sort(Comparator.comparing(PipelineExecLog::getTaskSort));
                    for (PipelineExecLog pipelineExecLog : allStagesLog) {
                        String logId = pipelineExecLog.getLogId();
                        Integer integer = runTime.get(logId);
                        if (integer == null){
                            return null;
                        }
                        timeList.add(String.valueOf(integer));
                        runtime = runtime +integer;
                    }
                    run.setTimeList(timeList);

                    //阶段运行时长
                    run.setStagesTime(runtime);

                    //总运行时长
                    int times = 0;
                    List<String> list = logMap.get(historyId);
                    if (list != null){
                        for (String s : list) {
                            int integer = runTime.get(s);
                            times = times +integer;
                        }
                    }
                    run.setRunTime(times);

                    runStagesList.add(run);
                }

                pipelineRun.setRunList(runStagesList);
                runList.add(pipelineRun);
            }
        }
        return runList;
    }

    private PipelineRun initPipelineRun(PipelineExecHistory history){
        PipelineRun run = new PipelineRun();
        String historyId = history.getHistoryId();
        run.setHistoryId(historyId);
        run.setRunWay(history.getRunWay());
        run.setRunLog(history.getRunLog());
        run.setCreateTime(history.getCreateTime());
        User user = history.getUser();
        run.setExecUser(user.getName());
        if (user.getNickname() != null){
            run.setExecUser(user.getNickname());
        }
        return run;
    }

    /**
     * 停止流水线运行
     * @param pipelineId 流水线id
     */
    @Override
    public void killInstance(String pipelineId) {
        PipelineExecHistory history = historyMap.get(pipelineId);

        if (history == null){
            killAllLog(pipelineId);
            updatePipelineStatus(pipelineId, false);
            stop(pipelineId);
            return;
        }
        String historyId = history.getHistoryId();
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);

        commonService.updateState(historyId,logMap.get(historyId).get(logMap.get(historyId).size()-1),PIPELINE_RUN_HALT);

        //停止运行
        stop(pipelineId);
        updatePipelineStatus(pipelineId, false);

        Map<String, String> maps = homeService.initMap(pipeline);
        updateStatus("halt",pipelineId,maps,history);
    }


    private void killAllLog(String pipelineId){
        PipelineExecHistory history = historyMap.get(pipelineId);
        if (history == null){
            return;
        }
        String historyId = history.getHistoryId();
        List<String> list = logMap.get(historyId);
        if (list == null || list.size() == 0){
           return;
        }
        for (String s : list) {
            stop(s);
        }
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

    // 构建开始
    private void begin(Pipeline pipeline,int startWAy) {

        String pipelineId = pipeline.getId();

        //初始化历史
        PipelineExecHistory pipelineExecHistory = commonService.initializeHistory(pipeline,startWAy);
        String historyId = pipelineExecHistory.getHistoryId();

        //获取配置信息
        PipelineProcess pipelineProcess = new PipelineProcess();
        pipelineProcess.setPipelineExecHistory(pipelineExecHistory);
        pipelineProcess.setPipeline(pipeline);
        pipelineProcess.setHistoryId(historyId);
        historyMap.put(pipelineId,pipelineExecHistory);

        //消息
        Map<String, String> maps = homeService.initMap(pipeline);

        boolean courseState = beginCourse(pipelineProcess);

        //执行后置任务
        boolean afterState = beginAfter(pipelineProcess, pipeline, historyId);

        //执行结束
        updatePipelineStatus(pipeline.getId(), false);

        if (courseState && afterState){
            updateStatus("success",pipelineId,maps,pipelineExecHistory);
            return;
        }
        updateStatus("error",pipelineId,maps,pipelineExecHistory);
    }

    //执行任务
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
            if (allStagesTask == null || allStagesTask.size() == 0  ){
                return true;
            }
            for (PipelineStages stages : allStagesTask) {

                // List<Future<Boolean>> list = new ArrayList<>();

                Map<String , Future<Boolean> > map = new HashMap<>();

                //并行阶段
                List<PipelineStages> stagesList = stages.getStagesList();

                for (PipelineStages stage : stagesList) {
                    //放入线程执行
                    Future<Boolean> future = executorService.submit(() -> {
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
                    // list.add(future);
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

                // for (Future<Boolean> future : list) {
                //     try {
                //         Boolean aBoolean = future.get();
                //         if (!aBoolean){
                //             return false;
                //         }
                //     } catch (InterruptedException | ExecutionException e) {
                //         throw new RuntimeException(e);
                //     }
                // }
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

        PipelineExecLog pipelineExecLog = commonService.initializeLog(historyId,taskSort,taskType,stagesId);
        String logId = pipelineExecLog.getLogId();
        pipelineExecLog.setStagesId(stagesId);
        execMap.put(logId,pipelineExecLog);

        //执行时间
        time(logId,historyId);
        PipelineProcess process = new PipelineProcess();
        process.setPipelineExecLog(pipelineExecLog);
        process.setPipelineExecHistory(pipelineProcess.getPipelineExecHistory());
        process.setPipeline(pipelineProcess.getPipeline());

        // pipelineProcess.setPipelineExecLog(pipelineExecLog);

        boolean b = taskExecService.beginCourseState(process, configId,taskType);
        stop(logId);
        if (!b){
            commonService.updateState(historyId,logId,PIPELINE_RUN_ERROR);
            return false;
        }
        commonService.updateState(historyId,logId,PIPELINE_RUN_SUCCESS);
        return true;
    }


    //执行后置任务
    public boolean beginAfter(PipelineProcess pipelineProcess,Pipeline pipeline,String historyId){
        String pipelineId = pipeline.getId();
        List<PipelinePost> allAfterConfig = afterConfigServer.findAllPost(pipelineId);
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

            int taskType = pipelinePost.getTaskType();

            taskSort =taskSort + pipelinePost.getTaskSort();
            PipelineExecLog pipelineExecLog = commonService.initializeLog(historyId,taskSort,taskType,null);

            String logId = pipelineExecLog.getLogId();

            runTime.put(logId,0);
            time(logId,historyId);

            pipelineProcess.setPipeline(pipeline);
            pipelineProcess.setPipelineExecLog(pipelineExecLog);

            boolean b = taskExecService.beginAfterState(pipelineProcess, pipelinePost);
            stop(logId);
            if (!b){
                commonService.updateState(historyId,logId,PIPELINE_RUN_ERROR);
                return false;
            }
            commonService.updateState(historyId,logId,PIPELINE_RUN_SUCCESS);
        }
        commonService.updateExecHistory(pipelineId,PipelineUntil.date(4)+"后置任务执行完成。");
        return true;
    }

    //时间增加
    private void time(String logId ,String historyId){
        runTime.put(logId,0);
        List<String> list = logMap.get(historyId);
        if (list == null){
            list = new ArrayList<>();
        }

        list.add(logId);
        logMap.put(historyId,list);
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


    /**
     * 更新运行状态
     * @param status 状态
     * @param pipelineId 流水线id
     * @param maps 执行信息
     * @param pipelineExecHistory 历史
     */
    private void updateStatus(String status,String pipelineId, Map<String, String> maps,PipelineExecHistory pipelineExecHistory){

        commonService.runEnd(pipelineExecHistory, pipelineId,status);

        maps.put("title","流水线运行信息");
        //日志，消息，添加不同的执行图片
        if (status.equals("success")){
            maps.put("message","成功");
        }
        if (status.equals("error")){
            maps.put("message","失败");
        }
        if (status.equals("halt")){
            maps.put("message","停止执行");
        }

        String historyId = pipelineExecHistory.getHistoryId();
        historyMap.remove(pipelineId);
        List<String> list = logMap.get(historyId);
        logMap.remove(historyId);
        if (list != null){
            for (String s : list) {
                runTime.remove(s);
            }
        }

        //创建日志
        homeService.log(LOG_PIPELINE_RUN,LOG_MD_PIPELINE_RUN,LOG_TEM_PIPELINE_RUN, maps);

    }

    /**
     * 更新流水线状态
     * @param pipelineId 流水线id
     * @param b ture:运行,false:停止
     */
    public void updatePipelineStatus(String pipelineId,boolean b){
        // 判断同一任务是否在运行
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        if (b){
            pipeline.setState(2);
        }else {
            pipeline.setState(1);
        }
        pipelineService.updatePipeline(pipeline);
    }

    /**
     * 停止线程
     * @param threadName 线程名称
     */
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
    
    
    
    




}


































