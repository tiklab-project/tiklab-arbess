package io.thoughtware.matflow.task.artifact.service;


import com.jcraft.jsch.*;
import io.thoughtware.matflow.setting.model.AuthHost;
import io.thoughtware.matflow.setting.model.AuthThird;
import io.thoughtware.matflow.setting.model.Scm;
import io.thoughtware.matflow.setting.service.ScmService;
import io.thoughtware.matflow.support.condition.service.ConditionService;
import io.thoughtware.matflow.support.util.PipelineFileUtil;
import io.thoughtware.matflow.support.util.PipelineFinal;
import io.thoughtware.matflow.support.util.PipelineUtil;
import io.thoughtware.matflow.support.util.PipelineUtilService;
import io.thoughtware.matflow.support.variable.service.VariableService;
import io.thoughtware.matflow.task.artifact.model.TaskArtifact;
import io.thoughtware.matflow.task.artifact.model.XpackRepository;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.matflow.support.util.*;
import io.thoughtware.matflow.task.build.model.TaskBuildProduct;
import io.thoughtware.matflow.task.task.model.Tasks;
import io.thoughtware.matflow.task.task.service.TasksInstanceService;
import io.thoughtware.rpc.annotation.Exporter;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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

        // 替换变量
        product = replaceVariable(product,pipelineId,taskId);


        try {
            switch (product.getType()){
                case PipelineFinal.TASK_ARTIFACT_MAVEN ->{
                    String path = findRuleArtifact(pipelineId,product.getFileAddress(), product.getRule());
                    // 获取文件后缀名
                    String ext = FilenameUtils.getExtension(path);
                    product.setFileType(ext);
                    maven(product,taskId,path);
                }
                case PipelineFinal.TASK_ARTIFACT_NODEJS ->{
                    // TaskBuildProduct taskBuildProduct = findArtifact(pipelineId, taskId,DEFAULT_ARTIFACT_DOCKER);
                    // nodeJs(product,taskId,taskBuildProduct);
                }
                case PipelineFinal.TASK_ARTIFACT_DOCKER ->{
                    // TaskBuildProduct taskBuildProduct = findArtifact(pipelineId, taskId,DEFAULT_ARTIFACT_DOCKER);
                    docker(product,taskId);
                }
            }
        } catch (Exception e) {
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"推送制品执行失败\n"+ PipelineUtil.date(4)+e.getMessage());
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"推送制品完成");
        return true;
    }

    /**
     * maven推送
     * @param product 推送详情
     * @param taskId 任务id
     * @param path 制品信息
     * @throws IOException 推送失败异常
     */
    private void maven(TaskArtifact product,String taskId,String path) throws IOException {
        String artifactType = product.getArtifactType();
        Process process = null;
        switch (artifactType) {
            case PipelineFinal.TASK_ARTIFACT_XPACK -> {
                process = mavenXpack(product,path);
            }
            case PipelineFinal.TASK_ARTIFACT_NEXUS -> {
                process = mavenNexus(product,path);
            }
            case PipelineFinal.TASK_ARTIFACT_SSH -> {
                ssh(product,path);
            }
        }

        if (!Objects.isNull(process)){
            boolean result = tasksInstanceService.readCommandExecResult(process, PipelineFinal.UTF_8, error(artifactType), taskId);
            if (!result){
                throw new ApplicationException("推送失败！");
            }
        }
    }

    /**
     * docker推送
     * @param artifact 推送详情
     * @param taskId 任务id
     * @throws IOException 推送失败异常
     */
    private void docker(TaskArtifact artifact,String taskId) throws IOException {

        String artifactType = artifact.getArtifactType();

        Process process = null;
        switch (artifactType) {
            case PipelineFinal.TASK_ARTIFACT_XPACK -> {
                process = dockerXpack(artifact);
            }
            case PipelineFinal.TASK_ARTIFACT_NEXUS -> {
                process = dockerNexus(artifact);
            }
        }
        if (!Objects.isNull(process)){
            boolean result = tasksInstanceService.readCommandExecResult(process, PipelineFinal.UTF_8, error(artifactType), taskId);
            if (!result){
                throw new ApplicationException("推送失败！");
            }
        }

    }

    /**
     * nodejs推送
     * @param artifact 推送详情
     * @param taskId 任务id
     * @param taskBuildProduct 制品信息
     * @throws IOException 推送失败异常
     */
    private void nodeJs(TaskArtifact artifact,String taskId,TaskBuildProduct taskBuildProduct){

    }

    /**
     * 替换变量
     * @param product 任务详情
     * @param pipelineId 流水线id
     * @param taskId 任务id
     * @return 替换后的任务详情
     */
    private TaskArtifact replaceVariable(TaskArtifact product,String pipelineId,String taskId){
        String artifactId = variableService.replaceVariable(pipelineId, taskId, product.getArtifactId());
        String groupId = variableService.replaceVariable(pipelineId, taskId, product.getGroupId());
        String version = variableService.replaceVariable(pipelineId, taskId, product.getVersion());
        String fileType = variableService.replaceVariable(pipelineId, taskId, product.getFileType());
        String putAddress = variableService.replaceVariable(pipelineId, taskId, product.getPutAddress());
        //替换变量
        product.setArtifactId(artifactId);
        product.setGroupId( groupId);
        product.setVersion( version);
        product.setFileType(fileType);
        product.setPutAddress(putAddress);
        return product;
    }

    //maven推送到Xpack
    private Process mavenXpack(TaskArtifact product, String path)throws ApplicationException, IOException{
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

        XpackRepository repository = product.getRepository();
        if (Objects.isNull(repository)){
            throw new ApplicationException("获取制品库信息失败！");
        }
        authThird.setServerAddress(repository.getRepositoryUrl());

        execOrder = execOrder +
                " -DgroupId="+product.getGroupId() +
                " -DartifactId="+product.getArtifactId() +
                " -Dversion="+product.getVersion()+
                " -Dpackaging="+product.getFileType() +
                " -Dfile=\""+path +"\""+
                " -Durl=\""+authThird.getServerAddress() +"\"";
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
            logger.warn("settings.xml文件地址为："+ s);
            File file1 = new File(s);

            if (!file1.exists()){
                throw new ApplicationException("系统异常，获取setting文件错误！" + s);
            }

            execOrder = execOrder +
                    " -Dusername="+authThird.getUsername()+
                    " -Dpassword="+authThird.getPassword()+
                    " -Did="+id+
                    " -DrepositoryId="+id+
                    " -s"+" \""+s +"\"";
        }else {
            execOrder = execOrder +
                    " -DrepositoryId="+authThird.getPrivateKey();
        }

        logger.warn("命令为："+execOrder);

        order = mavenOrder(execOrder, path);
        return PipelineUtil.process(mavenAddress, order);
    }

    //maven推送到nexus
    private Process mavenNexus(TaskArtifact product, String path) throws ApplicationException, IOException {
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

    //docker推送到nexus
    private Process dockerNexus(TaskArtifact artifact)throws ApplicationException, IOException {
        String domain;
        boolean isHttp = true;
        int port;

        String dockerImage = artifact.getDockerImage();

        AuthThird auth = (AuthThird)artifact.getAuth();
        try {

            // 创建URL对象
            URL url = new URL(auth.getServerAddress());

            // 判断是否为HTTPS
            if ("https".equalsIgnoreCase(url.getProtocol())) {
                isHttp = false;
            }

            // 提取域名
            domain = url.getHost();

            // 提取端口
            port = url.getPort();
            if (port == -1) {
                // 如果端口未明确指定，您可以使用默认端口
                port = url.getDefaultPort();
            }

        } catch (Exception e) {
            throw new ApplicationException("远程地址获取失败！"+e.getMessage());
        }

        int authType = auth.getAuthType();

        if (authType == 2){
            throw new ApplicationException("暂不支持秘钥认证！");
        }
        String username = auth.getUsername();
        String password = auth.getPassword();

        String replace = auth.getServerAddress().replace("https://", "").replace("http://", "");

        String loginOrder;
        if (isHttp){
            loginOrder = "docker login -u " + username + " -p " + password +" http://"+domain+":"+port  ;
        }else {
            loginOrder = "docker login -u " + username + " -p " + password +" https://"+domain+":"+port  ;
        }
        String tarOrder = "docker tag  " + dockerImage + " " + replace+"/"+ dockerImage ;
        String pushOrder = "docker push  " + replace+"/"+ dockerImage ;


        String order = loginOrder + " && " + tarOrder + " && " + pushOrder;

        logger.warn("执行命令：{}",order);

        // docker login -u admin -p darth2020 http://172.13.1.11:9003 ;docker tag matflow:1.0.3 "172.13.1.11:9003/b/matflow:1.0.3"; docker push 172.13.1.11:9003/b/matflow:1.0.3

         PipelineUtil.process(null, loginOrder);
         PipelineUtil.process(null, tarOrder);

        return PipelineUtil.process(null, pushOrder);
    }

    //docker推送到xpack
    private Process dockerXpack(TaskArtifact artifact)throws ApplicationException, IOException{

        String domain;
        boolean isHttp = true;
        int port;

        XpackRepository repository = artifact.getRepository();
        if (Objects.isNull(repository)){
            throw new ApplicationException("查询不到仓库");
        }

        String dockerImage = artifact.getDockerImage();

        AuthThird auth = (AuthThird)artifact.getAuth();
        try {

            // 创建URL对象
            URL url = new URL(auth.getServerAddress());

            // 判断是否为HTTPS
            if ("https".equalsIgnoreCase(url.getProtocol())) {
                isHttp = false;
            }

            // 提取域名
            domain = url.getHost();

            // 提取端口
            port = url.getPort();
            if (port == -1) {
                // 如果端口未明确指定，您可以使用默认端口
                port = url.getDefaultPort();
            }

        } catch (Exception e) {
            throw new ApplicationException("远程地址获取失败！"+e.getMessage());
        }

        int authType = auth.getAuthType();

        if (authType == 2){
            throw new ApplicationException("暂不支持秘钥认证！");
        }
        String username = auth.getUsername();
        String password = auth.getPassword();

        String replace = repository.getRepositoryUrl().replace("https://", "").replace("http://", "");

        String loginOrder;
        if (isHttp){
            loginOrder = "docker login -u " + username + " -p " + password +" http://"+domain+":"+port  ;
        }else {
            loginOrder = "docker login -u " + username + " -p " + password +" https://"+domain+":"+port  ;
        }
        String tarOrder = "docker tag  " + dockerImage + " " + replace+"/"+ dockerImage ;
        String pushOrder = "docker push  " + replace+"/"+ dockerImage ;


        String order = loginOrder + " && " + tarOrder + " && " + pushOrder;

        logger.warn("执行命令：{}",order);

        // docker login -u admin -p darth2020 http://172.13.1.11:9003 ;docker tag matflow:1.0.3 "172.13.1.11:9003/b/matflow:1.0.3"; docker push 172.13.1.11:9003/b/matflow:1.0.3

        PipelineUtil.process(null, loginOrder);
        PipelineUtil.process(null, tarOrder);

        return PipelineUtil.process(null, pushOrder);
    }

    // ssh 推送
    private void ssh(TaskArtifact product, String path) {
        Session session = createSession(product);
        String putAddress = product.getPutAddress();

        if (!putAddress.startsWith("/")){
            putAddress = "/"+putAddress;
        }
        try {
            sshPut(session,path,putAddress);
        } catch (JSchException e) {
            throw new ApplicationException("无法连接到服务器:" + e.getMessage());
        }
    }


    private String findRuleArtifact(String pipelineId,String fileDir,String rule) {

        if (fileDir.contains(PipelineFinal.PROJECT_DEFAULT_ADDRESS)){
            String path = utilService.findPipelineDefaultAddress(pipelineId,1);
            fileDir = fileDir.replace(PipelineFinal.PROJECT_DEFAULT_ADDRESS,path);
        }

        // 匹配制品
        String productAddress;
        try {
            productAddress = utilService.findFile(pipelineId,fileDir,rule);
        }catch (ApplicationException e){
            throw new ApplicationException(e.getMessage());
        }

        if (Objects.isNull(productAddress)){
            throw new ApplicationException("没有匹配到制品!");
        }

        return productAddress;
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
            session.connect(10000);
        } catch (JSchException e) {
            String message = e.getMessage();
            throw new ApplicationException("连接服务器失败："+message);
        }

        return session;
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
        switch (type){
            case PipelineFinal.TASK_ARTIFACT_XPACK  , PipelineFinal.TASK_ARTIFACT_NEXUS ->{
                map.put("Error executing Maven","执行maven命令失败！");
                map.put("The specified user settings file does not exist","无法找到Setting文件！");
                map.put("405 HTTP method PUT is not supported by this URL","方法不被允许！");
                map.put("[INFO] BUILD FAILURE","构建失败！");
                map.put("BUILD FAILURE","构建失败！");
                map.put("[ERROR]","推送失败！");
                map.put("failed ","执行失败！");
                return map;
            }
            default -> {
                return map;
            }
        }

    }


}