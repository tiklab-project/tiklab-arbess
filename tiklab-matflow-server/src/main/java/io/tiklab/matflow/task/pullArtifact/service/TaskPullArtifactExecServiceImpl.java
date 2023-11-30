package io.tiklab.matflow.task.pullArtifact.service;

import com.jcraft.jsch.*;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
import io.tiklab.matflow.setting.model.AuthHost;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineFileUtil;
import io.tiklab.matflow.support.util.PipelineFinal;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.matflow.support.util.PipelineUtilService;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.artifact.model.TaskArtifact;
import io.tiklab.matflow.task.artifact.model.XpackRepository;
import io.tiklab.matflow.task.artifact.service.TaskArtifactExecService;
import io.tiklab.matflow.task.artifact.service.TaskArtifactXpackService;
import io.tiklab.matflow.task.build.model.TaskBuildProduct;
import io.tiklab.matflow.task.build.service.TaskBuildProductService;
import io.tiklab.matflow.task.pullArtifact.model.TaskPullArtifact;
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
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.tiklab.matflow.support.util.PipelineFinal.*;

@Service
@Exporter
public class TaskPullArtifactExecServiceImpl implements TaskPullArtifactExecService {

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

    private static final Logger logger = LoggerFactory.getLogger(TaskPullArtifactExecServiceImpl.class);

    @Override
    public boolean pullArtifact(String pipelineId, Tasks task , String taskType) {
        String taskId = task.getTaskId();
        String names = "执行任务："+task.getTaskName();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+names);
        // Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        // if (!aBoolean){
        //     String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
        //     tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
        //     return true;
        // }

        TaskPullArtifact pullArtifact = (TaskPullArtifact) task.getTask();
        pullArtifact.setType(taskType);

        // 替换变量
        pullArtifact = replaceVariable(pullArtifact,pipelineId,taskId);


