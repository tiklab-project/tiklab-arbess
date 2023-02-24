package net.tiklab.matflow.task.product.service;

import com.jcraft.jsch.*;
import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.service.PipelineStagesTaskService;
import net.tiklab.matflow.pipeline.definition.service.PipelineTasksService;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.setting.model.AuthHost;
import net.tiklab.matflow.setting.model.AuthThird;
import net.tiklab.matflow.support.until.PipelineFinal;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.task.product.model.TaskProduct;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Exporter
public class TaskProductExecServiceImpl implements TaskProductExecService {

    @Autowired
    PipelineExecCommonService commonService;

    @Autowired
    PipelineTasksService tasksService;

    @Autowired
    PipelineStagesTaskService stagesTaskServer;


    private static final Logger logger = LoggerFactory.getLogger(TaskProductExecServiceImpl.class);

    /**
     * 推送制品代码
     * @param pipelineProcess 执行信息
     * @return 执行状态
     */
    @Override
    public boolean product(PipelineProcess pipelineProcess, String configId ,int taskType) {

        Pipeline pipeline = pipelineProcess.getPipeline();
        Object o;
        if (pipeline.getType() == 1){
            o = tasksService.findOneTasksTask(configId);
        }else {
            o = stagesTaskServer.findOneStagesTasksTask(configId);
        }

        TaskProduct product = (TaskProduct) o;
        String name = product.getName();
        Boolean variableCond = commonService.variableCond(pipeline.getId(), configId);
        if (!variableCond){
            commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"任务："+ name+"执行条件不满足,跳过执行。");
            return true;
        }
        commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"执行任务："+name);

        product.setType(taskType);

        String fileAddress = product.getFileAddress();
        String path;
        try {
             path = PipelineUntil.getFile(pipeline.getId(),fileAddress);
        }catch (ApplicationException e){
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+e);
            return false;
        }

        if (path == null){
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"匹配不到制品");
            return false;
        }

        commonService.updateExecLog(pipelineProcess,
                PipelineUntil.date(4)+"制品匹配成功\n"+
                    PipelineUntil.date(4)+"制品名称："+ new File(path).getName() + "\n"+
                    PipelineUntil.date(4)+"制品地址："+path);
        try {
            if (product.getType() == 51){
                //替换变量
                String  artifactId = commonService.variableKey(pipeline.getId(), configId, product.getArtifactId());
                String  groupId = commonService.variableKey(pipeline.getId(), configId, product.getGroupId());
                String  version = commonService.variableKey(pipeline.getId(), configId, product.getVersion());
                String  fileType = commonService.variableKey(pipeline.getId(), configId, product.getFileType());
                product.setArtifactId(artifactId);
                product.setGroupId(groupId);
                product.setVersion(version);
                product.setFileType(fileType);

                Process process = getProductOrder(product,path);
                pipelineProcess.setError(error(product.getType()));
                pipelineProcess.setEnCode("UTF-8");
                commonService.execState(pipelineProcess,process,name);
                process.destroy();
            }else {
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"连接制品服务器。");
                Session session = createSession(product);
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"制品服务器连接成功。");
                String putAddress = product.getPutAddress();
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"开始推送制品。");

                //替换变量
                String key = commonService.variableKey(pipeline.getId(), configId, putAddress);
                commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"制品推送位置："+key);
                sshPut(session,path,key);
            }
        } catch (IOException | ApplicationException e) {
            commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"推送制品执行错误\n"+ PipelineUntil.date(4)+e.getMessage());
            return false;
        } catch (JSchException e) {
            commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"无法连接到服务器\n" +PipelineUntil.date(4)+e.getMessage());
            return false;
        }
        commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"推送制品完成");
        return true;
    }

    //推送到nexus
    private Process getProductOrder(TaskProduct product, String path) throws ApplicationException, IOException {
        String order ;
        String execOrder =  "mvn deploy:deploy-file ";

        String mavenAddress = commonService.getScm(21);
        if (mavenAddress == null) {
            throw new ApplicationException("不存在maven配置");
        }

        PipelineUntil.validFile(mavenAddress, 21);

        AuthThird authThird = (AuthThird)product.getAuth();
        if (authThird == null){
            order = mavenOrder(execOrder, path);
            return PipelineUntil.process(mavenAddress, order);
        }

        execOrder = execOrder +
                " -DgroupId="+product.getGroupId() +
                " -DartifactId="+product.getArtifactId() +
                " -Dversion="+product.getVersion()+
                " -Dpackaging="+product.getFileType() +
                " -Dfile="+path +
                " -Durl="+authThird.getServerAddress() ;
        if (authThird.getAuthType() == 1){
            String id = PipelineFinal.appName;

            String s = System.getProperty("user.dir") + "/" + settingAddress ;

            File file = new File(System.getProperty("user.dir"));
            if (!PipelineUntil.isNoNull(settingAddress)){
                String parent = file.getParent();
                settingAddress = "conf/settings.xml";
                if (!file.getAbsolutePath().endsWith("matflow")){
                    s = parent +"/"+settingAddress;
                }
            }

            logger.info("项目地址为："+ System.getProperty("user.dir"));
            logger.info("模块地址为："+ s);

            execOrder = execOrder +
                    " -Dusername="+authThird.getUsername()+
                    " -Dpassword="+authThird.getPassword()+
                    " -Did="+id+
                    " -DrepositoryId="+id+
                    " -s"+" "+s;
        }else {
            execOrder = execOrder +
                    " -DrepositoryId="+authThird.getPrivateKey();
        }

       logger.info("命令为："+execOrder);

        order = mavenOrder(execOrder, path);
        return PipelineUntil.process(mavenAddress, order);
    }

    @Value("${setting.address:null}")
    private String settingAddress;

    /**
     * 创建连接实例
     * @param product 连接配置信息
     * @return 实例
     * @throws ApplicationException 连接失败
     */
    private Session createSession(TaskProduct product) throws ApplicationException {
        AuthHost authHost = (AuthHost) product.getAuth();
        String sshIp = authHost.getIp();
        int sshPort = authHost.getPort();
        String username = authHost.getUsername();
        String password = authHost.getPassword();
        JSch jsch = new JSch();
        if (!PipelineUntil.isNoNull(username)){
            // username = System.getProperty("user.name");
            username = "root";
        }

        if (authHost.getAuthType() == 2){
            String tempFile = PipelineUntil.createTempFile(authHost.getPrivateKey());
            if (!PipelineUntil.isNoNull(tempFile)){
                throw new ApplicationException("获取私钥失败。");
            }
            try {
                jsch.addIdentity(tempFile);
            } catch (JSchException e) {
                String message = e.getMessage();
                throw new ApplicationException("私钥无效："+message);
            }
            PipelineUntil.deleteFile(new File(tempFile));
        }

        Session session;
        try {
            session = jsch.getSession(username, sshIp, sshPort);
        } catch (JSchException e) {
            String message = e.getMessage();
            throw new ApplicationException("创建连接失败："+message);
        }
        if (authHost.getAuthType() == 1){
            session.setPassword(password);
        }
        session.setConfig("StrictHostKeyChecking", "no");
        try {
            session.connect();
        } catch (JSchException e) {
            String message = e.getMessage();
            throw new ApplicationException("连接服务器失败："+message);
        }

        return session;
    }

    /**
     * 发送文件
     * @param session 连接实例
     * @param localFile 文件
     * @throws JSchException 连接失败
     */
    private void sshPut(Session session, String localFile, String uploadAddress) throws JSchException {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();

        //判断目录是否存在
        try {
            sftp.lstat(uploadAddress);
        }catch (SftpException e){
            try {
                sftp.mkdir(uploadAddress);
            } catch (SftpException ex) {
                throw new ApplicationException("创建文件夹"+uploadAddress+"失败,"+ex);
            }
        }
        //ChannelSftp.OVERWRITE 覆盖上传
        try {
            sftp.put(localFile,uploadAddress,ChannelSftp.OVERWRITE);
        } catch (SftpException e) {
            throw new ApplicationException();
        }
        sftp.disconnect();
    }

    //拼装maven命令
    private String mavenOrder(String buildOrder,String path){
        String order = " ./" + buildOrder  ;
        if (PipelineUntil.findSystemType() == 1){
            order = " .\\" + buildOrder ;
        }
        return order;
    }

    private String[] error(int type){
        String[] strings;
        if (type == 5){
            strings = new String[]{
                    "svn: E170000:",
                    "invalid option",
                    "Error executing Maven",
                    "The specified user settings file does not exist",
                    "405 HTTP method PUT is not supported by this URL"
            };
            return strings;
        }
        strings = new String[]{

        };
        return strings;
    }

}
