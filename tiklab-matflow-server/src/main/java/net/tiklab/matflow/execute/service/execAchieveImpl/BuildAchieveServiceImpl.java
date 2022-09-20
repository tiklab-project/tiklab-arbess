package net.tiklab.matflow.execute.service.execAchieveImpl;

import net.tiklab.matflow.definition.model.MatFlowConfigure;
import net.tiklab.matflow.definition.service.MatFlowCommonService;
import net.tiklab.matflow.definition.model.MatFlowBuild;
import net.tiklab.matflow.definition.service.MatFlowBuildService;
import net.tiklab.matflow.execute.model.MatFlowExecHistory;
import net.tiklab.matflow.execute.model.MatFlowExecLog;
import net.tiklab.matflow.execute.service.MatFlowExecServiceImpl;
import net.tiklab.matflow.execute.service.execAchieveService.BuildAchieveService;
import net.tiklab.matflow.orther.model.MatFlowProcess;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 构建执行方法
 */

@Service
@Exporter
public class BuildAchieveServiceImpl implements BuildAchieveService {

    @Autowired
    MatFlowBuildService matFlowBuildService;

    @Autowired
    CommonAchieveServiceImpl commonAchieveServiceImpl;

    @Autowired
    MatFlowCommonService matFlowCommonService;

    private static final Logger logger = LoggerFactory.getLogger(BuildAchieveServiceImpl.class);

    // 构建
    public int build(MatFlowProcess matFlowProcess)  {
        List<MatFlowExecHistory> matFlowExecHistoryList = MatFlowExecServiceImpl.matFlowExecHistoryList;
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //获取配置信息
        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        MatFlowConfigure matFlowConfigure = matFlowProcess.getMatFlowConfigure();
        //初始化日志
        MatFlowExecLog matFlowExecLog = commonAchieveServiceImpl.initializeLog(matFlowExecHistory, matFlowConfigure);
        matFlowProcess.setMatFlowExecLog(matFlowExecLog);
        MatFlowBuild matFlowBuild = matFlowBuildService.findOneBuild(matFlowConfigure.getTaskId());

        //执行信息
        String buildOrder = matFlowBuild.getBuildOrder();
        String buildAddress = matFlowBuild.getBuildAddress();

        //命令执行地址
        String path = matFlowCommonService.getFileAddress()+ matFlowConfigure.getMatFlow().getMatflowName();

        try {
            String a = "------------------------------------" + " \n"
                    +"开始构建" + " \n"
                    +"执行 : \""  + buildOrder + "\"\n";

            Process process = commonAchieveServiceImpl.process(path, buildOrder);

            matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog() + a);
            matFlowExecLog.setRunLog(matFlowExecLog.getRunLog()+a);
            //构建失败
            int state = commonAchieveServiceImpl.log(process.getInputStream(), matFlowProcess, matFlowExecHistoryList);
            process.destroy();
            commonAchieveServiceImpl.updateTime(matFlowProcess,beginTime);
            if (state == 0){
                commonAchieveServiceImpl.updateState(matFlowProcess,"构建失败。", matFlowExecHistoryList);
                return 0;
            }
        } catch (IOException e) {
            commonAchieveServiceImpl.updateState(matFlowProcess,e.getMessage(), matFlowExecHistoryList);
            return 0;
        }
        commonAchieveServiceImpl.updateState(matFlowProcess,null, matFlowExecHistoryList);
        return 1;
    }

    private String mavenOrder(String buildOrder,String path,String buildAddress ){
        String order;
        int systemType = commonAchieveServiceImpl.getSystemType();
        order = " ./" + buildOrder + " " + "-f" +path ;
        if (systemType == 1){
            order = " .\\" + buildOrder + " " + "-f" +path;
        }
        if (buildAddress != null){
            order = order + buildAddress;
        }
        return order;
    }

    private String nodeOrder(String buildOrder,String path,String buildAddress ){
        String order;
        int systemType = commonAchieveServiceImpl.getSystemType();
        order = " ./" + buildOrder + " " + "-f" +path ;
        if (systemType == 1){
            order = " .\\" + buildOrder + " " + "-f" +path;
        }
        if (buildAddress != null){
            order = order + buildAddress;
        }
        return order;
    }
}
