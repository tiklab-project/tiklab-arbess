package net.tiklab.matflow.execute.service.execAchieveImpl;

import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.matflow.definition.model.MatFlowConfigure;
import net.tiklab.matflow.execute.model.MatFlowExecHistory;
import net.tiklab.matflow.execute.model.MatFlowExecLog;
import net.tiklab.matflow.execute.service.MatFlowExecHistoryService;
import net.tiklab.matflow.execute.service.MatFlowExecLogService;
import net.tiklab.matflow.execute.service.execAchieveService.CommonAchieveService;
import net.tiklab.matflow.orther.model.MatFlowProcess;
import net.tiklab.matflow.setting.model.MatFlowScm;
import net.tiklab.matflow.setting.service.MatFlowScmService;
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
public class CommonAchieveServiceImpl implements CommonAchieveService {

    @Autowired
    MatFlowExecHistoryService matFlowExecHistoryService;

    @Autowired
    MatFlowExecLogService matFlowExecLogService;

    @Autowired
    MatFlowScmService matFlowScmService;

    private static final Logger logger = LoggerFactory.getLogger(CommonAchieveServiceImpl.class);


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
     * @param matFlowProcess 执行信息
     * @return map 执行状态
     */
    @Override
    public int log(InputStream inputStream, MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList)  {

        InputStreamReader inputStreamReader;

        //根据系统指定不同日志输出格式
        if (getSystemType()==1){
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("GBK"));
        }else {
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }

        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        MatFlowExecLog matFlowExecLog = matFlowProcess.getMatFlowExecLog();
        String s;
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder logRunLog = new StringBuilder();

        //更新日志信息
        try {
            while ((s = bufferedReader.readLine()) != null) {
                logRunLog.append(s).append("\n");
                matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+s+"\n");
                matFlowExecHistoryList.add(matFlowExecHistory);
                if (logRunLog.toString().contains("BUILD FAILURE")||logRunLog.toString().contains("ERROR")) {
                    inputStreamReader.close();
                    bufferedReader.close();
                    return 0;
                }
            }
            //更新状态
            matFlowExecLog.setRunLog(matFlowExecLog.getRunLog()+logRunLog);
            matFlowExecHistoryService.updateHistory(matFlowExecHistory);
            matFlowExecLogService.updateLog(matFlowExecLog);
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
     * @param matFlowProcess 执行信息
     * @param beginTime 开始时间
     */
    @Override
    public void updateTime(MatFlowProcess matFlowProcess, long beginTime){
        long overTime = new Timestamp(System.currentTimeMillis()).getTime();
        int time = (int) (overTime - beginTime) / 1000;
        MatFlowExecLog matFlowExecLog = matFlowProcess.getMatFlowExecLog();
        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        matFlowExecLog.setRunTime(time+1);
        matFlowExecHistory.setRunTime(matFlowExecHistory.getRunTime()+time);
    }

    /**
     * 更新状态
     * @param matFlowProcess 执行信息
     * @param b 异常
     * @param matFlowExecHistoryList 状态集合
     */
    @Override
    //public  void updateState(MatFlowProcess matFlowProcess, String e, List<MatFlowExecHistory> matFlowExecHistoryList){
    public  void updateState(MatFlowProcess matFlowProcess, boolean b, List<MatFlowExecHistory> matFlowExecHistoryList){
        MatFlowExecLog matFlowExecLog = matFlowProcess.getMatFlowExecLog();
        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        matFlowExecLog.setRunState(10);
        MatFlow oneMatFlow = matFlowExecHistory.getMatFlow();
        matFlowExecLog.setHistoryId(matFlowExecHistory.getHistoryId());
        matFlowExecHistoryService.updateHistory(matFlowExecHistory);
        matFlowExecHistoryList.add(matFlowExecHistory);
        if (!b){
            matFlowExecLog.setRunState(1);
        }
        matFlowExecLogService.updateLog(matFlowExecLog);
    }


