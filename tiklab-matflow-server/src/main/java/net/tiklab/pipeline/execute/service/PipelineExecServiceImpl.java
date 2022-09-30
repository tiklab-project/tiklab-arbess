package net.tiklab.pipeline.execute.service;


import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.definition.model.PipelineConfig;
import net.tiklab.pipeline.definition.service.PipelineConfigService;
import net.tiklab.pipeline.definition.service.PipelineService;
import net.tiklab.pipeline.execute.model.PipelineExecHistory;
import net.tiklab.pipeline.execute.model.PipelineExecLog;
import net.tiklab.pipeline.execute.service.execAchieveService.ConfigCommonService;
import net.tiklab.pipeline.orther.model.PipelineProcess;
import net.tiklab.pipeline.execute.service.execAchieveService.PipelineTaskExecService;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    PipelineTaskExecService pipelineTaskExecService;

    @Autowired
    ConfigCommonService configCommonService;

    @Autowired
    PipelineConfigService pipelineConfigService;

    //存放过程状态
    public static final List<PipelineExecHistory> pipelineExecHistoryList = new ArrayList<>();

    //开始执行时间
    long beginTime = 0;

    //创建线程池
    ExecutorService executorService = Executors.newCachedThreadPool();

    //启动
    @Override
    public int  start(String pipelineId,String userId){
        // 判断同一任务是否在运行
        Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
        if (pipeline.getPipelineState() == 1){
            return 100;
        }

        try {
            executorService.submit(() -> {
                beginTime = new Timestamp(System.currentTimeMillis()).getTime();
                Thread.currentThread().setName(pipelineId);
                begin(pipeline,userId);
            });
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
    public PipelineExecHistory findInstanceState(String pipelineId){
        for (PipelineExecHistory pipelineExecHistory : pipelineExecHistoryList) {
            Pipeline pipeline = pipelineExecHistory.getPipeline();
            if (pipeline == null || !pipeline.getPipelineId().equals(pipelineId)){
                continue;
            }
            time[pipelineExecHistory.getSort()-1] = time[pipelineExecHistory.getSort()-1] + 1;
            pipelineExecHistory.setOneTime(configCommonService.formatDateTime(time[0]));
            pipelineExecHistory.setTwoTime(configCommonService.formatDateTime(time[1]));
            pipelineExecHistory.setThreeTime(configCommonService.formatDateTime(time[2]));
            pipelineExecHistory.setFourTime(configCommonService.formatDateTime(time[3]));
            pipelineExecHistory.setAllTime(configCommonService.formatDateTime(time[0]+time[1]+time[2]+time[3]));
            return pipelineExecHistory;
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
            Pipeline pipeline = pipelineService.findOnePipeline(pipelineId);
            pipeline.setPipelineState(0);
            pipelineService.updatePipeline(pipeline);
            return;
        }

        PipelineExecLog pipelineExecLog = new PipelineExecLog();

        pipelineProcess.setPipelineExecLog(pipelineExecLog);
        pipelineProcess.setPipelineExecHistory(pipelineExecHistory);
        Pipeline pipeline = pipelineExecHistory.getPipeline();

        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        pipelineExecLog.setRunTime(time- pipelineExecHistory.getRunTime());
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"流水线停止运行......");
        pipelineExecHistoryList.add(pipelineExecHistory);

        configCommonService.halt(pipelineProcess,pipelineId, pipelineExecHistoryList);

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
            return;
        }
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
    private void begin(Pipeline pipeline, String userId) {

        //更新流水线状态
        pipeline.setPipelineState(1);
        pipelineService.updatePipeline(pipeline);
        String pipelineId = pipeline.getPipelineId();

        //初始化历史
        PipelineExecHistory pipelineExecHistory = configCommonService.initializeHistory(pipeline,userId);

        //获取配置信息
        PipelineProcess pipelineProcess = new PipelineProcess();
        pipelineProcess.setPipelineExecHistory(pipelineExecHistory);
        pipelineExecHistoryList.add(pipelineExecHistory);

        for (int i = 1; i <= 4; i++) {
            Map<Integer, Integer> map = pipelineConfigService.findConfig(pipeline.getPipelineId());
            PipelineConfig pipelineConfig = pipelineConfigService.AllConfig(pipelineId);
            String state = pipelineTaskExecService.beginState(pipelineProcess,pipelineConfig,map.get(i));

            if (state != null){
                pipeline.setPipelineState(0);
                configCommonService.error(pipelineExecHistory, state, pipeline.getPipelineId(), pipelineExecHistoryList);
                pipelineService.updatePipeline(pipeline);
                time[0]=1;time[1]=0;time[2]=0;time[3]=0;
                return;
            }
            pipelineExecHistory.setSort(pipelineExecHistory.getSort() +1);
            pipelineExecHistory.setStatus(pipelineExecHistory.getStatus() +1);
        }



        configCommonService.success(pipelineExecHistory, pipeline.getPipelineId(), pipelineExecHistoryList);
        pipeline.setPipelineState(0);
        pipelineService.updatePipeline(pipeline);
        time[0]=1;time[1]=0;time[2]=0;time[3]=0;
    }
}
