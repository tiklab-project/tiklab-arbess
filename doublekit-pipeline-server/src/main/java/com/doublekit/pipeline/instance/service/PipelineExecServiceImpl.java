package com.doublekit.pipeline.instance.service;


import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.service.execAchieve.*;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    CommonAchieve commonAchieve;

    @Autowired
    CodeAchieve codeAchieve;

    @Autowired
    StructureAchieve structureAchieve;

    @Autowired
    TestAchieve testAchieve;

    @Autowired
    DeployAchieve deployAchieve;

    @Autowired
    ProofService proofService;

    //存放过程状态
    List<PipelineExecHistory> pipelineExecHistoryList = new ArrayList<>();

    //存放构建流水线id
    List<String> pipelineIdList = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //创建线程池
    ExecutorService executorService = Executors.newCachedThreadPool();

    //启动
    @Override
    public int  start(String pipelineId){
        // 判断同一任务是否在运行
        if (pipelineIdList != null){
            for (String id : pipelineIdList) {
                if (id .equals(pipelineId)){
                    return 100;
                }
            }
        }

        executorService.submit(() -> {
            Thread.currentThread().setName(pipelineId);
            begin(pipelineId);
        });
        // 执行构建
        //executorService.submit(() -> begin(pipelineId));
        return 1;
    }

    //查询构建状态
    @Override
    public PipelineExecHistory findInstanceState(String pipelineId){
        if (pipelineExecHistoryList != null){
            for (PipelineExecHistory pipelineExecHistory : pipelineExecHistoryList) {
                if (pipelineExecHistory.getPipeline() != null){
                    if (pipelineExecHistory.getPipeline().getPipelineId().equals(pipelineId)){
                        return  pipelineExecHistory;
                    }
                }
            }
        }
        return null;
    }

    //停止运行
    @Override
    public void killInstance(String pipelineId) {
        PipelineExecHistory pipelineExecHistory = findInstanceState(pipelineId);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"流水线停止运行......");
        pipelineExecHistoryList.add(pipelineExecHistory);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        commonAchieve.success(pipelineExecHistory,pipelineId,pipelineExecHistoryList);
        if (pipelineIdList != null) {
            pipelineIdList.removeIf(id -> id.equals(pipelineId));
        }
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            String nm = lstThreads[i].getName();
            if (nm.equals(pipelineId)) {
                logger.info("结束线程 ：" + i + "  pipelineId ：" + pipelineId);
                lstThreads[i].stop();
            }
        }
    }

    //判断流水线是否正在执行
    @Override
    public int findState(String pipelineId){
        if (pipelineIdList.size() != 0){
            for (String s : pipelineIdList) {
                if (pipelineId.equals(s)){
                    return 1;
                }
            }
        }
        return 0;
    }

    // 构建开始
    private void begin(String pipelineId) {
        pipelineIdList.add(pipelineId);
        List<PipelineConfigure> allConfigure = pipelineConfigureService.findAllConfigure(pipelineId);
        String historyId = pipelineExecHistoryService.createHistory(new PipelineExecHistory());
        PipelineExecHistory pipelineExecHistory = commonAchieve.initializeHistory(historyId);
        if (allConfigure != null){
            for (PipelineConfigure pipelineConfigure : allConfigure) {
                //初始化历史信息
                Pipeline pipeline = pipelineConfigure.getPipeline();
                pipelineExecHistory.setPipeline(pipeline);
                pipelineExecHistory.setHistoryId(historyId);
                pipelineExecHistory.setExecName(pipeline.getPipelineCreateUser());
                pipelineExecHistoryService.updateHistory(pipelineExecHistory);
                pipelineExecHistoryList.add(pipelineExecHistory);
                switch (pipelineConfigure.getTaskType()) {
                    case 1, 2 -> {
                        int state = codeAchieve.codeStart(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (state == 0) {
                            pipelineIdList.removeIf(id -> id.equals(pipelineId));
                            return ;
                        }
                    }
                    case 11 -> {
                        int state = testAchieve.testStart(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (state == 0) {
                            pipelineIdList.removeIf(id -> id.equals(pipelineId));
                            return ;
                        }
                    }
                    case 21, 22 -> {
                        int state  = structureAchieve.structureStart(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (state == 0) {
                            pipelineIdList.removeIf(id -> id.equals(pipelineId));
                            return ;
                        }
                    }
                    case 31, 32-> {
                        int state = deployAchieve.deployStart(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (state == 0) {
                            pipelineIdList.removeIf(id -> id.equals(pipelineId));
                            return ;
                        }
                    }
                }
                pipelineExecHistory.setSort(pipelineExecHistory.getSort() +1);
                pipelineExecHistory.setStatus(pipelineExecHistory.getStatus() +1);
                pipelineExecHistoryService.updateHistory(pipelineExecHistory);
            }
        }
        commonAchieve.success(pipelineExecHistory, pipelineId, pipelineExecHistoryList);
        pipelineIdList.removeIf(id -> id.equals(pipelineId));
    }

    @Override
    public Boolean testPass(String url ,String proofId){
        Proof proof = proofService.findOneProof(proofId);
        if (proof != null){
            if (url != null){
                return codeAchieve.checkAuth(url, proof);
            }else {
                return deployAchieve.testSshSftp(proof);
            }
        }
        return false;
    }

















}
