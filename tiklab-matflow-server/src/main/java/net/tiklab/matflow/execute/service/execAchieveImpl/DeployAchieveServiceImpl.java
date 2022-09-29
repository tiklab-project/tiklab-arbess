package net.tiklab.matflow.execute.service.execAchieveImpl;

import com.jcraft.jsch.*;
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
    public String deploy(MatFlowProcess matFlowProcess) {
        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();

        List<MatFlowExecHistory> matFlowExecHistoryList = MatFlowExecServiceImpl.matFlowExecHistoryList;

        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        MatFlowConfigure matFlowConfigure = matFlowProcess.getMatFlowConfigure();

        MatFlowExecLog matFlowExecLog = commonAchieveService.initializeLog(matFlowExecHistory, matFlowConfigure);

        MatFlowDeploy matFlowDeploy = matFlowDeployService.findOneDeploy(matFlowConfigure.getTaskId());

        Proof proof = matFlowDeploy.getProof();

        matFlowProcess.setMatFlowExecLog(matFlowExecLog);
        matFlowProcess.setProof(proof);
        matFlowProcess.setMatFlowDeploy(matFlowDeploy);

        if (proof == null){
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
            return "凭证为空。";
        }

        //获取部署文件
        String filePath = matFlowCommonService.getFile(matFlowConfigure.getMatFlow().getMatflowName(), matFlowDeploy.getSourceAddress());
        if (filePath == null){
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            commonAchieveService.updateState(matFlowProcess,true, matFlowExecHistoryList);
            return "部署文件找不到。";
        }

        //文件名
        String fileName = new File(filePath).getName();

        //发送文件位置
        String deployAddress ="/"+ matFlowDeploy.getDeployAddress();

        Session session;
        try {
            session = createSession(matFlowProcess);
        } catch (JSchException e) {
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            commonAchieveService.updateState(matFlowProcess,true, matFlowExecHistoryList);
            return "连接失败，无法连接到服务器";
        }

        //ftp(session,deployAddress, )



        return null;
    }

    /**
     * linux部署
     * @param matFlowProcess 配置信息
     * @param matFlowExecHistoryList 状态集合
     */
    private void linux(Session session, MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList) throws JSchException, IOException {
        MatFlowDeploy matFlowDeploy = matFlowProcess.getMatFlowDeploy();

        //选择自定义部署
        if (matFlowDeploy.getDeployType() == 1){
           sshOrder(session,matFlowDeploy.getStartShell(),matFlowProcess,matFlowExecHistoryList);
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
        if ((startOrder == null || startOrder.equals("")) && (startAddress == null || startAddress.equals("")) ){
            if (matFlowDeploy.getStartShell() == null || matFlowDeploy.getStartShell().equals("") ){
                return;
            }
            sshOrder(session,order, matFlowProcess, matFlowExecHistoryList);
            return;
        }
        String orders = "cd "+" "+ deployAddress + "/" + startAddress+";" + matFlowDeploy.getStartShell();
        if (startAddress != null && !startAddress.equals("")){
            if (startOrder == null ||startOrder.equals("")){
                sshOrder(session,orders, matFlowProcess, matFlowExecHistoryList);
                return;
            }
            startOrder = "cd "+" "+ deployAddress +";"+startOrder;
            sshOrder( session,startOrder, matFlowProcess, matFlowExecHistoryList);
            sshOrder(session,orders, matFlowProcess, matFlowExecHistoryList);
        }else {
            sshOrder(session,order, matFlowProcess, matFlowExecHistoryList);
        }
    }


    /**
     * 创建连接实例
     * @param matFlowProcess 连接配置信息
     * @return 实例
     * @throws JSchException 连接失败
     */
    private Session createSession(MatFlowProcess matFlowProcess) throws JSchException {
        MatFlowDeploy matFlowDeploy = matFlowProcess.getMatFlowDeploy();
        String sshIp = matFlowDeploy.getSshIp();
        int sshPort = matFlowDeploy.getSshPort();

        Proof proof = matFlowDeploy.getProof();
        String proofUsername = proof.getProofUsername();
        String proofPassword = proof.getProofPassword();

        JSch jsch = new JSch();

        Session session = jsch.getSession(proofPassword, sshIp, sshPort);
        session.setPassword(proofUsername);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    /**
     * 连接服务器执行命令
     * @param orders 命令
     * @param matFlowProcess 配置信息
     * @param matFlowExecHistoryList 执行历史
     * @throws JSchException 连接错误
     * @throws IOException 读取执行信息失败
     */
    private void sshOrder(Session session,String orders,MatFlowProcess matFlowProcess,List<MatFlowExecHistory> matFlowExecHistoryList) throws JSchException, IOException {
        ChannelExec exec = (ChannelExec) session.openChannel("exec");
        exec.setCommand(orders);
        commonAchieveService.log(exec.getInputStream(), matFlowProcess, matFlowExecHistoryList);
        exec.disconnect();
        session.disconnect();
    }


    /**
     * 判断sftp是否连接
     */
    public boolean isChannel(Channel channel) {
        try {
            if (channel.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    /**
     * 发送文件
     * @param session 连接实例
     * @param localFile 文件
     * @throws JSchException 连接失败
     */
    private String ftp(Session session,String localFile,String uploadAddress) throws JSchException, SftpException {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();
        File file = new File(localFile);
        if(file.exists()){
            //判断目录是否存在
            sftp.lstat(uploadAddress);
            //ChannelSftp.OVERWRITE 覆盖上传
            sftp.put(localFile,uploadAddress,ChannelSftp.OVERWRITE);
            return null;
        }
        return "找不到需要部署的文件。";

    }

    /**
     * docker部署
     * @param matFlowProcess 配置信息
     * @param matFlowExecHistoryList 状态集合
     */
    private void docker(Session session, MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList) throws JSchException, IOException {
        MatFlowDeploy matFlowDeploy = matFlowProcess.getMatFlowDeploy();

        //选择自定义部署
        if (matFlowDeploy.getDeployType() == 1){
            sshOrder(session, matFlowDeploy.getStartShell(), matFlowProcess, matFlowExecHistoryList);
            return;
        }

        String matFlowName = matFlowProcess.getMatFlowConfigure().getMatFlow().getMatflowName();
        //部署位置
        String deployAddress = "/"+  matFlowDeploy.getDeployAddress();
        //部署文件命令
        String  deployOrder= matFlowDeploy.getDeployOrder();
        //启动文件地址
        String startAddress = matFlowDeploy.getStartAddress();


        String order = "docker stop $(docker ps -a | grep '"+matFlowName+"' | awk '{print $1 }');"
                +"docker rm $(docker ps -a | grep '"+matFlowName+"' | awk '{print $1 }');"
                +"docker image rm"+" "+matFlowName+";";

        if ((deployOrder == null || deployOrder.equals("")) && (startAddress == null || startAddress.equals("/")) ){

             order = order +"cd"+" "+deployAddress+";"+"docker image build -t"+" "+matFlowName+"  .;"
                    +"docker run -itd -p"+" "+ matFlowDeploy.getMappingPort()+":"+ matFlowDeploy.getStartPort()+" "+matFlowName;
            sshOrder(session,order, matFlowProcess, matFlowExecHistoryList);
            return;
        }
        if (deployOrder != null && !deployOrder.equals("") ) {
            deployOrder = "cd "+" "+ deployAddress +";"+deployOrder;
            sshOrder(session,deployOrder, matFlowProcess, matFlowExecHistoryList);
            if (startAddress == null || startAddress.equals("/")) {

                order = order + "cd" + " " + deployAddress + ";" + "docker image build -t" + " " + matFlowName + "  .;"
                        + "docker run -itd -p" + " " + matFlowDeploy.getMappingPort() + ":" + matFlowDeploy.getStartPort() + " " + matFlowName;
                sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
                return;
            }
        }

        order = order +"cd"+" "+deployAddress+"/"+startAddress+";"+"docker image build -t"+" "+matFlowName+"  .;"
                +"docker run -itd -p"+" "+ matFlowDeploy.getMappingPort()+":"+ matFlowDeploy.getStartPort()+" "+matFlowName;
        sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
    }

}
