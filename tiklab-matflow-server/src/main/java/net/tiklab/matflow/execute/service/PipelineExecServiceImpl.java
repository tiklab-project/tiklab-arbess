package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineAfterConfig;
import net.tiklab.matflow.definition.model.PipelineCourseConfig;
import net.tiklab.matflow.definition.model.PipelineStages;
import net.tiklab.matflow.definition.service.PipelineAfterConfigServer;
import net.tiklab.matflow.definition.service.PipelineCourseConfigService;
import net.tiklab.matflow.definition.service.PipelineService;
import net.tiklab.matflow.definition.service.PipelineStagesServer;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.model.PipelineRun;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static net.tiklab.matflow.orther.service.PipelineFinal.*;

@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineExecTaskService taskExecService;

    @Autowired
    PipelineExecCommonService commonService;

    @Autowired
    PipelineCourseConfigService courseConfigService;

    @Autowired
    PipelineHomeService homeService;

    @Autowired
    PipelineAfterConfigServer afterConfigServer;

    @Autowired
    PipelineStagesServer stagesServer;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线运行历史
    public static Map<String,PipelineExecHistory> historyMap = new HashMap<>();

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
        if (pipeline.getPipelineState() == 1){
            return false;
        }
        updatePipelineStatus(pipeline.getPipelineId(), true);
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
     * 查询流水线运行中信息
     * @param pipelineId 流水线id
     * @return 正在运行的信息
     */
    @Override
    public PipelineExecHistory findInstanceState(String pipelineId){
        PipelineExecHistory pipelineExecHistory = historyMap.get(pipelineId);
        // if (pipelineExecHistory == null){
        //     return null;
        // }
        //
        // int time = 0;
        // for (Integer integer1 : timeList) {
        //     time = time+ integer1;
        // }
        // pipelineExecHistory.setRunTime(time);
        // pipelineExecHistory.setTimeList(timeList);
        return pipelineExecHistory;
    }

    /**
     * 查询流水线运行状态
     * @param pipelineId 流水线id
     * @return 运行状态
     */
    public List<PipelineRun> pipelineRunStatus(String pipelineId){
        PipelineExecHistory history = historyMap.get(pipelineId);
        if (history == null){
            return null;
        }
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        int pipelineType = pipeline.getPipelineType();
        String historyId = history.getHistoryId();

        List<PipelineRun> runList = new ArrayList<>();

        if (pipelineType == 1){
            PipelineRun run = new PipelineRun();
            run.setHistoryId(historyId);
            run.setRunWay(history.getRunWay());
            int runtime = 0;
            run.setRunLog(history.getRunLog());
            List<Integer>  timeList = new ArrayList<>();
            List<String> list = logMap.get(historyId);
            if (list != null){
                for (String s : list) {
                    Integer integer = runTime.get(s);
                    if (integer == null){
                        return null;
                    }
                    timeList.add(integer);
                    runtime = runtime +integer;
                }
            }
            run.setRunTime(runtime);
            run.setTimeList(timeList);
            runList.add(run);
        }

        if (pipelineType == 2){
            List<PipelineStages> allStagesConfig = stagesServer.findAllStagesConfig(pipelineId);
            for (PipelineStages pipelineStages : allStagesConfig) {
                PipelineRun run = new PipelineRun();
                run.setHistoryId(historyId);
                run.setRunWay(history.getRunWay());
                int runtime = 0;
                run.setRunLog(history.getRunLog());
                String stagesId = pipelineStages.getStagesId();
                List<PipelineExecLog> allStagesLog = commonService.findAllStagesLog(historyId, stagesId);
                if (allStagesLog == null || allStagesLog.size() == 0){
                    continue;
                }
                List<Integer>  timeList = new ArrayList<>();
                for (PipelineExecLog pipelineExecLog : allStagesLog) {
                    String logId = pipelineExecLog.getLogId();
                    Integer integer = runTime.get(logId);
                    if (integer == null){
                        return null;
                    }
                    timeList.add(integer);
                    runtime = runtime +integer;
                }
                run.setTimeList(timeList);
                run.setRunTime(runtime);
                runList.add(run);
            }
        }

        return runList;
    }

    /**
     * 停止流水线运行
     * @param pipelineId 流水线id
     */
    @Override
    public void killInstance(String pipelineId) {
        PipelineExecHistory history = historyMap.get(pipelineId);

        if (history == null){
            updatePipelineStatus(pipelineId, false);
            stop(pipelineId);
            return;
        }
        String historyId = history.getHistoryId();
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);

        commonService.updateState(historyId,null,PIPELINE_RUN_HALT);

        //停止运行
        stop(pipelineId);
        updatePipelineStatus(pipelineId, false);

        Map<String, String> maps = homeService.initMap(pipeline);
        updateStatus("halt",pipelineId,maps,history);
    }

    /**
     * 查询流水线是否正在运行
     * @param pipelineId 流水线ID
     * @return 结果
     */
    @Override
    public int findState(String pipelineId){
        PipelineExecHistory pipelineExecHistory = historyMap.get(pipelineId);
        if (pipelineExecHistory != null){
            return 1;
        }
        return 2;
    }

    // 构建开始
    private void begin(Pipeline pipeline,int startWAy) {

        String pipelineId = pipeline.getPipelineId();

        //初始化历史
        PipelineExecHistory pipelineExecHistory = commonService.initializeHistory(pipeline,startWAy);
        String historyId = pipelineExecHistory.getHistoryId();

        //获取配置信息
        PipelineProcess pipelineProcess = new PipelineProcess();
        pipelineProcess.setPipelineExecHistory(pipelineExecHistory);

        historyMap.put(pipelineId,pipelineExecHistory);

        //消息
        Map<String, String> maps = homeService.initMap(pipeline);

        boolean courseState = beginCourse(pipelineProcess, pipeline, historyId);

        //执行后置任务
        boolean afterState = beginAfter(pipelineProcess, pipeline, historyId);

        //执行结束
        updatePipelineStatus(pipeline.getPipelineId(), false);

        if (courseState && afterState){
            updateStatus("success",pipelineId,maps,pipelineExecHistory);
            return;
        }
        updateStatus("error",pipelineId,maps,pipelineExecHistory);
    }

    //执行任务
    public boolean beginCourse(PipelineProcess pipelineProcess,Pipeline pipeline,String historyId){
        String pipelineId = pipeline.getPipelineId();
        int pipelineType = pipeline.getPipelineType();
        //多任务
        if (pipelineType == 1 ){
            List<PipelineCourseConfig> allCourseConfig = courseConfigService.findAllCourseConfig(pipelineId);
            if (allCourseConfig == null){
                return true;
            }
          return execCourse(allCourseConfig, pipelineProcess, historyId, pipeline);
        }

        //多阶段
        if (pipelineType == 2){
            List<PipelineStages> allStagesConfig = stagesServer.findAllStagesConfig(pipelineId);
            if (allStagesConfig == null || allStagesConfig.size() == 0){
                return true;
            }
            for (PipelineStages pipelineStages : allStagesConfig) {
                String stagesId = pipelineStages.getStagesId();
                List<PipelineCourseConfig> allStagesCourseConfig = courseConfigService.findAllStagesCourseConfig(stagesId);
                boolean b = execCourse(allStagesCourseConfig, pipelineProcess, historyId, pipeline);
                if (!b){
                    return false;
                }
            }
        }
        return true;
    }

    //执行任务
    private boolean execCourse(List<PipelineCourseConfig> allCourseConfig,
                               PipelineProcess pipelineProcess,String historyId,Pipeline pipeline){

        for (PipelineCourseConfig pipelineCourseConfig : allCourseConfig) {

            int taskType = pipelineCourseConfig.getTaskType();
            int taskSort = pipelineCourseConfig.getTaskSort();
            String stagesId = pipelineCourseConfig.getStagesId();
            PipelineExecLog pipelineExecLog = commonService.initializeLog(historyId,taskSort,taskType);
            String logId = pipelineExecLog.getLogId();
            runTime.put(logId,0);
            time(logId,historyId);
            pipelineExecLog.setStagesId(stagesId);

            // List<String> list = logMap.get(historyId);
            // if (list == null){
            //     list = new ArrayList<>();
            // }
            // list.add(logId);
            // logMap.put(historyId,list);

            pipelineProcess.setPipeline(pipeline);
            pipelineProcess.setPipelineExecLog(pipelineExecLog);
            pipelineProcess.setEnCode("");

            boolean b = taskExecService.beginCourseState(pipelineProcess, pipelineCourseConfig);
            stop(logId+"time");
            if (!b){
                commonService.updateState(historyId,null,PIPELINE_RUN_ERROR);
                return false;
            }
            commonService.updateState(historyId,null,PIPELINE_RUN_SUCCESS);
        }
        return true;
    }

    //执行后置任务
    public boolean beginAfter(PipelineProcess pipelineProcess,Pipeline pipeline,String historyId){

        String pipelineId = pipeline.getPipelineId();
        List<PipelineAfterConfig> allAfterConfig = afterConfigServer.findAllAfterConfig(pipelineId);
        if (allAfterConfig == null){
            return true;
        }

        for (PipelineAfterConfig pipelineAfterConfig : allAfterConfig) {

            int taskSort = 0;
            List<PipelineCourseConfig> courseConfig = courseConfigService.findAllCourseConfig(pipelineId);

            if (courseConfig != null){
                taskSort = courseConfig.size() ;
            }

            int taskType = pipelineAfterConfig.getTaskType();

            taskSort =taskSort + pipelineAfterConfig.getTaskSort();
            PipelineExecLog pipelineExecLog = commonService.initializeLog(historyId,taskSort,taskType);

            String logId = pipelineExecLog.getLogId();

            runTime.put(logId,0);
            time(logId,historyId);

            // List<String> list = logMap.get(historyId);
            // if (list == null){
            //     list = new ArrayList<>();
            // }
            //
            // list.add(logId);
            // logMap.put(historyId,list);

            pipelineProcess.setPipeline(pipeline);
            pipelineProcess.setPipelineExecLog(pipelineExecLog);

            commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"执行后置处理");

            boolean b = taskExecService.beginAfterState(pipelineProcess, pipelineAfterConfig);
            stop(logId+"time");
            if (!b){
                commonService.updateState(historyId,null,PIPELINE_RUN_ERROR);
                return false;
            }
            commonService.updateState(historyId,null,PIPELINE_RUN_SUCCESS);
        }
        // commonService.execHistory(pipelineProcess, PipelineUntil.date(4)+"后置处理完成。");
        return true;
    }

    //时间增加
    private void time(String logId ,String historyId){
        List<String> list = logMap.get(historyId);
        if (list == null){
            list = new ArrayList<>();
        }

        list.add(logId);
        logMap.put(historyId,list);
        executorService.submit(() -> {
            while (true){
                Thread.currentThread().setName(logId+"time");
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
        if (!b){
            pipeline.setPipelineState(2);
        }else {
            pipeline.setPipelineState(1);
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


































