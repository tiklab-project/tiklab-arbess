package com.tiklab.matflow.instance.service;



import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.service.MatFlowService;
import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowExecLog;
import com.tiklab.matflow.instance.model.MatFlowProcess;
import com.tiklab.matflow.instance.service.execAchieveImpl.*;
import com.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Exporter
public class MatFlowExecServiceImpl implements MatFlowExecService {

    @Autowired
    MatFlowExecHistoryService matFlowExecHistoryService;

    @Autowired
    MatFlowService matFlowService;

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
    MatFlowActionService matFlowActionService;


    //存放过程状态
    List<MatFlowExecHistory> matFlowExecHistoryList = new ArrayList<>();


    private static final Logger logger = LoggerFactory.getLogger(MatFlowExecServiceImpl.class);

    //开始执行时间
    long beginTime = 0;


    //创建线程池
    ExecutorService executorService = Executors.newCachedThreadPool();

    //启动
    @Override
    public int  start(String matFlowId,String userId){
        // 判断同一任务是否在运行
        MatFlow matFlow = matFlowService.findMatFlow(matFlowId);
        if (matFlow.getMatflowState() == 1){
            return 100;
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            return 1;
        }
        executorService.submit(() -> {
            beginTime = new Timestamp(System.currentTimeMillis()).getTime();
         Thread.currentThread().setName(matFlowId);
                begin(matFlow,userId);
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
           return 1;
        }
        return 1;
    }

    //存放运行时间
    int[] time = {1,0,0,0};

    //查询构建中的信息
    @Override
    public MatFlowExecHistory findInstanceState(String matFlowId){
        if (matFlowExecHistoryList == null){
            return null;
        }
        for (MatFlowExecHistory matFlowExecHistory : matFlowExecHistoryList) {
            MatFlow matFlow = matFlowExecHistory.getMatFlow();
            if (matFlow == null || !matFlow.getMatflowId().equals(matFlowId)){
                continue;
            }
            time[matFlowExecHistory.getSort()-1] = time[matFlowExecHistory.getSort()-1] + 1;
            matFlowExecHistory.setOneTime(matFlowExecHistoryService.formatDateTime(time[0]));
            matFlowExecHistory.setTwoTime(matFlowExecHistoryService.formatDateTime(time[1]));
            matFlowExecHistory.setThreeTime(matFlowExecHistoryService.formatDateTime(time[2]));
            matFlowExecHistory.setFourTime(matFlowExecHistoryService.formatDateTime(time[3]));
            matFlowExecHistory.setAllTime(matFlowExecHistoryService.formatDateTime(time[0]+time[1]+time[2]+time[3]));
            return matFlowExecHistory;
        }
      return null;
    }

    //停止运行
    @Override
    public void killInstance(String matFlowId,String userId) {
        MatFlowProcess matFlowProcess = new MatFlowProcess();
        time[0]=1;time[1]=0;time[2]=0;time[3]=0;
        MatFlowExecHistory matFlowExecHistory = findInstanceState(matFlowId);
        if (matFlowExecHistory == null){
            MatFlow matFlow = matFlowService.findMatFlow(matFlowId);
            matFlow.setMatflowState(0);
            matFlowService.updateMatFlow(matFlow);
            return;
        }
        MatFlowExecLog matFlowExecLog = matFlowExecHistoryService.getRunLog(matFlowExecHistory.getHistoryId());
        matFlowProcess.setMatFlowExecLog(matFlowExecLog);
        matFlowProcess.setMatFlowExecHistory(matFlowExecHistory);
        MatFlow matFlow = matFlowExecHistory.getMatFlow();
        //动态
        matFlowActionService.createActive(userId, matFlow,"停止流水线/的运行");

        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        matFlowExecLog.setRunTime(time- matFlowExecHistory.getRunTime());
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n"+"流水线停止运行......");
        matFlowExecHistoryList.add(matFlowExecHistory);
        commonAchieveServiceImpl.halt(matFlowProcess,matFlowId, matFlowExecHistoryList);

        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            String nm = lstThreads[i].getName();
            if (!nm.equals(matFlowId)) {
               continue;
            }
            lstThreads[i].stop();
            matFlow.setMatflowState(0);
            matFlowService.updateMatFlow(matFlow);
            return;
        }
    }

    //判断流水线是否正在执行
    @Override
    public int findState(String matFlowId){
        MatFlow matFlow = matFlowService.findMatFlow(matFlowId);
        if (matFlow == null ){
            return 0;
        }
        if (matFlow.getMatflowState() == 1){
            return 1;
        }
        return 0;
    }

    // 构建开始
    private void begin(MatFlow matFlow, String userId) {

        //更新流水线状态
        matFlow.setMatflowState(1);
        matFlowService.updateMatFlow(matFlow);

        //添加动态
        matFlowActionService.createActive(matFlow.getUser().getId(), matFlow,"执行流水线");

        //初始化历史
        String historyId = matFlowExecHistoryService.createHistory(new MatFlowExecHistory());
        MatFlowExecHistory matFlowExecHistory = commonAchieveServiceImpl.initializeHistory(historyId, matFlow,userId);

        //获取配置信息
        MatFlowProcess matFlowProcess = new MatFlowProcess();
        List<MatFlowConfigure> allConfigure = matFlowService.findMatFlowConfigure(matFlow.getMatflowId());
        if (allConfigure != null){
            //开始执行
            for (MatFlowConfigure matFlowConfigure : allConfigure) {
                matFlowProcess.setMatFlowExecHistory(matFlowExecHistory);
                matFlowProcess.setMatFlowConfigure(matFlowConfigure);
                matFlowExecHistoryList.add(matFlowExecHistory);
                Integer integer = beginState(matFlowProcess, matFlow, matFlowConfigure.getTaskType());
                if (integer == 0){
                    return;
                }
                matFlowExecHistory.setSort(matFlowExecHistory.getSort() +1);
                matFlowExecHistory.setStatus(matFlowExecHistory.getStatus() +1);
            }
        }
        commonAchieveServiceImpl.success(matFlowExecHistory, matFlow.getMatflowId(), matFlowExecHistoryList);
        matFlow.setMatflowState(0);
        matFlowService.updateMatFlow(matFlow);
        time[0]=1;time[1]=0;time[2]=0;time[3]=0;
    }

    private Integer beginState(MatFlowProcess matFlowProcess, MatFlow matFlow, int type){
        int state = 1;
        switch (type) {
            case 1,2,3,4,5 -> state = codeAchieveServiceImpl.clone(matFlowProcess, matFlowExecHistoryList);
            case 11 -> state = testAchieveServiceImpl.test(matFlowProcess, matFlowExecHistoryList);
            case 21, 22 -> state  = structureAchieveServiceImpl.structure(matFlowProcess, matFlowExecHistoryList);
            case 31, 32-> state = deployAchieveServiceImpl.deploy(matFlowProcess, matFlowExecHistoryList);
        }
        if (state == 0) {
            matFlow.setMatflowState(0);
            matFlowService.updateMatFlow(matFlow);
            time[0]=1;time[1]=0;time[2]=0;time[3]=0;
            return 0;
        }
        return 1;
    }

}
