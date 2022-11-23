package net.tiklab.matflow.execute.service;

import com.jcraft.jsch.*;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineDeploy;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuthHost;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

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
        Pipeline pipeline = pipelineProcess.getPipeline();
        String startShell = pipelineDeploy.getStartOrder();

        //执行自定义脚本
        try {
            if (pipelineDeploy.getAuthType() == 2) {
                commonService.execHistory(pipelineProcess,"执行脚本：" + startShell);
                Process process = PipelineUntil.process("/", startShell);

                pipelineProcess.setInputStream(process.getInputStream());
                pipelineProcess.setErrInputStream(process.getErrorStream());
                pipelineProcess.setEnCode("GBK");
                pipelineProcess.setError(error(pipelineDeploy.getType()));

                commonService.log(pipelineProcess);
                return true;
            }
        } catch (IOException e) {
            commonService.execHistory(pipelineProcess, "命令执行错误");
            return false;
        }


        commonService.execHistory(pipelineProcess,"获取部署文件......");

        //获取部署文件
        String filePath = PipelineUntil.getFile(pipeline.getPipelineName(), pipelineDeploy.getLocalAddress());
        if (filePath == null){
            commonService.execHistory(pipelineProcess,"无法匹配到部署文件。");
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
            commonService.execHistory(pipelineProcess,"连接失败，无法连接到服务器\n"+e.getMessage());
            return false;
        }

        commonService.execHistory(pipelineProcess,"连接建立成功\n上传部署文件："+ fileName );

        try {
         ftp(session, filePath, deployAddress);
        } catch (JSchException | SftpException e) {
            session.disconnect();
            commonService.execHistory(pipelineProcess,"部署文件上传失败");
            return false;
        }

        commonService.execHistory(pipelineProcess,"部署文件上传成功" );

        try {
            linux(session, pipelineProcess,pipelineDeploy);
        } catch (JSchException | IOException e) {
            session.disconnect();
            commonService.execHistory(pipelineProcess,"命令执行失败");
            return false;
        }
        session.disconnect();
        return true;
    }

    /**
     * linux部署
     * @param pipelineProcess 配置信息
     */
    private void linux(Session session, PipelineProcess pipelineProcess,PipelineDeploy pipelineDeploy) throws JSchException, IOException {
        //部署地址
        String deployAddress = "/"+ pipelineDeploy.getDeployAddress();

        //启动文件地址
        String startAddress = "/"+ pipelineDeploy.getStartAddress();

        //部署脚本命令
        String deployOrder= pipelineDeploy.getDeployOrder();

        //启动脚本命令
        String startOrder = pipelineDeploy.getStartOrder();

        //部署命令和启动命令都为空
        if (!PipelineUntil.isNoNull(startOrder) && !PipelineUntil.isNoNull(deployOrder)){
           return;
        }

        if (PipelineUntil.isNoNull(deployOrder)){
            String orders = "cd "+" "+ deployAddress + ";" + deployOrder;
            commonService.execHistory(pipelineProcess,"执行部署命令：" + orders);
            sshOrder(session,orders, pipelineProcess);
        }

        if (PipelineUntil.isNoNull(startOrder)){
            String orders = "cd "+" "+ startAddress+";" + startOrder;
            commonService.execHistory(pipelineProcess,"执行启动命令：" + orders );
            sshOrder(session,orders, pipelineProcess);
        }

    }


    /**
     * 创建连接实例
     * @param pipelineDeploy 连接配置信息
     * @return 实例
     * @throws JSchException 连接失败
     */
    private Session createSession(PipelineDeploy pipelineDeploy) throws JSchException,ApplicationException {

        PipelineAuthHost authHost = (PipelineAuthHost) pipelineDeploy.getAuth();
        String username = authHost.getUsername();

        String sshIp = authHost.getIp();
        int sshPort = authHost.getPort();
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, sshIp, sshPort);
        if (authHost.getAuthType() ==2){
            String tempFile = PipelineUntil.createTempFile(authHost.getPrivateKey());
            if (!PipelineUntil.isNoNull(tempFile)){
                throw new ApplicationException("写入私钥失败。");
            }
            jsch.addIdentity(tempFile);
            PipelineUntil.deleteFile(new File(tempFile));
        }else {
            String password = authHost.getPassword();
            session.setPassword(password);
        }
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
        pipelineProcess.setInputStream(exec.getInputStream());
        pipelineProcess.setErrInputStream(exec.getErrStream());
        pipelineProcess.setEnCode("UTF-8");
        pipelineProcess.setError(error(41));
        commonService.log( pipelineProcess);
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

        // if ((deployOrder == null || deployOrder.equals("")) && (startAddress == null || startAddress.equals("/")) ){
        //
        //      order = order +"cd"+" "+deployAddress+";"+"docker image build -t"+" "+pipelineName+"  .;"
        //             +"docker run -itd -p"+" "+ pipelineDeploy.getMappingPort()+":"+ pipelineDeploy.getStartPort()+" "+pipelineName;
        //     sshOrder(session,order, pipelineProcess);
        //     return;
        // }
        // if (deployOrder != null && !deployOrder.equals("") ) {
        //     deployOrder = "cd "+" "+ deployAddress +";"+deployOrder;
        //     sshOrder(session,deployOrder, pipelineProcess);
        //     if (startAddress == null || startAddress.equals("/")) {
        //
        //         order = order + "cd" + " " + deployAddress + ";" + "docker image build -t" + " " + pipelineName + "  .;"
        //                 + "docker run -itd -p" + " " + pipelineDeploy.getMappingPort() + ":" + pipelineDeploy.getStartPort() + " " + pipelineName;
        //         sshOrder(session, order, pipelineProcess);
        //         return;
        //     }
        // }
        //
        // order = order +"cd"+" "+deployAddress+"/"+startAddress+";"+"docker image build -t"+" "+pipelineName+"  .;"
        //         +"docker run -itd -p"+" "+ pipelineDeploy.getMappingPort()+":"+ pipelineDeploy.getStartPort()+" "+pipelineName;
        sshOrder(session, order, pipelineProcess);
    }


    private String[] error(int type){
        String[] strings;
        if (type == 5){
            strings = new String[]{
                    "svn: E170000:",
                    "invalid option;"
            };
            return strings;
        }
        strings = new String[]{

        };
        return strings;
    }

}
