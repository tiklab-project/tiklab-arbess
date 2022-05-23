package com.doublekit.pipeline.instance.service.execAchieve;

import ch.ethz.ssh2.Connection;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineDeploy;
import com.doublekit.pipeline.execute.service.PipelineDeployService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Exporter
public class DeployAchieve {


    @Autowired
    PipelineExecLogService pipelineExecLogService;

    @Autowired
    PipelineDeployService pipelineDeployService;

    @Autowired
    CommonAchieve commonAchieve;

    public String deployStart(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory ,List<PipelineExecHistory> pipelineExecHistoryList){
        return switch (pipelineConfigure.getTaskType()) {
            case 31 -> linux(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
            case 32 -> docker(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
            default -> null;
        };
    }
    /**
     * 部署到liunx
     * @param pipelineConfigure 配置信息
     * @param pipelineExecHistory 日志
     * @return 状态
     */
    private String linux(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory ,List<PipelineExecHistory> pipelineExecHistoryList) {
        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();

        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        pipelineExecLog.setTaskAlias(pipelineConfigure.getTaskAlias());
        pipelineExecLog.setTaskSort(pipelineConfigure.getTaskSort());
        pipelineExecLog.setTaskType(pipelineConfigure.getTaskType());
        pipelineExecLog.setRunLog("");
        String logId = pipelineExecLogService.createLog(pipelineExecLog);
        pipelineExecLog.setPipelineLogId(logId);

        PipelineDeploy pipelineDeploy = pipelineDeployService.findOneDeploy(pipelineConfigure.getTaskId());
        String deployTargetAddress = pipelineDeploy.getDeployTargetAddress();
        String deployAddress = pipelineDeploy.getDeployAddress();
        pipelineExecHistoryList.add(pipelineExecHistory);
        pipelineExecLog.setTaskSort(pipelineConfigure.getTaskSort());
        pipelineExecLog.setTaskType(pipelineConfigure.getTaskType());
        Proof proof = pipelineDeploy.getProof();
        if (proof == null){
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            return "凭证为空";
        }
        String s = "部署到服务器" + proof.getProofIp() + "。。。。。。。。";
        pipelineExecLog.setRunLog(s);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+s);
        //文件地址
        String[] split = deployTargetAddress.split(" ");
        String path = "D:\\clone\\" + pipeline.getPipelineName()+"\\"+split[0]+"\\"+"target";

        //发送文件名
        String zipName = commonAchieve.zipName(path,split[1]);
        path  = path + "\\" +zipName ;

        //发送文件位置
        deployAddress = deployAddress +"/"+ zipName;
        try {
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"开始发送文件:"+path);
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+"开始发送文件:"+path);

            //发送文件
            sshSftp(proof,deployAddress,path);
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"文件:"+zipName+"发送成功！");
            pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+"文件:"+zipName+"发送成功！");

            //执行shell
            String shell = pipelineDeploy.getDeployShell();
            if (shell != null && !shell.equals("null")){
                Map<String, String> map = sshOrder(proof, pipelineDeploy.getDeployShell(), pipelineExecHistory, pipelineExecHistoryList, pipelineExecLog);
                if (map.get("state").equals("0")){
                    commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
                    commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"shell 命令执行错误",pipelineExecHistoryList);
                }
               pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+map.get("log"));
            }
        } catch (JSchException | SftpException | IOException e) {
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,e.toString(),pipelineExecHistoryList);
            return e.toString();
        }
        //更新状态
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return null;
    }


    /**
     * docker部署
     * @param pipelineConfigure 配置信息
     * @param pipelineExecHistory 日志
     * @return 部署状态
     */
    private String docker(PipelineConfigure pipelineConfigure,PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList) {
        //开始运行时间
        PipelineDeploy pipelineDeploy = pipelineDeployService.findOneDeploy(pipelineConfigure.getTaskId());
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineConfigure.getPipeline();
        Proof proof = pipelineDeploy.getProof();

        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        pipelineExecLog.setTaskAlias(pipelineConfigure.getTaskAlias());
        pipelineExecLog.setTaskSort(pipelineConfigure.getTaskSort());
        pipelineExecLog.setTaskType(pipelineConfigure.getTaskType());
        pipelineExecLog.setRunLog("");
        String logId = pipelineExecLogService.createLog(pipelineExecLog);
        pipelineExecLog.setPipelineLogId(logId);

        //模块名
        String[] split = pipelineDeploy.getDeployTargetAddress().split(" ");
        String path = "D:\\clone\\" + pipeline.getPipelineName()+"\\"+split[0]+"\\"+"target";
        //文件名称
        String zipName = commonAchieve.zipName(path,split[1]);
        //本机文件地址
        String fileAddress  = path + "\\" +zipName ;
        String  fileName = null;
        if (zipName != null) {
            String[] split1 = zipName.split("."+split[1]);
            String[] split2 = split1[0].split("-distribution");
            fileName = split2[0];
        }
        String liunxAddress = pipelineDeploy.getDeployAddress();
        //发送文件位置
        String deployAddress = liunxAddress+ "/" +zipName ;
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"压缩包文件名为： "+zipName+"\n"+"解压文件名称："+fileName+"\n"+"部署到docker地址 ： " +deployAddress);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"发送文件中。。。。。");
        pipelineExecLog.setRunLog("压缩包文件名为： "+zipName+"\n"+"解压文件名称："+fileName+"\n"+"部署到docker文件地址 ： " +deployAddress);
        try {
            sshSftp(proof,deployAddress,fileAddress);
            HashMap<Integer, String> map = new HashMap<>();
            map.put(1,"rm -rf "+" "+liunxAddress+ "/" +fileName);
            map.put(2,"unzip"+" "+deployAddress);
            map.put(3,"docker stop $(docker ps -a | grep '"+pipeline.getPipelineName()+"' | awk '{print $1 }')");
            map.put(4,"docker rm $(docker ps -a | grep '"+pipeline.getPipelineName()+"' | awk '{print $1 }')");
            map.put(5,"docker image rm"+" "+pipeline.getPipelineName());
            map.put(6,"find"+" "+liunxAddress+"/"+fileName+" "+ "-name '*.sh' | xargs dos2unix");
            map.put(7,"cd"+" "+fileName+";"+"docker image build -t"+" "+pipeline.getPipelineName()+"  .");
            map.put(8,"docker run -itd -p"+" "+pipelineDeploy.getMappingPort()+":"+pipelineDeploy.getDockerPort()+" "+pipeline.getPipelineName());
            for (int i = 1; i <= map.size(); i++) {
                pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"第"+i+"步 ："+ map.get(i));
                pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n第"+i+"步 ："+ map.get(i));
                Map<String, String> log = sshOrder(proof, map.get(i), pipelineExecHistory,pipelineExecHistoryList,pipelineExecLog);
                pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+log.get("log"));
            }
        } catch (JSchException | SftpException |IOException   e) {
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            return  e.toString();
        }
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return null;
    }


    /**
     * ssh 连接发送文件
     * @param proof 凭证信息
     * @param nowPath 部署文件地址
     * @param lastPath 本机文件地址
     */
    public void sshSftp(Proof proof, String nowPath, String lastPath) throws JSchException, SftpException, IOException {

        JSch jsch = new JSch();

        //采用指定的端口连接服务器
        Session session =jsch.getSession(proof.getProofUsername(), proof.getProofIp() ,proof.getProofPort());

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            throw new JSchException(proof.getProofIp() + "连接异常。。。。");
        }
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");

        if (proof.getProofType().equals("password") && proof.getProofScope()==2){
            //设置登陆主机的密码
            session.setPassword(proof.getProofPassword());
        }else {
            //添加私钥
            jsch.addIdentity(proof.getProofPassword());
        }
        //设置登陆超时时间 10s
        session.connect(10000);
        //调用发送方法
        sshSending(session,nowPath,lastPath);
        session.disconnect();

    }



    /**
     * 发送文件
     * @param session 连接
     * @param nowPath 部署文件地址
     * @param lastPath 本机文件地址
     */
    public void sshSending(Session session,String nowPath,String lastPath) throws JSchException, IOException, SftpException {
        //创建sftp通信通道
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        //ChannelSftp sftp = channel;

        //发送
        OutputStream outputStream  = channel.put(nowPath);
        InputStream inputStream =  new FileInputStream(lastPath);

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
    public Map<String, String> sshOrder(Proof proof, String order, PipelineExecHistory pipelineExecHistory,List<PipelineExecHistory> pipelineExecHistoryList,PipelineExecLog pipelineExecLog) throws IOException {
        Connection conn = new Connection(proof.getProofIp(),proof.getProofPort());
        conn.connect();

        if (proof.getProofType().equals("password") && proof.getProofScope()==2){
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
        Map<String, String> map = commonAchieve.log(inputStreamReader, pipelineExecHistory,pipelineExecHistoryList,pipelineExecLog);
        session.close();
        inputStreamReader.close();
        return map;
    }


}
