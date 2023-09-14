package io.tiklab.matflow.task.deploy.service;

import com.jcraft.jsch.*;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.pipeline.execute.service.PipelineExecServiceImpl;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.setting.model.AuthHost;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.build.model.TaskBuildProduct;
import io.tiklab.matflow.task.build.model.TaskBuildProductQuery;
import io.tiklab.matflow.task.build.service.TaskBuildProductService;
import io.tiklab.matflow.task.deploy.model.TaskDeploy;
import io.tiklab.matflow.task.task.model.TaskInstance;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 部署执行方法
 */

@Service
@Exporter
public class TaskDeployExecServiceImpl implements TaskDeployExecService {

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    VariableService variableServer;

    @Autowired
    ConditionService conditionService;

    @Autowired
    TaskBuildProductService taskBuildProductService;

    @Autowired
    PipelineInstanceService pipelineInstanceService;

    private static final Logger logger = LoggerFactory.getLogger(TaskDeployExecServiceImpl.class);


    public boolean deploy(String pipelineId, Tasks task , String taskType) {

        String taskId = task.getTaskId();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "执行任务："+task.getTaskName());
        // 判断是否满足执行条件
        Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        if (!aBoolean){
            String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
            return true;
        }
        
        TaskDeploy taskDeploy = (TaskDeploy) task.getValues();
        taskDeploy.setType(taskType);
        //获取部署文件
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"获取部署文件......");

        //执行自定义脚本
        try {
            if (taskDeploy.getAuthType() == 2) {
                execShell(pipelineId,taskId,taskDeploy.getStartOrder());
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务：" + task.getTaskName() +"执行完成。");
                return true;
            }
        } catch (IOException |InterruptedException e) {
            String s = PipelineUtil.date(4) + "命令执行失败" ;
            tasksInstanceService.writeExecLog(taskId, s);
            tasksInstanceService.writeExecLog(taskId, e.getMessage());
            return false;
        } catch (ApplicationException e){
            String s = PipelineUtil.date(4) + e.getMessage() ;
            tasksInstanceService.writeExecLog(taskId, s);
            return false;
        }

        //建立服务器连接
        Session session;
        try {
            Object auth = taskDeploy.getAuth();
            if (Objects.isNull(auth)){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"连接失败，服务器地址为空\n");
                return false;
            }
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"建立与远程服务器链接：" );
            session = createSession(taskDeploy);
        } catch (JSchException e) {
            String message = PipelineUtil.date(4)+ e.getMessage();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"连接失败，无法连接到服务器\n"+message);
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"服务器链接建立成功。" );


        //  获取制品信息
        String instanceId = findPipelineInstanceId(pipelineId);
        List<TaskBuildProduct> buildProductList = findTaskBuildProduct(instanceId);
        if (buildProductList.isEmpty()){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"无法获取到制品!");
            return false;
        }

        // 上传制品
        try {
            TaskBuildProduct buildProduct = buildProductList.get(0);
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"制品文件获取成功："+ buildProduct.getValue() );
            String deployAddress = variableServer.replaceVariable(pipelineId, taskId, taskDeploy.getDeployAddress());
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"制品文件文件上传中..." );
            // 开始上传文件
            ftp(session, buildProduct.getValue(), deployAddress);
        } catch (JSchException | SftpException e) {
            session.disconnect();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"制品文件上传失败："+e.getMessage());
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"制品文件上传成功" );

        try {
            // 替换运行时产生的变量
            String order = taskBuildProductService.replace(instanceId,taskDeploy.getDeployOrder());
            taskDeploy.setDeployOrder(order);

            // 执行远程命令
            linux(session, pipelineId, taskDeploy,taskId);
        } catch (JSchException | IOException e) {
            session.disconnect();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"命令执行失败");
            return false;
        }
        session.disconnect();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+ task.getTaskName() +"执行完成。");
        return true;
    }


    public String findPipelineInstanceId(String pipelineId){

        PipelineInstanceQuery pipelineInstanceQuery = new PipelineInstanceQuery();
        pipelineInstanceQuery.setState(PipelineFinal.RUN_RUN);
        pipelineInstanceQuery.setPipelineId(pipelineId);
        List<PipelineInstance> pipelineInstanceList = pipelineInstanceService.findPipelineInstanceList(pipelineInstanceQuery);
        if (pipelineInstanceList.isEmpty()){
            return null;
        }
        PipelineInstance pipelineInstance = pipelineInstanceList.get(0);
        return pipelineInstance.getInstanceId();
    }

    /**
     * 执行shell脚本
     * @param pipelineId 流水线id
     * @param taskId 任务id
     * @param startShell 执行命令
     * @throws InterruptedException 脚本状态获取失败
     * @throws IOException 运行脚本失败
     */
    private void execShell(String pipelineId,String taskId,String startShell) throws InterruptedException, IOException {
        // 执行的脚本为空
        if (!PipelineUtil.isNoNull(startShell)){
            return;
        }

        String instanceId = findPipelineInstanceId(pipelineId);
        List<TaskBuildProduct> buildProductList = findTaskBuildProduct(instanceId);

        // 替换运行时产生的变量
        if (!buildProductList.isEmpty()){
            startShell = taskBuildProductService.replace(instanceId,startShell);
        }

        // 替换自定义的变量
        String key = variableServer.replaceVariable(pipelineId, taskId, startShell);
        String tempFile = PipelineFileUtil.createTempFile(key,PipelineFinal.FILE_TYPE_SH);

        if (Objects.isNull(tempFile)){
            throw new ApplicationException("无法获取到执行环境，创建脚本执行文件错误！");
        }

        File file = new File(tempFile);

        String absolutePath = file.getParentFile().getAbsolutePath();
        String name = file.getName();

        // 执行脚本
        String validOrder =" sh -n " + name;
        Process processs = PipelineUtil.process(absolutePath, validOrder);
        tasksInstanceService.readCommandExecResult(processs, "UTF-8", error(41), taskId);
        int exitCodes = processs.waitFor();
        if (exitCodes != 0) {
            PipelineFileUtil.deleteFile(file);
            throw new ApplicationException("Shell脚本语法错误！");
        }

        String order = " sh " + name;
        Process process = PipelineUtil.process(absolutePath, order);
        tasksInstanceService.readCommandExecResult(process, "UTF-8", error(41), taskId);

        // 获取执行状态
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            PipelineFileUtil.deleteFile(file);
            throw new ApplicationException("Shell脚本执行失败");
        }
        PipelineFileUtil.deleteFile(file);
    }

    private List<TaskBuildProduct> findTaskBuildProduct(String instanceId){
        // 查询制品信息
        TaskBuildProductQuery taskBuildProductQuery = new TaskBuildProductQuery();
        taskBuildProductQuery.setInstanceId(instanceId);
        taskBuildProductQuery.setType(PipelineFinal.DEFAULT_TYPE);
        taskBuildProductQuery.setKey(PipelineFinal.DEFAULT_ARTIFACT_ADDRESS);
        return taskBuildProductService.findBuildProductList(taskBuildProductQuery);
    }

    /**
     * linux部署
     * @param taskId 配置信息
     */
    private void linux(Session session, String pipelineId, TaskDeploy taskDeploy, String taskId) throws JSchException, IOException {

        //部署地址
        String deployAddress = "/"+ taskDeploy.getDeployAddress();
        deployAddress = variableServer.replaceVariable(pipelineId, taskId, deployAddress);

        //部署脚本命令
        String deployOrder= taskDeploy.getDeployOrder();

        //部署命令和启动命令都为空
        if (!PipelineUtil.isNoNull(deployOrder)){
           return;
        }

        if (PipelineUtil.isNoNull(deployOrder)){
            List<String> list = PipelineUtil.execOrder(deployOrder);
            for (String s : list) {
                String key = variableServer.replaceVariable(pipelineId, taskId, s);
                String orders = "cd "+" "+ deployAddress + ";" + key;
                System.out.println("命令："+orders);
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"执行部署命令：" + key);
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
            String tempFile = PipelineFileUtil.createTempFile(authHost.getPrivateKey(),PipelineFinal.FILE_TYPE_TXT);
            if (!PipelineUtil.isNoNull(tempFile)){
                throw new ApplicationException("写入私钥失败。");
            }
            jsch.addIdentity(tempFile);
            PipelineFileUtil.deleteFile(new File(tempFile));
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
    private void sshOrder(Session session,String orders,String taskId) throws JSchException {
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
                return exec.getExitStatus();
            }

            @Override
            public void destroy() {

            }
        };
        tasksInstanceService.readCommandExecResult(process, PipelineFinal.UTF_8, error(41), taskId);
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
        try {
            sftp.lstat(uploadAddress);
        } catch (SftpException e) {
            sftp.mkdir(uploadAddress);
        }

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


    private Map<String,String> error(int type){
        Map<String,String> map = new HashMap<>();
        map.put("syntax error:","脚本语法错误！");
        map.put("invalid option;","");
        return map;
    }

}
