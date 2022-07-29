package com.tiklab.matflow.instance.service.execAchieveImpl;



import com.jcraft.jsch.*;
import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.service.MatFlowCommonService;
import com.tiklab.matflow.execute.model.MatFlowDeploy;
import com.tiklab.matflow.execute.service.MatFlowDeployService;
import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowExecLog;
import com.tiklab.matflow.instance.model.MatFlowProcess;
import com.tiklab.matflow.instance.service.execAchieveService.CommonAchieveService;
import com.tiklab.matflow.instance.service.execAchieveService.DeployAchieveService;
import com.tiklab.matflow.setting.proof.model.Proof;
import com.tiklab.rpc.annotation.Exporter;
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
    public int deploy(MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList) {
        //开始运行时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();

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
        Session session = null;
        try {
            String log = "------------------------------ "+ "\n"
                    + "开始部署" + "\n"
                    + "匹配到文件 ： " +fileName + "\n"
                    + "文件地址 ： "+filePath +"\n"
                    + "发送服务器位置 ： "+deployAddress +"\n"
                    + "连接服务器  ： " + matFlowDeploy.getSshIp() + "\n"
                    + "连接类型  ： " +proof.getProofType();
            matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n"+log);

            String remotePath ="/"+ matFlowDeploy.getDeployAddress();
            JSch jsch = new JSch();
            //指定的端口连接服务器
            session =jsch.getSession(proof.getProofUsername(), matFlowDeploy.getSshIp() , matFlowDeploy.getSshPort());
            if (session == null){
                throw new JSchException(matFlowDeploy.getSshIp() + "连接异常。。。。");
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

            if (matFlowDeploy.getDeployType() == 0){
                log = "服务器连接"+ matFlowDeploy.getSshIp()+"成功";

                matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n"+log+"\n"+"开始发送文件:"+fileName+"\n"+"文件发送中......");
                matFlowExecLog.setRunLog(matFlowExecLog.getRunLog()+"\n"+log+"\n"+"开始发送文件:"+fileName+"\n"+"文件发送中......");

                //发送文件
                sshSftp(session,remotePath,filePath,fileName);

                matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n"+"文件:"+fileName+"发送成功！");
                matFlowExecLog.setRunLog(matFlowExecHistory.getRunLog()+"\n"+"文件:"+fileName+"发送成功！");

            }
            //执行shell
            switch (matFlowDeploy.getType()) {
                case 31 -> linux(session, matFlowProcess, matFlowExecHistoryList);
                case 32 -> docker(session, matFlowProcess, matFlowExecHistoryList);
            }
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            } catch (JSchException | SftpException | IOException e) {
                commonAchieveService.updateState(matFlowProcess,"部署失败 ： "+e, matFlowExecHistoryList);
            if (session != null) {
                session.disconnect();
            }
            return 0;
            }
        //更新状态
        commonAchieveService.updateState(matFlowProcess,null, matFlowExecHistoryList);
        session.disconnect();
        return 1;
    }

    /**
     * linux部署
     * @param matFlowProcess 配置信息
     * @param matFlowExecHistoryList 状态集合
     */
    private void linux(Session session, MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList) throws IOException, JSchException {
        MatFlowDeploy matFlowDeploy = matFlowProcess.getMatFlowDeploy();

        //选择自定义部署
        if (matFlowDeploy.getDeployType() == 1){
           sshOrder(session, matFlowDeploy.getStartShell(), matFlowProcess, matFlowExecHistoryList);
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
            sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
            return;
        }
        String orders = "cd "+" "+ deployAddress + "/" + startAddress+";" + matFlowDeploy.getStartShell();
        if (startAddress != null && !startAddress.equals("")){
            if (startOrder == null ||startOrder.equals("")){
                sshOrder(session, orders, matFlowProcess, matFlowExecHistoryList);
                return;
            }
            startOrder = "cd "+" "+ deployAddress +";"+startOrder;
            sshOrder(session, startOrder, matFlowProcess, matFlowExecHistoryList);
            sshOrder(session, orders, matFlowProcess, matFlowExecHistoryList);
        }else {
            sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
        }
    }

    /**
     * docker部署
     * @param matFlowProcess 配置信息
     * @param matFlowExecHistoryList 状态集合
     */
    private void docker(Session session, MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList) throws IOException, JSchException, SftpException {
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

            //initializeDocker(session,matFlowProcess,matFlowExecHistoryList);

             order = order +"cd"+" "+deployAddress+";"+"docker image build -t"+" "+matFlowName+"  .;"
                    +"docker run -itd -p"+" "+ matFlowDeploy.getMappingPort()+":"+ matFlowDeploy.getStartPort()+" "+matFlowName;
            sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
            return;
        }
        if (deployOrder != null && !deployOrder.equals("") ) {
            deployOrder = "cd "+" "+ deployAddress +";"+deployOrder;
            sshOrder(session, deployOrder, matFlowProcess, matFlowExecHistoryList);
            if (startAddress == null || startAddress.equals("/")) {

                //initializeDocker(session,matFlowProcess,matFlowExecHistoryList);

                order = order + "cd" + " " + deployAddress + ";" + "docker image build -t" + " " + matFlowName + "  .;"
                        + "docker run -itd -p" + " " + matFlowDeploy.getMappingPort() + ":" + matFlowDeploy.getStartPort() + " " + matFlowName;
                sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
                return;
            }
        }

        //initializeDocker(session,matFlowProcess,matFlowExecHistoryList);

        order = order +"cd"+" "+deployAddress+"/"+startAddress+";"+"docker image build -t"+" "+matFlowName+"  .;"
                +"docker run -itd -p"+" "+ matFlowDeploy.getMappingPort()+":"+ matFlowDeploy.getStartPort()+" "+matFlowName;
        sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
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
    public void sshOrder(Session session, String order, MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList) throws JSchException, IOException {
        //创建sftp通信通道
        ChannelExec channel = (ChannelExec) session.openChannel("exec");

        channel.setCommand(order);

        channel.setInputStream(null);
        channel.setErrStream(System.err);

        channel.connect();
        commonAchieveService.log(channel.getInputStream(), matFlowProcess, matFlowExecHistoryList);
        channel.disconnect();
    }

    /**
     * 初始化Docker镜像
     */
    public void initializeDocker(Session session, MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList) throws JSchException, IOException {

        String order ;
        //创建centos镜像
        order = "docker pull zcamy/darth_matFlow:centos-8";
        sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);

        //创建jdk镜像
        order = "docker pull zcamy/darth_matFlow:jdk-16.0.2";
        sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
        logger.info("jdk镜像初始化成功。。。。。。。。。。。。。。。");

        //创建mysql镜像
        order = "[[ ! -z `docker ps -a | grep 'mysql' | awk '{print $1 }'` ]] " +
                "&& docker container restart matFlow || "
                +"docker pull zcamy/darth_matFlow:mysql-8.0.28;docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=darth2020 -d zcamy/darth_matFlow:mysql-8.0.28";
        sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
        logger.info("mysql镜像初始化成功。。。。。。。。。。。。。。。");

        //创建maven镜像
        //order ="docker pull zcamy/darth_matFlow:maven-3.8.6";
        //sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
        //logger.info("maven镜像初始化成功。。。。。。。。。。。。。。。");

        //创建node镜像
        //order = "docker pull zcamy/darth_matFlow:node-14.19.0";
        //sshOrder(session, order, matFlowProcess, matFlowExecHistoryList);
        //logger.info("node镜像初始化成功。。。。。。。。。。。。。。。");
        //
        //logger.info("初始化镜像完成。。。。。。。。。。。。。。。");

    }


}
