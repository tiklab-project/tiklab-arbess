package net.tiklab.matflow.execute.service.execAchieveImpl;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.MatFlowConfigure;
import net.tiklab.matflow.definition.service.MatFlowCommonService;
import net.tiklab.matflow.definition.model.MatFlowDeploy;
import net.tiklab.matflow.definition.service.MatFlowDeployService;
import net.tiklab.matflow.execute.model.MatFlowExecHistory;
import net.tiklab.matflow.execute.model.MatFlowExecLog;
import net.tiklab.matflow.execute.service.MatFlowExecServiceImpl;
import net.tiklab.matflow.execute.service.execAchieveService.CommonAchieveService;
import net.tiklab.matflow.execute.service.execAchieveService.DeployAchieveService;
import net.tiklab.matflow.orther.model.MatFlowProcess;
import net.tiklab.matflow.setting.model.Proof;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * 部署执行方法
 */

@Service
@Exporter
public class DeployAchieveServiceImpl implements DeployAchieveService {

    @Autowired
    MatFlowDeployService matFlowDeployService;

    @Autowired
    CommonAchieveService commonAchieveService;

    @Autowired
    MatFlowCommonService matFlowCommonService;

    private static final Logger logger = LoggerFactory.getLogger(DeployAchieveServiceImpl.class);

    /**
     * 部署
     * @param matFlowProcess 配置信息
     * @return 状态
     */
    public int deploy(MatFlowProcess matFlowProcess) {
        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();

        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        MatFlowConfigure matFlowConfigure = matFlowProcess.getMatFlowConfigure();

        MatFlowExecLog matFlowExecLog = commonAchieveService.initializeLog(matFlowExecHistory, matFlowConfigure);
        MatFlowDeploy matFlowDeploy = matFlowDeployService.findOneDeploy(matFlowConfigure.getTaskId());
        Proof proof = matFlowDeploy.getProof();
        List<MatFlowExecHistory> matFlowExecHistoryList = MatFlowExecServiceImpl.matFlowExecHistoryList;
        matFlowProcess.setMatFlowExecLog(matFlowExecLog);
        matFlowProcess.setProof(proof);
        matFlowProcess.setMatFlowDeploy(matFlowDeploy);

        if (proof == null){
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            commonAchieveService.updateState(matFlowProcess,"凭证为空。", matFlowExecHistoryList);
            return 0;
        }

        String filePath = matFlowCommonService.getFile(matFlowConfigure.getMatFlow().getMatflowName(), matFlowDeploy.getSourceAddress());
        
        if (filePath == null){
            commonAchieveService.updateState(matFlowProcess,"部署文件找不到。", matFlowExecHistoryList);
            return 0;
        }
        //文件名
        String fileName = new File(filePath).getName();

        //发送文件位置
        String deployAddress ="/"+ matFlowDeploy.getDeployAddress();

        if (matFlowExecHistory.getRunLog() == null){
            matFlowExecHistory.setRunLog("");
        }

        return 1;
    }

    /**
     * linux部署
     * @param matFlowProcess 配置信息
     * @param matFlowExecHistoryList 状态集合
     */
    private void linux(MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList){
        MatFlowDeploy matFlowDeploy = matFlowProcess.getMatFlowDeploy();

        //选择自定义部署
        if (matFlowDeploy.getDeployType() == 1){
           //sshOrder(session, matFlowDeploy.getStartShell(), matFlowProcess, matFlowExecHistoryList);
           return;
        }

        //部署地址
        String deployAddress = "/"+ matFlowDeploy.getDeployAddress();

        //部署文件命令
        String  startOrder= matFlowDeploy.getDeployOrder();
        //启动文件地址
        String startAddress = matFlowDeploy.getStartAddress();

        //部署文件命令启动文件地址都为null的时候
        String order = "cd "+" "+ deployAddress +";"+ matFlowDeploy.getStartShell();
        //if ((startOrder == null || startOrder.equals("")) && (startAddress == null || startAddress.equals("")) ){
        //    if (matFlowDeploy.getStartShell() == null || matFlowDeploy.getStartShell().equals("") ){
        //        return;
        //    }
        //    commonAchieveService.process(gitAddress, order);
        //    return;
        //}
        //String orders = "cd "+" "+ deployAddress + "/" + startAddress+";" + matFlowDeploy.getStartShell();
        //if (startAddress != null && !startAddress.equals("")){
        //    if (startOrder == null ||startOrder.equals("")){
        //        sshOrder(session, orders, matFlowProcess, matFlowExecHistoryList);
        //        return;
        //    }
        //    startOrder = "cd "+" "+ deployAddress +";"+startOrder;
        //    sshOrder(session, startOrder, matFlowProcess, matFlowExecHistoryList);
        //    sshOrder(session, orders, matFlowProcess, matFlowExecHistoryList);
        //}else {
        //    sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
        //}
    }

