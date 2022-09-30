package net.tiklab.pipeline.execute.service.execAchieveImpl;

import net.tiklab.pipeline.definition.model.*;
import net.tiklab.pipeline.execute.model.PipelineExecHistory;
import net.tiklab.pipeline.execute.model.PipelineExecLog;
import net.tiklab.pipeline.execute.service.PipelineExecHistoryService;
import net.tiklab.pipeline.execute.service.PipelineExecLogService;
import net.tiklab.pipeline.execute.service.execAchieveService.ConfigCommonService;
import net.tiklab.pipeline.orther.model.PipelineProcess;
import net.tiklab.pipeline.setting.model.PipelineScm;
import net.tiklab.pipeline.setting.service.PipelineScmService;
import net.tiklab.rpc.annotation.Exporter;
import net.tiklab.user.user.model.User;
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

/**
 * 执行过程公共方法
 */

@Service
@Exporter
public class ConfigCommonServiceImpl implements ConfigCommonService {

    @Autowired
    PipelineScmService pipelineScmService;

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    private static final Logger logger = LoggerFactory.getLogger(ConfigCommonServiceImpl.class);


    /**
     * 获取系统类型
     * @return 系统类型
     */
    @Override
    public int getSystemType(){
        String property = System.getProperty("os.name");
        String[] s1 = property.split(" ");
        if (s1[0].equals("Windows")){
            return 1;
        }else {
            return 2;
        }
    }

    /**
     * 执行日志
     * @param inputStream 执行信息
     * @param pipelineProcess 执行信息
     * @return map 执行状态
     */
    @Override
    public int log(InputStream inputStream, PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList)  {

        InputStreamReader inputStreamReader;

        //根据系统指定不同日志输出格式
        if (getSystemType()==1){
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("GBK"));
        }else {
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }

        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        PipelineExecLog pipelineExecLog = pipelineProcess.getPipelineExecLog();
        String s;
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder logRunLog = new StringBuilder();

