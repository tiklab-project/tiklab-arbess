package net.tiklab.matflow.pipeline.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.home.service.PipelineHomeService;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.service.PipelineService;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.pipeline.execute.model.TaskRunLog;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.instance.model.TaskInstanceLog;
import net.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import net.tiklab.matflow.pipeline.instance.service.TaskInstanceLogService;
import net.tiklab.matflow.stages.model.Stages;
import net.tiklab.matflow.stages.service.StagesService;
import net.tiklab.matflow.support.postprocess.model.Postprocess;
import net.tiklab.matflow.support.postprocess.service.PostprocessService;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.task.task.service.TaskExecDispatchService;
import net.tiklab.matflow.task.task.service.TasksService;
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

import static net.tiklab.matflow.support.util.PipelineFinal.*;

/**
 * 流水线运行服务
 */
@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private TaskExecDispatchService taskExecService;

    @Autowired
    private PipelineExecLogService commonService;

    @Autowired
    private PipelineHomeService homeService;

    @Autowired
    private TasksService tasksService;

    @Autowired
    private PostprocessService postServer;

    @Autowired
    private StagesService stagesServer;

    @Autowired
    private TaskInstanceLogService logService;
    
    @Autowired
    private PipelineInstanceService historyService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线运行历史(流水线id:历史id)
    public static Map<String,String> historyMap = new HashMap<>();

    //流水线运行日志(日志Id:日志)
    public static Map<String, TaskInstanceLog> logMap = new HashMap<>();

    //运行任务执行时间(日志id:运行时间)
    public static  Map<String,Integer> runTime = new HashMap<>();

    //流水线配置(配置Id:日志Id)
    Map<String ,String> configLogMap = new HashMap<>();

    //任务线程池
    public static ExecutorService executorService = Executors.newCachedThreadPool();

    //时间线程池
    // public static ExecutorService threadPool = Executors.newCachedThreadPool();

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
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
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
                PipelineInstance pipelineInstance = historyService.initializeInstance(pipelineId, startWAy);
                String historyId = pipelineInstance.getInstanceId();
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

    @Override
    public int findPipelineState(String pipelineId){
        String historyId = historyMap.get(pipelineId);
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        int state = pipeline.getState();
        //判断内存中改流水线是否正在运行
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

    @Override
    public TaskRunLog findPipelineRunMessage(String pipelineId){

        String execHistoryId = historyMap.get(pipelineId);
        TaskRunLog execRunLog = new TaskRunLog();

        if (execHistoryId == null){
            PipelineInstance lastHistory = historyService.findLastInstance(pipelineId);
            if (lastHistory == null){
                return null;
            }
            String historyId = lastHistory.getInstanceId();
            return historyService.findAll(historyId);
        }

        PipelineInstance history = historyService.findOneInstance(execHistoryId);
        execRunLog.setName(String.valueOf(history.getFindNumber()));
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        int type = pipeline.getType();

        List<TaskRunLog> list = new ArrayList<>();
        //多任务
        if (type == 1){
            List<Tasks> allCourseConfig = tasksService.finAllPipOrStages(pipelineId,type);
            for (Tasks tasks : allCourseConfig) {
                String configId = tasks.getTaskId();
                TaskRunLog runLog = findOneRunLog(configId);
                list.add(runLog);
            }
            //后置处理
            List<Postprocess> allPostprocess = postServer.findAllPost(pipelineId);
            if (allPostprocess.size() != 0){
                for (Postprocess postprocess : allPostprocess) {
                    String configId = postprocess.getConfigId();
                    TaskRunLog runLog = findOneRunLog(configId);
                    list.add(runLog);
                }
            }

            Map<String, Object> timeState = findTaskRunState(list);
            execRunLog.setRunLog((String) timeState.get("runLog"));
        }
        //多阶段
        if (type == 2){
            List<Stages> stagesMainStage = stagesServer.findAllMainStage(pipelineId);
            //阶段
            for (Stages stages : stagesMainStage) {
                List<TaskRunLog> runLogList = new ArrayList<>();
                //并行阶段
                String stagesId = stages.getStagesId();
                List<Stages> allMainStage = stagesServer.findOtherStage(stagesId);
                for (Stages pipelineStages : allMainStage) {
                    String id = pipelineStages.getStagesId();
                    // 并行阶段任务
                    List<Tasks> allStagesTask = tasksService.finAllPipOrStages(id,2);
                    List<TaskRunLog> taskList = new ArrayList<>();
                    for (Tasks stagesTask : allStagesTask) {
                        String taskId = stagesTask.getTaskId();
                        TaskRunLog logs = findOneRunLog(taskId);
                        taskList.add(logs);
                    }
                    TaskRunLog taskRunLog = findStatesRunLog(pipelineStages, taskList);
                    runLogList.add(taskRunLog);
                }
                TaskRunLog runLog = findStatesRunLog(stages, runLogList);
                list.add(runLog);
            }


            List<Postprocess> allPostprocess = postServer.findAllPost(pipelineId);
            //不存在后置处理
            if (allPostprocess.size() == 0){
                execRunLog.setAllState(1);
                execRunLog.setAllTime(runTime.get(history.getInstanceId()));
                execRunLog.setRunLogList(list);
                return execRunLog;
            }
            //后置处理
            List<TaskRunLog> logs = new ArrayList<>();
            for (Postprocess postprocess : allPostprocess) {
                String configId = postprocess.getConfigId();
                TaskRunLog taskRunLog = findOneRunLog(configId);
                logs.add(taskRunLog);
            }
            Stages stages = new Stages();
            stages.setStagesName("后置处理");
            stages.setStagesId("后置处理");
            TaskRunLog runLog = findStatesRunLog(stages, logs);

            list.add(runLog);
        }

        execRunLog.setAllState(1);
        execRunLog.setAllTime(runTime.get(history.getInstanceId()));
        execRunLog.setRunLogList(list);

        return execRunLog;
    }


    @Override
    public void killInstance(String pipelineId) {

        String historyId  = historyMap.get(pipelineId);
        //（流水线并未运行）更新流水线状态
        if (historyId == null){
            PipelineInstance lastHistory = historyService.findLatelyInstance(pipelineId);
            lastHistory.setRunStatus(PIPELINE_RUN_HALT);
            historyService.updateInstance(lastHistory);
            return;
        }

        //更新日志状态
        List<TaskInstanceLog> allLog = logService.findAllLog(historyId);
        for (TaskInstanceLog execLog : allLog) {
            String logId = execLog.getLogId();
            TaskInstanceLog log = logMap.get(logId);
            if (log == null){
                continue;
            }
            commonService.updateLogState(pipelineId,logId,PIPELINE_RUN_HALT);
        }
        updateStatus(pipelineId,PIPELINE_RUN_HALT);
    }


    //构建开始
    private void begin(Pipeline pipeline,String historyId) {
        String pipelineId = pipeline.getId();

        //执行流程任务
        boolean courseState = beginCourse(pipeline,historyId);
        //执行后置任务
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
            List<Tasks> allPipOrStages = tasksService.finAllPipOrStages(pipelineId,pipelineType);
            if (allPipOrStages == null){
                return true;
            }
            for (Tasks tasks : allPipOrStages) {
                String taskId = tasks.getTaskId();
                PipelineProcess process = initProcess(taskId, pipelineId, historyId);
                // 执行任务
                boolean b = execTask(process,tasks.getTaskType()," ");
                if (!b){
                    return false;
                }
            }
          return true;
        }

        //多阶段
        if (pipelineType == 2){
            List<Stages> allStagesTask = stagesServer.findAllMainStage(pipelineId);
            if (allStagesTask == null || allStagesTask.size() == 0 ){
                return true;
            }
            for (Stages stages : allStagesTask) {
                String stagesId = stages.getStagesId();
                Map<String , Future<Boolean>> map = new HashMap<>();

                //并行阶段
                List<Stages> stagesList = stagesServer.findOtherStage(stagesId);
                ExecutorService threadPool = Executors.newCachedThreadPool();
                for (Stages stage : stagesList) {
                    //放入线程执行
                    try {
                        Future<Boolean> future = threadPool.submit(() -> {
                            String stagesId1 = stage.getStagesId();
                            Thread.currentThread().setName(stagesId1);
                            //获取任务，执行
                            List<Tasks> allPipOrStages = tasksService.finAllPipOrStages(stagesId1,pipelineType);
                            for (Tasks stagesTask : allPipOrStages) {
                                String taskId = stagesTask.getTaskId();
                                PipelineProcess process = initProcess(taskId, pipelineId, historyId);
                                // boolean state = execTask(process,stagesTask.getTaskType(),stagesTask.getName());
                                // if (!state){
                                //     return  false;
                                // }
                            }
                            return true;
                        });
                        map.put(stage.getStagesId(),future);
                    }catch (ApplicationException e){
                        throw new ApplicationException(e);
                    }
                }
                //等待
                for (Stages pipelineStages : stagesList) {
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
            commonService.writeExecLog(process, PipelineUtil.date(4)+"执行后置任务");
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
        Pipeline pipeline = pipelineService.findPipelineById(id);

        pipelineProcess.setPipeline(pipeline);
        String configId = pipelineProcess.getConfigId();
        String logId = pipelineProcess.getLogId();

        //执行时间
        time(logId);

        boolean b = taskExecService.beginCourseState(pipelineProcess, configId, taskType, new HashMap<>());

        //任务存在后置任务并且条件满足
        List<Postprocess> allPostprocess = postServer.findAllPost(configId);
        Boolean variableCond = commonService.variableCondition(pipeline.getId(), configId);
        if (allPostprocess.size() != 0 && variableCond){
            for (Postprocess postprocess : allPostprocess) {
                commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"后置任务:"+ postprocess.getName());
                String postConfigId = postprocess.getConfigId();
                Map<String,String> map = new HashMap<>();
                map.put("task","true");
                if (b){
                    map.put("taskMessage","任务"+taskName+"执行成功。");
                }else {
                    map.put("taskMessage","任务"+taskName+"执行失败。");
                }
                boolean state = taskExecService.beginCourseState(pipelineProcess, postConfigId, postprocess.getTaskType(),map);
                //任务执行失败
                if (!state){
                    commonService.writeExecLog(pipelineProcess, PipelineUtil.date(4)+"后置任务:"+ postprocess.getName()+"执行失败！");
                    b = false;
                }
            }
        }

        stop(logId);

        if (!b){
            commonService.updateLogState(pipeline.getId(),logId,PIPELINE_RUN_ERROR);
            return false;
        }
        commonService.updateLogState(pipeline.getId(),logId,PIPELINE_RUN_SUCCESS);
        return true;
    }

    //初始化执行任务过程信息
    private PipelineProcess initProcess(String configId,String pipelineId,String historyId){
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
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        int type = pipeline.getType();
        int sort = 0;

        //多任务
        if (type == 1){
            List<Tasks> list = tasksService.finAllPipOrStages(pipelineId,type);
            sort = list.size()+1;
            for (Tasks tasks : list) {
                createPipelineLog(type,tasks,historyId);
            }
        }
        //多阶段
        if (type == 2){
            List<Stages> stagesMainStage = stagesServer.findAllMainStage(pipelineId);
            sort = stagesMainStage.size()+1;
            for (Stages stages : stagesMainStage) {
                String stagesId = stages.getStagesId();
                List<Stages> mainStage = stagesServer.findOtherStage(stagesId);
                for (Stages pipelineStages : mainStage) {
                    String id = pipelineStages.getStagesId();
                    // List<StagesTask> allStagesTask = stagesServer.findStagesTask(id);
                    // for (StagesTask stagesTask : allStagesTask) {
                    //     createPipelineLog(type,stagesTask,historyId);
                    // }
                }
            }
        }

        //后置处理
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

        PipelineInstance history = historyService.findOneInstance(historyId);
        String id = history.getPipeline().getId();
        //后置任务
        if (type == 0){
            Postprocess postprocess = (Postprocess) o;
            taskSort = postprocess.getTaskSort();
            taskType = postprocess.getTaskType();
            configId = postprocess.getConfigId();
            name = postprocess.getName();
        }

        //多任务
        if (type == 1){
            Tasks tasks = (Tasks) o;
            taskSort = tasks.getTaskSort();
            taskType = tasks.getTaskType();
            configId = tasks.getTaskId();
            // name = tasks.getName();
        }

        //多阶段
        if (type == 2){
            // StagesTask stagesTask = (StagesTask) o;
            // taskSort = stagesTask.getTaskSort();
            // taskType = stagesTask.getTaskType();
            // configId = stagesTask.getConfigId();
            // stagesId = stagesTask.getStagesId();
            // name = stagesTask.getName();
        }

        //添加日志信息
        TaskInstanceLog taskInstanceLog = new TaskInstanceLog();
        taskInstanceLog.setInstanceId(historyId);
        taskInstanceLog.setTaskSort(taskSort);
        taskInstanceLog.setTaskType(taskType);
        taskInstanceLog.setStagesId(stagesId);
        taskInstanceLog.setRunState(3);
        taskInstanceLog.setTaskName(name);
        String logId = logService.createLog(taskInstanceLog);

        String address= PipelineUtil.findFileAddress(id, 2);
        String s = address + "/" + historyId + "/" + logId + ".log";
        String logAddress = PipelineUtil.createFile(s);
        taskInstanceLog.setLogAddress(logAddress);
        taskInstanceLog.setLogId(logId);
        logService.updateLog(taskInstanceLog);
        logMap.put(logId, taskInstanceLog);
        configLogMap.put(configId,logId);
    }

    /**
     * 获取阶段运行信息
     * @param stages 阶段信息
     * @param taskList 运行日志
     * @return 运行信息
     */
    private TaskRunLog findStatesRunLog(Stages stages, List<TaskRunLog> taskList){
        TaskRunLog taskRunLog = new TaskRunLog();
        taskRunLog.setName(stages.getStagesName());
        taskRunLog.setId(stages.getStagesId());
        Map<String, Object> timeState = findTaskRunState(taskList);
        taskRunLog.setRunLog((String) timeState.get("runLog"));
        taskRunLog.setTime((Integer) timeState.get("time"));
        taskRunLog.setState((Integer) timeState.get("state"));
        taskRunLog.setRunLogList(taskList);
        return taskRunLog;
    }

    /**
     * 根据配置id读取日志信息
     * @param configId 配置id
     * @return 日志信息
     */
    public TaskRunLog findOneRunLog(String configId){
        TaskRunLog runLog = new TaskRunLog();
        String logId = configLogMap.get(configId);
        TaskInstanceLog log = logMap.get(logId);

        if (log == null){
            log = logService.findOneLog(logId);
        }
        String s = log.getRunLog();
        //判断是否存在运行日志
        if (!PipelineUtil.isNoNull(log.getRunLog())){
            //读取本地文件信息
            String logAddress = log.getLogAddress();
            s = PipelineUtil.readFile(logAddress, 300);
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

    /**
     * 获取流水线各个任务的运行状态
     * @param logs 任务日志
     * @return 运行状态
     */
    public Map<String,Object> findTaskRunState(List<TaskRunLog> logs){
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
            if (!PipelineUtil.isNoNull(log.getRunLog())){
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
        Pipeline pipeline = pipelineService.findPipelineById(pipelineId);
        //更新状态
        commonService.runEnd(pipelineId,status);
        int type = pipeline.getType();

        //多阶段停止
        if (type == 2){
            List<Stages> allStagesMainStage = stagesServer.findAllMainStage(pipelineId);
            for (Stages stages : allStagesMainStage) {
                String stagesId = stages.getStagesId();
                List<Stages> allMainStage = stagesServer.findOtherStage(stagesId);
                for (Stages pipelineStages : allMainStage) {
                    String id = pipelineStages.getStagesId();
                    // List<StagesTask> allStagesTask = stagesServer.findStagesTask(id);
                    // for (StagesTask stagesTask : allStagesTask) {
                    //     String configId = stagesTask.getConfigId();
                    //     stop(configId);
                    // }
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

    /**
     * 停止指定线程
     * @param threadName 线程名称
     */
    private void stop(String threadName){
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            String nm = lstThreads[i].getName();
            if (!PipelineUtil.isNoNull(nm) ||!nm.equals(threadName)) {
                continue;
            }
            lstThreads[i].stop();
        }
    }

    /**
     * 任务执行时长
     * @param logId 日志id
     */
    private void time(String logId){
        runTime.put(logId,0);
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


































