package com.doublekit.pipeline.instance.service.execAchieveImpl;

import ch.ethz.ssh2.Connection;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineDeploy;
import com.doublekit.pipeline.execute.service.PipelineDeployService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineProcess;
import com.doublekit.pipeline.instance.service.execAchieveService.DeployAchieveService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.List;


@Service
@Exporter
public class DeployAchieveServiceImpl implements DeployAchieveService {

    @Autowired
    PipelineDeployService pipelineDeployService;

    @Autowired
    CommonAchieveServiceImpl commonAchieveServiceImpl;


    public void deployStart(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList) throws IOException {
        PipelineDeploy pipelineDeploy = pipelineProcess.getPipelineDeploy();
        switch (pipelineDeploy.getType()) {
            case 31 -> linux(pipelineProcess, pipelineExecHistoryList);
            case 32 -> docker(pipelineProcess, pipelineExecHistoryList);
        }
    }

    /**
     * 部署
     * @param pipelineProcess 配置信息
     * @return 状态
     */
    public int deploy(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList) {
        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();

        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        PipelineConfigure pipelineConfigure = pipelineProcess.getPipelineConfigure();

        PipelineExecLog pipelineExecLog = commonAchieveServiceImpl.initializeLog(pipelineExecHistory, pipelineConfigure);
        PipelineDeploy pipelineDeploy = pipelineDeployService.findOneDeploy(pipelineConfigure.getTaskId());
        Proof proof = pipelineDeploy.getProof();

        pipelineProcess.setPipelineExecLog(pipelineExecLog);
        pipelineProcess.setProof(proof);
        pipelineProcess.setPipelineDeploy(pipelineDeploy);

        if (proof == null){
            commonAchieveServiceImpl.updateTime(pipelineProcess,beginTime);
            commonAchieveServiceImpl.updateState(pipelineProcess,"凭证为空。",pipelineExecHistoryList);
            return 0;
        }

        //文件地址
        String path = "D:\\clone\\" + pipelineConfigure.getPipeline().getPipelineName();
        //发送文件地址
        String deployTargetAddress = pipelineDeploy.getDeployTargetAddress();
        String filePath = commonAchieveServiceImpl.getFile(path,deployTargetAddress);
        if (filePath == null){
            commonAchieveServiceImpl.updateState(pipelineProcess,"部署文件找不到。",pipelineExecHistoryList);
            return 0;
        }
        //文件名
        String fileName = new File(filePath).getName();
        //发送文件位置
        String deployAddress = pipelineDeploy.getDeployAddress()+"/"+fileName;

        try {
            String log = "------------------------------ "+ "\n"
                    + "开始部署" + "\n"
                    + "匹配到一个文件 ： " +fileName + "\n"
                    + "文件地址 ： "+filePath +"\n"
                    + "发送服务器位置 ： "+deployAddress +"\n"
                    + "连接服务器  ： " +pipelineDeploy.getIp() + "\n"
                    + "连接类型  ： " +proof.getProofType();
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+log);
            JSch jsch = new JSch();
            //采用指定的端口连接服务器
            Session session =jsch.getSession(proof.getProofUsername(), pipelineDeploy.getIp() ,pipelineDeploy.getPort());
            if (session == null){
                throw new JSchException(pipelineDeploy.getIp() + "连接异常。。。。");
            }
            log = "服务器连接"+pipelineDeploy.getIp()+"成功";
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+log+"\n"+"开始发送文件:"+fileName);
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+log+"\n"+"开始发送文件:"+fileName);

            //发送文件
            sshSftp(session,jsch,proof,deployAddress,filePath);

            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"文件:"+fileName+"发送成功！");
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+"文件:"+fileName+"发送成功！");

            deployStart(pipelineProcess,pipelineExecHistoryList);

            commonAchieveServiceImpl.updateTime(pipelineProcess,beginTime);
            } catch (JSchException | SftpException | IOException e) {
                commonAchieveServiceImpl.updateState(pipelineProcess,e.toString(),pipelineExecHistoryList);
                return 0;
            }
        //更新状态
        commonAchieveServiceImpl.updateState(pipelineProcess,null,pipelineExecHistoryList);
        return 1;
    }

    /**
     * linux部署
     * @param pipelineProcess 配置信息
     * @param pipelineExecHistoryList 状态集合
     */
    private void linux(PipelineProcess pipelineProcess,List<PipelineExecHistory> pipelineExecHistoryList) throws IOException {
        PipelineDeploy pipelineDeploy = pipelineProcess.getPipelineDeploy();
        String shell = pipelineDeploy.getDeployShell();
        if (shell != null){
            sshOrder( pipelineDeploy.getDeployShell(), pipelineExecHistoryList, pipelineProcess);
        }
    }
    /**
     * docker部署
     * @param pipelineProcess 配置信息
     * @param pipelineExecHistoryList 状态集合
     */
    private void docker(PipelineProcess pipelineProcess,List<PipelineExecHistory> pipelineExecHistoryList) throws IOException {
        PipelineDeploy pipelineDeploy = pipelineProcess.getPipelineDeploy();
        Pipeline pipeline = pipelineProcess.getPipelineConfigure().getPipeline();
        String pipelineName = pipeline.getPipelineName();
        String fileName = commonAchieveServiceImpl.getFile("D:\\clone\\" + pipelineName, pipelineDeploy.getDeployTargetAddress());
        //服务器部署位置
        String deployAddress = pipelineDeploy.getDeployAddress();
        String order = "rm -rf "+" "+deployAddress +";"
                + "unzip"+" "+deployAddress +";"
                +"docker stop $(docker ps -a | grep '"+pipelineName+"' | awk '{print $1 }');"
                +"docker rm $(docker ps -a | grep '"+pipelineName+"' | awk '{print $1 }');"
                +"docker image rm"+" "+pipelineName+";"
                +"find"+" "+deployAddress+" "+ "-name '*.sh' | xargs dos2unix;"
                +"cd"+" "+fileName+";"+"docker image build -t"+" "+pipelineName+"  .;"
                +"docker run -itd -p"+" "+pipelineDeploy.getMappingPort()+":"+pipelineDeploy.getDockerPort()+" "+pipelineName;
        sshOrder(order,pipelineExecHistoryList,pipelineProcess);
    }

    /**
     * ssh 连接发送文件
     * @param proof 凭证信息
     * @param remotePath 部署文件地址
     * @param localPath 本机文件地址
     */
    public void sshSftp(Session session,JSch jsch,Proof proof, String remotePath, String localPath) throws JSchException, SftpException, IOException {

        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");

        if (proof.getProofType().equals("password")){
            //设置登陆主机的密码
            session.setPassword(proof.getProofPassword());
        }else {
            //添加私钥
            jsch.addIdentity(proof.getProofPassword());
        }
        //设置登陆超时时间 10s
        session.connect(10000);
        //调用发送方法
        sshSending(session,remotePath,localPath);
        session.disconnect();
    }

    /**
     * 发送文件
     * @param session 连接
     * @param remotePath 部署文件地址
     * @param localPath 本机文件地址
     */
    public void sshSending(Session session,String remotePath, String localPath) throws JSchException, IOException, SftpException {
        //创建sftp通信通道
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        //ChannelSftp sftp = channel;

        //发送
        InputStream inputStream =  new FileInputStream(localPath);
        OutputStream outputStream  = channel.put(remotePath);

        byte[] b = new byte[1024];
        int n;
        while ((n = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, n);
        }
        //关闭流
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /**
     *  执行ssh命令
     * @param order 执行命令
     * @throws IOException 日志读写异常
     */
    public void sshOrder(String order, List<PipelineExecHistory> pipelineExecHistoryList,PipelineProcess pipelineProcess) throws IOException {
        PipelineDeploy pipelineDeploy = pipelineProcess.getPipelineDeploy();
        Proof proof = pipelineProcess.getProof();
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        Connection conn = new Connection(pipelineDeploy.getIp(),pipelineDeploy.getPort());
        conn.connect();

        if (proof.getProofType().equals("password")){
            //设置登陆主机的密码
            conn.authenticateWithPassword(proof.getProofUsername(), proof.getProofPassword());
        }else {
            //添加私钥
            conn.authenticateWithPublicKey(proof.getProofUsername(),new File(proof.getProofPassword()),null);
        }
        ch.ethz.ssh2.Session session = conn.openSession();
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog() + "\n" + order+ "\n");
        session.execCommand(order);
        InputStreamReader inputStreamReader = new InputStreamReader(session.getStdout(), Charset.forName("GBK"));
        commonAchieveServiceImpl.log(inputStreamReader,pipelineProcess,pipelineExecHistoryList);
        session.close();
        inputStreamReader.close();
    }


}