    /**
     * 输出错误信息
     * @param matFlowExecHistory 历史
     * @param matFlowId 流水线id
     * @param e 错误信息
     */
    @Override
    public  void  error(MatFlowExecHistory matFlowExecHistory, String e, String matFlowId, List<MatFlowExecHistory> matFlowExecHistoryList){
        matFlowExecHistory.setStatus(matFlowExecHistory.getStatus()+2);
        matFlowExecHistoryList.add(matFlowExecHistory);
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+ "\n" + e + "\n" + "RUN RESULT : FAIL");
        matFlowExecHistory.setRunStatus(1);
        matFlowExecHistory.setFindState(1);
        matFlowExecHistoryService.updateHistory(matFlowExecHistory);
        matFlowExecHistoryList.add(matFlowExecHistory);
        // 清空缓存
        matFlowExecHistoryList.removeIf(execHistory -> execHistory.getMatFlow().getMatflowId().equals(matFlowId));
    }

    /**
     * 输出成功信息
     * @param matFlowExecHistory 历史
     * @param matFlowId 流水线id
     */
    @Override
    public  void  success(MatFlowExecHistory matFlowExecHistory, String matFlowId, List<MatFlowExecHistory> matFlowExecHistoryList) {
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+ "\n" + "RUN RESULT : SUCCESS");
        matFlowExecHistory.setRunStatus(30);
        matFlowExecHistory.setFindState(1);
        matFlowExecHistoryList.add(matFlowExecHistory);
        matFlowExecHistoryService.updateHistory(matFlowExecHistory);
        //清空缓存
        matFlowExecHistoryList.removeIf(execHistory -> execHistory.getMatFlow().getMatflowId().equals(matFlowId));
    }

    /**
     * 输出停止信息
     * @param matFlowProcess 历史
     * @param matFlowId 流水线id
     */
    @Override
    public  void  halt(MatFlowProcess matFlowProcess, String matFlowId, List<MatFlowExecHistory> matFlowExecHistoryList) {
        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();

        //更新信息
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+ "\n" + "RUN RESULT : HALT");
        matFlowExecHistory.setRunStatus(20);
        matFlowExecHistory.setFindState(1);
        matFlowExecHistoryList.add(matFlowExecHistory);
        //更新状态
        matFlowExecLogService.updateLog(matFlowProcess.getMatFlowExecLog());
        matFlowExecHistoryService.updateHistory(matFlowExecHistory);
        //清空缓存
        matFlowExecHistoryList.removeIf(execHistory -> execHistory.getMatFlow().getMatflowId().equals(matFlowId));
    }

    /**
     * 初始化日志
     * @param matFlowExecHistory 历史
     * @param matFlowConfigure 配置信息
     * @return 日志
     */
    @Override
    public MatFlowExecLog initializeLog(MatFlowExecHistory matFlowExecHistory, MatFlowConfigure matFlowConfigure){
        MatFlowExecLog matFlowExecLog = new MatFlowExecLog();
        matFlowExecLog.setHistoryId(matFlowExecHistory.getHistoryId());
        matFlowExecLog.setTaskAlias(matFlowConfigure.getTaskAlias());
        matFlowExecLog.setTaskSort(matFlowConfigure.getTaskSort());
        matFlowExecLog.setTaskType(matFlowConfigure.getTaskType());
        matFlowExecLog.setRunLog("");
        String logId = matFlowExecLogService.createLog(matFlowExecLog);
        matFlowExecLog.setLogId(logId);
        return matFlowExecLog;
    }

    /**
     * 初始化历史
     * @param historyId 历史id
     * @return 历史
     */
    @Override
    public MatFlowExecHistory initializeHistory(String historyId, MatFlow matFlow, String userId) {
        MatFlowExecHistory matFlowExecHistory = new MatFlowExecHistory();
        //初始化基本信息
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        matFlowExecHistory.setCreateTime(time);
        matFlowExecHistory.setRunWay(1);
        matFlowExecHistory.setSort(1);
        matFlowExecHistory.setStatus(0);
        matFlowExecHistory.setMatFlow(matFlow);
        matFlowExecHistory.setHistoryId(historyId);
        User user = new User();
        user.setId(userId);
        matFlowExecHistory.setUser(user);
        matFlowExecHistory.setHistoryId(historyId);
        //构建次数
        List<MatFlowExecHistory> allHistory = matFlowExecHistoryService.findAllHistory(matFlow.getMatflowId());
        allHistory.sort(Comparator.comparing(MatFlowExecHistory::getCreateTime));
        matFlowExecHistory.setFindNumber(1);
        if (allHistory.size() >= 1){
            matFlowExecHistory.setFindNumber(allHistory.get(allHistory.size()-1).getFindNumber()+1);
        }
        matFlowExecHistoryService.updateHistory(matFlowExecHistory);
        return matFlowExecHistory;
    }


    /**
     * 获取环境配置信息
     * @param type 类型
     * @return 配置信息
     */
    @Override
    public String getScm(int type){
        MatFlowScm matFlowScm = matFlowScmService.findOneMatFlowScm(type);
        if (matFlowScm == null){
            return null;
        }
        return matFlowScm.getScmAddress();
    }

}
