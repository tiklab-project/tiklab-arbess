package io.tiklab.matflow.task.artifact.service;

import com.jcraft.jsch.*;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthHost;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.*;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.artifact.model.TaskArtifact;
import io.tiklab.matflow.task.artifact.model.XpackRepository;
import io.tiklab.matflow.task.build.model.TaskBuildProduct;
import io.tiklab.matflow.task.build.model.TaskBuildProductQuery;
import io.tiklab.matflow.task.build.service.TaskBuildProductService;
import io.tiklab.matflow.task.task.model.TaskInstance;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.rpc.annotation.Exporter;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Exporter
public class TaskArtifactExecServiceImpl implements TaskArtifactExecService {

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    VariableService variableService;

    @Autowired
    ConditionService conditionService;

    @Autowired
    ScmService scmService;

    @Autowired
    TaskArtifactXpackService taskArtifactXpackService;

    @Autowired
    PipelineUtilService utilService;


    @Autowired
    TaskBuildProductService taskBuildProductService;

    private static final Logger logger = LoggerFactory.getLogger(TaskArtifactExecServiceImpl.class);

    @Override
    public boolean product(String pipelineId, Tasks task , String taskType) {
        String taskId = task.getTaskId();
        String names = "执行任务："+task.getTaskName();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+names);
        Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        if (!aBoolean){
            String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
            return true;
        }

        TaskArtifact product = (TaskArtifact) task.getTask();
        product.setType(taskType);

        // 查询制品信息
        TaskInstance execInstance = tasksInstanceService.findExecInstance(taskId);
        TaskBuildProductQuery taskBuildProductQuery = new TaskBuildProductQuery();
        taskBuildProductQuery.setInstanceId(execInstance.getInstanceId());
        taskBuildProductQuery.setKey(PipelineFinal.DEFAULT_ARTIFACT_ADDRESS);
        taskBuildProductQuery.setType(PipelineFinal.DEFAULT_TYPE);
        List<TaskBuildProduct> buildProductList = taskBuildProductService.findBuildProductList(taskBuildProductQuery);

