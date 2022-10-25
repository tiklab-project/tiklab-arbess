package net.tiklab.matflow.execute.service.execAchieveImpl;

import com.jcraft.jsch.*;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.execute.model.PipelineExecLog;
import net.tiklab.matflow.execute.service.execAchieveService.ConfigCommonService;
import net.tiklab.matflow.orther.service.PipelineFileService;
import net.tiklab.matflow.definition.model.PipelineDeploy;
import net.tiklab.matflow.execute.model.PipelineExecHistory;
import net.tiklab.matflow.execute.service.PipelineExecServiceImpl;
import net.tiklab.matflow.execute.service.execAchieveService.DeployAchieveService;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
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
    ConfigCommonService commonService;

    private static final Logger logger = LoggerFactory.getLogger(DeployAchieveServiceImpl.class);

    /**
     * 部署
     * @param pipelineProcess 配置信息
     * @return 状态
     */
    public boolean deploy(PipelineProcess pipelineProcess, PipelineDeploy pipelineDeploy) {
        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineProcess.getPipeline();
        List<PipelineExecHistory> list = PipelineExecServiceImpl.pipelineExecHistoryList;
        pipelineProcess.setBeginTime(beginTime);
        Proof proof = pipelineDeploy.getProof();
        pipelineProcess.setProof(proof);

        String startShell = pipelineDeploy.getStartShell();

        //执行自定义脚本
        try {
            if (pipelineDeploy.getDeployType() == 1) {
                commonService.execHistory(pipelineProcess,"执行脚本：" + startShell);
                Process process = PipelineUntil.process("/", startShell);
                commonService.log(process.getInputStream(), pipelineProcess);
                commonService.updateState(pipelineProcess,true);
                return true;
            }
        } catch (IOException e) {
            commonService.updateState(pipelineProcess,false);
            commonService.execHistory(pipelineProcess, "命令执行错误");
            return false;
        }

        if (proof == null){
            commonService.execHistory(pipelineProcess,"凭证为空。");
            commonService.updateState(pipelineProcess,false);
            return false;
        }

        commonService.execHistory(pipelineProcess,"获取部署文件......" + startShell);

        //获取部署文件
        String filePath = PipelineUntil.getFile(pipeline.getPipelineName(), pipelineDeploy.getSourceAddress());
        if (filePath == null){
            commonService.execHistory(pipelineProcess,"部署文件找不到。");
            commonService.updateState(pipelineProcess,false);
            return false;
        }

        commonService.execHistory(pipelineProcess,"部署文件获取成功："+ filePath );

        //文件名
        String fileName = new File(filePath).getName();

        //发送文件位置
        String deployAddress ="/"+ pipelineDeploy.getDeployAddress();

        commonService.execHistory(pipelineProcess,"建立服务器连接：" );

        Session session;
        try {
            session = createSession(pipelineDeploy);
        } catch (JSchException e) {
            commonService.updateState(pipelineProcess,false);
            commonService.execHistory(pipelineProcess,"连接失败，无法连接到服务器");
            return false;
        }

        commonService.execHistory(pipelineProcess,"连接建立成功\n上传部署文件："+ fileName );

        try {
         ftp(session, filePath, deployAddress);
        } catch (JSchException | SftpException e) {
            session.disconnect();
            commonService.updateState(pipelineProcess,false);
            commonService.execHistory(pipelineProcess,"部署文件上传失败");
            return false;
        }

        commonService.execHistory(pipelineProcess,"部署文件上传成功\n执行部署命令：" );

        try {
            linux(session, pipelineProcess,pipelineDeploy);
        } catch (JSchException | IOException e) {
            session.disconnect();
            commonService.updateState(pipelineProcess,false);
            commonService.execHistory(pipelineProcess,"命令执行失败");
            return false;
        }
        session.disconnect();
        commonService.updateState(pipelineProcess,true);
        return true;
    }

    /**
     * linux部署
     * @param pipelineProcess 配置信息
     */
    private void linux(Session session, PipelineProcess pipelineProcess,PipelineDeploy pipelineDeploy) throws JSchException, IOException {
        //部署地址
        String deployAddress = "/"+ pipelineDeploy.getDeployAddress();
        //部署脚本命令
        String  startOrder= pipelineDeploy.getDeployOrder();
        //启动文件地址
        String startAddress = pipelineDeploy.getStartAddress();
        //启动脚本命令
        String startShell = pipelineDeploy.getStartShell();

        if (PipelineUntil.isNoNull(startOrder)){
            //部署文件命令启动文件地址都为null的时候
            String order = "cd "+" "+ deployAddress +";"+ startOrder;
            sshOrder(session,order, pipelineProcess);
        }

        if (PipelineUntil.isNoNull(startShell)){
            String order = "cd "+" "+ startAddress +";"+ startShell;
            sshOrder(session,order, pipelineProcess);
        }


        //if ((startOrder == null || startOrder.equals("")) && (startAddress == null || startAddress.equals("")) ){
        //    if (pipelineDeploy.getStartShell() == null || pipelineDeploy.getStartShell().equals("") ){
        //        return;
        //    }
        //    sshOrder(session,order, pipelineProcess);
        //    return;
        //}


        //String orders = "cd "+" "+ deployAddress + "/" + startAddress+";" + pipelineDeploy.getStartShell();
        //if (startAddress != null && !startAddress.equals("")){
        //    if (startOrder == null ||startOrder.equals("")){
        //        sshOrder(session,orders, pipelineProcess);
        //        return;
        //    }
        //    startOrder = "cd "+" "+ deployAddress +";"+startOrder;
        //    sshOrder( session,startOrder, pipelineProcess);
        //    sshOrder(session,orders, pipelineProcess);
        //}else {
        //    sshOrder(session,order, pipelineProcess);
        //}
    }


    /**
     * 创建连接实例
     * @param pipelineDeploy 连接配置信息
     * @return 实例
     * @throws JSchException 连接失败
     */
    private Session createSession(PipelineDeploy pipelineDeploy) throws JSchException {

        String sshIp = pipelineDeploy.getSshIp();
        int sshPort = pipelineDeploy.getSshPort();

        Proof proof = pipelineDeploy.getProof();
        String proofUsername = proof.getProofUsername();
        String proofPassword = proof.getProofPassword();

        JSch jsch = new JSch();

        Session session = jsch.getSession(proofUsername, sshIp, sshPort);
        session.setPassword(proofPassword);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    /**
     * 连接服务器执行命令
     * @param orders 命令
     * @param pipelineProcess 配置信息
     * @throws JSchException 连接错误
     * @throws IOException 读取执行信息失败
     */
    private void sshOrder(Session session,String orders,PipelineProcess pipelineProcess) throws JSchException, IOException {
        ChannelExec exec = (ChannelExec) session.openChannel("exec");
        commonService.execHistory(pipelineProcess,"执行："+orders );
        exec.setCommand(orders);
        exec.connect();
        InputStream inputStream = exec.getInputStream();
        commonService.log(inputStream, pipelineProcess);
        exec.disconnect();

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
    private void ftp(Session session,String localFile,String uploadAddress) throws JSchException, SftpException {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();
        //判断目录是否存在
        sftp.lstat(uploadAddress);
        //ChannelSftp.OVERWRITE 覆盖上传
        sftp.put(localFile,uploadAddress,ChannelSftp.OVERWRITE);
        sftp.disconnect();
    }

    /**
     * docker部署
     * @param pipelineProcess 配置信息
     */
    private void docker(Session session, PipelineProcess pipelineProcess,PipelineDeploy pipelineDeploy) throws JSchException, IOException {
        Pipeline pipeline = pipelineProcess.getPipeline();

        //选择自定义部署
        if (pipelineDeploy.getDeployType() == 1){
            sshOrder(session, pipelineDeploy.getStartShell(), pipelineProcess);
            return;
        }

        String pipelineName = pipeline.getPipelineName();
        //部署位置
        String deployAddress = "/"+  pipelineDeploy.getDeployAddress();
        //部署文件命令
        String  deployOrder= pipelineDeploy.getDeployOrder();
        //启动文件地址
        String startAddress = pipelineDeploy.getStartAddress();


        String order = "docker stop $(docker ps -a | grep '"+pipelineName+"' | awk '{print $1 }');"
                +"docker rm $(docker ps -a | grep '"+pipelineName+"' | awk '{print $1 }');"
                +"docker image rm"+" "+pipelineName+";";

        if ((deployOrder == null || deployOrder.equals("")) && (startAddress == null || startAddress.equals("/")) ){

             order = order +"cd"+" "+deployAddress+";"+"docker image build -t"+" "+pipelineName+"  .;"
                    +"docker run -itd -p"+" "+ pipelineDeploy.getMappingPort()+":"+ pipelineDeploy.getStartPort()+" "+pipelineName;
            sshOrder(session,order, pipelineProcess);
            return;
        }
        if (deployOrder != null && !deployOrder.equals("") ) {
            deployOrder = "cd "+" "+ deployAddress +";"+deployOrder;
            sshOrder(session,deployOrder, pipelineProcess);
            if (startAddress == null || startAddress.equals("/")) {

                order = order + "cd" + " " + deployAddress + ";" + "docker image build -t" + " " + pipelineName + "  .;"
                        + "docker run -itd -p" + " " + pipelineDeploy.getMappingPort() + ":" + pipelineDeploy.getStartPort() + " " + pipelineName;
                sshOrder(session, order, pipelineProcess);
                return;
            }
        }

        order = order +"cd"+" "+deployAddress+"/"+startAddress+";"+"docker image build -t"+" "+pipelineName+"  .;"
                +"docker run -itd -p"+" "+ pipelineDeploy.getMappingPort()+":"+ pipelineDeploy.getStartPort()+" "+pipelineName;
        sshOrder(session, order, pipelineProcess);
    }

}
