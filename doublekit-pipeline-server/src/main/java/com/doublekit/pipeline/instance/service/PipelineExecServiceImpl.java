package com.doublekit.pipeline.instance.service;


import com.doublekit.pipeline.definition.service.PipelineConfigureService;
import com.doublekit.pipeline.instance.model.*;
import com.doublekit.rpc.annotation.Exporter;
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
    PipelineExecLogService pipelineExecLogService;

    //存放过程状态
    List<PipelineExecHistory> pipelineExecHistoryList = new ArrayList<>();

    //存放构建流水线id
    List<String> pipelineIdList = new ArrayList<>();

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
        // executorService.submit(() -> begin(pipelineId));
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
    // private String begin(String pipelineId) {
    //     String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    //     //把执行构建的流水线加入进来
    //     pipelineIdList.add(pipelineId);
    //     //创建日志
    //     PipelineExecLog pipelineExecLog = pipelineExecLogService.createLog();
    //     pipelineExecLog.setPipelineId(pipelineId);
    //     pipelineExecLogList.add(pipelineExecLog);
    //
    //     return "1";
    // }
}
