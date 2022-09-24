package net.tiklab.matflow.execute.service.execAchieveImpl;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.MatFlowConfigure;
import net.tiklab.matflow.definition.service.MatFlowCommonService;
import net.tiklab.matflow.definition.model.MatFlowBuild;
import net.tiklab.matflow.definition.service.MatFlowBuildService;
import net.tiklab.matflow.execute.model.MatFlowExecHistory;
import net.tiklab.matflow.execute.model.MatFlowExecLog;
import net.tiklab.matflow.execute.service.MatFlowExecServiceImpl;
import net.tiklab.matflow.execute.service.execAchieveService.BuildAchieveService;
import net.tiklab.matflow.execute.service.execAchieveService.CommonAchieveService;
import net.tiklab.matflow.orther.model.MatFlowProcess;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * 构建执行方法
 */

@Service
@Exporter
public class BuildAchieveServiceImpl implements BuildAchieveService {

    @Autowired
    MatFlowBuildService matFlowBuildService;

    @Autowired
    MatFlowCommonService matFlowCommonService;

    @Autowired
    CommonAchieveService commonAchieveService;

    private static final Logger logger = LoggerFactory.getLogger(BuildAchieveServiceImpl.class);

    // 构建
    public String build(MatFlowProcess matFlowProcess)  {
        List<MatFlowExecHistory> matFlowExecHistoryList = MatFlowExecServiceImpl.matFlowExecHistoryList;
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        //获取配置信息
        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        MatFlowConfigure matFlowConfigure = matFlowProcess.getMatFlowConfigure();
        //初始化日志
        MatFlowExecLog matFlowExecLog = commonAchieveService.initializeLog(matFlowExecHistory, matFlowConfigure);
        matFlowProcess.setMatFlowExecLog(matFlowExecLog);
        MatFlowBuild matFlowBuild = matFlowBuildService.findOneBuild(matFlowConfigure.getTaskId());

        //项目地址
        String path = matFlowCommonService.getFileAddress()+ matFlowConfigure.getMatFlow().getMatflowName();

        try {
            String a = "------------------------------------" + " \n"
                    +"开始构建" + " \n"
                    +"执行 : \""  + matFlowBuild.getBuildOrder() + "\"\n";

            //更新日志
            matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog() + a);
            matFlowExecLog.setRunLog(matFlowExecLog.getRunLog()+a);

            //执行命令
            Process process = getOrder(matFlowBuild, path);
            if (process == null){
                commonAchieveService.updateTime(matFlowProcess,beginTime);
                commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
                return "构建命令执行错误" ;
            }

            //构建失败
            int state = commonAchieveService.log(process.getInputStream(), matFlowProcess, matFlowExecHistoryList);
            process.destroy();
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            if (state == 0){
                commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
                return "构建失败";
            }
        } catch (IOException | ApplicationException e) {
            commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
            return e.getMessage() ;
        }
        commonAchieveService.updateState(matFlowProcess,true, matFlowExecHistoryList);
        return null;
    }

    /**
     * 执行build
     * @param matFlowBuild 执行信息
     * @param path 项目地址
     * @return 执行命令
     */
    private Process getOrder(MatFlowBuild matFlowBuild,String path ) throws ApplicationException, IOException {
        String buildOrder = matFlowBuild.getBuildOrder();
        String buildAddress = matFlowBuild.getBuildAddress();
        
        int type = matFlowBuild.getType();
        String order ;
        switch (type){
            case 21 -> {
                String mavenAddress = commonAchieveService.getScm(21);
                if (mavenAddress == null) {
                    throw new ApplicationException("不存在maven配置");
                }
                order = mavenOrder(buildOrder, path, buildAddress);
                return commonAchieveService.process(mavenAddress, order);
            }
            case 22 -> {
                String nodeAddress = commonAchieveService.getScm(22);
                if (nodeAddress == null) {
                    throw new ApplicationException("不存在node配置");
                }
                order = nodeOrder(buildOrder, path, buildAddress);
                return commonAchieveService.process(nodeAddress, order);
            }
        }
        return null;
    }

    /**
     * 拼装maven命令
     * @param buildOrder 执行命令
     * @param path 项目地址
     * @param buildAddress 模块地址
     * @return 命令
     */
    private String mavenOrder(String buildOrder,String path,String buildAddress){
        
        String order;
        int systemType = commonAchieveService.getSystemType();
        order = " ./" + buildOrder + " " + "-f" +" " +path ;
        if (systemType == 1){
            order = " .\\" + buildOrder + " " + "-f"+" "  +path;
        }
        if (!Objects.equals(buildAddress, "/")){
            order = order + buildAddress;
        }
        return order;
    }

    /**
     * 拼装node命令
     * @param buildOrder 执行命令
     * @param path 项目地址
     * @param buildAddress 模块地址
     * @return 命令
     */
    private String nodeOrder(String buildOrder,String path,String buildAddress ){
        String order;
        int systemType = commonAchieveService.getSystemType();
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
