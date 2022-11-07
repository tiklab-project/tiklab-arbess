package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineConfigOrder;
import net.tiklab.matflow.definition.service.PipelineConfigOrderService;
import net.tiklab.matflow.definition.service.PipelineService;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineHomeService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Exporter
public class PipelineExecServiceImpl implements PipelineExecService {

    @Autowired
    PipelineService pipelineService;

    @Autowired
    PipelineTaskExecService taskExecService;

    @Autowired
    ConfigCommonService commonService;

    @Autowired
    PipelineConfigOrderService configOrderService;

    @Autowired
    PipelineHomeService homeService;

    Map<String,List<Integer>> map = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //存放过程状态
    public static final List<PipelineExecHistory> pipelineExecHistoryList = new ArrayList<>();

    //开始执行时间
    long beginTime = 0;

    //创建线程池
    ExecutorService executorService = Executors.newCachedThreadPool();

    //启动
    @Override
    public int start(String pipelineId){
        // 判断同一任务是否在运行
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        if (pipeline.getPipelineState() == 1){
            return 100;
        }

        homeService.message("matflowExec", "流水线"+pipeline.getPipelineName()+"开始执行");

        executorService.submit(() -> {
            beginTime = new Timestamp(System.currentTimeMillis()).getTime();
            Thread.currentThread().setName(pipelineId);
            begin(pipeline);
        });
        return 1;
    }

    //查询构建中的信息
    @Override
    public PipelineExecHistory findInstanceState(String pipelineId){
        String loginId = LoginContext.getLoginId();
        for (PipelineExecHistory history : pipelineExecHistoryList) {
            Pipeline pipeline = history.getPipeline();
            if (pipeline == null || !pipeline.getPipelineId().equals(pipelineId)){
                continue;
            }
            PipelineExecLog log = commonService.getExecPipelineLog(history.getHistoryId());
            List<Integer> timeList = map.get(loginId);
            Integer integer = timeList.get(timeList.size() - 1);
            System.out.println("数据为："+integer+1);
            timeList.remove(timeList.size() - 1);
            timeList.add(integer+1);
            history.setTimeList(timeList);
            System.out.println(timeList.size());
            return history;
        }
      return null;
    }

    //停止运行
    @Override
    public void killInstance(String pipelineId) {
        PipelineProcess pipelineProcess = new PipelineProcess();
        PipelineExecHistory pipelineExecHistory = findInstanceState(pipelineId);
        if (pipelineExecHistory == null){
            Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
            pipeline.setPipelineState(0);
            pipelineService.updatePipeline(pipeline);
            return;
        }
        PipelineExecLog pipelineExecLog =commonService.getExecPipelineLog(pipelineExecHistory.getHistoryId());
        pipelineProcess.setPipelineExecLog(pipelineExecLog);
        pipelineProcess.setPipelineExecHistory(pipelineExecHistory);
        Pipeline pipeline = pipelineExecHistory.getPipeline();

        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        pipelineExecLog.setRunTime(time- pipelineExecHistory.getRunTime());
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"流水线停止运行......");
        String loginId = LoginContext.getLoginId();
        map.remove(loginId);
        //停止运行
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            String nm = lstThreads[i].getName();
            if (!nm.equals(pipelineId)) {
               continue;
            }
            lstThreads[i].stop();
            pipeline.setPipelineState(0);
            pipelineService.updatePipeline(pipeline);
        }
        commonService.halt(pipelineProcess,pipelineId);
        HashMap<String, String> map = new HashMap<>();
        map.put("message","停止执行");
        map.put("pipelineId", pipeline.getPipelineId());
        map.put("pipelineName", pipeline.getPipelineName());
        homeService.log("execStatus", "pipelineExec", map);
        homeService.message("matflowExec", "用户停止了流水线"+pipeline.getPipelineName()+"的运行。");
    }

    //判断流水线是否正在执行
    @Override
    public int findState(String pipelineId){
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        if (pipeline == null ){
            return 0;
        }
        if (pipeline.getPipelineState() == 1){
            return 1;
        }
        return 0;
    }

    // 构建开始
    private void begin(Pipeline pipeline) {

        //更新流水线状态为执行
        pipeline.setPipelineState(1);
        pipelineService.updatePipeline(pipeline);
        String pipelineId = pipeline.getPipelineId();

        //初始化历史
        PipelineExecHistory pipelineExecHistory = commonService.initializeHistory(pipeline);

        //获取配置信息
        PipelineProcess pipelineProcess = new PipelineProcess();
        pipelineProcess.setPipelineExecHistory(pipelineExecHistory);
        pipelineExecHistoryList.add(pipelineExecHistory);

        //获取所有配置顺序
        List<PipelineConfigOrder> allPipelineConfig = configOrderService.findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null){
            return;
        }

        HashMap<String, String> maps = new HashMap<>();
        maps.put("message","执行成功");
        maps.put("pipelineId", pipeline.getPipelineId());
        maps.put("pipelineName", pipeline.getPipelineName());

        String loginId = LoginContext.getLoginId();

        for (PipelineConfigOrder pipelineConfigOrder : allPipelineConfig) {
            List<Integer> integers = map.get(loginId);
            if (integers == null || integers.size() == 0){
                integers = new ArrayList<>();
            }
            integers.add(0);
            map.put(loginId,integers);

            //初始化日志
            PipelineExecLog pipelineExecLog = commonService.initializeLog(pipelineExecHistory.getHistoryId(), pipelineConfigOrder);
            pipelineProcess.setPipeline(pipeline);
            pipelineProcess.setPipelineExecLog(pipelineExecLog);

            int taskType = pipelineConfigOrder.getTaskType();
            Object config = configOrderService.findOneConfig(pipelineId,taskType);

            boolean state = taskExecService.beginState(pipelineProcess,config,taskType);

            if (!state){
                pipeline.setPipelineState(0);
                commonService.error(pipelineExecHistory, pipeline.getPipelineId());
                pipelineService.updatePipeline(pipeline);
                maps.put("message","执行失败");
                homeService.log("execStatus", "pipelineExec", maps);
                homeService.message("matflowExec", "流水线"+pipeline.getPipelineName()+"执行失败。");
                map.remove(loginId);
                return;
            }
        }

        commonService.success(pipelineExecHistory, pipeline.getPipelineId());
        pipeline.setPipelineState(0);
        pipelineService.updatePipeline(pipeline);
        maps.put("message","执行成功");
        homeService.log("execStatus", "pipelineExec", maps);
        homeService.message("matflowExec", "流水线"+pipeline.getPipelineName()+"执行成功。");
        map.remove(loginId);
    }


}


































