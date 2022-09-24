package net.tiklab.matflow.execute.service;


import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.matflow.definition.model.MatFlowConfigure;
import net.tiklab.matflow.definition.service.MatFlowService;
import net.tiklab.matflow.execute.model.MatFlowExecHistory;
import net.tiklab.matflow.execute.model.MatFlowExecLog;
import net.tiklab.matflow.orther.model.MatFlowProcess;
import net.tiklab.matflow.orther.service.MatFlowActivityService;
import net.tiklab.matflow.execute.service.execAchieveService.MatFlowTaskExecService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.matflow.execute.service.execAchieveImpl.CommonAchieveServiceImpl;
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
    MatFlowService matFlowService;

    @Autowired
    MatFlowActivityService matFlowActivityService;

    @Autowired
    MatFlowTaskExecService matFlowTaskExecService;

    @Autowired
    CommonAchieveServiceImpl commonAchieveServiceImpl;

    @Autowired
    MatFlowExecHistoryService matFlowExecHistoryService;

    //存放过程状态
    public static final List<MatFlowExecHistory> matFlowExecHistoryList = new ArrayList<>();

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
            executorService.submit(() -> {
                beginTime = new Timestamp(System.currentTimeMillis()).getTime();
                Thread.currentThread().setName(matFlowId);
                begin(matFlow,userId);
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
    public MatFlowExecHistory findInstanceState(String matFlowId){
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
        matFlowActivityService.createActive(userId, matFlow,"停止流水线/的运行");

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
        matFlowActivityService.createActive(matFlow.getUser().getId(), matFlow,"执行流水线");

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
                String state = matFlowTaskExecService.beginState(matFlowProcess, matFlowConfigure.getTaskType());
                if (state != null){
                    matFlow.setMatflowState(0);
                    commonAchieveServiceImpl.error(matFlowExecHistory, state, matFlow.getMatflowId(), matFlowExecHistoryList);
                    matFlowService.updateMatFlow(matFlow);
                    time[0]=1;time[1]=0;time[2]=0;time[3]=0;
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
}