    /**
     * docker部署
     * @param matFlowProcess 配置信息
     * @param matFlowExecHistoryList 状态集合
     */
    private void docker( MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList){
        //MatFlowDeploy matFlowDeploy = matFlowProcess.getMatFlowDeploy();
        //
        ////选择自定义部署
        //if (matFlowDeploy.getDeployType() == 1){
        //    sshOrder(session, matFlowDeploy.getStartShell(), matFlowProcess, matFlowExecHistoryList);
        //    return;
        //}
        //
        //String matFlowName = matFlowProcess.getMatFlowConfigure().getMatFlow().getMatflowName();
        ////部署位置
        //String deployAddress = "/"+  matFlowDeploy.getDeployAddress();
        ////部署文件命令
        //String  deployOrder= matFlowDeploy.getDeployOrder();
        ////启动文件地址
        //String startAddress = matFlowDeploy.getStartAddress();
        //
        //
        //String order = "docker stop $(docker ps -a | grep '"+matFlowName+"' | awk '{print $1 }');"
        //        +"docker rm $(docker ps -a | grep '"+matFlowName+"' | awk '{print $1 }');"
        //        +"docker image rm"+" "+matFlowName+";";
        //
        //if ((deployOrder == null || deployOrder.equals("")) && (startAddress == null || startAddress.equals("/")) ){
        //
        //     order = order +"cd"+" "+deployAddress+";"+"docker image build -t"+" "+matFlowName+"  .;"
        //            +"docker run -itd -p"+" "+ matFlowDeploy.getMappingPort()+":"+ matFlowDeploy.getStartPort()+" "+matFlowName;
        //    sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
        //    return;
        //}
        //if (deployOrder != null && !deployOrder.equals("") ) {
        //    deployOrder = "cd "+" "+ deployAddress +";"+deployOrder;
        //    sshOrder(session, deployOrder, matFlowProcess, matFlowExecHistoryList);
        //    if (startAddress == null || startAddress.equals("/")) {
        //
        //        order = order + "cd" + " " + deployAddress + ";" + "docker image build -t" + " " + matFlowName + "  .;"
        //                + "docker run -itd -p" + " " + matFlowDeploy.getMappingPort() + ":" + matFlowDeploy.getStartPort() + " " + matFlowName;
        //        sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
        //        return;
        //    }
        //}
        //
        //order = order +"cd"+" "+deployAddress+"/"+startAddress+";"+"docker image build -t"+" "+matFlowName+"  .;"
        //        +"docker run -itd -p"+" "+ matFlowDeploy.getMappingPort()+":"+ matFlowDeploy.getStartPort()+" "+matFlowName;
        //sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
    }

    private String getBuildOrder(Proof proof){
        int systemType = commonAchieveService.getSystemType();
        if (systemType==1){
            logger.info("部署Windows未开放");
        }else {
            return null;
        }
        return null;
    }



    /**
     * 初始化Docker镜像
     */
    //public void initializeDocker(String gitAddress) {
    //
    //    String order ;
    //    //创建centos镜像
    //    order = "docker pull zcamy/darth_matFlow:centos-8";
    //    try {
    //        commonAchieveService.process(gitAddress, order);
    //    } catch (IOException e) {
    //        throw new ApplicationException("拉取");
    //    }
    //
    //    //创建jdk镜像
    //    order = "docker pull zcamy/darth_matFlow:jdk-16.0.2";
    //    try {
    //        commonAchieveService.process(gitAddress, order);
    //    } catch (IOException e) {
    //        throw new RuntimeException(e);
    //    }
    //
    //    //创建mysql镜像
    //    order = "[[ ! -z `docker ps -a | grep 'mysql' | awk '{print $1 }'` ]] " +
    //            "&& docker container restart matFlow || "
    //            +"docker pull zcamy/darth_matFlow:mysql-8.0.28;docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=darth2020 -d zcamy/darth_matFlow:mysql-8.0.28";
    //    try {
    //        commonAchieveService.process(gitAddress, order);
    //    } catch (IOException e) {
    //        throw new RuntimeException(e);
    //    }
    //    logger.info("mysql镜像初始化成功。。。。。。。。。。。。。。。");
    //
    //}


}
