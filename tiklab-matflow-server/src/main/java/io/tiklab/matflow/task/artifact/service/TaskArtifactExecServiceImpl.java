package io.tiklab.matflow.task.artifact.service;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.*;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.pipeline.execute.service.PipelineExecServiceImpl;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import io.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import io.tiklab.matflow.pipeline.instance.service.PipelineInstanceService;
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
import org.apache.commons.io.FileUtils;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.tiklab.matflow.support.util.PipelineFinal.*;

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

    @Autowired
    PipelineInstanceService pipelineInstanceService;

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
        String path = findRuleArtifact(pipelineId, product.getRule());

        try {
            switch (product.getType()){
                case TASK_ARTIFACT_MAVEN ->{
                    // 获取文件后缀名
                    String ext = FilenameUtils.getExtension(path);
                    product.setFileType(ext);
                    maven(product,taskId,path);
                }
                case TASK_ARTIFACT_NODEJS ->{
                    // TaskBuildProduct taskBuildProduct = findArtifact(pipelineId, taskId,DEFAULT_ARTIFACT_DOCKER);
                    // nodeJs(product,taskId,taskBuildProduct);
                }
                case TASK_ARTIFACT_DOCKER ->{
                    // TaskBuildProduct taskBuildProduct = findArtifact(pipelineId, taskId,DEFAULT_ARTIFACT_DOCKER);
                    docker(product,taskId,path);
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
            case TASK_ARTIFACT_XPACK -> {
                process = mavenXpack(product,path);
            }
            case TASK_ARTIFACT_NEXUS -> {
                process = mavenNexus(product,path);
            }
            case TASK_ARTIFACT_SSH -> {
                ssh(product,path);
            }
        }

        if (!Objects.isNull(process)){
            boolean result = tasksInstanceService.readCommandExecResult(process, PipelineFinal.UTF_8, error(product.getType()), taskId);
            if (!result){
                throw new ApplicationException("推送失败！");
            }
        }
    }

    /**
     * docker推送
     * @param artifact 推送详情
     * @param taskId 任务id
     * @param path 制品信息
     * @throws IOException 推送失败异常
     */
    private void docker(TaskArtifact artifact,String taskId,String path) throws IOException {

        String artifactType = artifact.getArtifactType();

        Process process = null;
        switch (artifactType) {
            case TASK_ARTIFACT_XPACK -> {
                process = dockerXpack(artifact,path);
            }
            case TASK_ARTIFACT_NEXUS -> {
                process = dockerNexus(artifact);
            }
        }
        if (!Objects.isNull(process)){
            boolean result = tasksInstanceService.readCommandExecResult(process, PipelineFinal.UTF_8, error(artifact.getType()), taskId);
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
        authThird.setServerAddress(repository.getAddress());

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
    private Process dockerXpack(TaskArtifact product, String path)throws ApplicationException, IOException{

        return null;
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


    private String findRuleArtifact(String pipelineId,String rule) {

        String path = utilService.findPipelineDefaultAddress(pipelineId,1);
        // 匹配制品
        String productAddress;
        try {
            productAddress = utilService.findFile(pipelineId,rule);
        }catch (ApplicationException e){
            throw new ApplicationException(e.getMessage());
        }

        if (Objects.isNull(path)){
            throw new ApplicationException("没有匹配到构建产物!");
        }

        File file = new File(productAddress);

        String defaultAddress = utilService.findPipelineDefaultAddress(pipelineId, 2);

        // 默认路径
        String instanceId = findPipelineInstanceId(pipelineId);
        String fileAddress = defaultAddress + instanceId +"/"+file.getName();

        LinkedHashMap<String,Object> linkedMap = new LinkedHashMap<>();
        linkedMap.put(DEFAULT_ARTIFACT_ADDRESS,fileAddress);
        linkedMap.put(DEFAULT_ARTIFACT_NAME,file.getName());

        // 创建流水线运行时产生的制品信息
        TaskBuildProduct taskBuildProduct = new TaskBuildProduct(instanceId);
        taskBuildProduct.setKey(PipelineFinal.DEFAULT_ARTIFACT_ADDRESS);
        taskBuildProduct.setValue(JSONObject.toJSONString(linkedMap));
        taskBuildProduct.setInstanceId(instanceId);
        taskBuildProduct.setType(PipelineFinal.DEFAULT_TYPE);

        taskBuildProductService.createBuildProduct(taskBuildProduct);

        // 移动文件
        try {
            FileUtils.copyFile(file, new File(fileAddress));
        } catch (IOException e) {
            throw new ApplicationException("报错构建产物失败!");
        }
        return fileAddress;
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
            session.connect();
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
            case "xpack","nexus","51" ->{
                map.put("Error executing Maven","执行maven命令失败！");
                map.put("The specified user settings file does not exist","无法找到Setting文件！");
                map.put("405 HTTP method PUT is not supported by this URL","方法不被允许！");
                map.put("[INFO] BUILD FAILURE","构建失败！");
                map.put("BUILD FAILURE","构建失败！");
                return map;
            }
            default -> {
                return map;
            }
        }

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

}
