package net.tiklab.matflow.pipeline.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.core.page.Pagination;
import net.tiklab.matflow.pipeline.definition.model.*;
import net.tiklab.matflow.pipeline.instance.model.PipelineAllInstanceQuery;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.instance.model.TaskInstanceLog;
import net.tiklab.matflow.pipeline.instance.model.TaskRunLog;
import net.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import net.tiklab.matflow.pipeline.instance.service.TaskInstanceLogService;
import net.tiklab.matflow.support.postprocess.model.Postprocess;
import net.tiklab.matflow.support.postprocess.service.PostprocessService;
import net.tiklab.matflow.pipeline.definition.service.PipelineService;
import net.tiklab.matflow.pipeline.definition.service.PipelineStagesService;
import net.tiklab.matflow.pipeline.definition.service.PipelineTasksService;
import net.tiklab.matflow.home.service.PipelineHomeService;
import net.tiklab.matflow.pipeline.execute.model.*;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
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

import static net.tiklab.matflow.support.until.PipelineFinal.*;

@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private PipelineExecTaskService taskExecService;

    @Autowired
    private PipelineExecCommonService commonService;

    @Autowired
    private PipelineHomeService homeService;

    @Autowired
    private PipelineTasksService tasksService;

    @Autowired
    private PostprocessService postServer;

    @Autowired
    private PipelineStagesService stagesServer;

    @Autowired
    private TaskInstanceLogService logService;
    
    @Autowired
    private PipelineInstanceService historyService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线运行历史(流水线id:历史id)
    public static Map<String,String> historyMap = new HashMap<>();

    //流水线运行历史(日志Id:日志)
    public static Map<String, TaskInstanceLog> logMap = new HashMap<>();

    //运行日志时间(日志id:运行时间)
    public static  Map<String,Integer> runTime = new HashMap<>();

    //流水线运行历史(配置Id:日志Id)
    Map<String ,String> configLogMap = new HashMap<>();

    //任务线程池
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
    public boolean start(String pipelineId,int startWAy) {
        boolean result = true;

        // 判断同一任务是否在运行
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        if (pipeline.getState() == 2) {
            result = false;
        } else {

            // pipeline.setState(2);
            // pipelineService.updatePipeline(pipeline);
            // logger.info(pipeline.getName()+"开始运行。");
            // PipelineInstance pipelineExecHistory = commonService.initializeHistory(pipelineId, startWAy);
            // String historyId = pipelineExecHistory.getHistoryId();
            // historyMap.put(pipelineId, pipelineExecHistory);
            // time(historyId);
            // begin(pipeline, historyId);

            executorService.submit(() -> {
                pipeline.setState(2);
                pipelineService.updatePipeline(pipeline);
                logger.info(pipeline.getName()+"开始运行。");
                Thread.currentThread().setName(pipelineId);
                PipelineInstance pipelineInstance = commonService.initializeHistory(pipelineId, startWAy);
                String historyId = pipelineInstance.getHistoryId();
                historyMap.put(pipelineId, historyId);
                time(historyId);
                begin(pipeline, historyId);
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new ApplicationException(e);
            }
        }
        return result;
    }

    /**
     * 查询流水线状态(1.未运行 2.正在运行)
     * @param pipelineId 流水线Id
     * @return 结果
     */
    @Override
    public int findPipelineState(String pipelineId){
        String historyId = historyMap.get(pipelineId);
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int state = pipeline.getState();

        if (historyId != null){
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

    /**
     * 查询正在运行的流水线日志信息
     * @param pipelineId 流水线id
     * @return 运行状态
     */
    @Override
    public TaskRunLog findPipelineRunMessage(String pipelineId){

        String execHistoryId = historyMap.get(pipelineId);

        TaskRunLog execRunLog = new TaskRunLog();

        if (execHistoryId == null){
            PipelineInstance lastHistory = historyService.findLastHistory(pipelineId);
            if (lastHistory == null){
                return null;
            }
            String historyId = lastHistory.getHistoryId();
            return historyService.findAll(historyId);
        }

        PipelineInstance history = historyService.findOneHistory(execHistoryId);

        execRunLog.setName(String.valueOf(history.getFindNumber()));

        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int type = pipeline.getType();

        List<TaskRunLog> list = new ArrayList<>();

        if (type == 1){
            List<PipelineTasks> allCourseConfig = tasksService.finAllTasks(pipelineId);
            for (PipelineTasks tasks : allCourseConfig) {
                String configId = tasks.getConfigId();
                TaskRunLog runLog = findOneRunLog(configId);
                list.add(runLog);
            }
            List<Postprocess> allPostprocess = postServer.findAllPost(pipelineId);
            if (allPostprocess.size() != 0){
                for (Postprocess postprocess : allPostprocess) {
                    String configId = postprocess.getConfigId();
                    TaskRunLog runLog = findOneRunLog(configId);
                    list.add(runLog);
                }
            }

            Map<String, Object> timeState = findTimeState(list);
            execRunLog.setRunLog((String) timeState.get("runLog"));
        }

        if (type == 2){
            List<PipelineStages> stagesMainStage = stagesServer.findAllStagesMainStage(pipelineId);
            //阶段
            for (PipelineStages stages : stagesMainStage) {

                List<TaskRunLog> runLogList = new ArrayList<>();
                //并行阶段
                String stagesId = stages.getStagesId();
                List<PipelineStages> allMainStage = stagesServer.findAllMainStage(stagesId);
                for (PipelineStages pipelineStages : allMainStage) {
                    String id = pipelineStages.getStagesId();
                    //并行阶段任务
                    List<PipelineStagesTask> allStagesTask = stagesServer.findAllStagesTask(id);
                    List<TaskRunLog> taskList = new ArrayList<>();
                    for (PipelineStagesTask stagesTask : allStagesTask) {
                        String configId = stagesTask.getConfigId();
                        TaskRunLog logs = findOneRunLog(configId);
                        taskList.add(logs);
                    }
                    TaskRunLog taskRunLog = initRunLog(pipelineStages, taskList);
                    runLogList.add(taskRunLog);
                }
                TaskRunLog runLog = initRunLog(stages, runLogList);
                list.add(runLog);
            }

            //添加消息阶段
            List<Postprocess> allPostprocess = postServer.findAllPost(pipelineId);
            if (allPostprocess.size() == 0){
                execRunLog.setAllState(1);
                execRunLog.setAllTime(runTime.get(history.getHistoryId()));
                execRunLog.setRunLogList(list);
                return execRunLog;
            }

            List<TaskRunLog> logs = new ArrayList<>();
            for (Postprocess postprocess : allPostprocess) {
                String configId = postprocess.getConfigId();
                TaskRunLog taskRunLog = findOneRunLog(configId);
                logs.add(taskRunLog);
            }
            PipelineStages stages = new PipelineStages();
            stages.setName("后置处理");
            stages.setStagesId("后置处理");
            TaskRunLog runLog = initRunLog(stages, logs);

            list.add(runLog);
        }

        execRunLog.setAllState(1);
        execRunLog.setAllTime(runTime.get(history.getHistoryId()));
        execRunLog.setRunLogList(list);

        return execRunLog;
    }

    /**
     * 停止流水线运行
     * @param pipelineId 流水线id
     */
    @Override
    public void killInstance(String pipelineId) {
        String historyId  = historyMap.get(pipelineId);

        if (historyId == null){
            PipelineInstance lastHistory = historyService.findLastHistory(pipelineId);
            lastHistory.setRunStatus(PIPELINE_RUN_HALT);
            historyService.updateHistory(lastHistory);
            return;
        }

        List<TaskInstanceLog> allLog = logService.findAllLog(historyId);
        for (TaskInstanceLog execLog : allLog) {
            String logId = execLog.getLogId();
            TaskInstanceLog log = logMap.get(logId);
            if (log == null){
                continue;
            }
            commonService.updateState(pipelineId,logId,PIPELINE_RUN_HALT);
        }
        updateStatus(pipelineId,PIPELINE_RUN_HALT);
    }


    /**
     * 获取正在运行的流水线
     * @param pipelineHistoryQuery 分页
     * @return 流水线信息
     */
    @Override
    public Pagination<PipelineInstance> findUserRunPageHistory(PipelineAllInstanceQuery pipelineHistoryQuery){
        List<Pipeline> userPipeline = pipelineService.findUserPipeline(LoginContext.getLoginId());
        if (userPipeline.isEmpty()){
            return null;
        }
        pipelineHistoryQuery.setPipelineList(userPipeline);
        Pagination<PipelineInstance> pageHistory =
                historyService.findUserAllHistory(pipelineHistoryQuery);

        List<PipelineInstance> dataList = pageHistory.getDataList();
        if (dataList.isEmpty()){
            return null;
        }
        //判断是否有正在运行的历史
        int historyStatus = dataList.get(0).getRunStatus();
        if (historyStatus != 30){
            return pageHistory;
        }
        for (PipelineInstance history : dataList) {
            String historyId = history.getHistoryId();
            int status = history.getRunStatus();
            if (status != 30){
                continue;
            }
            int time = 0;
            //获取正在运行的历史的时间
            List<TaskInstanceLog> allLog = logService.findAllLog(historyId);
            for (TaskInstanceLog log : allLog) {
                Integer integer = runTime.get(log.getLogId());
                if (integer != null){
                    time = time + integer;
                }
            }
            history.setRunTime(time);
        }
        pageHistory.setDataList(dataList);
        return pageHistory;
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

    //执行流程任务
    public boolean beginCourse(Pipeline pipeline, String historyId) throws ApplicationException {
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
                PipelineProcess process = initProcess(configId, pipelineId, historyId);

                boolean b = execTask(process,tasks.getTaskType(),tasks.getName());

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
                    try {
                        Future<Boolean> future = threadPool.submit(() -> {
                            Thread.currentThread().setName(stage.getStagesId());
                            //获取任务，执行
                            List<PipelineStagesTask> stagesTaskList = stagesServer.findAllStagesTask(stage.getStagesId());
                            for (PipelineStagesTask stagesTask : stagesTaskList) {
                                String configId = stagesTask.getConfigId();
                                PipelineProcess process = initProcess(configId, pipelineId, historyId);
                                commonService.updateExecLog(process,PipelineUntil.date(4)+"阶段："+stages.getName());
                                commonService.updateExecLog(process,PipelineUntil.date(4)+"并行阶段："+stage.getName());
                                boolean state = execTask(process,stagesTask.getTaskType(),stagesTask.getName());
                                if (!state){
                                    return  false;
                                }
                            }
                            return true;
                        });
                        map.put(stage.getStagesId(),future);
                    }catch (ApplicationException e){
                        throw new ApplicationException(e);
                    }
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

    //执行后置任务
    private boolean beginAfter(Pipeline pipeline, String historyId){
        String pipelineId = pipeline.getId();
        List<Postprocess> allAfterConfig = postServer.findAllPost(pipelineId);
        if (allAfterConfig == null){
            return true;
        }
        for (Postprocess postprocess : allAfterConfig) {
            String configId = postprocess.getConfigId();
            PipelineProcess process =  initProcess(configId,pipelineId,historyId);
            commonService.updateExecLog(process,PipelineUntil.date(4)+"执行后置任务");
            boolean b = execTask(process, postprocess.getTaskType(), postprocess.getName());
            if (!b){
                return false;
            }
        }
        return true;
    }

    //执行不同类型的任务
    private boolean execTask(PipelineProcess pipelineProcess,int taskType,String taskName){

        String id = pipelineProcess.getPipeline().getId();
        Pipeline pipeline = pipelineService.findOnePipeline(id);

        pipelineProcess.setPipeline(pipeline);
        String configId = pipelineProcess.getConfigId();
        String logId = pipelineProcess.getLogId();

        //执行时间
        time(logId);

        boolean b = taskExecService.beginCourseState(pipelineProcess, configId, taskType, new HashMap<>());

        //任务存在后置任务并且条件满足
        List<Postprocess> allPostprocess = postServer.findAllPost(configId);
        Boolean variableCond = commonService.variableCond(pipeline.getId(), configId);
        if (allPostprocess.size() != 0 && variableCond){
            for (Postprocess postprocess : allPostprocess) {
                commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"后置任务:"+ postprocess.getName());
                String postConfigId = postprocess.getConfigId();
                Map<String,String> map = new HashMap<>();
                map.put("task","true");
                if (b){
                    map.put("taskMessage","任务"+taskName+"执行成功。");
                }else {
                    map.put("taskMessage","任务"+taskName+"执行失败。");
                }
                boolean state = taskExecService.beginCourseState(pipelineProcess, postConfigId, postprocess.getTaskType(),map);
                if (!state){
                    commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"后置任务:"+ postprocess.getName()+"执行失败！");
                    b = false;
                }
            }
        }

        stop(logId);

        if (!b){
            commonService.updateState(pipeline.getId(),logId,PIPELINE_RUN_ERROR);
            return false;
        }
        commonService.updateState(pipeline.getId(),logId,PIPELINE_RUN_SUCCESS);
        return true;
    }

    //初始化执行任务过程信息
    private PipelineProcess initProcess( String configId,String pipelineId,String historyId){
        PipelineProcess process = new PipelineProcess(pipelineId);
        String logId = configLogMap.get(configId);
        TaskInstanceLog taskInstanceLog = logMap.get(logId);
        taskInstanceLog.setLogId(logId);
        taskInstanceLog.setRunState(0);
        logMap.put(logId, taskInstanceLog);
        process.setConfigId(configId);
        process.setHistoryId(historyId);
        process.setLogId(logId);
        return process;
    }

    //创建任务对应日志数据
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

        List<Postprocess> allPostprocess = postServer.findAllPost(pipelineId);
        if (allPostprocess == null || allPostprocess.size() == 0){
            return;
        }
        for (Postprocess postprocess : allPostprocess) {
            postprocess.setTaskSort(postprocess.getTaskSort()+sort);
            createPipelineLog(0, postprocess,historyId);
        }
    }

    //创建对应日志的文件
    private void createPipelineLog(int type,Object o,String historyId){
        int taskSort = 0;
        int taskType = 0;
        String stagesId = null;
        String configId = null;
        String name = null;

        PipelineInstance history = historyService.findOneHistory(historyId);
        String id = history.getPipeline().getId();

        if (type == 0){
            Postprocess postprocess = (Postprocess) o;
            taskSort = postprocess.getTaskSort();
            taskType = postprocess.getTaskType();
            configId = postprocess.getConfigId();
            name = postprocess.getName();
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

        TaskInstanceLog taskInstanceLog = new TaskInstanceLog();
        taskInstanceLog.setHistoryId(historyId);
        taskInstanceLog.setTaskSort(taskSort);
        taskInstanceLog.setTaskType(taskType);
        taskInstanceLog.setStagesId(stagesId);
        taskInstanceLog.setRunState(3);
        taskInstanceLog.setTaskName(name);
        String address= PipelineUntil.findFileAddress(id, 2);
        String logId = logService.createLog(taskInstanceLog);
        String logAddress = PipelineUntil.createFile(address + "/" + historyId + "/" + logId + ".log");
        taskInstanceLog.setLogAddress(logAddress);
        taskInstanceLog.setLogId(logId);
        logService.updateLog(taskInstanceLog);
        logMap.put(logId, taskInstanceLog);
        configLogMap.put(configId,logId);
    }

    //根据任务信息初始化日志
    public TaskRunLog initRunLog(PipelineStages pipelineStages, List<TaskRunLog> taskList){
        TaskRunLog taskRunLog = new TaskRunLog();
        taskRunLog.setName(pipelineStages.getName());
        taskRunLog.setId(pipelineStages.getStagesId());
        Map<String, Object> timeState = findTimeState(taskList);
        taskRunLog.setRunLog((String) timeState.get("runLog"));
        taskRunLog.setTime((Integer) timeState.get("time"));
        taskRunLog.setState((Integer) timeState.get("state"));
        taskRunLog.setRunLogList(taskList);
        return taskRunLog;
    }

    //根据配置查询日志
    public TaskRunLog findOneRunLog(String configId){
        TaskRunLog runLog = new TaskRunLog();
        String logId = configLogMap.get(configId);
        TaskInstanceLog log = logMap.get(logId);

        if (log == null){
            log = logService.findOneLog(logId);
        }
        String s = log.getRunLog();
        if (!PipelineUntil.isNoNull(log.getRunLog())){
            String logAddress = log.getLogAddress();
            s = PipelineUntil.readFile(logAddress, 300);
        }

        Integer integer = runTime.get(logId);
        if (integer == null){
            integer = 0 ;
        }
        runLog.setName(log.getTaskName());
        runLog.setRunLog(s);

        runLog.setState(log.getRunState());
        runLog.setTime(integer);
        runLog.setType(log.getTaskType());
        runLog.setId(logId);
        return runLog;
    }

    //查询当前阶段的运行信息
    public Map<String,Object> findTimeState(List<TaskRunLog> logs){
        int time = 0;
        int runState = 3;
        int state = 0;
        StringBuilder runLog  = new StringBuilder();
        Map<String,Object> map = new HashMap<>();
        for (TaskRunLog log : logs) {
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

    //更新运行状态
    private void updateStatus(String pipelineId,int status){

        String historyId = historyMap.get(pipelineId);

        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        commonService.runEnd(pipelineId,status);

        int type = pipeline.getType();

        if (type == 2){
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
        }

        //清除执行历史信息
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
            maps.put("message","运行成功");
        }
        if (status == PIPELINE_RUN_ERROR){
            maps.put("message","运行失败");
        }
        if (status == PIPELINE_RUN_HALT){
            maps.put("message","停止执行");
        }

        homeService.log(LOG_RUN, LOG_TEM_RUN,maps);

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
            if (!PipelineUntil.isNoNull(nm) ||!nm.equals(threadName)) {
                continue;
            }
            lstThreads[i].stop();
        }
    }

    //任务执行时间
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


































