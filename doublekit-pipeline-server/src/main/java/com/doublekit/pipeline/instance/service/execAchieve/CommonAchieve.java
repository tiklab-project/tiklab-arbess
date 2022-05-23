package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
import com.doublekit.rpc.annotation.Exporter;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Exporter
public class CommonAchieve {

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;


    @Autowired
    PipelineExecLogService pipelineExecLogService;


    /**
     * 执行日志
     * @param inputStreamReader 执行信息
     * @param pipelineExecHistory 历史
     * @throws IOException 字符流转换异常
     * @return map 执行状态
     */
    public Map<String, String> log(InputStreamReader inputStreamReader , PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList,PipelineExecLog pipelineExecLog) throws IOException {
        Map<String, String> map = new HashMap<>();
        String s;
        // InputStreamReader  inputStreamReader = new InputStreamReader(process.getInputStream());
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        String logRunLog = "";
        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            logRunLog = logRunLog + s + "\n";
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+s+"\n");
            pipelineExecHistoryList.add(pipelineExecHistory);
            if (logRunLog.contains("BUILD FAILURE")){
                pipelineExecLog.setRunLog(logRunLog);
                pipelineExecLogService.updateLog(pipelineExecLog);
                pipelineExecHistoryList.add(pipelineExecHistory);
                map.put("state","0");
                map.put("log",logRunLog);
                return map;
            }
        }
        pipelineExecLog.setRunLog(logRunLog);
        pipelineExecLogService.updateLog(pipelineExecLog);
        map.put("state","1");
        map.put("log",logRunLog);
        inputStreamReader.close();
        bufferedReader.close();
        return map;
    }




    /**
     * 获取符合条件的文件名
     * @param path 文件地址
     * @param s 文件后缀名
     * @return 文件名
     */
    public  String zipName(String path,String s) {

        File f = new File(path);
        List<String> list = new ArrayList<>();
        File[] fa = f.listFiles();
        String regex = ".*."+s;
        if (fa != null){
            for (File fs : fa) {
                //判断符合条件的文件
                if (fs.getName().matches(regex)) {
                    if (!fs.isDirectory()) {
                        list.add(fs.getName());
                    }
                }
            }
            //获取符合条件的
            if (list.size() == 1){
                return list.get(0);
            }
        }
        return null;
    }


    /**
     * 调用cmd执行命令
     * @param path 构建地址
     * @param order 构建命令
     * @return 构建信息
     * @throws IOException 构建命令执行异常
     */
    public Process process(String path,String order,String sourceAddress) throws IOException {
        Runtime runtime=Runtime.getRuntime();
        Process process;
        if (sourceAddress != null){
            process = runtime.exec("cmd.exe /c cd " + path + "\\"+sourceAddress + " &&" + " " + order);
            return process;
        }
        //执行命令
        process = runtime.exec("cmd.exe /c cd " + path + " &&" + " " + order);
        return process;
    }


    /**
     * 凭证信息（UsernamePassword）方式
     * @param gitUser 用户名
     * @param gitPasswd 密码
     * @return 验证信息
     */
    public  UsernamePasswordCredentialsProvider usernamePassword(String gitUser, String gitPasswd) {

        UsernamePasswordCredentialsProvider credentialsProvider = null;
        if (StringUtils.isNotEmpty(gitUser) && StringUtils.isNotEmpty(gitPasswd)) {
            credentialsProvider = new UsernamePasswordCredentialsProvider(gitUser, gitPasswd);
        }
        return credentialsProvider;
    }

    /**
     * 删除旧的代码
     * @param dir 文件地址
     */
    public boolean deleteFile(File dir){
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteFile(new File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
            // 目录此时为空，可以删除
        }
        return dir.delete();
    }

    /**
     * 更新执行时间
     * @param pipelineExecHistory 历史
     * @param pipelineExecLog 日志
     * @param beginTime 开始时间
     */
    public void updateTime(PipelineExecHistory pipelineExecHistory, PipelineExecLog pipelineExecLog, long beginTime){
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        pipelineExecLog.setRunTime(time);
        pipelineExecHistory.setRunTime(pipelineExecHistory.getRunTime()+time);
    }

    /**
     * 更新状态
     * @param pipelineExecHistory 历史
     * @param pipelineExecLog 日志
     * @param e 异常
     * @param pipelineExecHistoryList 状态集合
     */
    public  void updateState(PipelineExecHistory pipelineExecHistory,PipelineExecLog pipelineExecLog,String e,List<PipelineExecHistory> pipelineExecHistoryList){
        pipelineExecLog.setRunState(10);
        Pipeline onePipeline = pipelineExecHistory.getPipeline();
        if (e != null){
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog() +"\n"+e);
            pipelineExecLog.setRunState(1);
            pipelineExecLogService.updateLog(pipelineExecLog);
            error(pipelineExecHistory,e, onePipeline.getPipelineId(),pipelineExecHistoryList);
        }
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        pipelineExecLogService.updateLog(pipelineExecLog);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        pipelineExecHistoryList.add(pipelineExecHistory);
        //success(pipelineExecHistory,onePipeline.getPipelineId(),pipelineExecHistoryList);
    }


    /**
     * 输出错误信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     * @param e 错误信息
     */
    public  void  error(PipelineExecHistory pipelineExecHistory, String e, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList){
        pipelineExecHistory.setStatus(pipelineExecHistory.getStatus()+2);
        pipelineExecHistoryList.add(pipelineExecHistory);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + e + "\n" + " RUN RESULT : FAIL");
        pipelineExecHistory.setRunStatus(1);
        pipelineExecHistory.setFindState(1);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        pipelineExecHistoryList.add(pipelineExecHistory);
        // 清空缓存
        clean(pipelineExecHistory,pipelineId,pipelineExecHistoryList);
    }

    /**
     * 输出成功信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     */
    public  void  success(PipelineExecHistory pipelineExecHistory, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList) {
        if (pipelineExecHistory.getRunLog() != null){
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n"  + "\n" + " RUN RESULT : SUCCESS");
        }else {
            pipelineExecHistory.setRunLog( "\n"+ " RUN RESULT : SUCCESS");
        }
        pipelineExecHistory.setRunStatus(30);
        pipelineExecHistory.setFindState(1);
        pipelineExecHistoryList.add(pipelineExecHistory);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        //清空缓存
        clean(pipelineExecHistory,pipelineId,pipelineExecHistoryList);
    }

    /**
     * 清空缓存
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     */
    public  void clean(PipelineExecHistory pipelineExecHistory, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList){
        //恢复中断状态
        try {
            Thread.sleep(1000);
        } catch (InterruptedException s) {
            Thread.currentThread().interrupt();
        }
        // 清除集合缓存
        Pipeline pipeline = pipelineExecHistory.getPipeline();
        pipelineExecHistoryList.removeIf(history ->  pipeline.getPipelineId().equals(pipelineId));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
