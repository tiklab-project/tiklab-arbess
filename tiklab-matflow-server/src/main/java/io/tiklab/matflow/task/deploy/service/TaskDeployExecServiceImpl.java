package io.tiklab.matflow.task.deploy.service;

import com.jcraft.jsch.*;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.deploy.model.TaskDeploy;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthHost;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * 部署执行方法
 */

@Service
@Exporter
public class TaskDeployExecServiceImpl implements TaskDeployExecService {

    @Autowired
    private TasksInstanceService tasksInstanceService;

    @Autowired
    private VariableService variableServer;

    @Autowired
    private ConditionService conditionService;

    private static final Logger logger = LoggerFactory.getLogger(TaskDeployExecServiceImpl.class);


    public boolean deploy(String pipelineId, Tasks task , int taskType) {

        String taskId = task.getTaskId();
        Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        if (!aBoolean){
            String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
            return true;
        }
        
        TaskDeploy taskDeploy = (TaskDeploy) task.getValues();
        String name = task.getTaskName();

        taskDeploy.setType(taskType);

        //部署命令
        String startShell = taskDeploy.getStartOrder();

        //连接服务器
        Session session;
        try {
            session = createSession(taskDeploy);
        } catch (JSchException e) {
            String message = PipelineUtil.date(4)+ e.getMessage();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"连接失败，无法连接到服务器\n"+message);
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"建立服务器链接：" );
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"服务器链接建立成功。" );

        //执行自定义脚本
        try {
            if (taskDeploy.getAuthType() == 2) {
                List<String> list = PipelineUtil.execOrder(startShell);
                for (String s : list) {
                    String key = variableServer.replaceVariable(pipelineId, taskId, s);
                    tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ key );
                    sshOrder(session,key,taskId);
                }
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+name+"执行完成。");
                session.disconnect();
                return true;
            }
        } catch (IOException | JSchException e) {
            String s = PipelineUtil.date(4) + "命令执行失败" ;
            tasksInstanceService.writeExecLog(taskId, s);
            tasksInstanceService.writeExecLog(taskId, e.getMessage());
            return false;
        }

        //获取部署文件
        String filePath;

        try {
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"获取部署文件......");
            filePath = PipelineUtil.getFile(pipelineId, taskDeploy.getLocalAddress());
        } catch (ApplicationException e) {
            String message = PipelineUtil.date(4) + e.getMessage();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"部署文件获取失败，\n"+ message);
            return false;
        }

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"部署文件获取成功："+ filePath );

        //发送文件位置
        String deployAddress ="/"+ taskDeploy.getDeployAddress();
        deployAddress = variableServer.replaceVariable(pipelineId, taskId, deployAddress);
        try {
         ftp(session, filePath, deployAddress);
        } catch (JSchException | SftpException e) {
            session.disconnect();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"部署文件上传失败"+e.getMessage());
            return false;
        }

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"部署文件上传成功" );

        try {
            linux(session, pipelineId, taskDeploy,taskId);
        } catch (JSchException | IOException e) {
            session.disconnect();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"命令执行失败");
            return false;
        }
        session.disconnect();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+name+"执行完成。");
        return true;
    }

    /**
     * linux部署
     * @param taskId 配置信息
     */
    private void linux(Session session, String pipelineId, TaskDeploy taskDeploy, String taskId) throws JSchException, IOException {

        //部署地址
        String deployAddress = "/"+ taskDeploy.getDeployAddress();
        deployAddress = variableServer.replaceVariable(pipelineId, taskId, deployAddress);

        //启动文件地址
        String startAddress = "/"+ taskDeploy.getStartAddress();
        startAddress = variableServer.replaceVariable(pipelineId, taskId, startAddress);

        //部署脚本命令
        String deployOrder= taskDeploy.getDeployOrder();

        //启动脚本命令
        String startOrder = taskDeploy.getStartOrder();

        //部署命令和启动命令都为空
        if (!PipelineUtil.isNoNull(startOrder) && !PipelineUtil.isNoNull(deployOrder)){
           return;
        }

        if (PipelineUtil.isNoNull(deployOrder)){
            List<String> list = PipelineUtil.execOrder(deployOrder);
            for (String s : list) {
                String key = variableServer.replaceVariable(pipelineId, taskId, s);
                String orders = "cd "+" "+ deployAddress + ";" + key;
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行部署命令：" + key);
                sshOrder(session,orders, taskId);
            }
        }

        if (PipelineUtil.isNoNull(startOrder)){
            List<String> list = PipelineUtil.execOrder(startOrder);
            for (String s : list) {
                String key = variableServer.replaceVariable(pipelineId, taskId, s);
                String orders = "cd "+" "+ startAddress+";" + key;
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行启动命令：" + key );
                sshOrder(session,orders, taskId);
            }
        }

    }


    /**
     * 创建连接实例
     * @param taskDeploy 连接配置信息
     * @return 实例
     * @throws JSchException 连接失败
     */
    private Session createSession(TaskDeploy taskDeploy) throws JSchException,ApplicationException {

        AuthHost authHost = (AuthHost) taskDeploy.getAuth();
        String username = authHost.getUsername();

        String sshIp = authHost.getIp();
        int sshPort = authHost.getPort();
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, sshIp, sshPort);
        if (authHost.getAuthType() ==2){
            String tempFile = PipelineUtil.createTempFile(authHost.getPrivateKey());
            if (!PipelineUtil.isNoNull(tempFile)){
                throw new ApplicationException("写入私钥失败。");
            }
            jsch.addIdentity(tempFile);
            PipelineUtil.deleteFile(new File(tempFile));
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
     * @param taskId 配置信息
     * @throws JSchException 连接错误
     * @throws IOException 读取执行信息失败
     */
    private void sshOrder(Session session,String orders,String taskId) throws JSchException, IOException {
        ChannelExec exec = (ChannelExec) session.openChannel("exec");
        exec.setCommand(orders);
        exec.connect();
        Process process = new Process() {
            @Override
            public OutputStream getOutputStream() {
                return null;
            }

            @Override
            public InputStream getInputStream() {
                try {
                    return exec.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public InputStream getErrorStream() {
                try {
                    return exec.getErrStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public int waitFor() {
                return 0;
            }

            @Override
            public int exitValue() {
                return 0;
            }

            @Override
            public void destroy() {

            }
        };
        tasksInstanceService.readCommandExecResult(process, "UTF-8", error(41), taskId);
        exec.disconnect();
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
     * @param pipelineId 配置信息
     */
    private void docker(Session session,String pipelineId, TaskDeploy taskDeploy) throws JSchException, IOException {

        // String pipelineName = pipeline.getName();
        //部署位置
        String deployAddress = "/"+  taskDeploy.getDeployAddress();
        //部署文件命令
        String  deployOrder= taskDeploy.getDeployOrder();
        //启动文件地址
        String startAddress = taskDeploy.getStartAddress();


        // String order = "docker stop $(docker ps -a | grep '"+pipelineName+"' | awk '{print $1 }');"
        //         +"docker rm $(docker ps -a | grep '"+pipelineName+"' | awk '{print $1 }');"
        //         +"docker image rm"+" "+pipelineName+";";

        // if ((deployOrder == null || deployOrder.equals("")) && (startAddress == null || startAddress.equals("/")) ){
        //
        //      order = order +"cd"+" "+deployAddress+";"+"docker image build -t"+" "+pipelineName+"  .;"
        //             +"docker run -itd -p"+" "+ taskDeploy.getMappingPort()+":"+ taskDeploy.getStartPort()+" "+pipelineName;
        //     sshOrder(session,order, taskId);
        //     return;
        // }
        // if (deployOrder != null && !deployOrder.equals("") ) {
        //     deployOrder = "cd "+" "+ deployAddress +";"+deployOrder;
        //     sshOrder(session,deployOrder, taskId);
        //     if (startAddress == null || startAddress.equals("/")) {
        //
        //         order = order + "cd" + " " + deployAddress + ";" + "docker image build -t" + " " + pipelineName + "  .;"
        //                 + "docker run -itd -p" + " " + taskDeploy.getMappingPort() + ":" + taskDeploy.getStartPort() + " " + pipelineName;
        //         sshOrder(session, order, taskId);
        //         return;
        //     }
        // }
        //
        // order = order +"cd"+" "+deployAddress+"/"+startAddress+";"+"docker image build -t"+" "+pipelineName+"  .;"
        //         +"docker run -itd -p"+" "+ taskDeploy.getMappingPort()+":"+ taskDeploy.getStartPort()+" "+pipelineName;
        // sshOrder(session, order, taskId);
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
