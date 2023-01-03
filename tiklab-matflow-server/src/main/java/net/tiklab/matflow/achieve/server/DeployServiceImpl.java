package net.tiklab.matflow.achieve.server;

import com.jcraft.jsch.*;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.service.PipelineStagesTaskServer;
import net.tiklab.matflow.definition.service.PipelineTasksService;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuthHost;
import net.tiklab.matflow.task.model.PipelineDeploy;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 部署执行方法
 */

@Service
@Exporter
public class DeployServiceImpl implements DeployService {

    @Autowired
    PipelineExecCommonService commonService;

    @Autowired
    PipelineTasksService tasksService;

    @Autowired
    PipelineStagesTaskServer stagesTaskServer;

    private static final Logger logger = LoggerFactory.getLogger(DeployServiceImpl.class);

    /**
     * 部署
     * @param pipelineProcess 配置信息
     * @return 状态
     */
    public boolean deploy(PipelineProcess pipelineProcess,String configId ,int taskType) {

        Pipeline pipeline = pipelineProcess.getPipeline();
        Object o;
        if (pipeline.getType() == 1){
            o = tasksService.findOneTasksTask(configId);
        }else {
            o = stagesTaskServer.findOneStagesTasksTask(configId);
        }
        PipelineDeploy pipelineDeploy = (PipelineDeploy) o;
        String name = pipelineDeploy.getName();

        Boolean variableCond = commonService.variableCond(pipeline.getId(), configId);
        if (!variableCond){
            commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"任务："+ name+"执行条件不满足,跳过执行。");
            return true;
        }

        commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"执行任务："+name);

        pipelineDeploy.setType(taskType);

        //部署命令
        String startShell = pipelineDeploy.getStartOrder();

        //连接服务器
        Session session;
        try {
            session = createSession(pipelineDeploy);
        } catch (JSchException e) {
            String message = PipelineUntil.date(4)+ e.getMessage();
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"连接失败，无法连接到服务器\n"+message);
            return false;
        }
        commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"建立服务器链接：" );
        commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"服务器链接建立成功。" );

        //执行自定义脚本
        try {
            if (pipelineDeploy.getAuthType() == 2) {
                List<String> list = PipelineUntil.execOrder(startShell);
                for (String s : list) {
                    String key = commonService.variableKey(pipeline.getId(), configId, s);
                    commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+ key );
                    sshOrder(session,key,pipelineProcess);
                }
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"任务："+name+"执行完成。");
                session.disconnect();
                return true;
            }
        } catch (IOException | JSchException e) {
            String s = PipelineUntil.date(4) + "命令执行失败" ;
            commonService.updateExecLog(pipelineProcess, s);
            commonService.updateExecLog(pipelineProcess, e.getMessage());
            return false;
        }

        //获取部署文件
        String filePath;
        try {
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"获取部署文件......");
            filePath = PipelineUntil.getFile(pipeline.getId(), pipelineDeploy.getLocalAddress());
        } catch (ApplicationException e) {
            String message = PipelineUntil.date(4) + e.getMessage();
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"部署文件获取失败，\n"+ message);
            return false;
        }

        commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"部署文件获取成功："+ filePath );

        //发送文件位置
        String deployAddress ="/"+ pipelineDeploy.getDeployAddress();
        deployAddress = commonService.variableKey(pipeline.getId(), configId, deployAddress);
        try {
         ftp(session, filePath, deployAddress);
        } catch (JSchException | SftpException e) {
            session.disconnect();
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"部署文件上传失败"+e.getMessage());
            return false;
        }

        commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"部署文件上传成功" );

        try {
            linux(session, pipelineProcess,pipelineDeploy,configId);
        } catch (JSchException | IOException e) {
            session.disconnect();
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"命令执行失败");
            return false;
        }
        session.disconnect();
        commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"任务："+name+"执行完成。");
        return true;
    }

    /**
     * linux部署
     * @param pipelineProcess 配置信息
     */
    private void linux(Session session, PipelineProcess pipelineProcess,PipelineDeploy pipelineDeploy,String configId) throws JSchException, IOException {
        Pipeline pipeline = pipelineProcess.getPipeline();

        //部署地址
        String deployAddress = "/"+ pipelineDeploy.getDeployAddress();
        deployAddress = commonService.variableKey(pipeline.getId(), configId, deployAddress);

        //启动文件地址
        String startAddress = "/"+ pipelineDeploy.getStartAddress();
        startAddress = commonService.variableKey(pipeline.getId(), configId, startAddress);

        //部署脚本命令
        String deployOrder= pipelineDeploy.getDeployOrder();

        //启动脚本命令
        String startOrder = pipelineDeploy.getStartOrder();

        //部署命令和启动命令都为空
        if (!PipelineUntil.isNoNull(startOrder) && !PipelineUntil.isNoNull(deployOrder)){
           return;
        }

        if (PipelineUntil.isNoNull(deployOrder)){
            List<String> list = PipelineUntil.execOrder(deployOrder);
            for (String s : list) {
                String key = commonService.variableKey(pipeline.getId(), configId, s);
                String orders = "cd "+" "+ deployAddress + ";" + key;
                commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"执行部署命令：" + key);
                sshOrder(session,orders, pipelineProcess);
            }
        }

        if (PipelineUntil.isNoNull(startOrder)){
            List<String> list = PipelineUntil.execOrder(startOrder);
            for (String s : list) {
                String key = commonService.variableKey(pipeline.getId(), configId, s);
                String orders = "cd "+" "+ startAddress+";" + key;
                commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"执行启动命令：" + key );
                sshOrder(session,orders, pipelineProcess);
            }
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
        exec.setCommand(orders);
        exec.connect();
        pipelineProcess.setInputStream(exec.getInputStream());
        pipelineProcess.setErrInputStream(exec.getErrStream());
        pipelineProcess.setEnCode("UTF-8");
        pipelineProcess.setError(error(41));
        commonService.log(pipelineProcess);
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

        String pipelineName = pipeline.getName();
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
