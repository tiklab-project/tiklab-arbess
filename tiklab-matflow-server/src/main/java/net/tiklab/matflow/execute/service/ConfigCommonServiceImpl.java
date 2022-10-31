package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.*;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineScm;
import net.tiklab.matflow.setting.service.PipelineScmService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

/**
 * 执行过程公共方法
 */

@Service
@Exporter
public class ConfigCommonServiceImpl implements ConfigCommonService {

    @Autowired
    PipelineScmService pipelineScmService;

    @Autowired
    PipelineExecHistoryService historyService;

    @Autowired
    PipelineExecLogService logService;

    private static final Logger logger = LoggerFactory.getLogger(ConfigCommonServiceImpl.class);


    List<PipelineExecHistory> list = PipelineExecServiceImpl.pipelineExecHistoryList;
    

    /**
     * 执行日志
     * @param inputStream 执行信息
     * @param pipelineProcess 执行信息
     * @return map 执行状态
     */
    @Override
    public int log(InputStream inputStream,InputStream errInputStream, PipelineProcess pipelineProcess) throws IOException {

        int taskType = pipelineProcess.getPipelineExecLog().getTaskType();
        String encode = "GBK";
        if (taskType >= 30 && taskType<40){
            encode = "UTF-8";
        }

        InputStreamReader inputStreamReader = PipelineUntil.encode(inputStream, encode);
        
        String s;
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        String logRunLog = null;

        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logRunLog = logRunLog + s +"\n";
            execHistory(pipelineProcess,s);
//            if (logRunLog.contains("BUILD FAILURE")||logRunLog.contains("ERROR")) {
//                inputStreamReader.close();
//                bufferedReader.close();
//                return 0;
//            }
        }
        if (logRunLog == null){
            inputStreamReader = PipelineUntil.encode(errInputStream, encode);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((s = bufferedReader.readLine()) != null) {
                logRunLog = logRunLog + s +"\n";
                execHistory(pipelineProcess,s);
            }
        }
        inputStreamReader.close();
        bufferedReader.close();