        if (buildProductList.isEmpty()){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"无法获取到制品!");
            return false;
        }

        TaskBuildProduct taskBuildProduct = buildProductList.get(0);

        String path = taskBuildProduct.getValue();
        // 获取文件后缀名
        String ext = FilenameUtils.getExtension(path);
        product.setFileType(ext);

        tasksInstanceService.writeExecLog(taskId,
                PipelineUtil.date(4)+"制品匹配成功\n"+
                    PipelineUtil.date(4)+"制品名称："+ new File(path).getName() + "\n"+
                    PipelineUtil.date(4)+"制品地址："+path);

        try {
            switch (product.getType()){
                case "nexus","51","xpack" ->{
                    String artifactId = variableService.replaceVariable(pipelineId, taskId, product.getArtifactId());
                    String groupId = variableService.replaceVariable(pipelineId, taskId, product.getGroupId());
                    String version = variableService.replaceVariable(pipelineId, taskId, product.getVersion());
                    String fileType = variableService.replaceVariable(pipelineId, taskId, product.getFileType());
                    //替换变量
                    product.setArtifactId(artifactId);
                    product.setGroupId( groupId);
                    product.setVersion( version);
                    product.setFileType(fileType);

                    //执行命令
                    Process process = getProductOrder(product,path);

                    boolean result = tasksInstanceService.readCommandExecResult(process, "UTF-8", error(product.getType()), taskId);
                    if (!result){
                        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+task.getTaskName()+"执行失败。");
                        return false;
                    }
                }
                case "ssh" ,"52" ->{
                    tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"开始连接制品服务器...");
                    Session session = createSession(product);
                    tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"制品服务器连接成功。");
                    String putAddress = product.getPutAddress();
                    tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"开始推送制品。");

                    //替换变量
                    String key = variableService.replaceVariable(pipelineId, taskId, putAddress);
                    tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"制品推送位置："+key);

                    sshPut(session,path,key);
                }
            }
        } catch (IOException | ApplicationException e) {
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"推送制品执行错误\n"+ PipelineUtil.date(4)+e.getMessage());
            return false;
        } catch (JSchException e) {
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"无法连接到服务器\n" + PipelineUtil.date(4)+e.getMessage());
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"推送制品完成");
        return true;
    }

    //推送到nexus
    private Process getProductOrder(TaskArtifact product, String path) throws ApplicationException, IOException {
        String order ;
        String execOrder =  "mvn deploy:deploy-file ";
        Scm pipelineScm = scmService.findOnePipelineScm(21);

        if (pipelineScm == null) {
            throw new ApplicationException("不存在maven配置");
        }
        String mavenAddress = pipelineScm.getScmAddress();
        PipelineUtil.validFile(mavenAddress, "maven");

        AuthThird authThird = (AuthThird) product.getAuth();
        if (authThird == null){
            order = mavenOrder(execOrder, path);
            return PipelineUtil.process(mavenAddress, order);
        }

        if (product.getType().equals("xpack")){
            XpackRepository repository = product.getRepository();
            if (Objects.isNull(repository)){
                throw new ApplicationException("获取制品库信息失败！");
            }
            authThird.setServerAddress(repository.getAddress());
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

            if (!PipelineUtil.isNoNull(settingAddress)){
                settingAddress = "conf/settings.xml";
            }
            String s  ;

            File file = new File(System.getProperty("user.dir"));
            if (file.getAbsolutePath().endsWith("bin")){
                String parent = file.getParent();
                s = parent +"/"+settingAddress;
            }else if (file.getAbsolutePath().endsWith("matflow")){
                s = file.getAbsolutePath() +"/"+settingAddress;
            }else {
                s = file.getAbsolutePath() + "/" + settingAddress;
            }
            logger.info("模块地址为："+ s);
            File file1 = new File(s);

            if (!file1.exists()){
                throw new ApplicationException("系统异常，获取setting文件错误！" + s);
            }

            logger.info("项目地址为："+ System.getProperty("user.dir"));

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
        return PipelineUtil.process(mavenAddress, order);
    }

    @Value("${setting.address:null}")
    private String settingAddress;

    /**
     * 创建连接实例
     * @param product 连接配置信息
     * @return 实例
     * @throws ApplicationException 连接失败
     */
    private Session createSession(TaskArtifact product) throws ApplicationException {
        AuthHost authHost = (AuthHost) product.getAuth();
        String sshIp = authHost.getIp();
        int sshPort = authHost.getPort();
        String username = authHost.getUsername();
        String password = authHost.getPassword();
        JSch jsch = new JSch();
        if (!PipelineUtil.isNoNull(username)){
            // username = System.getProperty("user.name");
            username = "root";
        }

        if (authHost.getAuthType() == 2){
            String tempFile = PipelineFileUtil.createTempFile(authHost.getPrivateKey(),PipelineFinal.FILE_TYPE_TXT);
            if (!PipelineUtil.isNoNull(tempFile)){
                throw new ApplicationException("获取私钥失败。");
            }
            try {
                jsch.addIdentity(tempFile);
            } catch (JSchException e) {
                String message = e.getMessage();
                throw new ApplicationException("私钥无效："+message);
            }
            PipelineFileUtil.deleteFile(new File(tempFile));
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
        if (PipelineUtil.findSystemType() == 1){
            order = " .\\" + buildOrder ;
        }
        return order;
    }

    private Map<String,String> error(String type){
        Map<String,String> map =new HashMap<>();
        String[] strings = new String[]{};
        switch (type){
            case "xpack","nexus","51" ->{
                map.put("Error executing Maven","执行maven命令失败！");
                map.put("The specified user settings file does not exist","无法找到Setting文件！");
                map.put("405 HTTP method PUT is not supported by this URL","方法不被允许！");
                map.put("[INFO] BUILD FAILURE","构建失败！");
                map.put("BUILD FAILURE","构建失败！");
                return map;
             // return new String[]{
             //        "Error executing Maven",
             //        "The specified user settings file does not exist",
             //        "405 HTTP method PUT is not supported by this URL",
             //        "[INFO] BUILD FAILURE",
             //        "BUILD FAILURE"
             //    };
            }
            default -> {
                return map;
            }
        }

    }

}
