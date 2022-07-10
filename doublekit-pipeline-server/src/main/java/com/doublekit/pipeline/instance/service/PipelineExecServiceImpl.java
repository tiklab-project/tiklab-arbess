package com.doublekit.pipeline.instance.service;


import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.execute.model.CodeGit.FileTree;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineProcess;
import com.doublekit.pipeline.instance.service.execAchieveImpl.*;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineService pipelineService;

    @Autowired
    CommonAchieveServiceImpl commonAchieveServiceImpl ;

    @Autowired
    CodeAchieveServiceImpl codeAchieveServiceImpl ;

    @Autowired
    StructureAchieveServiceImpl structureAchieveServiceImpl ;

    @Autowired
    TestAchieveServiceImpl testAchieveServiceImpl;

    @Autowired
    DeployAchieveServiceImpl deployAchieveServiceImpl ;

    @Autowired
    PipelineActionService pipelineActionService;


    //存放过程状态
    List<PipelineExecHistory> pipelineExecHistoryList = new ArrayList<>();


    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //开始执行时间
    long beginTime = 0;



    //启动
    @Override
    public int  start(String pipelineId,String userId){
        // 判断同一任务是否在运行
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        if (pipeline.getPipelineState() == 1){
            return 100;
        }
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();

        Thread.currentThread().setName(pipelineId);
        begin(pipeline,userId);

        //executorService.submit(() -> {
        //    beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //
        //});
        return 1;
    }

    //存放运行时间
    int[] time = {1,0,0,0};

    //查询构建中的信息
    @Override
    public PipelineExecHistory findInstanceState(String pipelineId){
        if (pipelineExecHistoryList == null){
            return null;
        }
        for (PipelineExecHistory pipelineExecHistory : pipelineExecHistoryList) {
            Pipeline pipeline = pipelineExecHistory.getPipeline();
            if (pipeline == null || !pipeline.getPipelineId().equals(pipelineId)){
                continue;
            }
            time[pipelineExecHistory.getSort()-1] = time[pipelineExecHistory.getSort()-1] + 1;
            pipelineExecHistory.setOneTime(pipelineExecHistoryService.formatDateTime(time[0]));
            pipelineExecHistory.setTwoTime(pipelineExecHistoryService.formatDateTime(time[1]));
            pipelineExecHistory.setThreeTime(pipelineExecHistoryService.formatDateTime(time[2]));
            pipelineExecHistory.setFourTime(pipelineExecHistoryService.formatDateTime(time[3]));
            pipelineExecHistory.setAllTime(pipelineExecHistoryService.formatDateTime(time[0]+time[1]+time[2]+time[3]));
            return  pipelineExecHistory;
        }
      return null;
    }

    //停止运行
    @Override
    public void killInstance(String pipelineId,String userId) {
        PipelineProcess pipelineProcess = new PipelineProcess();
        time[0]=1;time[1]=0;time[2]=0;time[3]=0;
        PipelineExecHistory pipelineExecHistory = findInstanceState(pipelineId);
        if (pipelineExecHistory == null){
            Pipeline pipeline = pipelineService.findPipeline(pipelineId);
            pipeline.setPipelineState(0);
            pipelineService.updatePipeline(pipeline);
            return;
        }

        PipelineExecLog pipelineExecLog = pipelineExecHistoryService.getRunLog(pipelineExecHistory.getHistoryId());
        pipelineProcess.setPipelineExecLog(pipelineExecLog);
        pipelineProcess.setPipelineExecHistory(pipelineExecHistory);
        Pipeline pipeline = pipelineExecHistory.getPipeline();
        //动态
        pipelineActionService.createActive(userId,pipeline,"停止流水线/的运行");

        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        pipelineExecLog.setRunTime(time-pipelineExecHistory.getRunTime());
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"流水线停止运行......");
        pipelineExecHistoryList.add(pipelineExecHistory);
        commonAchieveServiceImpl.halt(pipelineProcess,pipelineId,pipelineExecHistoryList);

        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            String nm = lstThreads[i].getName();
            if (!nm.equals(pipelineId)) {
               continue;
            }
            logger.info("结束线程 ：" + i + "  pipelineId ：" + pipelineId);
            lstThreads[i].stop();
            return;
        }
    }

    //判断流水线是否正在执行
    @Override
    public int findState(String pipelineId){
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        if (pipeline == null ){
            return 0;
        }
        if (pipeline.getPipelineState() == 1){
            return 1;
        }
        return 0;
    }

    // 构建开始
    private void begin(Pipeline pipeline,String userId) {

        //更新流水线状态
        //Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        pipeline.setPipelineState(1);
        pipelineService.updatePipeline(pipeline);

        //添加动态
        //pipelineActionService.createActive(pipeline.getUser().getId(),pipeline,"执行流水线");

        //初始化历史
        String historyId = pipelineExecHistoryService.createHistory(new PipelineExecHistory());
        PipelineExecHistory pipelineExecHistory = commonAchieveServiceImpl.initializeHistory(historyId,pipeline,userId);

        //获取配置信息
        PipelineProcess pipelineProcess = new PipelineProcess();
        List<PipelineConfigure> allConfigure = pipelineService.findPipelineConfigure(pipeline.getPipelineId());
        if (allConfigure == null){
           return;
        }
        //开始执行
        for (PipelineConfigure pipelineConfigure : allConfigure) {
            pipelineProcess.setPipelineExecHistory(pipelineExecHistory);
            pipelineProcess.setPipelineConfigure(pipelineConfigure);
            pipelineExecHistoryList.add(pipelineExecHistory);
            Integer integer = beginState(pipelineProcess, pipeline, pipelineConfigure.getTaskType());
            if (integer == 0){
                return;
            }
            //switch (pipelineConfigure.getTaskType()) {
            //    case 1,2,3,4,5 -> {
            //        int state = codeAchieveServiceImpl.clone(pipelineProcess, pipelineExecHistoryList);
            //        if (state == 0) {
            //            error(pipeline);
            //            return ;
            //        }
            //    }
            //    case 11 -> {
            //        int state = testAchieveServiceImpl.test(pipelineProcess, pipelineExecHistoryList);
            //        if (state == 0) {
            //            error(pipeline);
            //            return ;
            //        }
            //    }
            //    case 21, 22 -> {
            //        int state  = structureAchieveServiceImpl.structure(pipelineProcess, pipelineExecHistoryList);
            //        if (state == 0) {
            //            error(pipeline);
            //            return ;
            //        }
            //    }
            //    case 31, 32-> {
            //        int state = deployAchieveServiceImpl.deploy(pipelineProcess, pipelineExecHistoryList);
            //        if (state == 0) {
            //            error(pipeline);
            //            return ;
            //        }
            //    }
            //}
            pipelineExecHistory.setSort(pipelineExecHistory.getSort() +1);
            pipelineExecHistory.setStatus(pipelineExecHistory.getStatus() +1);
        }
        commonAchieveServiceImpl.success(pipelineExecHistory, pipeline.getPipelineId(), pipelineExecHistoryList);
        time[0]=1;time[1]=0;time[2]=0;time[3]=0;
    }

    private Integer beginState(PipelineProcess pipelineProcess ,Pipeline pipeline,int type){
        int state = 1;
        switch (type) {
            case 1,2,3,4,5 -> state = codeAchieveServiceImpl.clone(pipelineProcess, pipelineExecHistoryList);
            case 11 -> state = testAchieveServiceImpl.test(pipelineProcess, pipelineExecHistoryList);
            case 21, 22 -> state  = structureAchieveServiceImpl.structure(pipelineProcess, pipelineExecHistoryList);
            case 31, 32-> state = deployAchieveServiceImpl.deploy(pipelineProcess, pipelineExecHistoryList);
        }
        if (state == 0) {
            pipeline.setPipelineState(0);
            pipelineService.updatePipeline(pipeline);
            time[0]=1;time[1]=0;time[2]=0;time[3]=0;
            return 0;
        }
        return 1;
    }

}
