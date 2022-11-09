package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineConfigOrder;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineScm;
import net.tiklab.matflow.setting.service.PipelineScmService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
import net.tiklab.utils.context.LoginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

    Map<String,PipelineExecHistory> historyMap = PipelineExecServiceImpl.historyMap;

    /**
     * 执行日志
     * @param inputStream 执行信息
     * @param pipelineProcess 执行信息
     * @return map 执行状态
     */
    @Override
    public int log(InputStream inputStream,InputStream errInputStream, PipelineProcess pipelineProcess,String encode) throws IOException {
        int state = 1;

        InputStreamReader inputStreamReader = PipelineUntil.encode(inputStream, encode);
        
        String s;
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        String logRunLog = null;

        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logRunLog = logRunLog + s +"\n";
            if (logRunLog.contains("BUILD FAILURE")) {
                state = 0 ;
            }
            execHistory(pipelineProcess,s);
        }
        if (logRunLog == null){
            inputStreamReader = PipelineUntil.encode(errInputStream, encode);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((s = bufferedReader.readLine()) != null) {
                logRunLog = logRunLog + s +"\n";
                if (logRunLog.contains("BUILD FAILURE")
                        || logRunLog.contains("ERROR")
                        || logRunLog.contains("npm ERR!")
                        || logRunLog.contains("Access denied")) {
                    state = 0 ;
                }
                execHistory(pipelineProcess,s);
            }
        }
        inputStreamReader.close();
        bufferedReader.close();
        return state;
    }

    /**
     * 输出错误信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     */
    @Override
    public void error(PipelineExecHistory pipelineExecHistory, String pipelineId){
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\nRUN RESULT : FAIL");
        pipelineExecHistory.setRunStatus(1);
        pipelineExecHistory.setFindState(1);
        historyService.updateHistory(pipelineExecHistory);
        historyMap.put(pipelineId,pipelineExecHistory);
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
        historyMap.put(pipelineId,pipelineExecHistory);
    }

    /**
     * 输出停止信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     */
    @Override
    public void halt(PipelineExecHistory pipelineExecHistory, String pipelineId) {
        //更新信息
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + "RUN RESULT : HALT");
        pipelineExecHistory.setRunStatus(20);
        pipelineExecHistory.setFindState(1);
        //更新状态
        historyService.updateHistory(pipelineExecHistory);
        historyMap.put(pipelineId,pipelineExecHistory);
    }

    /**
     * 更新状态
     * @param historyId 执行信息
     * @param time 异常
     */
    public void updateState(String historyId,List<Integer> time,int state){
        PipelineExecLog pipelineExecLog = historyService.findLastRunLog(historyId);
        PipelineExecHistory pipelineExecHistory = historyService.findOneHistory(historyId);
        pipelineExecLog.setRunTime(time.get(time.size()-1));
        int times = 0;
        for (Integer integer : time) {
            times = times+ integer;
        }
        pipelineExecHistory.setRunTime(times+1);
        historyService.updateHistory(pipelineExecHistory);
        pipelineExecLog.setRunState(state);
        logService.updateLog(pipelineExecLog);
    }

    /**
     * 初始化历史
     * * @return 历史
     */
    @Override
    public PipelineExecHistory initializeHistory(Pipeline pipeline) {
        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory();
        String historyId = historyService.createHistory(pipelineExecHistory);
        //初始化基本信息
        // pipelineExecHistory.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        pipelineExecHistory.setCreateTime(PipelineUntil.date());
        pipelineExecHistory.setRunWay(1);
        pipelineExecHistory.setSort(1);
        pipelineExecHistory.setPipeline(pipeline);
        pipelineExecHistory.setHistoryId(historyId);
        pipelineExecHistory.setRunLog("流水线"+pipeline.getPipelineName()+"开始执行。");
        User user = new User();
        user.setId(LoginContext.getLoginId());
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

        PipelineExecHistory history = historyMap.get(pipelineId);

        history.setRunLog(history.getRunLog()+"\n"+log);
        historyMap.put(pipelineId,history);

        //更新单个配置日志
        pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+log);
        logService.updateLog(pipelineExecLog);
    }



}
