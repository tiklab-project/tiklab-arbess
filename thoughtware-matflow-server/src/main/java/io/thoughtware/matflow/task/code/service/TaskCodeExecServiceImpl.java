package io.thoughtware.matflow.task.code.service;

import com.alibaba.fastjson.JSON;
import io.thoughtware.matflow.setting.model.Auth;
import io.thoughtware.matflow.setting.model.AuthThird;
import io.thoughtware.matflow.setting.model.Scm;
import io.thoughtware.matflow.setting.service.AuthService;
import io.thoughtware.matflow.setting.service.AuthThirdService;
import io.thoughtware.matflow.setting.service.ScmService;
import io.thoughtware.matflow.support.condition.service.ConditionService;
import io.thoughtware.matflow.support.util.util.PipelineFileUtil;
import io.thoughtware.matflow.support.util.util.PipelineFinal;
import io.thoughtware.matflow.support.util.util.PipelineUtil;
import io.thoughtware.matflow.support.util.service.PipelineUtilService;
import io.thoughtware.matflow.support.variable.service.VariableService;
import io.thoughtware.matflow.task.code.model.TaskCode;
import io.thoughtware.matflow.task.code.model.XcodeRepository;
import io.thoughtware.matflow.task.task.model.Tasks;
import io.thoughtware.matflow.task.task.service.TasksInstanceService;
import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static io.thoughtware.matflow.support.util.util.PipelineFinal.*;

/**
 * 源码管理执行方法
 */

@Service
@Exporter
public class TaskCodeExecServiceImpl implements TaskCodeExecService {

    @Autowired
    TasksInstanceService tasksInstanceService;

    @Autowired
    VariableService variableServer;

    @Autowired
    ScmService scmService;

    @Autowired
    ConditionService conditionService;

    @Autowired
    AuthService authServer;

    @Autowired
    AuthThirdService thirdService;

    @Autowired
    TaskCodeThirdService codeThirdService;

    @Autowired
    PipelineUtilService utilService;

    private final Logger logger = LoggerFactory.getLogger(TaskCodeExecServiceImpl.class);

