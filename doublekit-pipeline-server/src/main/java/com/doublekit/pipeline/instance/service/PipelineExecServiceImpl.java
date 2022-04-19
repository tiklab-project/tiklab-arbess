package com.doublekit.pipeline.instance.service;


import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.instance.model.*;
import com.doublekit.pipeline.instance.service.execAchieve.*;
import com.doublekit.rpc.annotation.Exporter;
import com.ibm.icu.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.*;


@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    PipelineConfigureService pipelineConfigureService;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    //存放过程状态
    List<PipelineExecHistory> pipelineExecHistoryList = new ArrayList<>();

    //存放构建流水线id
    List<String> pipelineIdList = new ArrayList<>();

    CommonAchieve commonAchieve = new CommonAchieve();

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //启动
    @Override
    public int  start(String pipelineId){
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //判断同一任务是否在运行
        if (pipelineIdList != null){
            for (String id : pipelineIdList) {
                if (id .equals(pipelineId)){
                    return 100;
                }
            }
        }
        // 执行构建
        executorService.submit(() -> begin(pipelineId));
        return 1;
    }

    //查询构建状态
    @Override
    public PipelineExecHistory findInstanceState(String pipelineId){

        if (pipelineExecHistoryList != null){
            for (PipelineExecHistory pipelineExecHistory : pipelineExecHistoryList) {
                if (pipelineExecHistory.getPipeline().getPipelineId().equals(pipelineId)){
                    return  pipelineExecHistory;
                }
            }
        }
        return null;
    }


    // 构建开始
    private void begin(String pipelineId) {
        // String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        //把执行构建的流水线加入进来
        pipelineIdList.add(pipelineId);
        String historyId = pipelineExecHistoryService.createHistory(new PipelineExecHistory());
        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory();
        pipelineExecHistory.setHistoryId(historyId);
        List<PipelineConfigure> allConfigure = pipelineConfigureService.findAllConfigure(pipelineId);
        if (allConfigure != null){
            allConfigure.sort(Comparator.comparing(PipelineConfigure::getTaskSort));
            for (PipelineConfigure pipelineConfigure : allConfigure) {
                switch (pipelineConfigure.getTaskType()) {
                    case 1, 2 -> {
                        int code = new CodeAchieve().gitClone(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (code == 0) {
                            return ;
                        }
                    }
                    case 11 -> {
                        int test = new TestAchieve().unitTesting(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (test == 0) {
                            return ;
                        }
                    }
                    case 21, 22 -> {
                        int structure = new StructureAchieve().structure(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (structure == 0) {
                            return ;
                        }
                    }
                    case 31 -> {
                        int liunx = new DeployAchieve().liunx(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (liunx == 0) {
                            return ;
                        }
                    }
                    case 32 -> {
                        int docker = new DeployAchieve().docker(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (docker == 0) {
                            return ;
                        }
                    }
                }
            }
        }
        commonAchieve.success(pipelineExecHistory,pipelineId);
    }
}
