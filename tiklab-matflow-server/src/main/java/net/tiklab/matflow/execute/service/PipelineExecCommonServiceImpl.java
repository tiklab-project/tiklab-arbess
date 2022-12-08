package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.definition.model.Pipeline;
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
public class PipelineExecCommonServiceImpl implements PipelineExecCommonService {

    @Autowired
    PipelineScmService pipelineScmService;

    @Autowired
    PipelineExecHistoryService historyService;

    @Autowired
    PipelineExecLogService logService;


    private static final Logger logger = LoggerFactory.getLogger(PipelineExecCommonServiceImpl.class);

    Map<String,PipelineExecHistory> historyMap = PipelineExecServiceImpl.historyMap;

    Map<String, List<String>> logMap = PipelineExecServiceImpl.logMap;

    Map<String, Integer> runTime = PipelineExecServiceImpl.runTime;

    /**
     * 执行日志
     * @param pipelineProcess 执行信息
     * @return map 执行状态
     */
    @Override
    public int log(PipelineProcess pipelineProcess) throws IOException {
        int state = 1;
        String enCode = pipelineProcess.getEnCode();
        if (!PipelineUntil.isNoNull(enCode)){
            int systemType = PipelineUntil.findSystemType();
            if (systemType == 1){
                enCode = "GBK";
            }else {
                enCode = "UTF-8";
            }
        }

        String[] error = pipelineProcess.getError();

        InputStream inputStream = pipelineProcess.getInputStream();
        InputStream errInputStream = pipelineProcess.getErrInputStream();

        InputStreamReader inputStreamReader ;
        BufferedReader  bufferedReader ;
        if (inputStream == null){
            inputStreamReader = PipelineUntil.encode(errInputStream, enCode);
        }else {
            inputStreamReader = PipelineUntil.encode(inputStream, enCode);
        }
        
        String s;
        bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder logRunLog = new StringBuilder();

        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logRunLog.append(s).append("\n");
            if (validStatus(s,error)){state = 0 ;}
            execHistory(pipelineProcess, PipelineUntil.date(4)+s);
        }

        if (!PipelineUntil.isNoNull(logRunLog.toString())){
            inputStreamReader = PipelineUntil.encode(errInputStream, enCode);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((s = bufferedReader.readLine()) != null) {
                logRunLog.append(s).append("\n");
                if (validStatus(s,error)){state = 0 ;}
                execHistory(pipelineProcess, PipelineUntil.date(4)+s);
            }
        }
        inputStreamReader.close();
        bufferedReader.close();
        return state;
    }

    /**
     * 效验日志状态
     * @param s 日志
     * @param error 错误状态
     * @return true 正确 false：错误
     */
    private boolean validStatus(String s,String[] error){
        if (error.length == 0){
            return false;
        }
        for (String s1 : error) {
            if (!s.contains(s1)){
                continue;
            }
            return true;
        }
        return false;
    }


    public List<PipelineExecLog> findAllStagesLog(String historyId,String stagesId){
        return logService.findAllStagesLog(historyId,stagesId);
    }


    /**
     * 运行结束更新历史状态
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     * @param status 状态 success error halt
     */
    public void runEnd(PipelineExecHistory pipelineExecHistory, String pipelineId ,String status){
        if (status.equals("success")){
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+ PipelineUntil.date(4) + "RUN RESULT : SUCCESS");
            pipelineExecHistory.setRunStatus(10);
        }
        if (status.equals("error")){
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+ PipelineUntil.date(4)+ "RUN RESULT : FAIL");
            pipelineExecHistory.setRunStatus(1);
        }
        if (status.equals("halt")){
            //更新信息
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+ PipelineUntil.date(4)+ "RUN RESULT : HALT");
            pipelineExecHistory.setRunStatus(20);
        }
        PipelineExecHistory oneHistory = historyService.findOneHistory(pipelineExecHistory.getHistoryId());
        //更新状态
        pipelineExecHistory.setFindState(1);
        pipelineExecHistory.setRunTime(oneHistory.getRunTime()+1);
        historyService.updateHistory(pipelineExecHistory);
        historyMap.put(pipelineId,pipelineExecHistory);
    }


    /**
     * 更新日志执行状态
     * @param historyId 执行信息
     * @param time 时间
     */
    public void updateState(String historyId,List<Integer> time,int state){
        PipelineExecHistory pipelineExecHistory = historyService.findOneHistory(historyId);

        String logId = logMap.get(historyId).get(logMap.get(historyId).size()-1);
        PipelineExecLog pipelineExecLog = logService.findOneLog(logId);

        int times = 0;
        List<String> list = logMap.get(historyId);
        if (list != null){
            for (String s : list) {
                Integer integer = runTime.get(s);
                times = times +integer;
            }
        }
        Integer integer = runTime.get(logId);
        if (integer == 0){
            integer = integer+1;
        }
        pipelineExecLog.setRunTime(integer);
        pipelineExecLog.setRunState(state);
        logService.updateLog(pipelineExecLog);
        if (times == 0){
            return;
        }
        pipelineExecHistory.setRunTime(times);
        historyService.updateHistory(pipelineExecHistory);
    }

    /**
     * 初始化历史
     * * @return 历史
     */
    @Override
    public PipelineExecHistory initializeHistory(Pipeline pipeline,int startWAy) {
        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory();
        String historyId = historyService.createHistory(pipelineExecHistory);
        //初始化基本信息
        pipelineExecHistory.setCreateTime(PipelineUntil.date(1));
        pipelineExecHistory.setRunWay(startWAy);
        pipelineExecHistory.setSort(1);
        pipelineExecHistory.setPipeline(pipeline);
        pipelineExecHistory.setHistoryId(historyId);
        pipelineExecHistory.setRunLog(PipelineUntil.date(4) +"流水线"+pipeline.getPipelineName()+"开始执行。");
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
    public PipelineExecLog initializeLog(String historyId,int sort,int type){
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setHistoryId(historyId);
        pipelineExecLog.setTaskSort(sort);
        pipelineExecLog.setType(type);
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