        try {
            switch (pullArtifact.getType()){
                case TASK_PULL_MAVEN ->{
                    maven(pullArtifact,taskId);
                }
                case TASK_PULL_NODEJS ->{
                    // TaskBuildProduct taskBuildProduct = findArtifact(pipelineId, taskId,DEFAULT_ARTIFACT_DOCKER);
                    // nodeJs(product,taskId,taskBuildProduct);
                }
                case TASK_PULL_DOCKER ->{
                    docker(pullArtifact,taskId);
                }
            }
        } catch (Exception e) {
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"拉取制品执行失败\n"+ PipelineUtil.date(4)+e.getMessage());
            return false;
        }
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"拉取制品完成");
        return true;
    }

    /**
     * maven拉取
     * @param pullArtifact 拉取详情
     * @param taskId 任务id
     * @throws IOException 拉取失败异常
     */
    private void maven(TaskPullArtifact pullArtifact,String taskId) throws IOException {
        String pullType = pullArtifact.getPullType();
        Process process = null;
        switch (pullType) {
            case TASK_ARTIFACT_XPACK -> {
                process = mavenXpack(pullArtifact);
            }
            case TASK_ARTIFACT_NEXUS -> {
                process = mavenNexus(pullArtifact);
            }
            case TASK_ARTIFACT_SSH -> {
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"拉取制品......");
                ssh(pullArtifact);
            }
        }

        if (!Objects.isNull(process)){
            boolean result = tasksInstanceService.readCommandExecResult(process, PipelineFinal.UTF_8, error(pullArtifact.getType()), taskId);
            if (!result){
                throw new ApplicationException("拉取失败！");
            }
        }
    }

    /**
     * docker拉取
     * @param pullArtifact 拉取详情
     * @param taskId 任务id
     * @throws IOException 拉取失败异常
     */
    private void docker(TaskPullArtifact pullArtifact,String taskId) throws IOException {

        String pullType = pullArtifact.getPullType();

        Process process = null;
        switch (pullType) {
            case TASK_ARTIFACT_XPACK -> {
                process = dockerXpack(pullArtifact);
            }
            case TASK_ARTIFACT_NEXUS -> {
                process = dockerNexus(pullArtifact);
            }
        }
        if (!Objects.isNull(process)){
            boolean result = tasksInstanceService.readCommandExecResult(process, PipelineFinal.UTF_8, error(pullArtifact.getType()), taskId);
            if (!result){
                throw new ApplicationException("拉取失败！");
            }
        }

    }

    /**
     * nodejs拉取
     * @param artifact 拉取详情
     * @param taskId 任务id
     * @param taskBuildProduct 制品信息
     * @throws IOException 拉取失败异常
     */
    private void nodeJs(TaskArtifact artifact,String taskId,TaskBuildProduct taskBuildProduct){

    }

    /**
     * 替换变量
     * @param pullArtifact 任务详情
     * @param pipelineId 流水线id
     * @param taskId 任务id
     * @return 替换后的任务详情
     */
    private TaskPullArtifact replaceVariable(TaskPullArtifact pullArtifact,String pipelineId,String taskId){
        String artifactId = variableService.replaceVariable(pipelineId, taskId, pullArtifact.getArtifactId());
        String groupId = variableService.replaceVariable(pipelineId, taskId, pullArtifact.getGroupId());
        String version = variableService.replaceVariable(pipelineId, taskId, pullArtifact.getVersion());
        //替换变量
        pullArtifact.setArtifactId(artifactId);
        pullArtifact.setGroupId( groupId);
        pullArtifact.setVersion( version);
        return pullArtifact;
    }

    //maven拉取到Xpack
    private Process mavenXpack(TaskPullArtifact pullArtifact)throws ApplicationException, IOException{
        String order ;
        String execOrder =  "mvn dependency:get ";
        Scm pipelineScm = scmService.findOnePipelineScm(21);

        if (Objects.isNull(pipelineScm)) {
            throw new ApplicationException("不存在maven配置");
        }
        String mavenAddress = pipelineScm.getScmAddress();
        PipelineUtil.validFile(mavenAddress, "maven");


        execOrder = execOrder +
                " -DgroupId=\""+pullArtifact.getGroupId() +"\"" +
                " -DartifactId=\""+pullArtifact.getArtifactId() +"\"" +
                " -Dversion=\""+pullArtifact.getVersion() +"\"" +
                " -Dtransitive=\""+pullArtifact.getTransitive() +"\"";

        AuthThird authThird = (AuthThird) pullArtifact.getAuth();
        if (Objects.isNull(authThird)){
            order = mavenOrder(execOrder);
            return PipelineUtil.process(mavenAddress, order);
        }

        if (Objects.isNull(pullArtifact.getRepository())){
            throw new ApplicationException("无法获取到远程仓库！");
        }
        String address = pullArtifact.getRepository().getRepositoryUrl();

        execOrder = execOrder + " -DremoteRepositories=\"" + address+"\""
                +" -Durl=\"" + address+"\"";

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
                    " -s"+" \""+s+"\"";
        }else {
            execOrder = execOrder +
                    " -DrepositoryId="+authThird.getPrivateKey();
        }

        logger.warn("命令为："+execOrder);

        order = mavenOrder(execOrder);
        return PipelineUtil.process(mavenAddress, order);
    }

    //maven拉取到nexus
    private Process mavenNexus(TaskPullArtifact pullArtifact) throws ApplicationException, IOException {
        String order ;
        String execOrder =  "mvn dependency:get ";
        Scm pipelineScm = scmService.findOnePipelineScm(21);

        if (Objects.isNull(pipelineScm)) {
            throw new ApplicationException("不存在maven配置");
        }
        String mavenAddress = pipelineScm.getScmAddress();
        PipelineUtil.validFile(mavenAddress, "maven");


        execOrder = execOrder +
                " -DgroupId=\""+pullArtifact.getGroupId() +"\"" +
                " -DartifactId=\""+pullArtifact.getArtifactId() +"\"" +
                " -Dversion=\""+pullArtifact.getVersion() +"\""+
                " -Dtransitive=\""+pullArtifact.getTransitive() +"\"";

        AuthThird authThird = (AuthThird) pullArtifact.getAuth();
        if (authThird == null){
            order = mavenOrder(execOrder);
            logger.warn("命令为："+order);
            return PipelineUtil.process(mavenAddress, order);
        }

        execOrder = execOrder + " -DremoteRepositories=\"" + authThird.getServerAddress()+"\""
                              +" -Durl=\"" + authThird.getServerAddress()+"\"";

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
            logger.info("settings.xml文件地址为："+ s);
            File file1 = new File(s);

            if (!file1.exists()){
                throw new ApplicationException("系统异常，获取setting文件错误！" + s);
            }

            execOrder = execOrder +
                    " -Dusername="+authThird.getUsername()+
                    " -Dpassword="+authThird.getPassword()+
                    " -Did="+id+
                    " -DrepositoryId="+id+
                    " -s"+" \""+s+"\"";
        }else {
            execOrder = execOrder +
                    " -DrepositoryId="+authThird.getPrivateKey();
        }

        logger.warn("命令为："+execOrder);

        order = mavenOrder(execOrder);
        return PipelineUtil.process(mavenAddress, order);
    }

    //docker拉取到nexus
    private Process dockerNexus(TaskPullArtifact pullArtifact)throws IOException {

        String dockerImage = pullArtifact.getDockerImage();

        AuthThird auth = (AuthThird)pullArtifact.getAuth();

        if (Objects.isNull(auth)){
            String order = "docker pull " + dockerImage;
            logger.warn("执行命令：{}",order);

            return PipelineUtil.process(null, order);
        }


        String domain;
        boolean isHttp = true;
        int port;

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

        // docker pull --username=your-username --password=your-password remote-registry-url/repository/image-name:tag

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

        String pullOrder = "docker pull "  + replace+"/"+dockerImage;
        String order = loginOrder + " && " + pullOrder;

        logger.warn("执行命令：{}",order);

        return PipelineUtil.process(null, order);
    }

    //docker拉取到xpack
    private Process dockerXpack(TaskPullArtifact pullArtifact)throws ApplicationException, IOException{
        String dockerImage = pullArtifact.getDockerImage();

        if (Objects.isNull(pullArtifact.getRepository())){
            throw new ApplicationException("无法获取到远程仓库！");
        }

        AuthThird auth = (AuthThird)pullArtifact.getAuth();

        if (Objects.isNull(auth)){
            String order = "docker pull " + dockerImage;
            logger.warn("执行命令：{}",order);

            return PipelineUtil.process(null, order);
        }


        String domain;
        boolean isHttp = true;
        int port;

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

        // docker pull --username=your-username --password=your-password remote-registry-url/repository/image-name:tag

        if (authType == 2){
            throw new ApplicationException("暂不支持秘钥认证！");
        }
        String username = auth.getUsername();
        String password = auth.getPassword();

        String replace = pullArtifact.getRepository().getRepositoryUrl().replace("https://", "").replace("http://", "");

        String loginOrder;
        if (isHttp){
            loginOrder = "docker login -u " + username + " -p " + password +" http://"+domain+":"+port  ;
        }else {
            loginOrder = "docker login -u " + username + " -p " + password +" https://"+domain+":"+port  ;
        }

        String pullOrder = "docker pull "  + replace+"/"+dockerImage;
        String order = loginOrder + " && " + pullOrder;

        logger.warn("执行命令：{}",order);

        return PipelineUtil.process(null, order);
    }

    // ssh 拉取
    private void ssh(TaskPullArtifact pullArtifact) {
        Session session = createSession(pullArtifact);
        try {
            sshGet(session,pullArtifact.getRemoteAddress(),pullArtifact.getLocalAddress());
        } catch (JSchException e) {
            throw new ApplicationException("无法连接到服务器:" + e.getMessage());
        }
    }

    /**
     * 下载文件
     * @param session 连接实例
     * @param remoteFile 远程文件
     * @param localFile 本地地址
     * @throws JSchException 连接失败
     */
    private void sshGet(Session session, String remoteFile, String localFile) throws JSchException {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();

        // 下载文件
        try {
            sftp.get(remoteFile, localFile);
        } catch (SftpException e) {
            throw new ApplicationException("下载文件失败: " + e.getMessage());
        }

        sftp.disconnect();
    }

    @Value("${setting.address:null}")
    private String settingAddress;

    /**
     * 创建连接实例
     * @param pullArtifact 连接配置信息
     * @return 实例
     * @throws ApplicationException 连接失败
     */
    private Session createSession(TaskPullArtifact pullArtifact) throws ApplicationException {
        AuthHost authHost = (AuthHost) pullArtifact.getAuth();
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
    private String mavenOrder(String buildOrder){
        String order = " ./" + buildOrder  ;
        if (PipelineUtil.findSystemType() == 1){
            order = " .\\" + buildOrder ;
        }
        return order;
    }

    private Map<String,String> error(String type){
        Map<String,String> map =new HashMap<>();
        switch (type){
            default -> {
                map.put("Error executing Maven","执行maven命令失败！");
                map.put("The specified user settings file does not exist","无法找到Setting文件！");
                map.put("405 HTTP method PUT is not supported by this URL","方法不被允许！");
                map.put("[INFO] BUILD FAILURE","拉取失败！");
                map.put("BUILD FAILURE","拉取失败！");
                map.put("[ERROR]","");
                map.put("Error","拉取失败！");
                return map;
            }
        }

    }

}
