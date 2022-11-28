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
    PipelineTaskExecService taskExecService;

    @Autowired
    ConfigCommonService commonService;

    @Autowired
    PipelineConfigOrderService configOrderService;

    @Autowired
    PipelineHomeService homeService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineExecServiceImpl.class);

    //运行时间
    Map<String,List<Integer>> map = new HashMap<>();

    //流水线运行历史
    public static Map<String,PipelineExecHistory> historyMap = new HashMap<>();

    //创建线程池
    ExecutorService executorService = Executors.newCachedThreadPool();

    //启动
    @Override
    public boolean start(String pipelineId){
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
            begin(pipeline);
        });

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new ApplicationException(e);
        }

        return true;
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
            updatePipelineStatus(pipelineId, false);
            stop(pipelineId);
            return;
        }
        Pipeline pipeline = pipelineExecHistory.getPipeline();
        String historyId = pipelineExecHistory.getHistoryId();
        commonService.updateState(historyId,map.get(pipelineId),20);

        //停止运行
        stop(pipelineId);
        updatePipelineStatus(pipelineId, false);

        Map<String, String> maps = homeService.initMap(pipeline);
        updateStatus("halt",pipelineId,maps,pipelineExecHistory);
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

        String pipelineId = pipeline.getPipelineId();

        //初始化历史
        PipelineExecHistory pipelineExecHistory = commonService.initializeHistory(pipeline);
        String historyId = pipelineExecHistory.getHistoryId();

        //获取配置信息
        PipelineProcess pipelineProcess = new PipelineProcess();
        pipelineProcess.setPipelineExecHistory(pipelineExecHistory);
        historyMap.put(pipelineId,pipelineExecHistory);

        //消息
        Map<String, String> maps = homeService.initMap(pipeline);

        //获取所有配置顺序
        List<PipelineConfigOrder> allPipelineConfig = configOrderService.findAllPipelineConfig(pipelineId);
        if (allPipelineConfig == null || allPipelineConfig.size() == 0){
            commonService.updateState(historyId,map.get(pipelineId),10);
            updatePipelineStatus(pipeline.getPipelineId(), false);
            updateStatus("success",pipelineId,maps,pipelineExecHistory);
            return;
        }

        List<Integer> integers = map.get(pipelineId);

        for (PipelineConfigOrder pipelineConfigOrder : allPipelineConfig) {
            if (integers == null || integers.size() == 0){
                integers = new ArrayList<>();
                integers.add(-1);
            }else {
                integers.add(0);
            }
            map.put(pipelineId,integers);
            time(pipelineId);
            //初始化日志
            PipelineExecLog pipelineExecLog = commonService.initializeLog(historyId, pipelineConfigOrder);
            pipelineProcess.setPipeline(pipeline);
            pipelineProcess.setPipelineExecLog(pipelineExecLog);

            int taskType = pipelineConfigOrder.getTaskType();
            Object config = configOrderService.findOneConfig(pipelineId,taskType);

            //执行不同的实现
            boolean state = taskExecService.beginState(pipelineProcess,config,taskType);

            //当前阶段的执行状态
            if (!state){
                commonService.updateState(historyId,map.get(pipelineId),1);
                updatePipelineStatus(pipeline.getPipelineId(), false);
                updateStatus("error",pipelineId,maps,pipelineExecHistory);
                return;
            }
            //更新状态
            commonService.updateState(historyId,map.get(pipelineId),10);
        }
        //执行结束
        updatePipelineStatus(pipeline.getPipelineId(), false);
        updateStatus("success",pipelineId,maps,pipelineExecHistory);
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
            maps.put("image", "/images/success.svg");
        }
        if (status.equals("error")){
            maps.put("message","失败");
            maps.put("image", "/images/error.svg");
        }
        if (status.equals("halt")){
            maps.put("message","停止执行");
            maps.put("image", "/images/halt.svg");
        }

        //清除流水线缓存
        map.remove(pipelineId);
        historyMap.remove(pipelineId);

        //微信消息
        // maps.put("status",status);
        // homeService.wechatMarkdownMessage(maps);

        //创建日志
        homeService.log(LOG_PIPELINE_RUN,LOG_MD_PIPELINE_RUN,LOG_TEM_PIPELINE_RUN, maps);
        // homeService.message(MES_TEM_PIPELINE_RUN,MES_PIPELINE_RUN, maps);
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


































