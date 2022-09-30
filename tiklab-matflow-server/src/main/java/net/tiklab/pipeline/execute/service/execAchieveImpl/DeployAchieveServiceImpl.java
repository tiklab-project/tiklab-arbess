package net.tiklab.pipeline.execute.service.execAchieveImpl;

import com.jcraft.jsch.*;
import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.definition.model.PipelineConfig;
import net.tiklab.pipeline.execute.service.execAchieveService.ConfigCommonService;
import net.tiklab.pipeline.orther.service.PipelineFileService;
import net.tiklab.pipeline.definition.model.PipelineDeploy;
import net.tiklab.pipeline.execute.model.PipelineExecHistory;
import net.tiklab.pipeline.execute.model.PipelineExecLog;
import net.tiklab.pipeline.execute.service.PipelineExecServiceImpl;
import net.tiklab.pipeline.execute.service.execAchieveService.DeployAchieveService;
import net.tiklab.pipeline.orther.model.PipelineProcess;
import net.tiklab.pipeline.setting.model.Proof;
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
    ConfigCommonService configCommonService;

    @Autowired
    PipelineFileService pipelineFileService;

    private static final Logger logger = LoggerFactory.getLogger(DeployAchieveServiceImpl.class);

    /**
     * 部署
     * @param pipelineProcess 配置信息
     * @return 状态
     */
    public String deploy(PipelineProcess pipelineProcess, PipelineConfig pipelineConfig) {
        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        List<PipelineExecHistory> pipelineExecHistoryList = PipelineExecServiceImpl.pipelineExecHistoryList;
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();

        PipelineDeploy pipelineDeploy = pipelineConfig.getPipelineDeploy();
        Pipeline pipeline = pipelineDeploy.getPipeline();

        PipelineExecLog pipelineExecLog = configCommonService.initializeLog(pipelineExecHistory, pipelineDeploy,40);


        Proof proof = pipelineDeploy.getProof();
        pipelineProcess.setPipelineExecLog(pipelineExecLog);
        pipelineProcess.setProof(proof);


        if (proof == null){
            configCommonService.updateTime(pipelineProcess,beginTime);
            configCommonService.updateState(pipelineProcess,false, pipelineExecHistoryList);
            return "凭证为空。";
        }

        //获取部署文件
        String filePath = pipelineFileService.getFile(pipeline.getPipelineName(), pipelineDeploy.getSourceAddress());
        if (filePath == null){
            configCommonService.updateTime(pipelineProcess,beginTime);
            configCommonService.updateState(pipelineProcess,true, pipelineExecHistoryList);
            return "部署文件找不到。";
        }

        //文件名
        String fileName = new File(filePath).getName();

        //发送文件位置
        String deployAddress ="/"+ pipelineDeploy.getDeployAddress();

        Session session;
        try {
            session = createSession(pipelineDeploy);
        } catch (JSchException e) {
            configCommonService.updateTime(pipelineProcess,beginTime);
            configCommonService.updateState(pipelineProcess,true, pipelineExecHistoryList);
            return "连接失败，无法连接到服务器";
        }

        //ftp(session,deployAddress, )



        return null;
    }

    /**
     * linux部署
     * @param pipelineProcess 配置信息
     * @param pipelineExecHistoryList 状态集合
     */
    private void linux(Session session, PipelineProcess pipelineProcess,PipelineConfig pipelineConfig, List<PipelineExecHistory> pipelineExecHistoryList) throws JSchException, IOException {
        PipelineDeploy pipelineDeploy = pipelineConfig.getPipelineDeploy();

        //选择自定义部署
        if (pipelineDeploy.getDeployType() == 1){
           sshOrder(session,pipelineDeploy.getStartShell(),pipelineProcess,pipelineExecHistoryList);
           return;
        }

        //部署地址
        String deployAddress = "/"+ pipelineDeploy.getDeployAddress();

        //部署文件命令
        String  startOrder= pipelineDeploy.getDeployOrder();
        //启动文件地址
        String startAddress = pipelineDeploy.getStartAddress();

        //部署文件命令启动文件地址都为null的时候
        String order = "cd "+" "+ deployAddress +";"+ pipelineDeploy.getStartShell();
        if ((startOrder == null || startOrder.equals("")) && (startAddress == null || startAddress.equals("")) ){
            if (pipelineDeploy.getStartShell() == null || pipelineDeploy.getStartShell().equals("") ){
                return;
            }
            sshOrder(session,order, pipelineProcess, pipelineExecHistoryList);
            return;
        }
        String orders = "cd "+" "+ deployAddress + "/" + startAddress+";" + pipelineDeploy.getStartShell();
        if (startAddress != null && !startAddress.equals("")){
            if (startOrder == null ||startOrder.equals("")){
                sshOrder(session,orders, pipelineProcess, pipelineExecHistoryList);
                return;
            }
            startOrder = "cd "+" "+ deployAddress +";"+startOrder;
            sshOrder( session,startOrder, pipelineProcess, pipelineExecHistoryList);
            sshOrder(session,orders, pipelineProcess, pipelineExecHistoryList);
        }else {
            sshOrder(session,order, pipelineProcess, pipelineExecHistoryList);
        }
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

        Session session = jsch.getSession(proofPassword, sshIp, sshPort);
        session.setPassword(proofUsername);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        return session;
    }

    /**
     * 连接服务器执行命令
     * @param orders 命令
     * @param pipelineProcess 配置信息
     * @param pipelineExecHistoryList 执行历史
     * @throws JSchException 连接错误
     * @throws IOException 读取执行信息失败
     */
    private void sshOrder(Session session,String orders,PipelineProcess pipelineProcess,List<PipelineExecHistory> pipelineExecHistoryList) throws JSchException, IOException {
        ChannelExec exec = (ChannelExec) session.openChannel("exec");
        exec.setCommand(orders);
        configCommonService.log(exec.getInputStream(), pipelineProcess, pipelineExecHistoryList);
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
     * @param pipelineProcess 配置信息
     * @param pipelineExecHistoryList 状态集合
     */
    private void docker(Session session, PipelineProcess pipelineProcess,PipelineDeploy pipelineDeploy, List<PipelineExecHistory> pipelineExecHistoryList) throws JSchException, IOException {
        Pipeline pipeline = pipelineDeploy.getPipeline();

        //选择自定义部署
        if (pipelineDeploy.getDeployType() == 1){
            sshOrder(session, pipelineDeploy.getStartShell(), pipelineProcess, pipelineExecHistoryList);
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
            sshOrder(session,order, pipelineProcess, pipelineExecHistoryList);
            return;
        }
        if (deployOrder != null && !deployOrder.equals("") ) {
            deployOrder = "cd "+" "+ deployAddress +";"+deployOrder;
            sshOrder(session,deployOrder, pipelineProcess, pipelineExecHistoryList);
            if (startAddress == null || startAddress.equals("/")) {

                order = order + "cd" + " " + deployAddress + ";" + "docker image build -t" + " " + pipelineName + "  .;"
                        + "docker run -itd -p" + " " + pipelineDeploy.getMappingPort() + ":" + pipelineDeploy.getStartPort() + " " + pipelineName;
                sshOrder(session, order, pipelineProcess, pipelineExecHistoryList);
                return;
            }
        }

        order = order +"cd"+" "+deployAddress+"/"+startAddress+";"+"docker image build -t"+" "+pipelineName+"  .;"
                +"docker run -itd -p"+" "+ pipelineDeploy.getMappingPort()+":"+ pipelineDeploy.getStartPort()+" "+pipelineName;
        sshOrder(session, order, pipelineProcess, pipelineExecHistoryList);
    }

}
