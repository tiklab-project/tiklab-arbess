package com.doublekit.pipeline.instance.service.execAchieve;

import ch.ethz.ssh2.Connection;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineDeploy;
import com.doublekit.pipeline.execute.service.PipelineDeployService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;


@Service
@Exporter
public class DeployAchieve {

    @Autowired
    PipelineDeployService pipelineDeployService;

    @Autowired
    CommonAchieve commonAchieve;

    @Autowired
    ProofService proofService;

    public int deployStart(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory , List<PipelineExecHistory> pipelineExecHistoryList){
        return switch (pipelineConfigure.getTaskType()) {
            case 31 -> linux(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
            case 32 -> docker(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
            default -> 1;
        };
    }
    /**
     * 部署到liunx
     * @param pipelineConfigure 配置信息
     * @param pipelineExecHistory 日志
     * @return 状态
     */
    private int linux(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory ,List<PipelineExecHistory> pipelineExecHistoryList) {
        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        PipelineExecLog pipelineExecLog = commonAchieve.initializeLog(pipelineExecHistory, pipelineConfigure);
        PipelineDeploy pipelineDeploy = pipelineDeployService.findOneDeploy(pipelineConfigure.getTaskId());
        String deployTargetAddress = pipelineDeploy.getDeployTargetAddress();

        Proof proof = pipelineDeploy.getProof();
        if (proof == null){
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"凭证为空。",pipelineExecHistoryList);
            return 0;
        }

        //文件地址
        String path = "D:\\clone\\" + pipelineConfigure.getPipeline().getPipelineName();
        //发送文件地址
        String filePath = commonAchieve.getFile(path,deployTargetAddress);
        if (filePath == null){
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"部署文件找不到。",pipelineExecHistoryList);
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
                    + "连接服务器  ： " +proof.getProofIp() + "\n"
                    + "连接类型  ： " +proof.getProofType();
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+log);
            JSch jsch = new JSch();
            //采用指定的端口连接服务器
            Session session =jsch.getSession(proof.getProofUsername(), proof.getProofIp() ,proof.getProofPort());
            if (session == null){
                throw new JSchException(proof.getProofIp() + "连接异常。。。。");
            }
            log = "服务器连接"+proof.getProofIp()+"成功";
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+log+"\n"+"开始发送文件:"+fileName);

            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+log+"\n"+"开始发送文件:"+fileName);

            //发送文件
            sshSftp(session,jsch,proof,deployAddress,filePath);

            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"文件:"+fileName+"发送成功！");
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+"文件:"+fileName+"发送成功！");

            //执行shell
            String shell = pipelineDeploy.getDeployShell();
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            if (shell != null && !shell.equals("null")){
                int state = sshOrder(proof, pipelineDeploy.getDeployShell(), pipelineExecHistory, pipelineExecHistoryList, pipelineExecLog);
                if (state == 0){
                    commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"shell执行错误",pipelineExecHistoryList);
                }
            }
        } catch (JSchException | SftpException | IOException e) {
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,e.toString(),pipelineExecHistoryList);
            return 0;
        }
        //更新状态
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return 1;
    }


    /**
     * docker部署
     * @param pipelineConfigure 配置信息
     * @param pipelineExecHistory 日志
     * @return 部署状态
     */
    private int docker(PipelineConfigure pipelineConfigure,PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList) {
        //开始运行时间
        PipelineDeploy pipelineDeploy = pipelineDeployService.findOneDeploy(pipelineConfigure.getTaskId());
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();
        Proof proof = pipelineDeploy.getProof();

        PipelineExecLog pipelineExecLog = commonAchieve.initializeLog(pipelineExecHistory, pipelineConfigure);

        String path = "D:\\clone\\" + pipeline.getPipelineName();
        //文件地址
        String filePath = commonAchieve.getFile(path,pipelineDeploy.getDeployTargetAddress());
        //文件名
        String fileName = new File(filePath).getName();
        //部署文件路径
        String deployAddress = pipelineDeploy.getDeployAddress() + "/" +fileName;

        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"压缩包文件名为： "+fileName+"\n"+"\n"+"部署到docker地址 ： " +deployAddress);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"发送文件中。。。。。");
        pipelineExecLog.setRunLog("压缩包文件名为： "+fileName+"\n"+"解压文件名称："+fileName+"\n"+"部署到docker文件地址 ： " +deployAddress);
        try {

            JSch jsch = new JSch();
            //采用指定的端口连接服务器
            Session session =jsch.getSession(proof.getProofUsername(), proof.getProofIp() ,proof.getProofPort());
            if (session == null){
                commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"无法连接服务器。",pipelineExecHistoryList);
                return 0;
            }
            sshSftp(session,jsch,proof,filePath,filePath);
            HashMap<Integer, String> map = new HashMap<>();
            map.put(1,"rm -rf "+" "+deployAddress);
            map.put(2,"unzip"+" "+deployAddress);
            map.put(3,"docker stop $(docker ps -a | grep '"+pipeline.getPipelineName()+"' | awk '{print $1 }')");
            map.put(4,"docker rm $(docker ps -a | grep '"+pipeline.getPipelineName()+"' | awk '{print $1 }')");
            map.put(5,"docker image rm"+" "+pipeline.getPipelineName());
            map.put(6,"find"+" "+deployAddress+" "+ "-name '*.sh' | xargs dos2unix");
            map.put(7,"cd"+" "+fileName+";"+"docker image build -t"+" "+pipeline.getPipelineName()+"  .");
            map.put(8,"docker run -itd -p"+" "+pipelineDeploy.getMappingPort()+":"+pipelineDeploy.getDockerPort()+" "+pipeline.getPipelineName());
            for (int i = 1; i <= map.size(); i++) {
                pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"第"+i+"步 ："+ map.get(i));
                pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n第"+i+"步 ："+ map.get(i));
                sshOrder(proof, map.get(i), pipelineExecHistory,pipelineExecHistoryList,pipelineExecLog);
            }
        } catch (JSchException | SftpException |IOException   e) {
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            return  0;
        }
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return 1;
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

        if (proof.getProofType().equals("password") && proof.getProofScope()==31){
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
     * 判断服务器是否可以连接
     * @param proofId 凭证id
     * @return 连接状态
     */

    public Boolean testSshSftp(String proofId){
        Proof proof = proofService.findOneProof(proofId);

        JSch jsch = new JSch();
        //采用指定的端口连接服务器
        Session session;
        try {
            session = jsch.getSession(proof.getProofUsername(), proof.getProofIp() ,proof.getProofPort());
        } catch (JSchException e) {
            return false;
        }

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            return false;
        }
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");

        if (proof.getProofType().equals("password") && proof.getProofScope()==2){
            //设置登陆主机的密码
            session.setPassword(proof.getProofPassword());
        }else {
            //添加私钥
            try {
                jsch.addIdentity(proof.getProofPassword());
                //设置登陆超时时间 10s
                session.connect(10000);
            } catch (JSchException e) {
                return false;
            }
        }
        session.disconnect();
        return true;
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
     * @param proof 凭证信息
     * @param order 执行命令
     * @param pipelineExecHistory 日志信息
     * @return 执行状态
     * @throws IOException 日志读写异常
     */
    public int sshOrder(Proof proof, String order, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList,PipelineExecLog pipelineExecLog) throws IOException {
        Connection conn = new Connection(proof.getProofIp(),proof.getProofPort());
        conn.connect();

        if (proof.getProofType().equals("password") && proof.getProofScope()==31){
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
        int state = commonAchieve.log(inputStreamReader, pipelineExecHistory,pipelineExecHistoryList,pipelineExecLog);
        session.close();
        inputStreamReader.close();
        return state;
    }


}
