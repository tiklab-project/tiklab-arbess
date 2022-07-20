package com.doublekit.pipeline.instance.service.execAchieveImpl;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.definition.service.PipelineCommonService;
import com.doublekit.pipeline.execute.model.PipelineDeploy;
import com.doublekit.pipeline.execute.service.PipelineDeployService;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineProcess;
import com.doublekit.pipeline.instance.service.execAchieveService.CommonAchieveService;
import com.doublekit.pipeline.instance.service.execAchieveService.DeployAchieveService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;

@Service
@Exporter
public class DeployAchieveServiceImpl implements DeployAchieveService {

    @Autowired
    PipelineDeployService pipelineDeployService;

    @Autowired
    CommonAchieveService commonAchieveService;

    @Autowired
    PipelineCommonService pipelineCommonService;

    private static final Logger logger = LoggerFactory.getLogger(DeployAchieveServiceImpl.class);

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

        PipelineExecLog pipelineExecLog = commonAchieveService.initializeLog(pipelineExecHistory, pipelineConfigure);
        PipelineDeploy pipelineDeploy = pipelineDeployService.findOneDeploy(pipelineConfigure.getTaskId());
        Proof proof = pipelineDeploy.getProof();

        pipelineProcess.setPipelineExecLog(pipelineExecLog);
        pipelineProcess.setProof(proof);
        pipelineProcess.setPipelineDeploy(pipelineDeploy);

        if (proof == null){
            commonAchieveService.updateTime(pipelineProcess,beginTime);
            commonAchieveService.updateState(pipelineProcess,"凭证为空。",pipelineExecHistoryList);
            return 0;
        }

        String filePath = pipelineCommonService.getFile(pipelineConfigure.getPipeline().getPipelineName(),pipelineDeploy.getSourceAddress());
        
        if (filePath == null){
            commonAchieveService.updateState(pipelineProcess,"部署文件找不到。",pipelineExecHistoryList);
            return 0;
        }
        //文件名
        String fileName = new File(filePath).getName();

        //发送文件位置
        String deployAddress ="/"+ pipelineDeploy.getDeployAddress();

        if (pipelineExecHistory.getRunLog() == null){
            pipelineExecHistory.setRunLog("");
        }
        Session session = null;
        try {
            String log = "------------------------------ "+ "\n"
                    + "开始部署" + "\n"
                    + "匹配到文件 ： " +fileName + "\n"
                    + "文件地址 ： "+filePath +"\n"
                    + "发送服务器位置 ： "+deployAddress +"\n"
                    + "连接服务器  ： " +pipelineDeploy.getSshIp() + "\n"
                    + "连接类型  ： " +proof.getProofType();
            pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+log);

            String remotePath ="/"+ pipelineDeploy.getDeployAddress();
            JSch jsch = new JSch();
            //指定的端口连接服务器
            session =jsch.getSession(proof.getProofUsername(), pipelineDeploy.getSshIp() ,pipelineDeploy.getSshPort());
            if (session == null){
                throw new JSchException(pipelineDeploy.getSshIp() + "连接异常。。。。");
            }
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
            session.connect();

