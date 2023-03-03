package net.tiklab.matflow.pipeline.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import net.tiklab.matflow.setting.model.Scm;
import net.tiklab.matflow.setting.service.ScmService;
import net.tiklab.matflow.support.condition.model.Condition;
import net.tiklab.matflow.support.condition.service.ConditionService;
import net.tiklab.matflow.support.util.PipelineFinal;
import net.tiklab.matflow.support.util.PipelineUtil;
import net.tiklab.matflow.support.variable.model.Variable;
import net.tiklab.matflow.support.variable.service.VariableService;
import net.tiklab.matflow.task.task.model.TaskInstance;
import net.tiklab.matflow.task.task.service.TaskInstanceService;
import net.tiklab.rpc.annotation.Exporter;
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
public class PipelineExecLogServiceImpl implements PipelineExecLogService {

    @Autowired
    ScmService scmService;

    @Autowired
    PipelineInstanceService historyService;

    @Autowired
    TaskInstanceService logService;

    @Autowired
    VariableService variableServer;

    @Autowired
    ConditionService conditionServer;

    //历史
    Map<String,String> historyMap = PipelineExecServiceImpl.historyMap;

    Map<String, TaskInstance> logMap = PipelineExecServiceImpl.logMap;

    //运行时间
    Map<String, Integer> runTime = PipelineExecServiceImpl.runTime;

    /**
     * 执行日志
     * @param pipelineProcess 执行信息
     * @return map 执行状态
     */
    @Override
    public int readRunLog(PipelineProcess pipelineProcess) throws IOException {
        int state = 1;
        String enCode = pipelineProcess.getEnCode();
        //指定编码
        if (!PipelineUtil.isNoNull(enCode)){
            int systemType = PipelineUtil.findSystemType();
            if (systemType == 1){
                enCode = PipelineFinal.GBK;
            }else {
                enCode = PipelineFinal.UTF_8;
            }
        }

        String[] error = pipelineProcess.getError();
        //转换流
        InputStream inputStream = pipelineProcess.getInputStream();
        InputStream errInputStream = pipelineProcess.getErrInputStream();

        InputStreamReader inputStreamReader ;
        BufferedReader  bufferedReader ;
        if (inputStream == null){
            inputStreamReader = PipelineUtil.encode(errInputStream, enCode);
        }else {
            inputStreamReader = PipelineUtil.encode(inputStream, enCode);
        }
        
        String s;
        bufferedReader = new BufferedReader(inputStreamReader);

        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            if (validStatus(s,error)){
                state = 0 ;
            }
            writeExecLog(pipelineProcess, PipelineUtil.date(4)+s);
        }
        //读取err日志
        inputStreamReader = PipelineUtil.encode(errInputStream, enCode);
        bufferedReader = new BufferedReader(inputStreamReader);
        while ((s = bufferedReader.readLine()) != null) {
            if (validStatus(s,error)){state = 0 ;}
            writeExecLog(pipelineProcess, PipelineUtil.date(4)+s);
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
    public void commandExecState(PipelineProcess pipelineProcess, Process process,String taskName)
            throws IOException , ApplicationException {

        pipelineProcess.setInputStream(process.getInputStream());
        pipelineProcess.setErrInputStream(process.getErrorStream());

        //构建失败
        int state = readRunLog(pipelineProcess);
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

        PipelineInstance execHistory = historyService.findOneInstance(historyId);

        Integer integer = runTime.get(historyId);
        if (integer ==  null){
            integer = 0;
        }

        execHistory.setRunTime(integer+1);
        //更新状态
        execHistory.setRunStatus(status);
        historyService.updateInstance(execHistory);

    }

    /**
     * 更新日志执行状态
     * @param pipelineId 执行信息
     */
    public void updateLogState(String pipelineId,String logId,int state){
        TaskInstance taskInstance = logMap.get(logId);
        if (taskInstance != null){
            //状态,id
            taskInstance.setRunState(state);
            taskInstance.setLogId(logId);

            //运行时间
            Integer integer = runTime.get(logId);
            if (integer !=  null && integer == 0){
                integer = 1;
            }
            if (integer ==  null){
                integer = 0;
            }
            taskInstance.setRunTime(integer);

            String execLog = taskInstance.getRunLog();
            if (PipelineUtil.isNoNull(execLog)){
                String runLog = taskInstance.getLogAddress();
                PipelineUtil.logWriteFile(execLog,runLog);
            }
            logService.updateLog(taskInstance);
        }
        logMap.remove(logId);
    }



    /**
     * 获取环境配置信息
     * @param type 类型
     * @return 配置信息
     */
    @Override
    public String getScm(int type){
        Scm scm = scmService.findOnePipelineScm(type);
        if (scm == null){
            return null;
        }
        return scm.getScmAddress();
    }

    /**
     * 执行过程中的历史
     */
    @Override
    public void writeExecLog(PipelineProcess pipelineProcess,String log){
        if(!PipelineUtil.isNoNull(log)){
            return;
        }

        String logId = pipelineProcess.getLogId();
        TaskInstance taskInstance = logMap.get(logId);
        Integer integer = runTime.get(logId);
        if (integer == null){
            integer = 0;
        }
        taskInstance.setRunTime(integer);

        String execLog = taskInstance.getRunLog();

        if (!PipelineUtil.isNoNull(execLog)){
            taskInstance.setRunLog(log);
        }else {
            taskInstance.setRunLog(execLog +"\n"+ log);
        }

        //长度过长写入文件中
        if (taskInstance.getRunLog().length() > 25000){
            String runLog = taskInstance.getLogAddress();
            PipelineUtil.logWriteFile(execLog,runLog);
            taskInstance.setRunLog(null);
        }

        logMap.put(logId, taskInstance);
    }


    @Override
    public String replaceVariable(String pipelineId,String configId,String order){
        Map<String , String > map = new HashMap<>();
        //全局变量
        List<Variable> allVariable = variableServer.findAllVariable(pipelineId);
        if (allVariable.size() != 0){
            for (Variable variable : allVariable) {
                String varKey = variable.getVarKey();
                String varValue = variable.getVarValue();
                map.put(varKey,varValue);
            }
        }
        //局部变量
        List<Variable> variableList = variableServer.findAllVariable(configId);
        if (variableList.size() != 0){
            for (Variable variable : variableList) {
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
    public Boolean variableCondition(String pipelineId,String configId){
        List<Condition> allTaskCond = conditionServer.findAllTaskCond(configId);
        if (allTaskCond == null || allTaskCond.size() == 0 ){
            return true;
        }
        for (Condition condition : allTaskCond) {
            String condKey = "${"+condition.getCondKey()+"}";
            String condValue = condition.getCondValue();
            int type = condition.getCondType();
            String key = replaceVariable(pipelineId, configId, condKey);
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






























