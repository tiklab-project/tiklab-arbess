package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineConfigOrder;
import net.tiklab.matflow.definition.service.PipelineConfigOrderService;
import net.tiklab.matflow.definition.service.PipelineService;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineProcess;
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

    //运行时间
    Map<String,List<Integer>> map = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //流水线运行历史
    public static Map<String,PipelineExecHistory> historyMap = new HashMap<>();

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
        Map<String, String> map1 = PipelineUntil.initMap(pipeline);
        map1.put("pipelineId",pipelineId);
        map1.put("pipelineName",pipeline.getPipelineName());
        homeService.message("pipelineExec", map1);
        homeService.log("exec","run","pipelineExec", map1);
        executorService.submit(() -> {
            Thread.currentThread().setName(pipelineId);
            begin(pipeline);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new ApplicationException(e);
        }

        return 1;
    }

    //查询构建中的信息
    @Override
    public PipelineExecHistory findInstanceState(String pipelineId){
        PipelineExecHistory pipelineExecHistory = historyMap.get(pipelineId);
        if (pipelineExecHistory == null){
            return null;
        }
        List<Integer> timeList = map.get(pipelineId);
        int time = 0;
        for (Integer integer1 : timeList) {
            time = time+ integer1;
        }
        pipelineExecHistory.setRunTime(time);
        pipelineExecHistory.setTimeList(timeList);
        return pipelineExecHistory;
    }

    //停止运行
    @Override
    public void killInstance(String pipelineId) {
        PipelineExecHistory pipelineExecHistory = findInstanceState(pipelineId);
        if (pipelineExecHistory == null){
            Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
            pipeline.setPipelineState(2);
            pipelineService.updatePipeline(pipeline);
            return;
        }
        Pipeline pipeline = pipelineExecHistory.getPipeline();
        String historyId = pipelineExecHistory.getHistoryId();
        commonService.updateState(historyId,map.get(pipelineId),20);

        //停止运行
        stop(pipelineId);

        pipeline.setPipelineState(2);
        pipelineService.updatePipeline(pipeline);
        commonService.halt(pipelineExecHistory,pipelineId);
        map.remove(pipelineId);
        historyMap.remove(pipelineId);

        Map<String, String> map = PipelineUntil.initMap(pipeline);
        map.put("message","停止执行");
        map.put("image", "/images/cloudy.svg");
        homeService.message("pipelineRun", map);
        homeService.log("run","run","pipelineRun", map);
    }

    //判断流水线是否正在执行
    @Override
    public int findState(String pipelineId){
        PipelineExecHistory pipelineExecHistory = historyMap.get(pipelineId);
        if (pipelineExecHistory != null){
            return 1;
        }
        return 2;
    }

    // 构建开始
    private void begin(Pipeline pipeline) {

        //更新流水线状态为执行
        pipeline.setPipelineState(1);
        pipelineService.updatePipeline(pipeline);
        String pipelineId = pipeline.getPipelineId();

        //初始化历史
        PipelineExecHistory pipelineExecHistory = commonService.initializeHistory(pipeline);
        String historyId = pipelineExecHistory.getHistoryId();
        //获取配置信息
        PipelineProcess pipelineProcess = new PipelineProcess();
        pipelineProcess.setPipelineExecHistory(pipelineExecHistory);
        historyMap.put(pipelineId,pipelineExecHistory);

        //消息
        Map<String, String> maps = PipelineUntil.initMap(pipeline);

        //获取所有配置顺序
        List<PipelineConfigOrder> allPipelineConfig = configOrderService.findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null || allPipelineConfig.size() == 0){
            commonService.updateState(historyId,map.get(pipelineId),10);
            commonService.success(pipelineExecHistory, pipeline.getPipelineId());
            historyMap.remove(pipelineId);
            pipeline.setPipelineState(2);
            maps.put("message","成功");
            maps.put("image", "/images/sun.svg");
            pipelineService.updatePipeline(pipeline);
            homeService.message("pipelineRun", maps);
            homeService.log("run","run","pipelineRun", maps);
            return;
        }

        List<Integer> integers = map.get(pipelineId);

        for (PipelineConfigOrder pipelineConfigOrder : allPipelineConfig) {
            if (integers == null || integers.size() == 0){
                integers = new ArrayList<>();
            }
            integers.add(1);
            map.put(pipelineId,integers);
            time(pipelineId);
            //初始化日志
            PipelineExecLog pipelineExecLog = commonService.initializeLog(historyId, pipelineConfigOrder);
            pipelineProcess.setPipeline(pipeline);
            pipelineProcess.setPipelineExecLog(pipelineExecLog);

            int taskType = pipelineConfigOrder.getTaskType();
            Object config = configOrderService.findOneConfig(pipelineId,taskType);

            boolean state = taskExecService.beginState(pipelineProcess,config,taskType);

            if (!state){
                commonService.updateState(historyId,map.get(pipelineId),1);
                pipeline.setPipelineState(2);
                commonService.error(pipelineExecHistory, pipeline.getPipelineId());
                pipelineService.updatePipeline(pipeline);
                //缓存
                map.remove(pipelineId);
                historyMap.remove(pipelineId);
                //消息
                maps.put("message","失败");
                maps.put("image", "/images/rain.svg");
                homeService.log("run","run","pipelineRun", maps);
                homeService.message("pipelineRun", maps);
                return;
            }
            commonService.updateState(historyId,map.get(pipelineId),10);
        }
        commonService.success(pipelineExecHistory, pipeline.getPipelineId());
        pipeline.setPipelineState(2);
        pipelineService.updatePipeline(pipeline);
        //缓存
        map.remove(pipelineId);
        historyMap.remove(pipelineId);
        //消息
        maps.put("message","成功");
        maps.put("image", "/images/sun.svg");
        homeService.log("run","run","pipelineRun", maps);
        homeService.message("pipelineRun", maps);

    }

    /**
     * 更新执行时间
     * @param pipelineId 流水线id
     */
    private void time(String pipelineId){
        List<Integer> integers = map.get(pipelineId);
        if (integers == null){
            return;
        }
        executorService.submit(() -> {
            Thread.currentThread().setName(pipelineId+"time");
            int time = integers.get(integers.size()-1) ;
            int size = integers.size();
            boolean state = true;
            try {
                while (state){
                    integers.remove(integers.size()-1);
                    time = time+1;
                    integers.add(time);
                    Thread.sleep(1000);
                    if (size != map.get(pipelineId).size()){
                        state = false;
                        time(pipelineId);
                    }
                }
            } catch (InterruptedException e) {
                throw new ApplicationException("时间停止异常。");
            }
        });
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


































