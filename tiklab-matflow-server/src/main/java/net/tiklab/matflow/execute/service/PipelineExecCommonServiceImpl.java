package net.tiklab.matflow.execute.service;

import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.until.PipelineFinal;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineScm;
import net.tiklab.matflow.setting.service.PipelineScmService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
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

    //历史
    Map<String,PipelineExecHistory> historyMap = PipelineExecServiceImpl.historyMap;

    Map<String,PipelineExecLog> logMap = PipelineExecServiceImpl.logMap;

    //运行时间
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
        //指定编码
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

    /**
     * 运行结束更新历史状态
     * @param pipelineId 流水线id
     * @param status 状态 success error halt
     */
    public void runEnd(String pipelineId , int status){
        PipelineExecHistory pipelineExecHistory = historyMap.get(pipelineId);
        String date = PipelineUntil.date(4);
        String runLog = pipelineExecHistory.getRunLog();
        if (status == PipelineFinal.PIPELINE_RUN_SUCCESS){
            pipelineExecHistory.setRunLog(runLog+"\n"+ date + "RUN RESULT : SUCCESS");
            pipelineExecHistory.setRunStatus(10);
        }
        if (status == PipelineFinal.PIPELINE_RUN_ERROR){
            pipelineExecHistory.setRunLog(runLog+"\n"+ date + "RUN RESULT : FAIL");
            pipelineExecHistory.setRunStatus(1);
        }
        if (status == PipelineFinal.PIPELINE_RUN_HALT){
            //更新信息
            pipelineExecHistory.setRunLog(runLog+"\n"+ date + "RUN RESULT : HALT");
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
     * @param pipelineId 执行信息
     */
    public void updateState(String pipelineId,String logId,int state){

        PipelineExecLog pipelineExecLog = logMap.get(logId);
        if (pipelineExecLog != null){

            pipelineExecLog.setRunState(state);
            pipelineExecLog.setLogId(logId);

            Integer integer = runTime.get(logId);
            if (integer !=  null && integer == 0){
                integer = 1;
            }
            if (integer ==  null){
                integer = 0;
            }
            pipelineExecLog.setRunTime(integer);
            System.out.println("日志id："+ logId + "   日志状态："+state  );
            logService.updateLog(pipelineExecLog);
        }

        PipelineExecHistory execHistory = historyMap.get(pipelineId);
        String historyId = execHistory.getHistoryId();
        Integer integer = runTime.get(historyId);
        if (integer ==  null){
            integer = 0;
        }
        execHistory.setRunTime(integer);
        historyService.updateHistory(execHistory);
        logMap.remove(logId);
    }

    /**
     * 初始化历史
     * * @return 历史
     */
    @Override
    public PipelineExecHistory initializeHistory(String pipelineId ,int startWAy) {
        String loginId = LoginContext.getLoginId();
        String date = PipelineUntil.date(1);
        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory(date,startWAy,loginId,pipelineId);
        String historyId = historyService.createHistory(pipelineExecHistory);
        //初始化基本信息
        pipelineExecHistory.setSort(1);
        pipelineExecHistory.setHistoryId(historyId);
        //构建次数
        List<PipelineExecHistory> allHistory = historyService.findAllHistory(pipelineId);
        allHistory.sort(Comparator.comparing(PipelineExecHistory::getCreateTime));
        pipelineExecHistory.setFindNumber(1);
        if (allHistory.size() >= 1){
            pipelineExecHistory.setFindNumber(allHistory.get(allHistory.size()-1).getFindNumber()+1);
        }
        historyService.updateHistory(pipelineExecHistory);
        return pipelineExecHistory;
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
     * 更新执行时历史日志
     * @param pipelineId 流水线id
     * @param log 日志
     */
    @Override
    public void updateExecHistory(String pipelineId ,String log){
        PipelineExecHistory history = historyMap.get(pipelineId);
        history.setRunLog(history.getRunLog()+"\n"+log);
        historyMap.put(pipelineId,history);
    }

    /**
     * 执行过程中的历史
     */
    @Override
    public void execHistory(PipelineProcess pipelineProcess,String log){
        if(!PipelineUntil.isNoNull(log)){
            return;
        }
        String pipelineId = pipelineProcess.getPipeline().getId();
        String logId = pipelineProcess.getLogId();

        PipelineExecHistory history = historyMap.get(pipelineId);
        history.setRunTime(runTime.get(history.getHistoryId()));
        PipelineExecLog pipelineExecLog = logMap.get(logId);
        Integer integer = runTime.get(logId);
        if (integer == null){
            integer = 0;
        }
        pipelineExecLog.setRunTime(integer);
        history.setRunLog(history.getRunLog()+"\n"+log);
        historyMap.put(pipelineId,history);

        String runLog = pipelineExecLog.getRunLog();
        if (PipelineUntil.isNoNull(runLog)){
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+log);
        }else {
            pipelineExecLog.setRunLog(log);
        }
        logMap.put(log,pipelineExecLog);
    }



}






