        return 1;
    }

    /**
     * 输出错误信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     */
    @Override
    public void error(PipelineExecHistory pipelineExecHistory, String pipelineId){
        pipelineExecHistory.setStatus(pipelineExecHistory.getStatus()+2);
        list.add(pipelineExecHistory);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\nRUN RESULT : FAIL");
        pipelineExecHistory.setRunStatus(1);
        pipelineExecHistory.setFindState(1);
        historyService.updateHistory(pipelineExecHistory);
        list.add(pipelineExecHistory);
        // 清空缓存
        list.removeIf(execHistory -> execHistory.getPipeline().getPipelineId().equals(pipelineId));
    }

    /**
     * 输出成功信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     */
    @Override
    public void success(PipelineExecHistory pipelineExecHistory, String pipelineId) {
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + "RUN RESULT : SUCCESS");
        pipelineExecHistory.setRunStatus(30);
        pipelineExecHistory.setFindState(1);
        historyService.updateHistory(pipelineExecHistory);
        //清空缓存
        list.removeIf(execHistory -> execHistory.getPipeline().getPipelineId().equals(pipelineId));
    }

    /**
     * 输出停止信息
     * @param pipelineProcess 历史
     * @param pipelineId 流水线id
     */
    @Override
    public void halt(PipelineProcess pipelineProcess, String pipelineId) {

        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        PipelineExecLog pipelineExecLog = pipelineProcess.getPipelineExecLog();
        PipelineExecLog runLog = historyService.getRunLog(pipelineExecHistory.getHistoryId());
        pipelineExecLog.setLogId(runLog.getLogId());

        //更新信息
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + "RUN RESULT : HALT");
        pipelineExecHistory.setRunStatus(20);
        pipelineExecHistory.setFindState(1);
        //更新状态
        logService.updateLog(pipelineExecLog);
        historyService.updateHistory(pipelineExecHistory);
        //清空缓存
        list.removeIf(execHistory -> execHistory.getPipeline().getPipelineId().equals(pipelineId));
    }

    /**
     * 初始化历史
     * * @return 历史
     */
    @Override
    public PipelineExecHistory initializeHistory(Pipeline pipeline, String userId) {

        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory();
        String historyId = historyService.createHistory(pipelineExecHistory);

        //初始化基本信息
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        pipelineExecHistory.setCreateTime(time);
        pipelineExecHistory.setRunWay(1);
        pipelineExecHistory.setSort(1);
        pipelineExecHistory.setStatus(0);
        pipelineExecHistory.setPipeline(pipeline);
        pipelineExecHistory.setHistoryId(historyId);
        pipelineExecHistory.setRunLog("");
        User user = new User();
        user.setId(userId);
        pipelineExecHistory.setUser(user);
        pipelineExecHistory.setHistoryId(historyId);
        //构建次数
        List<PipelineExecHistory> allHistory = historyService.findAllHistory(pipeline.getPipelineId());
        allHistory.sort(Comparator.comparing(PipelineExecHistory::getCreateTime));
        pipelineExecHistory.setFindNumber(1);
        if (allHistory.size() >= 1){
            pipelineExecHistory.setFindNumber(allHistory.get(allHistory.size()-1).getFindNumber()+1);
        }
        historyService.updateHistory(pipelineExecHistory);
        return pipelineExecHistory;
    }
    
    /**
     * 初始化日志
     * @param historyId 历史
     * @return 日志
     */
    @Override
    public PipelineExecLog initializeLog(String historyId,PipelineConfigOrder configOrder){
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setHistoryId(historyId);
        pipelineExecLog.setTaskSort(configOrder.getTaskSort());
        pipelineExecLog.setTaskType(configOrder.getTaskType());
        pipelineExecLog.setRunLog("");
        String logId = logService.createLog(pipelineExecLog);
        pipelineExecLog.setLogId(logId);
        return pipelineExecLog;
    }

    /**
     * 获取最后一次的执行日志
     * @param historyId 历史id
     * @return 日志
     */
    @Override
    public PipelineExecLog getExecPipelineLog(String historyId){
        List<PipelineExecLog> allLog = logService.findAllLog(historyId);
        allLog.sort(Comparator.comparing(PipelineExecLog::getTaskSort));
        return allLog.get(allLog.size()-1);
    }

    /**
     * 获取环境配置信息
     * @param type 类型
     * @return 配置信息
     */
    @Override
    public String getScm(int type){
        PipelineScm pipelineScm = pipelineScmService.findOnePipelineScm(type);
        if (pipelineScm == null){
            return null;
        }
        return pipelineScm.getScmAddress();
    }

    /**
     * 执行过程中的历史
     */
    @Override
    public void execHistory(PipelineProcess pipelineProcess,String log){
        String pipelineId = pipelineProcess.getPipeline().getPipelineId();
        PipelineExecLog pipelineExecLog = pipelineProcess.getPipelineExecLog();
        PipelineExecHistory execHistory = null;
        //查询流水线当前历史
        for (PipelineExecHistory pipelineExecHistory : list) {
            if (!pipelineExecHistory.getPipeline().getPipelineId().equals(pipelineId)){
                continue;
            }
            execHistory = pipelineExecHistory;
        }
        if (execHistory == null) return ;

        //更新历史日志
        list.remove(execHistory);
        if (!PipelineUntil.isNoNull(execHistory.getRunLog())){
            execHistory.setRunLog(log);
        }else {
            execHistory.setRunLog(execHistory.getRunLog()+"\n"+log);
        }
        list.add(execHistory);

        //更新单个配置日志
        pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+log);
        logService.updateLog(pipelineExecLog);


    }

    /**
     * 更新状态
     * @param pipelineProcess 执行信息
     * @param b 异常
     */
    public void updateState(PipelineProcess pipelineProcess, boolean b){
        long beginTime = pipelineProcess.getBeginTime();
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        PipelineExecLog pipelineExecLog = pipelineProcess.getPipelineExecLog();
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        pipelineExecLog.setRunTime(time+1);
        pipelineExecHistory.setRunTime(pipelineExecHistory.getRunTime()+time);
        pipelineExecLog.setRunState(10);
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        historyService.updateHistory(pipelineExecHistory);
        if (!b){
            pipelineExecLog.setRunState(1);
        }
        logService.updateLog(pipelineExecLog);
    }

}