            if (pipelineDeploy.getDeployType() == 0){
                log = "服务器连接"+pipelineDeploy.getSshIp()+"成功";

                pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+log+"\n"+"开始发送文件:"+fileName+"\n"+"文件发送中......");
                pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+"\n"+log+"\n"+"开始发送文件:"+fileName+"\n"+"文件发送中......");

                //发送文件
                sshSftp(session,remotePath,filePath,fileName);

                pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"文件:"+fileName+"发送成功！");
                pipelineExecLog.setRunLog(pipelineExecHistory.getRunLog()+"\n"+"文件:"+fileName+"发送成功！");

            }
            //执行shell
            switch (pipelineDeploy.getType()) {
                case 31 -> linux(session,pipelineProcess, pipelineExecHistoryList);
                case 32 -> docker(session,pipelineProcess, pipelineExecHistoryList);
            }
            commonAchieveService.updateTime(pipelineProcess,beginTime);
            } catch (JSchException | SftpException | IOException e) {
                commonAchieveService.updateState(pipelineProcess,"部署失败 ： "+e,pipelineExecHistoryList);
            if (session != null) {
                session.disconnect();
            }
            return 0;
            }
        //更新状态
        commonAchieveService.updateState(pipelineProcess,null,pipelineExecHistoryList);
        session.disconnect();
        return 1;
    }

    /**
     * linux部署
     * @param pipelineProcess 配置信息
     * @param pipelineExecHistoryList 状态集合
     */
    private void linux( Session session,PipelineProcess pipelineProcess,List<PipelineExecHistory> pipelineExecHistoryList) throws IOException, JSchException {
        PipelineDeploy pipelineDeploy = pipelineProcess.getPipelineDeploy();

        //选择自定义部署
        if (pipelineDeploy.getDeployType() == 1){
           sshOrder(session, pipelineDeploy.getStartShell(),pipelineProcess, pipelineExecHistoryList);
           return;
        }

        //部署地址
        String deployAddress = "/"+ pipelineDeploy.getDeployAddress();

        //部署文件命令
        String  startOrder= pipelineDeploy.getDeployOrder();
        //启动文件地址
        String startAddress = pipelineDeploy.getStartAddress();

        //部署文件命令启动文件地址都为null的时候
        String order = "cd "+" "+ deployAddress +";"+pipelineDeploy.getStartShell();
        if ((startOrder == null || startOrder.equals("")) && (startAddress == null || startAddress.equals("")) ){
            if (pipelineDeploy.getStartShell() == null || pipelineDeploy.getStartShell().equals("") ){
                return;
            }
            sshOrder(session, order, pipelineProcess,pipelineExecHistoryList);
            return;
        }
        String orders = "cd "+" "+ deployAddress + "/" + startAddress+";" +pipelineDeploy.getStartShell();
        if (startAddress != null && !startAddress.equals("")){
            if (startOrder == null ||startOrder.equals("")){
                sshOrder(session, orders, pipelineProcess,pipelineExecHistoryList);
                return;
            }
            startOrder = "cd "+" "+ deployAddress +";"+startOrder;
            sshOrder(session, startOrder, pipelineProcess,pipelineExecHistoryList);
            sshOrder(session, orders, pipelineProcess,pipelineExecHistoryList);
        }else {
            sshOrder(session, order, pipelineProcess,pipelineExecHistoryList);
        }
    }

    /**
     * docker部署
     * @param pipelineProcess 配置信息
     * @param pipelineExecHistoryList 状态集合
     */
    private void docker(Session session,PipelineProcess pipelineProcess,List<PipelineExecHistory> pipelineExecHistoryList) throws IOException, JSchException, SftpException {
        PipelineDeploy pipelineDeploy = pipelineProcess.getPipelineDeploy();

        //选择自定义部署
        if (pipelineDeploy.getDeployType() == 1){
            sshOrder(session,pipelineDeploy.getStartShell(),pipelineProcess, pipelineExecHistoryList);
            return;
        }

        String pipelineName = pipelineProcess.getPipelineConfigure().getPipeline().getPipelineName();
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

            //initializeDocker(session,pipelineProcess,pipelineExecHistoryList);

             order = order +"cd"+" "+deployAddress+";"+"docker image build -t"+" "+pipelineName+"  .;"
                    +"docker run -itd -p"+" "+pipelineDeploy.getMappingPort()+":"+pipelineDeploy.getStartPort()+" "+pipelineName;
            sshOrder(session, order,pipelineProcess,pipelineExecHistoryList);
            return;
        }
        if (deployOrder != null && !deployOrder.equals("") ) {
            deployOrder = "cd "+" "+ deployAddress +";"+deployOrder;
            sshOrder(session, deployOrder, pipelineProcess, pipelineExecHistoryList);
            if (startAddress == null || startAddress.equals("/")) {

                //initializeDocker(session,pipelineProcess,pipelineExecHistoryList);

                order = order + "cd" + " " + deployAddress + ";" + "docker image build -t" + " " + pipelineName + "  .;"
                        + "docker run -itd -p" + " " + pipelineDeploy.getMappingPort() + ":" + pipelineDeploy.getStartPort() + " " + pipelineName;
                sshOrder(session, order, pipelineProcess, pipelineExecHistoryList);
                return;
            }
        }

        //initializeDocker(session,pipelineProcess,pipelineExecHistoryList);

        order = order +"cd"+" "+deployAddress+"/"+startAddress+";"+"docker image build -t"+" "+pipelineName+"  .;"
                +"docker run -itd -p"+" "+pipelineDeploy.getMappingPort()+":"+pipelineDeploy.getStartPort()+" "+pipelineName;
        sshOrder(session, order,pipelineProcess,pipelineExecHistoryList);
    }

    /**
     * ssh 连接发送文件
     * @param session 部署信息
     * @param localPath 本机文件地址
     */
    public void sshSftp(Session session, String remotePath, String localPath,String fileName) throws JSchException, IOException, SftpException {
        //创建sftp通信通道
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();

        //发送
        InputStream inputStream =  new FileInputStream(localPath);
        try {
            channelSftp.ls(remotePath);
        } catch (SftpException e) {
            //捕获文件夹不存在异常，创建文件夹
            channelSftp.mkdir(remotePath);
        }
        channelSftp.cd(remotePath);
        channelSftp.put(inputStream,fileName);
        inputStream.close();
        channelSftp.disconnect();
    }

    /**
     *  执行ssh命令
     * @param order 执行命令
     * @throws IOException 日志读写异常
     */
    public void sshOrder(Session session,String order, PipelineProcess pipelineProcess,List<PipelineExecHistory> pipelineExecHistoryList) throws JSchException, IOException {
        //创建sftp通信通道
        ChannelExec channel = (ChannelExec) session.openChannel("exec");

        channel.setCommand(order);

        channel.setInputStream(null);
        channel.setErrStream(System.err);

        channel.connect();
        commonAchieveService.log(channel.getInputStream(),pipelineProcess,pipelineExecHistoryList);
        channel.disconnect();
    }

    /**
     * 初始化Docker镜像
     */
    public void initializeDocker(Session session,PipelineProcess pipelineProcess,List<PipelineExecHistory> pipelineExecHistoryList) throws JSchException, IOException {

        String order ;
        //创建centos镜像
        order = "docker pull zcamy/darth_pipeline:centos-8";
        sshOrder(session, order, pipelineProcess, pipelineExecHistoryList);

        //创建jdk镜像
        order = "docker pull zcamy/darth_pipeline:jdk-16.0.2";
        sshOrder(session, order, pipelineProcess, pipelineExecHistoryList);
        logger.info("jdk镜像初始化成功。。。。。。。。。。。。。。。");

        //创建mysql镜像
        order = "[[ ! -z `docker ps -a | grep 'mysql' | awk '{print $1 }'` ]] " +
                "&& docker container restart pipeline || "
                +"docker pull zcamy/darth_pipeline:mysql-8.0.28;docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=darth2020 -d zcamy/darth_pipeline:mysql-8.0.28";
        sshOrder(session, order, pipelineProcess, pipelineExecHistoryList);
        logger.info("mysql镜像初始化成功。。。。。。。。。。。。。。。");

        //创建maven镜像
        //order ="docker pull zcamy/darth_pipeline:maven-3.8.6";
        //sshOrder(session, order, pipelineProcess, pipelineExecHistoryList);
        //logger.info("maven镜像初始化成功。。。。。。。。。。。。。。。");

        //创建node镜像
        //order = "docker pull zcamy/darth_pipeline:node-14.19.0";
        //sshOrder(session, order, pipelineProcess, pipelineExecHistoryList);
        //logger.info("node镜像初始化成功。。。。。。。。。。。。。。。");
        //
        //logger.info("初始化镜像完成。。。。。。。。。。。。。。。");

    }


}
