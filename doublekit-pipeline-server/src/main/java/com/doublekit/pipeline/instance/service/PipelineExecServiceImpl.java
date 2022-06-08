package com.doublekit.pipeline.instance.service;


import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.execute.model.CodeGit.FileTree;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineProcess;
import com.doublekit.pipeline.instance.service.execAchieve.*;
import com.doublekit.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
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
    CommonAchieve commonAchieve;

    @Autowired
    CodeAchieve codeAchieve;

    @Autowired
    StructureAchieve structureAchieve;

    @Autowired
    TestAchieve testAchieve;

    @Autowired
    DeployAchieve deployAchieve;

    //存放过程状态
    List<PipelineExecHistory> pipelineExecHistoryList = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //创建线程池
    ExecutorService executorService = Executors.newCachedThreadPool();

    //启动
    @Override
    public int  start(String pipelineId){
        // 判断同一任务是否在运行
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        if (pipeline.getPipelineState() == 1){
            return 100;
        }
        executorService.submit(() -> {
            Thread.currentThread().setName(pipelineId);
            begin(pipelineId);
        });
        return 1;
    }

    //查询构建中的信息
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
        commonAchieve.halt(pipelineExecHistory,pipelineId,pipelineExecHistoryList);
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();

        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            String nm = lstThreads[i].getName();
            if (nm.equals(pipelineId)) {
                logger.info("结束线程 ：" + i + "  pipelineId ：" + pipelineId);
                lstThreads[i].stop();
               return;
            }
        }
    }

    //判断流水线是否正在执行
    @Override
    public int findState(String pipelineId){
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        if (pipeline.getPipelineState() == 1){
            return 1;
        }
        return 0;
    }

    // 构建开始
    private void begin(String pipelineId) {
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        pipeline.setPipelineState(1);
        pipelineService.updatePipeline(pipeline);

        String historyId = pipelineExecHistoryService.createHistory(new PipelineExecHistory());
        PipelineExecHistory pipelineExecHistory = commonAchieve.initializeHistory(historyId,pipeline);

        PipelineProcess pipelineProcess = new PipelineProcess();
        List<PipelineConfigure> allConfigure = pipelineService.findPipelineConfigure(pipelineId);
        if (allConfigure != null){
            for (PipelineConfigure pipelineConfigure : allConfigure) {
                pipelineProcess.setPipelineExecHistory(pipelineExecHistory);
                pipelineProcess.setPipelineConfigure(pipelineConfigure);
                pipelineExecHistoryList.add(pipelineExecHistory);
                switch (pipelineConfigure.getTaskType()) {
                    case 1,2,3,4,5 -> {
                        int state = codeAchieve.clone(pipelineProcess, pipelineExecHistoryList);
                        if (state == 0) {
                            pipeline.setPipelineState(0);
                            pipelineService.updatePipeline(pipeline);
                            return ;
                        }
                    }
                    case 11 -> {
                        int state = testAchieve.test(pipelineProcess, pipelineExecHistoryList);
                        if (state == 0) {
                            pipeline.setPipelineState(0);
                            pipelineService.updatePipeline(pipeline);
                            return ;
                        }
                    }
                    case 21, 22 -> {
                        int state  = structureAchieve.structure(pipelineProcess, pipelineExecHistoryList);
                        if (state == 0) {
                            pipeline.setPipelineState(0);
                            pipelineService.updatePipeline(pipeline);
                            return ;
                        }
                    }
                    case 31, 32-> {
                        int state = deployAchieve.deploy(pipelineProcess, pipelineExecHistoryList);
                        if (state == 0) {
                            pipeline.setPipelineState(0);
                            pipelineService.updatePipeline(pipeline);
                            return ;
                        }
                    }
                }
                pipelineExecHistory.setSort(pipelineExecHistory.getSort() +1);
                pipelineExecHistory.setStatus(pipelineExecHistory.getStatus() +1);
            }
        }
        commonAchieve.success(pipelineExecHistory, pipelineId, pipelineExecHistoryList);
    }

    //获取文件树
    @Override
    public List<FileTree> fileTree(String pipelineId){
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        String path = "D:\\clone\\"+pipeline.getPipelineName();
        List<FileTree> trees = new ArrayList<>();
        File file = new File(path);
        //判断文件是否存在
        if (file.exists()){
            List<FileTree> list = codeAchieve.fileTree(file, trees);
            list.sort(Comparator.comparing(FileTree::getTreeType,Comparator.reverseOrder()));
            return list;
        }
       return null;
    }

    //
    @Override
    public  List<String> readFile(String path){
        return codeAchieve.readFile(path);
    }






}
