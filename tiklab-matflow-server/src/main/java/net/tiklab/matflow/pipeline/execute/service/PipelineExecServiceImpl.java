package net.tiklab.matflow.pipeline.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.home.service.PipelineHomeService;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.service.PipelineService;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.pipeline.execute.model.TaskRunLog;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import net.tiklab.matflow.stages.model.Stage;
import net.tiklab.matflow.stages.service.StageInstanceServer;
import net.tiklab.matflow.stages.service.StageService;
import net.tiklab.matflow.support.postprocess.model.Postprocess;
import net.tiklab.matflow.support.postprocess.service.PostprocessService;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.task.task.model.TaskInstance;
import net.tiklab.matflow.task.task.model.Tasks;
import net.tiklab.matflow.task.task.service.TaskExecDispatchService;
import net.tiklab.matflow.task.task.service.TaskInstanceService;
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
    private StageService stagesServer;

    @Autowired
    private StageInstanceServer stageInstanceServer;

    @Autowired
    private TaskInstanceService logService;
    
    @Autowired
    private PipelineInstanceService historyService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线运行历史(流水线id:历史id)
    public static Map<String,String> historyMap = new HashMap<>();

    //流水线运行日志(日志Id:日志)
    public static Map<String, TaskInstance> logMap = new HashMap<>();

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

                //执行流程任务
                boolean courseState = beginCourse(pipeline,historyId);
                //执行后置任务
                boolean afterState = beginAfter(pipeline,historyId);
                if (courseState && afterState){
                    updateStatus(pipelineId,PIPELINE_RUN_SUCCESS);
                    return;
                }
                updateStatus(pipelineId,PIPELINE_RUN_ERROR);
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
            List<Tasks> allCourseConfig = tasksService.finAllPipelineTask(pipelineId);
            for (Tasks tasks : allCourseConfig) {
                String configId = tasks.getTaskId();
                TaskRunLog runLog = findOneRunLog(configId);
                list.add(runLog);
            }
            //后置处理
            List<Postprocess> allPostprocess = postServer.findAllPost(pipelineId);
            if (allPostprocess.size() != 0){
                for (Postprocess postprocess : allPostprocess) {
                    String configId = postprocess.getId();
                    TaskRunLog runLog = findOneRunLog(configId);
                    list.add(runLog);
                }
            }

            Map<String, Object> timeState = findTaskRunState(list);
            execRunLog.setRunLog((String) timeState.get("runLog"));
        }

        //多阶段
        if (type == 2){
            List<Stage> stageMainStage = stagesServer.findAllMainStage(pipelineId);
            //阶段
            for (Stage stage : stageMainStage) {
                List<TaskRunLog> runLogList = new ArrayList<>();
                //并行阶段
                String stagesId = stage.getStageId();
                List<Stage> allMainStage = stagesServer.findOtherStage(stagesId);
                for (Stage pipelineStage : allMainStage) {
                    String id = pipelineStage.getStageId();
                    // 并行阶段任务
                    List<Tasks> allStagesTasks = tasksService.finAllStageTaskOrTask(id);
                    List<TaskRunLog> taskList = new ArrayList<>();
                    for (Tasks stagesTasks : allStagesTasks) {
                        String taskId = stagesTasks.getTaskId();
                        TaskRunLog logs = findOneRunLog(taskId);
                        taskList.add(logs);
                    }
                    TaskRunLog taskRunLog = findStatesRunLog(pipelineStage, taskList);
                    runLogList.add(taskRunLog);
                }
                TaskRunLog runLog = findStatesRunLog(stage, runLogList);
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
                String configId = postprocess.getId();
                TaskRunLog taskRunLog = findOneRunLog(configId);
                logs.add(taskRunLog);
            }
            Stage stage = new Stage();
            stage.setStageName("后置处理");
            stage.setStageId("后置处理");
            TaskRunLog runLog = findStatesRunLog(stage, logs);

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
        List<TaskInstance> allLog = logService.findAllLog(historyId);
        for (TaskInstance execLog : allLog) {
            String logId = execLog.getLogId();
            TaskInstance log = logMap.get(logId);
            if (log == null){
                continue;
            }
            commonService.updateLogState(pipelineId,logId,PIPELINE_RUN_HALT);
        }
        updateStatus(pipelineId,PIPELINE_RUN_HALT);
    }


    //执行流程任务
    public boolean beginCourse(Pipeline pipeline, String historyId) throws ApplicationException {
        String pipelineId = pipeline.getId();
        int pipelineType = pipeline.getType();

        //初始化日志信息
        initPipelineLog(pipelineId,historyId);

        //多任务
        if (pipelineType == 1 ){
            List<Tasks> allPipOrStages = tasksService.finAllPipelineTaskOrTask(pipelineId);
            if (allPipOrStages == null){
                return true;
            }
            for (Tasks tasks : allPipOrStages) {
                String taskId = tasks.getTaskId();
                PipelineProcess process = initProcess(taskId, pipelineId, historyId);
                // 执行任务
                boolean b = execTask(process, tasks.getTaskType(),tasks.getTaskName());
                if (!b){
                    return false;
                }
            }
          return true;
        }

        //多阶段
        if (pipelineType == 2){
            List<Stage> allStageTask = stagesServer.findAllMainStage(pipelineId);
            if (allStageTask == null || allStageTask.size() == 0 ){
                return true;
            }
            for (Stage stages : allStageTask) {
                String stagesId = stages.getStageId();
                Map<String , Future<Boolean>> map = new HashMap<>();

                //并行阶段
                List<Stage> stageList = stagesServer.findOtherStage(stagesId);
                ExecutorService threadPool = Executors.newCachedThreadPool();
                for (Stage stage : stageList) {
                    //放入线程执行
                    try {
                        Future<Boolean> future = threadPool.submit(() -> {
                            String stagesId1 = stage.getStageId();
                            Thread.currentThread().setName(stagesId1);
                            //获取任务，执行
                            List<Tasks> allPipOrStages = tasksService.finAllStageTaskOrTask(stagesId1);
                            for (Tasks stagesTask : allPipOrStages) {
                                String taskId = stagesTask.getTaskId();
                                PipelineProcess process = initProcess(taskId, pipelineId, historyId);
                                boolean state = execTask(process,stagesTask.getTaskType(),stagesTask.getTaskName());
                                if (!state){
                                    return  false;
                                }
                            }
                            return true;
                        });
                        map.put(stage.getStageId(),future);
                    }catch (ApplicationException e){
                        throw new ApplicationException(e);
                    }
                }
                //等待
                for (Stage pipelineStage : stageList) {
                    try {
                        Boolean aBoolean = map.get(pipelineStage.getStageId()).get();
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
            String configId = postprocess.getId();
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
                String postConfigId = postprocess.getId();
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
        TaskInstance taskInstance = logMap.get(logId);
        taskInstance.setLogId(logId);
        taskInstance.setRunState(0);
        logMap.put(logId, taskInstance);
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
            List<Tasks> list = tasksService.finAllPipelineTask(pipelineId);
            sort = list.size()+1;
            for (Tasks tasks : list) {
                createPipelineLog(type, tasks,historyId);
            }
        }
        //多阶段
        if (type == 2){
            List<Stage> stageMainStage = stagesServer.findAllMainStage(pipelineId);
            sort = stageMainStage.size()+1;
            for (Stage stage : stageMainStage) {
                String stagesId = stage.getStageId();
                List<Stage> mainStage = stagesServer.findOtherStage(stagesId);
                for (Stage pipelineStage : mainStage) {
                    String id = pipelineStage.getStageId();
                    List<Tasks> tasks = tasksService.finAllStageTaskOrTask(id);
                    for (Tasks task : tasks) {
                        createPipelineLog(type,task,historyId);
                    }
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
        String taskId = null;
        String name = null;

        PipelineInstance history = historyService.findOneInstance(historyId);
        String id = history.getPipeline().getId();
        //后置任务
        if (type == 0){
            Postprocess postprocess = (Postprocess) o;
            taskSort = postprocess.getTaskSort();
            taskType = postprocess.getTaskType();
            taskId = postprocess.getId();
            name = postprocess.getName();
        }else {
            Tasks tasks = (Tasks) o;
            taskSort = tasks.getTaskSort();
            taskType = tasks.getTaskType();
            taskId = tasks.getTaskId();
            name = tasks.getTaskName();
        }

        // //多任务
        // if (type == 1){
        //     Tasks tasks = (Tasks) o;
        //     taskSort = tasks.getTaskSort();
        //     taskType = tasks.getTaskType();
        //     configId = tasks.getTaskId();
        //     name = tasks.getTaskName();
        // }
        //
        // //多阶段
        // if (type == 2){
        //     // StagesTask stagesTask = (StagesTask) o;
        //     // taskSort = stagesTask.getTaskSort();
        //     // taskType = stagesTask.getTaskType();
        //     // configId = stagesTask.getConfigId();
        //     // stagesId = stagesTask.getStageId();
        //     // name = stagesTask.getName();
        // }

        //添加日志信息

        TaskInstance taskInstance = new TaskInstance();
        taskInstance.setInstanceId(historyId);
        taskInstance.setTaskSort(taskSort);
        taskInstance.setTaskType(taskType);
        taskInstance.setStagesId(stagesId);
        taskInstance.setRunState(3);
        taskInstance.setTaskName(name);
        String logId = logService.createLog(taskInstance);
        //创建日志文件
        String address= PipelineUtil.findFileAddress(id, 2);
        String s = address + "/" + historyId + "/" + logId + ".log";
        String logAddress = PipelineUtil.createFile(s);
        taskInstance.setLogAddress(logAddress);
        taskInstance.setLogId(logId);
        logService.updateLog(taskInstance);
        logMap.put(logId, taskInstance);
        configLogMap.put(taskId,logId);
    }

    /**
     * 获取阶段运行信息
     * @param stage 阶段信息
     * @param taskList 运行日志
     * @return 运行信息
     */
    private TaskRunLog findStatesRunLog(Stage stage, List<TaskRunLog> taskList){
        TaskRunLog taskRunLog = new TaskRunLog();
        taskRunLog.setName(stage.getStageName());
        taskRunLog.setId(stage.getStageId());
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
        TaskInstance log = logMap.get(logId);

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
            List<Stage> allStageMainStage = stagesServer.findAllMainStage(pipelineId);
            for (Stage stage : allStageMainStage) {
                String stagesId = stage.getStageId();
                List<Stage> allMainStage = stagesServer.findOtherStage(stagesId);
                for (Stage pipelineStage : allMainStage) {
                    String id = pipelineStage.getStageId();
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


































