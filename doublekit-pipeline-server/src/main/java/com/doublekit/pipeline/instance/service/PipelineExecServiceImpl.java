package com.doublekit.pipeline.instance.service;


import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.definition.service.PipelineService;
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

    //存放构建流水线id
    List<String> pipelineIdList = new ArrayList<>();

    @Autowired
    CommonAchieve commonAchieve;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //启动
    @Override
    public int  start(String pipelineId){
        //创建线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 判断同一任务是否在运行
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
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        pipelineIdList.add(pipelineId);
        String historyId = pipelineExecHistoryService.createHistory(new PipelineExecHistory());
        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory();
        pipelineExecHistory.setCreateTime(time);
        pipelineExecHistory.setRunWay(1);
        pipelineExecHistory.setHistoryId(historyId);
        List<PipelineConfigure> allConfigure = pipelineConfigureService.findAllConfigure(pipelineId);
        pipelineExecHistory.setSort(1);
        pipelineExecHistory.setStatus(0);
        if (allConfigure != null){
            // allConfigure.sort(Comparator.comparing(PipelineConfigure::getTaskSort));
            for (PipelineConfigure pipelineConfigure : allConfigure) {
                Pipeline pipeline = pipelineConfigure.getPipeline();
                pipelineExecHistory.setPipeline(pipeline);
                pipelineExecHistory.setExecName(pipeline.getPipelineCreateUser());
                pipelineExecHistoryService.updateHistory(pipelineExecHistory);
                pipelineExecHistoryList.add(pipelineExecHistory);
                switch (pipelineConfigure.getTaskType()) {
                    case 1, 2 -> {
                        String e = codeAchieve.codeStart(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (e != null) {
                            if (pipelineIdList != null) {
                                pipelineIdList.removeIf(id -> id.equals(pipelineId));
                            }
                            commonAchieve.error(pipelineExecHistory,e,pipelineId,pipelineExecHistoryList);
                            return ;
                        }
                    }
                    case 11 -> {
                        String e = testAchieve.testStart(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (e != null) {
                            if (pipelineIdList != null) {
                                pipelineIdList.removeIf(id -> id.equals(pipelineId));
                            }
                            commonAchieve.error(pipelineExecHistory,e,pipelineId,pipelineExecHistoryList);
                            return ;
                        }
                    }
                    case 21, 22 -> {
                        String e  = structureAchieve.structureStart(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (e != null) {
                            if (pipelineIdList != null) {
                                pipelineIdList.removeIf(id -> id.equals(pipelineId));
                            }
                            commonAchieve.error(pipelineExecHistory,e,pipelineId,pipelineExecHistoryList);
                            return ;
                        }
                    }
                    case 31, 32-> {
                        String e  = deployAchieve.deployStart(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
                        if (e != null) {
                            if (pipelineIdList != null) {
                                pipelineIdList.removeIf(id -> id.equals(pipelineId));
                            }
                            commonAchieve.error(pipelineExecHistory,e,pipelineId,pipelineExecHistoryList);
                            return ;
                        }
                    }
                }
                pipelineExecHistory.setSort(pipelineExecHistory.getSort() +1);
                pipelineExecHistory.setStatus(pipelineExecHistory.getStatus() +1);
            }
        }
        pipelineExecHistoryList.add(pipelineExecHistory);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        commonAchieve.success(pipelineExecHistory,pipelineId,pipelineExecHistoryList);
        if (pipelineIdList != null) {
            pipelineIdList.removeIf(id -> id.equals(pipelineId));
        }
    }
}
