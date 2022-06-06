package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
import com.doublekit.rpc.annotation.Exporter;
import com.ibm.icu.text.SimpleDateFormat;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Exporter
public class CommonAchieve {

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    @Autowired
    PipelineService pipelineService;

    private static final Logger logger = LoggerFactory.getLogger(CommonAchieve.class);
    /**
     * 执行日志
     * @param inputStreamReader 执行信息
     * @param pipelineExecHistory 历史
     * @throws IOException 字符流转换异常
     * @return map 执行状态
     */
    public int log(InputStreamReader inputStreamReader, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList,PipelineExecLog pipelineExecLog) throws IOException {

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
     * 获取符合条件的文件名
     * @param path 文件地址
     * @param list 存放文件地址
     * @return 文件地址集合
     */
    public  List<String> getFilePath(File path,List<String> list){
        File[] fa = path.listFiles();
        if (fa != null) {
            for (File file : fa) {
                if (file.isDirectory()){
                    getFilePath(file,list);
                }
                list.add(file.getPath());
            }
        }
        return list;
    }

    /**
     * 获取文件名
     * @param path 文件地址
     * @param regex 匹配条件
     * @return 文件地址
     */
    public  String getFile(String path, String regex){
        List<String> list = new ArrayList<>();
        List<String> filePath = getFilePath(new File(path),new ArrayList<>());
        for (String s : filePath) {
            File file = new File(s);
            if (file.getName().matches("^(.*"+regex+".*)")){
                list.add(s);
            }
        }
        if (list.size()==1){
            return list.get(0);
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
     * @param file 文件地址
     */
    public  Boolean deleteFile(File file){

        if (file.isDirectory()) {
            String[] children = file.list();
            //递归删除目录中的子目录下
            if (children != null) {
                for (String child : children) {
                    boolean state = deleteFile(new File(file, child));
                    int tryCount = 0;
                    while (!state && tryCount++ < 30) {
                        //回收资源
                        System.gc();
                        state = file.delete();
                    }
                }
            }
            // 目录此时为空，可以删除
        }
        return file.delete();
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
    public  void  error(PipelineExecHistory pipelineExecHistory, String e, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList){
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        pipeline.setPipelineState(0);
        pipelineService.updatePipeline(pipeline);
        pipelineExecHistory.setStatus(pipelineExecHistory.getStatus()+2);
        pipelineExecHistoryList.add(pipelineExecHistory);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + e + "\n" + " RUN RESULT : FAIL");
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
    public  void  success(PipelineExecHistory pipelineExecHistory, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList) {
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        pipeline.setPipelineState(0);
        pipelineService.updatePipeline(pipeline);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + " RUN RESULT : SUCCESS");
        pipelineExecHistory.setRunStatus(30);
        pipelineExecHistory.setFindState(1);
        pipelineExecHistoryList.add(pipelineExecHistory);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        //清空缓存
        pipelineExecHistoryList.removeIf(execHistory -> execHistory.getPipeline().getPipelineId().equals(pipelineId));
    }

    /**
     * 输出停止信息
     * @param pipelineExecHistory 历史
     * @param pipelineId 流水线id
     */
    public  void  halt(PipelineExecHistory pipelineExecHistory, String pipelineId,List<PipelineExecHistory> pipelineExecHistoryList) {
        Pipeline pipeline = pipelineService.findPipeline(pipelineId);
        pipeline.setPipelineState(0);
        pipelineService.updatePipeline(pipeline);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + " RUN RESULT : HALT");
        pipelineExecHistory.setRunStatus(20);
        pipelineExecHistory.setFindState(1);
        pipelineExecHistoryList.add(pipelineExecHistory);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        //清空缓存
        logger.info("开始清除数据 。");
        pipelineExecHistoryList.removeIf(execHistory -> execHistory.getPipeline().getPipelineId().equals(pipelineId));
        logger.info("清除数据完成 : "+ pipelineExecHistoryList.size());
    }

    /**
     * 初始化日志
     * @param pipelineExecHistory 历史
     * @param pipelineConfigure 配置信息
     * @return 日志
     */
    public PipelineExecLog initializeLog(PipelineExecHistory pipelineExecHistory, PipelineConfigure pipelineConfigure){
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        pipelineExecLog.setTaskAlias(pipelineConfigure.getTaskAlias());
        pipelineExecLog.setTaskSort(pipelineConfigure.getTaskSort());
        pipelineExecLog.setTaskType(pipelineConfigure.getTaskType());
        pipelineExecLog.setRunLog("");
        String logId = pipelineExecLogService.createLog(pipelineExecLog);
        pipelineExecLog.setPipelineLogId(logId);
        return pipelineExecLog;
    }

    /**
     * 初始化历史
     * @param historyId 历史id
     * @return 历史
     */
    public PipelineExecHistory initializeHistory( String historyId,Pipeline pipeline ) {
        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        pipelineExecHistory.setCreateTime(time);
        pipelineExecHistory.setRunWay(1);
        pipelineExecHistory.setSort(1);
        pipelineExecHistory.setStatus(0);
        pipelineExecHistory.setHistoryId(historyId);
        pipelineExecHistory.setPipeline(pipeline);
        pipelineExecHistory.setExecName(pipeline.getPipelineCreateUser());
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        return pipelineExecHistory;
    }
}