        //更新日志信息
        try {
            while ((s = bufferedReader.readLine()) != null) {
                logRunLog.append(s).append("\n");
                pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+s+"\n");
                pipelineExecHistoryList.add(pipelineExecHistory);
                if (logRunLog.toString().contains("BUILD FAILURE")||logRunLog.toString().contains("ERROR")) {
                    inputStreamReader.close();
                    bufferedReader.close();
                    return 0;
                }
            }
            //更新状态
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+logRunLog);
            pipelineExecHistoryService.updateHistory(pipelineExecHistory);
            pipelineExecLogService.updateLog(pipelineExecLog);
            inputStreamReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    /**
     * 调用cmd执行命令
     * @param path 构建地址
     * @param order 构建命令
     * @return 构建信息
     * @throws IOException 构建命令执行异常
     * @throws NullPointerException 命令为空
     */
    @Override
    public Process process(String path,String order) throws IOException {
        Runtime runtime=Runtime.getRuntime();
        Process process;

        if (getSystemType()==1){
            //执行命令
            process = runtime.exec(" cmd.exe /c cd " + path + " &&" + " " + order);
        }else {
            //执行命令
            String[] cmd = new String[] { "/bin/sh", "-c", "cd "+path+";"+" source /etc/profile;"+ order };
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
     * @param b 异常
     * @param pipelineExecHistoryList 状态集合
     */
    @Override
    public  void updateState(PipelineProcess pipelineProcess, boolean b, List<PipelineExecHistory> pipelineExecHistoryList){
        PipelineExecLog pipelineExecLog = pipelineProcess.getPipelineExecLog();
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        pipelineExecLog.setRunState(10);
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        pipelineExecHistoryList.add(pipelineExecHistory);
        if (!b){
            pipelineExecLog.setRunState(1);
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
    public  void  error(PipelineExecHistory pipelineExecHistory, String e, String pipelineId, List<PipelineExecHistory> pipelineExecHistoryList){
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
    public  void  success(PipelineExecHistory pipelineExecHistory, String pipelineId, List<PipelineExecHistory> pipelineExecHistoryList) {
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
    public  void  halt(PipelineProcess pipelineProcess, String pipelineId, List<PipelineExecHistory> pipelineExecHistoryList) {

        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        PipelineExecLog pipelineExecLog = pipelineProcess.getPipelineExecLog();
        PipelineExecLog runLog = pipelineExecHistoryService.getRunLog(pipelineExecHistory.getHistoryId());
        pipelineExecLog.setLogId(runLog.getLogId());

        //更新信息
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+ "\n" + "RUN RESULT : HALT");
        pipelineExecHistory.setRunStatus(20);
        pipelineExecHistory.setFindState(1);
        pipelineExecHistoryList.add(pipelineExecHistory);
        //更新状态
        pipelineExecLogService.updateLog(pipelineExecLog);
        pipelineExecHistoryService.updateHistory(pipelineExecHistory);
        //清空缓存
        pipelineExecHistoryList.removeIf(execHistory -> execHistory.getPipeline().getPipelineId().equals(pipelineId));
    }

    /**
     * 初始化历史
     * * @return 历史
     */
    @Override
    public PipelineExecHistory initializeHistory(Pipeline pipeline, String userId) {

        PipelineExecHistory pipelineExecHistory = new PipelineExecHistory();
        String historyId = pipelineExecHistoryService.createHistory(pipelineExecHistory);

        //初始化基本信息
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        pipelineExecHistory.setCreateTime(time);
        pipelineExecHistory.setRunWay(1);
        pipelineExecHistory.setSort(1);
        pipelineExecHistory.setStatus(0);
        pipelineExecHistory.setPipeline(pipeline);
        pipelineExecHistory.setHistoryId(historyId);
        User user = new User();
        user.setId(userId);
        pipelineExecHistory.setUser(user);
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

    /**
     * 初始化日志
     * @param pipelineExecHistory 历史
     * @return 日志
     */
    @Override
    public PipelineExecLog initializeLog(PipelineExecHistory pipelineExecHistory,Object o ,Integer type){
        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        switch (type){
            case 10:
                PipelineCode code = (PipelineCode) o;
                pipelineExecLog.setTaskAlias(code.getCodeAlias());
                pipelineExecLog.setTaskSort(code.getSort());
                pipelineExecLog.setTaskType(code.getType());
            case 20:
                PipelineTest test = (PipelineTest) o;
                pipelineExecLog.setTaskAlias(test.getTestAlias());
                pipelineExecLog.setTaskSort(test.getSort());
                pipelineExecLog.setTaskType(test.getType());
            case 30:
                PipelineBuild build = (PipelineBuild) o;
                pipelineExecLog.setTaskAlias(build.getBuildAlias());
                pipelineExecLog.setTaskSort(build.getSort());
                pipelineExecLog.setTaskType(build.getType());
            case 40:
                PipelineDeploy deploy = (PipelineDeploy) o;
                pipelineExecLog.setTaskAlias(deploy.getDeployAlias());
                pipelineExecLog.setTaskSort(deploy.getSort());
                pipelineExecLog.setTaskType(deploy.getType());
        }
        pipelineExecLog.setRunLog("");
        String logId = pipelineExecLogService.createLog(pipelineExecLog);
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

    //时间转换成时分秒
    @Override
    public String formatDateTime(long time) {
        String DateTimes ;
        long days = time / ( 60 * 60 * 24);
        long hours = (time % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (time % ( 60 * 60)) /60;
        long seconds = time % 60;
        if(days>0){
            DateTimes= days + "天" + hours + "小时" + minutes + "分钟" + seconds + "秒";
        }else if(hours>0){
            DateTimes=hours + "小时" + minutes + "分钟" + seconds + "秒";
        }else if(minutes>0){
            DateTimes=minutes + "分钟" + seconds + "秒";
        }else{
            DateTimes=seconds + "秒";
        }
        return DateTimes;
    }

}