    // git克隆
    public boolean clone(String pipelineId, Tasks task , String taskType) throws ApplicationException {

        String taskId = task.getTaskId();
        String names = "执行任务："+task.getTaskName();
        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+names);
        Boolean aBoolean = conditionService.variableCondition(pipelineId, taskId);
        if (!aBoolean){
            String s = "任务"+task.getTaskName()+"执行条件不满足，跳过执行\n";
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+s);
            return true;
        }
        
        String name = task.getTaskName();

        String object = JSON.toJSONString(task.getTask());
        TaskCode code = JSON.parseObject(object, TaskCode.class);
        code.setType(taskType);

        if (taskType.equals(PipelineFinal.TASK_CODE_XCODE)){
            XcodeRepository repository = code.getRepository();
            code.setCodeAddress(repository.getFullPath());
        }

        if (!PipelineUtil.isNoNull(code.getCodeAddress())){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"代码源地址未配置。");
            return false;
        }

        //代码保存路径
        String codeDir = utilService.findPipelineDefaultAddress(pipelineId,1);
        File file = new File(codeDir);

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"分配源码空间..." );

        //删除旧的代码
        Boolean aBoolean1 = PipelineFileUtil.deleteFile(file);
        if (!aBoolean1){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"空间被占用分配失败！" );
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+file.getAbsolutePath()+"文件夹被占用，请删除该文件夹！" );
            return false;
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "空间分配成功。" );

        //更新日志
        String s = PipelineUtil.date(4) + "Url : " + code.getCodeAddress() ;

        tasksInstanceService.writeExecLog(taskId,s);

        try {

            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "拉取远程代码..." );

            //命令执行失败
            Process process = codeStart(code,pipelineId);
            String  type = code.getType();
            String enCode = null;
            if (!type.equals(TASK_CODE_SVN)){
                enCode = PipelineFinal.UTF_8;
            }
            boolean result = tasksInstanceService.readCommandExecResult(process, enCode, error(type), taskId);
            if (!result){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+task.getTaskName()+"执行失败。");
                return false;
            }

            process.destroy();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "获取提交信息..." );

            //获取提交信息
            if (!type.equals(TASK_CODE_SVN)){
                Process message = cloneMessage(code.getType(),pipelineId);
                if (message != null){
                    boolean result1 = tasksInstanceService.readCommandExecResult(message, enCode, error(type), taskId);
                    if (!result1){
                        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+task.getTaskName()+"执行失败。");
                        return false;
                    }
                    message.destroy();
                }
            }

        } catch (IOException e) {
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"系统执行命令错误 \n" + e);
            return false;
        }catch (URISyntaxException e){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"Git地址错误 \n" + e);
            return false;
        }catch (ApplicationException e){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ e);
            return false;
        }

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务"+name+"执行成功。");
        return true;
    }

    /**
     * 执行命令
     * @param taskCode 配置信息
     * @return 命令执行实例
     * @throws IOException 命令错误
     * @throws URISyntaxException git地址错误
     * @throws ApplicationException 不存在配置
     */
    private Process codeStart(TaskCode taskCode, String pipelineId) throws IOException, URISyntaxException , ApplicationException{
        boolean b = !(taskCode.getType().equals(TASK_CODE_SVN));
        Scm pipelineScm ;

        if (b){
            pipelineScm = scmService.findOnePipelineScm(1);
        }else {
            pipelineScm = scmService.findOnePipelineScm(5);
        }

        //效验地址是否应用程序地址
        if (Objects.isNull(pipelineScm)){
            if (b){
                throw new ApplicationException(50001,"未配置git程序地址");
            }else {
                throw new ApplicationException(50001,"未配置SVN程序地址");
            }
        }
        String serverAddress = pipelineScm.getScmAddress();
        //效验地址应用程序的合法性
        PipelineUtil.validFile(serverAddress, taskCode.getType());

        //源码存放位置
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,1);
        String gitOrder;
        String path ;
        switch (taskCode.getType()) {
            //账号密码或ssh登录
            case TASK_CODE_GIT  ,TASK_CODE_GITLAB -> {
                List<String> list = gitUpOrder(taskCode, fileAddress);
                gitOrder = list.get(0);
                if (list.size() > 1){
                    gitOrder = list.get(2);
                    path = list.get(3);
                   if (PipelineUtil.findSystemType() != 1){
                       logger.info("执行更改文件权限:" +" chmod 600 "+" "+path);
                       process(serverAddress, " chmod 600 "+" "+path);
                   }

                    Process process = process(serverAddress, gitOrder);
                    logger.info("执行：" + gitOrder);
                    if (PipelineUtil.isNoNull(path)){
                        try {
                            Thread.sleep(1000);
                            PipelineFileUtil.deleteFile(new File(path));
                        } catch (InterruptedException e) {
                            return process;
                        }
                    }
                    return process;
                }
            }
            case TASK_CODE_XCODE ->{
                gitOrder = gitXcodeOrder(taskCode, fileAddress);
            }
            //第三方授权
            case TASK_CODE_GITEE ,TASK_CODE_GITHUB -> gitOrder = gitThirdOrder(taskCode, fileAddress);

            //svn
            case TASK_CODE_SVN -> gitOrder = svnOrder(taskCode, fileAddress);
            //错误
            default -> throw new ApplicationException("未知的任务类型");
        }
        logger.warn("执行代码克隆命令："+ gitOrder);
        return PipelineUtil.process(serverAddress, gitOrder);
    }

    /**
     * 组装xcode git命令
     * @param taskCode 源码信息
     * @param fileAddress 存放位置
     * @return 执行命令
     * @throws URISyntaxException url格式不正确
     * @throws MalformedURLException 不是https或者http
     */
    private String gitXcodeOrder(TaskCode taskCode, String fileAddress) throws MalformedURLException, URISyntaxException {
        String authId = taskCode.getAuthId();
        StringBuilder codeAddress = new StringBuilder(taskCode.getCodeAddress());
        AuthThird auth = thirdService.findOneAuthServer(authId);
        if (Objects.isNull(auth)){
            return gitBranch(codeAddress, taskCode, fileAddress);
        }

        StringBuilder stringBuilder = gitUrl(auth.getUsername(), auth.getPassword(), codeAddress);
        return gitBranch(stringBuilder, taskCode, fileAddress);
    }


    /**
     * 组装http git命令
     * @param taskCode 源码信息
     * @param fileAddress 存放位置
     * @return 执行命令
     * @throws URISyntaxException url格式不正确
     * @throws MalformedURLException 不是https或者http
     */
    private List<String> gitUpOrder(TaskCode taskCode, String fileAddress)
            throws URISyntaxException, MalformedURLException , ApplicationException {
        String authId = taskCode.getAuthId();
        Auth auth = authServer.findOneAuth(authId);

        List<String> list = new ArrayList<>();

        StringBuilder codeAddress = new StringBuilder(taskCode.getCodeAddress());

        //没有凭证
        if (auth == null){
            String s = gitBranch(codeAddress, taskCode, fileAddress);
            list.add(s);
            return list;
        }

        typeAddress(taskCode.getType(),auth.getAuthType(), taskCode.getCodeAddress());

        if (auth.getAuthType() == 1){
            gitUrl(auth.getUsername(), auth.getPassword(), codeAddress);
        }
        String s = gitBranch(codeAddress, taskCode, fileAddress);

        if (auth.getAuthType() == 1 ){
            list.add(s);
            return list;
        }

        String tempFile = PipelineFileUtil.createTempFile(auth.getPrivateKey(), PipelineFinal.FILE_TYPE_TXT);
        String userHome = System.getProperty("user.home");
        if (!PipelineUtil.isNoNull(tempFile)){
            throw new ApplicationException("私钥写入失败。");
        }

        logger.info("tempFile:" + tempFile);

        // 匹配私钥地址
        String address = tempFile.replace(userHome, "").replace("\\", "/");
        String orderClean;
        String orderAdd;
        if (PipelineUtil.findSystemType() == 1){
             orderClean = ".\\git.exe git config --global --unset core.sshCommand";
             orderAdd = ".\\git.exe config --global core.sshCommand \"ssh -i ~" + address + "\"";
        }else {
            orderClean = "./git git config --global --unset core.sshCommand";
            orderAdd = "./git config --global core.sshCommand \"ssh -i ~" + address + "\"";
        }

        String aa = null;
        if (s.contains("git.exe clone")){
            int indexOf = s.indexOf("git.exe clone");
            String substring = s.substring(indexOf+13);
            aa = ".\\git.exe -c core.sshCommand=\"ssh -i ~" + address + " -o StrictHostKeyChecking=no \"  clone " + substring;
        }

        if (s.contains("git clone") ){
            int indexOf = s.indexOf("git clone");
            String substring = s.substring(indexOf+9);
             aa = "./git -c core.sshCommand=\"ssh -i " + address + " -o StrictHostKeyChecking=no \"  clone " + substring;
        }

        list.add(orderClean);
        list.add(orderAdd);
        list.add(aa);
        list.add(tempFile);

        return list;
    }

    /**
     * git地址加入账号密码
     * @param username  用户名
     * @param password 密码
     * @param url git地址
     * @return 地址
     * @throws URISyntaxException 不是个url
     * @throws MalformedURLException 不属于http或者https
     */
    private StringBuilder gitUrl(String username,String password,StringBuilder url) throws URISyntaxException, MalformedURLException {
        String codeAddress = username.replace("@", "%40")+":"+password+"@";
        //获取url类型
        URL urls = new URL(url.toString());
        String urlType = urls.toURI().getScheme();

        //根据不同类型拼出不同地址
        if (urlType.equals("http")){
            url.insert(7, codeAddress);
        }else {
            url.insert(8, codeAddress);
        }
        return url;
    }

    /**
     * 地址加入分支
     * @param url 地址
     * @param code 配置
     * @param codeDir 拉取地址
     * @return 地址
     */
    private String gitBranch(StringBuilder url , TaskCode code, String codeDir){
        String type = code.getType();
        String branch = code.getCodeBranch();
        if (type.equals(TASK_CODE_XCODE) && !Objects.isNull(code.getBranch())){
            branch = code.getBranch().getBranchName();
        }

        //分支
        if(!PipelineUtil.isNoNull(branch)){
            branch = PipelineFinal.TASK_CODE_DEFAULT_BRANCH;
        }

        //更新日志
        String s = PipelineUtil.date(4) + "Branch : " + branch ;
        tasksInstanceService.writeExecLog(code.getTaskId(),s);

        //判断是否存在分支
        String order;
        if (!PipelineUtil.isNoNull(branch)){
            order = url+" "+codeDir;
        }else {
            order =" -b "+branch+" "+ url+" "+codeDir;
        }
        //根据不同系统更新命令
        if (PipelineUtil.findSystemType() == 1){
            order=".\\git.exe clone"+" " + order;
        }else {
            order="./git clone"+" " + order;
        }

        return order;
    }

    /**
     * 第三方认证授权
     * @param taskCode 配置
     * @param fileAddress 地址
     * @return 命令
     * @throws URISyntaxException 不是个url
     * @throws MalformedURLException 不属于http或者https
     */
    private String gitThirdOrder(TaskCode taskCode, String fileAddress)
            throws MalformedURLException, URISyntaxException,ApplicationException {
        String authId = taskCode.getAuthId();
        AuthThird auth = thirdService.findOneAuthServer(authId);
        StringBuilder codeAddress = new StringBuilder(taskCode.getCodeAddress());
        String thirdToken = codeThirdService.findUserAuthThirdToken(authId, auth.getAccessToken());
        if (Objects.isNull(thirdToken)){
            throw new ApplicationException("获取第三方Token失败。");
        }
        StringBuilder stringBuilder = gitUrl(auth.getUsername(), thirdToken, codeAddress);
        return gitBranch(stringBuilder, taskCode,fileAddress);
    }

    /**
     * 组装svn命令
     * @param taskCode 源码信息
     * @param codeDir 存放位置
     * @return 执行命令
     */
    private String svnOrder(TaskCode taskCode, String codeDir){
        //凭证
        String authId = taskCode.getAuthId();
        Auth auth = authServer.findOneAuth(authId);

        String codeAddress = taskCode.getCodeAddress();

        String svnFile = taskCode.getSvnFile();

        //检出文件夹
        if (PipelineUtil.isNoNull(svnFile)){
            codeAddress = codeAddress + "/./" +svnFile;
        }

        if (!Objects.isNull(auth)){
            //判断检出类型
            if (auth.getAuthType() == 1){
                String username = auth.getUsername();
                String password = auth.getPassword();
                codeAddress  = codeAddress + " --username "+ " "  +username + " --password " + " " + password + " " +codeDir;
            }else {
                throw new ApplicationException("SVN暂不支持使用秘钥认证！");
            }
        }else {
            codeAddress  = codeAddress + " " +codeDir;
        }

        //不同系统检出
        if (PipelineUtil.findSystemType() == 1){
            codeAddress=".\\svn.exe checkout"+" " + codeAddress;
        }else {
            codeAddress="./svn checkout"+" " + codeAddress;
        }

        //判断是否存在分支
       return codeAddress;
    }

    /**
     * 效验地址与认证方式是否一致
     * @param type 类型
     * @param address 地址
      */
    private void typeAddress(String type,int authType,String address) throws ApplicationException{
        String substring = address.substring(0, 3);
        if (type.equals(TASK_CODE_SVN)){
            if (substring.equals("svn") && authType == 1){
                return;
            }
            substring = address.substring(0, 7);
            if (substring.equals("svn+ssh") && authType == 2){
                return;
            }
            throw new ApplicationException("SVN地址类型与凭证认证类型不一致。");
        }else {
            if (substring.equals("htt") && authType == 1){
                return;
            }
            if (substring.equals("git") && authType == 2){
                return;
            }
            throw new ApplicationException("Git地址类型与凭证认证类型不一致。");
        }
    }

    /**
     * 获取提交信息
     * @param taskType 类型
     * @param pipelineId 流水线id
     * @return 信息实例
     * @throws IOException 执行失败
     */
    private Process cloneMessage(String taskType,String pipelineId) throws IOException {
        if (taskType.equals(TASK_CODE_SVN)){
            return null;
        }
        String order = "git log --pretty=format:\"commit：%cn email：%ae message：%s date：%ad time：%ar \" --date=format:\"%Y-%m-%d %H:%M:%S\" -n 1";
        String fileAddress = utilService.findPipelineDefaultAddress(pipelineId,1);
        return PipelineUtil.process(fileAddress, order);
    }

    /**
     * 错误
     * @param type 类型
     * @return 错误
     */
    private Map<String,String> error(String type){
        Map<String,String> map = new HashMap<>();
        if (type.equals(TASK_CODE_SVN)){
            map.put("svn: E170000","");
            map.put("invalid option","");
            map.put("vn: E204900","");
            map.put("svn: E170013","");
            map.put("svn: E210002","");
            map.put("svn: E205000","");
            return map;
        }
        map.put("fatal: Could not read from remote repository","无法连接到远程仓库！");
        map.put("remote: HTTP Basic: Access denied","访问被拒绝！");
        map.put("fatal: Authentication failed ","认证失败!");
        map.put("not found in upstream origin","分支不存在！");
        map.put("404 not found","获取远程仓库失败！");
        map.put("Could not resolve host","无法连接到远程仓库！");
        map.put("fatal: ","拉取失败！");
        return map;
    }


    /**
     * 执行cmd命令
     * @param path 执行文件夹
     * @param order 执行命令
     * @return 执行信息
     * @throws IOException 调取命令行失败
     */
    public static Process process(String path,String order) throws IOException {
        Runtime runtime=Runtime.getRuntime();
        Process process;
        String[] cmd;

        if (PipelineUtil.findSystemType()==1){
            if (!PipelineUtil.isNoNull(path)){
                cmd = new String[] { "cmd.exe", "/c", order };
                process = runtime.exec(cmd);
            }else {

                order = order.substring(0,order.length()-1);

                cmd = new String[] { "cmd.exe", "/c", " " + order  };
                process = runtime.exec(cmd,null,new File(path));
            }
        }else {
            if (!PipelineUtil.isNoNull(path)){
                cmd = new String[] { "/bin/sh", "-c", " source /etc/profile;"+ order };
                process = runtime.exec(cmd);
            }else {
                cmd = new String[] { "/bin/sh", "-c", "cd " + path + ";" + " source /etc/profile;" + order };
                process = runtime.exec(cmd,null,new File(path));
            }
        }
        return process;
    }

}
