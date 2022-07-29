package com.tiklab.matflow.instance.service.execAchieveImpl;

import com.tiklab.matflow.definition.model.Pipeline;
import com.tiklab.matflow.definition.model.PipelineConfigure;
import com.tiklab.matflow.definition.service.PipelineCommonService;
import com.tiklab.matflow.instance.model.PipelineExecHistory;
import com.tiklab.matflow.instance.model.PipelineExecLog;
import com.tiklab.matflow.instance.model.PipelineProcess;
import com.tiklab.matflow.instance.service.PipelineExecHistoryService;
import com.tiklab.matflow.instance.service.PipelineExecLogService;
import com.tiklab.matflow.instance.service.execAchieveService.CommonAchieveService;
import com.doublekit.rpc.annotation.Exporter;
import com.doublekit.user.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class CommonAchieveServiceImpl implements CommonAchieveService {

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    @Autowired
    PipelineCommonService pipelineCommonService;

    private static final Logger logger = LoggerFactory.getLogger(CommonAchieveServiceImpl.class);

    /**
     * 执行日志
     * @param inputStream 执行信息
     * @param pipelineProcess 执行信息
     * @throws IOException 字符流转换异常
     * @return map 执行状态
     */
    @Override
    public int log(InputStream inputStream, PipelineProcess pipelineProcess,List<PipelineExecHistory> pipelineExecHistoryList) throws IOException {
        String fileAddress = pipelineCommonService.getFileAddress();
        InputStreamReader inputStreamReader;
        if (fileAddress.equals("/usr/local/pipeline/")){
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }else {
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("GBK"));
        }
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        PipelineExecLog pipelineExecLog = pipelineProcess.getPipelineExecLog();
        String s;
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder logRunLog = new StringBuilder();

        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logRunLog.append(s).append("\n");
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+s+"\n");
            pipelineExecHistoryList.add(pipelineExecHistory);
            if (logRunLog.toString().contains("BUILD FAILURE")){
               return 0;
            }
        }
        //更新状态
        pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+logRunLog);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        pipelineExecLogService.updateLog(pipelineExecLog);
        inputStreamReader.close();
        bufferedReader.close();
        return 1;
    }

    /**
     * 调用cmd执行命令
     * @param path 构建地址
     * @param order 构建命令
     * @return 构建信息
     * @throws IOException 构建命令执行异常
     */
    @Override
    public Process process(String path,String order,String sourceAddress) throws IOException {

        Runtime runtime=Runtime.getRuntime();
        Process process;
        String property = System.getProperty("os.name");

        String[] s = property.split(" ");
        if (s[0].equals("Windows")){
            if (sourceAddress != null){
                process = runtime.exec("cmd.exe /c cd " + path + "\\"+sourceAddress + " &&" + " " + order);
                return process;
            }
            //执行命令
            process = runtime.exec("cmd.exe /c cd " + path + " &&" + " " + order);
        }else {
            if (sourceAddress != null){
                String[] cmd = new String[] { "/bin/sh", "-c", "cd "+path+"/"+sourceAddress+";"+" source /etc/profile;"+order };
                process = runtime.exec(cmd);
                return process;
            }
            //执行命令
            String[] cmd = new String[] { "/bin/sh", "-c", "cd "+path+";"+" source /etc/profile;"+order };
            process = runtime.exec(cmd);
        }
        return process;
    }

    /**
     * 更新执行时间
     * @param pipelineProcess 执行信息
     * @param beginTime 开始时间
     */
    @Override
    public void updateTime(PipelineProcess pipelineProcess, long beginTime){
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        PipelineExecLog pipelineExecLog = pipelineProcess.getPipelineExecLog();
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        pipelineExecLog.setRunTime(time+1);
        pipelineExecHistory.setRunTime(pipelineExecHistory.getRunTime()+time);
    }

    /**
     * 更新状态
     * @param pipelineProcess 执行信息
     * @param e 异常
     * @param pipelineExecHistoryList 状态集合
     */
    @Override
    public  void updateState(PipelineProcess pipelineProcess,String e,List<PipelineExecHistory> pipelineExecHistoryList){
        PipelineExecLog pipelineExecLog = pipelineProcess.getPipelineExecLog();
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        pipelineExecLog.setRunState(10);
        Pipeline onePipeline = pipelineExecHistory.getPipeline();
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        pipelineExecHistoryList.add(pipelineExecHistory);
        if (e != null){
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog() +"\n"+e);
            pipelineExecLog.setRunState(1);
            pipelineExecLogService.updateLog(pipelineExecLog);
            error(pipelineExecHistory,e, onePipeline.getPipelineId(),pipelineExecHistoryList);
            return;
        }
        pipelineExecLogService.updateLog(pipelineExecLog);
    }


    /**
     * 输出错误信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     * @param e 错误信息
     */
    @Override
    public  void  error(PipelineExecHistory pipelineExecHistory, String e, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList){
        pipelineExecHistory.setStatus(pipelineExecHistory.getStatus()+2);
        pipelineExecHistoryList.add(pipelineExecHistory);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + e + "\n" + "RUN RESULT : FAIL");
        pipelineExecHistory.setRunStatus(1);
        pipelineExecHistory.setFindState(1);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        pipelineExecHistoryList.add(pipelineExecHistory);
        // 清空缓存
        pipelineExecHistoryList.removeIf(execHistory -> execHistory.getPipeline().getPipelineId().equals(pipelineId));
    }

    /**
     * 输出成功信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     */
    @Override
    public  void  success(PipelineExecHistory pipelineExecHistory, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList) {
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + "RUN RESULT : SUCCESS");
        pipelineExecHistory.setRunStatus(30);
        pipelineExecHistory.setFindState(1);
        pipelineExecHistoryList.add(pipelineExecHistory);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        //清空缓存
        pipelineExecHistoryList.removeIf(execHistory -> execHistory.getPipeline().getPipelineId().equals(pipelineId));
    }

    /**
     * 输出停止信息
     * @param pipelineProcess 历史
     * @param pipelineId 流水线id
     */
    @Override
    public  void  halt(PipelineProcess pipelineProcess, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList) {
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();

        //更新信息
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + "RUN RESULT : HALT");
        pipelineExecHistory.setRunStatus(20);
        pipelineExecHistory.setFindState(1);
        pipelineExecHistoryList.add(pipelineExecHistory);
        //更新状态
        pipelineExecLogService.updateLog(pipelineProcess.getPipelineExecLog());
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        //清空缓存
        pipelineExecHistoryList.removeIf(execHistory -> execHistory.getPipeline().getPipelineId().equals(pipelineId));
    }

    /**
     * 初始化日志
     * @param pipelineExecHistory 历史
     * @param pipelineConfigure 配置信息
     * @return 日志
     */
    @Override
    public PipelineExecLog initializeLog(PipelineExecHistory pipelineExecHistory, PipelineConfigure pipelineConfigure){
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        pipelineExecLog.setTaskAlias(pipelineConfigure.getTaskAlias());
        pipelineExecLog.setTaskSort(pipelineConfigure.getTaskSort());
        pipelineExecLog.setTaskType(pipelineConfigure.getTaskType());
        pipelineExecLog.setRunLog("");
        String logId = pipelineExecLogService.createLog(pipelineExecLog);
        pipelineExecLog.setLogId(logId);
        return pipelineExecLog;
    }

    /**
     * 初始化历史
     * @param historyId 历史id
     * @return 历史
     */
    @Override
    public PipelineExecHistory initializeHistory(String historyId,Pipeline pipeline,String userId) {
        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory();
        //初始化基本信息
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        pipelineExecHistory.setCreateTime(time);
        pipelineExecHistory.setRunWay(1);
        pipelineExecHistory.setSort(1);
        pipelineExecHistory.setStatus(0);
        pipelineExecHistory.setPipeline(pipeline);
        pipelineExecHistory.setHistoryId(historyId);
        pipelineExecHistory.setUser(new User().setId(userId));
        pipelineExecHistory.setHistoryId(historyId);
        //构建次数
        List<PipelineExecHistory> allHistory = pipelineExecHistoryService.findAllHistory(pipeline.getPipelineId());
        allHistory.sort(Comparator.comparing(PipelineExecHistory::getCreateTime));
        pipelineExecHistory.setFindNumber(1);
        if (allHistory.size() >= 1){
            pipelineExecHistory.setFindNumber(allHistory.get(allHistory.size()-1).getFindNumber()+1);
        }
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        return pipelineExecHistory;
    }

}
