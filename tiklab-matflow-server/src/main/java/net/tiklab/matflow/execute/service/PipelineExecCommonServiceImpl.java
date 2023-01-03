package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.PipelineCondition;
import net.tiklab.matflow.definition.model.PipelineVariable;
import net.tiklab.matflow.definition.service.PipelineConditionServer;
import net.tiklab.matflow.definition.service.PipelineVariableServer;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.until.PipelineFinal;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineScm;
import net.tiklab.matflow.setting.service.PipelineScmService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.utils.context.LoginContext;
import org.apache.commons.lang.text.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
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

    @Autowired
    PipelineVariableServer variableServer;

    @Autowired
    PipelineConditionServer conditionServer;

    //历史
    Map<String,String> historyMap = PipelineExecServiceImpl.historyMap;

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
                enCode = PipelineFinal.GBK;
            }else {
                enCode = PipelineFinal.UTF_8;
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
        // StringBuilder logRunLog = new StringBuilder();

        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            // logRunLog.append(s).append("\n");
            if (validStatus(s,error)){
                state = 0 ;
            }
            updateExecLog(pipelineProcess, PipelineUntil.date(4)+s);
        }

        inputStreamReader = PipelineUntil.encode(errInputStream, enCode);
        bufferedReader = new BufferedReader(inputStreamReader);
        while ((s = bufferedReader.readLine()) != null) {
            // logRunLog.append(s).append("\n");
            if (validStatus(s,error)){state = 0 ;}
            updateExecLog(pipelineProcess, PipelineUntil.date(4)+s);
        }

        inputStreamReader.close();
        bufferedReader.close();
        return state;
    }

    /**
     * 获取运行状态
     * @param pipelineProcess 运行信息
     * @param process 运行实例
     * @throws IOException 执行异常
     * @throws ApplicationException 执行错误
     */
    @Override
    public void execState(PipelineProcess pipelineProcess, Process process,String taskName) throws IOException , ApplicationException {

        pipelineProcess.setInputStream(process.getInputStream());
        pipelineProcess.setErrInputStream(process.getErrorStream());

        //构建失败
        int state = log(pipelineProcess);
        process.destroy();

        int i;
        try {
            i = process.waitFor();
        } catch (InterruptedException e) {
            throw new ApplicationException("任务"+taskName+"执行失败");
        }

        if (state == 0 || i != 0){
            throw new ApplicationException("任务"+taskName+"执行失败");
        }
    }

    /**
     * 效验日志状态
     * @param s 日志
     * @param error 错误状态
     * @return true 正确 false：错误
     */
    private boolean validStatus(String s,String[] error){
        if (error == null || error.length == 0){
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
        String historyId = historyMap.get(pipelineId);

        PipelineExecHistory execHistory = historyService.findOneHistory(historyId);

        Integer integer = runTime.get(historyId);
        if (integer ==  null){
            integer = 0;
        }

        execHistory.setRunTime(integer+1);
        //更新状态
        execHistory.setFindState(1);
        execHistory.setRunStatus(status);
        historyService.updateHistory(execHistory);

    }

    /**
     * 更新日志执行状态
     * @param pipelineId 执行信息
     */
    public void updateState(String pipelineId,String logId,int state){
        PipelineExecLog pipelineExecLog = logMap.get(logId);
        if (pipelineExecLog != null){
            //状态,id
            pipelineExecLog.setRunState(state);
            pipelineExecLog.setLogId(logId);

            //运行时间
            Integer integer = runTime.get(logId);
            if (integer !=  null && integer == 0){
                integer = 1;
            }
            if (integer ==  null){
                integer = 0;
            }
            pipelineExecLog.setRunTime(integer);

            String execLog = pipelineExecLog.getRunLog();
            if (PipelineUntil.isNoNull(execLog)){
                String runLog = pipelineExecLog.getLogAddress();
                PipelineUntil.logWriteFile(execLog,runLog);
            }
            logService.updateLog(pipelineExecLog);
        }
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
        PipelineExecHistory latelyHistory = historyService.findLatelyHistory(pipelineId);
        pipelineExecHistory.setFindNumber(1);
        if (latelyHistory != null){
            int findNumber = latelyHistory.getFindNumber();
            pipelineExecHistory.setFindNumber(findNumber+1);
        }

        // List<PipelineExecHistory> allHistory = historyService.findAllHistory(pipelineId);
        //
        // pipelineExecHistory.setFindNumber(1);
        // if (allHistory.size() >= 1){
        //     pipelineExecHistory.setFindNumber(allHistory.size());
        // }

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
     * 执行过程中的历史
     */
    @Override
    public void updateExecLog(PipelineProcess pipelineProcess,String log){
        if(!PipelineUntil.isNoNull(log)){
            return;
        }

        String logId = pipelineProcess.getLogId();
        PipelineExecLog pipelineExecLog = logMap.get(logId);
        Integer integer = runTime.get(logId);
        if (integer == null){
            integer = 0;
        }
        pipelineExecLog.setRunTime(integer);

        String execLog = pipelineExecLog.getRunLog();

        if (!PipelineUntil.isNoNull(execLog)){
            pipelineExecLog.setRunLog(log);
        }else {
            pipelineExecLog.setRunLog(execLog +"\n"+ log);
        }

        //长度过长写入文件中
        if (pipelineExecLog.getRunLog().length() > 10000){
            String runLog = pipelineExecLog.getLogAddress();
            PipelineUntil.logWriteFile(execLog,runLog);
            pipelineExecLog.setRunLog(null);
        }

        logMap.put(logId,pipelineExecLog);
    }

    /**
     * 替换命令中的环境变量
     * @param pipelineId 流水线id
     * @param configId 任务id
     * @param order 命令
     * @return 替换后的环境变量
     */
    @Override
    public String variableKey(String pipelineId,String configId,String order){
        Map<String , String > map = new HashMap<>();
        //全局变量
        List<PipelineVariable> allVariable = variableServer.findAllVariable(pipelineId);
        if (allVariable.size() != 0){
            for (PipelineVariable variable : allVariable) {
                String varKey = variable.getVarKey();
                String varValue = variable.getVarValue();
                map.put(varKey,varValue);
            }
        }
        //局部变量
        List<PipelineVariable> variableList = variableServer.findAllVariable(configId);
        if (variableList.size() != 0){
            for (PipelineVariable variable : variableList) {
                String varValue = variable.getVarValue();
                String varKey = variable.getVarKey();
                map.put(varKey,varValue);
            }
        }

        StrSubstitutor substitutor = new StrSubstitutor(map);
        return substitutor.replace(order);
    }

    /**
     * 效验条件
     * @param pipelineId 流水线id
     * @param configId 配置id
     * @return 状态 true:条件满足 false:条件不满足
     */
    @Override
    public Boolean variableCond(String pipelineId,String configId){
        List<PipelineCondition> allTaskCond = conditionServer.findAllTaskCond(configId);
        if (allTaskCond == null || allTaskCond.size() == 0 ){
            return true;
        }
        for (PipelineCondition condition : allTaskCond) {
            String condKey = "${"+condition.getCondKey()+"}";
            String condValue = condition.getCondValue();
            int type = condition.getCondType();
            String key = variableKey(pipelineId, configId, condKey);
            if (type == 1 && !key.equals(condValue)){
                return false;
            }
            if (type == 2 && key.equals(condValue)){
                return false;
            }
        }
        return true;
    }


}






























