package io.tiklab.matflow.task.code.service;

import com.alibaba.fastjson.JSON;
import io.tiklab.matflow.setting.model.Auth;
import io.tiklab.matflow.setting.model.Scm;
import io.tiklab.matflow.setting.service.AuthThirdService;
import io.tiklab.matflow.setting.service.ScmService;
import io.tiklab.matflow.support.variable.service.VariableService;
import io.tiklab.matflow.task.code.model.TaskCode;
import io.tiklab.matflow.task.task.model.Tasks;
import io.tiklab.matflow.task.task.service.TasksInstanceService;
import io.tiklab.core.exception.ApplicationException;
import io.tiklab.matflow.setting.model.AuthThird;
import io.tiklab.matflow.setting.service.AuthService;
import io.tiklab.matflow.support.condition.service.ConditionService;
import io.tiklab.matflow.support.util.PipelineUtil;
import io.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 * 源码管理执行方法
 */

@Service
@Exporter
public class TaskCodeExecServiceImpl implements TaskCodeExecService {

    @Autowired
    private TasksInstanceService tasksInstanceService;

    @Autowired
    private VariableService variableServer;

    @Autowired
    private ScmService scmService;

    @Autowired
    private ConditionService conditionService;

    @Autowired
    AuthService authServer;

    @Autowired
    AuthThirdService thirdService;

    // git克隆
    public boolean clone(String pipelineId, Tasks task , int taskType) throws ApplicationException{

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

        String object = JSON.toJSONString(task.getValues());
        TaskCode code = JSON.parseObject(object, TaskCode.class);
        code.setType(taskType);

        if (!PipelineUtil.isNoNull(code.getCodeAddress())){
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"代码源地址未配置。");
            return false;
        }

        //代码保存路径
        String codeDir = PipelineUtil.findFileAddress(pipelineId,1);
        File file = new File(codeDir);

        //删除旧的代码
        boolean b = false;
        while (!b){
            if (file.exists()){
                b = PipelineUtil.deleteFile(file);
            }else {
                b = true;
            }
        }

        //分支
        String codeBranch = code.getCodeBranch();
        if(!PipelineUtil.isNoNull(codeBranch)){
            codeBranch = "master";
        }

        //更新日志
        String s = PipelineUtil.date(4) + "Uri : " + code.getCodeAddress() + "\n"
                + PipelineUtil.date(4) + "Branch : " + codeBranch ;

        tasksInstanceService.writeExecLog(taskId,s);

        tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"分配源码空间..." );

        try {
            //命令执行失败
            Process process = codeStart(code,pipelineId);
            int type = code.getType();
            String enCode = null;
            if (type != 5){
                enCode = "UTF-8";
            }
            boolean result = tasksInstanceService.readCommandExecResult(process, enCode, error(type), taskId);
            if (!result){
                tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+task.getTaskName()+"执行失败。");
                return false;
            }

            process.destroy();
            tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+ "空间分配成功。" );
            //获取提交信息
            Process message = cloneMessage(code.getType(),pipelineId);
            if (message != null){
                boolean result1 = tasksInstanceService.readCommandExecResult(message, enCode, error(type), taskId);
                if (!result1){
                    tasksInstanceService.writeExecLog(taskId, PipelineUtil.date(4)+"任务："+task.getTaskName()+"执行失败。");
                    return false;
                }
                message.destroy();
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
    private Process codeStart(TaskCode taskCode, String pipelineId) throws IOException, URISyntaxException ,ApplicationException{

        //效验地址是否应用程序地址
        Scm pipelineScm = scmService.findOnePipelineScm(taskCode.getType());

        if (pipelineScm == null ){
            if (taskCode.getType() != 5){
                throw new ApplicationException(50001,"未配置git程序地址");
            }else {
                throw new ApplicationException(50001,"未配置SVN程序地址");
            }
        }
        String serverAddress = pipelineScm.getScmAddress();
        //效验地址应用程序的合法性
        PipelineUtil.validFile(serverAddress, taskCode.getType());

        //源码存放位置
        String fileAddress = PipelineUtil.findFileAddress(pipelineId,1);
        String gitOrder;
        String path = null;
        switch (taskCode.getType()) {
            //账号密码或ssh登录
            case 1, 4 -> {
                List<String> list = gitUpOrder(taskCode, fileAddress);
                gitOrder = list.get(0);
                if (list.size() > 1){
                    PipelineUtil.process(serverAddress, list.get(0));
                    PipelineUtil.process(serverAddress, list.get(1));
                    gitOrder = list.get(2);
                    path = list.get(3);
                }
            }
            //第三方授权
            case  2, 3 -> gitOrder = gitThirdOrder(taskCode, fileAddress);

            //svn
            case 5 -> gitOrder = svnOrder(taskCode, fileAddress);
            //错误
            default ->
                    throw new ApplicationException("未知的任务类型");
        }

        Process process = PipelineUtil.process(serverAddress, gitOrder);

        if (PipelineUtil.isNoNull(path)){
            PipelineUtil.deleteFile(new File(path));
        }

        return process;

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

        String tempFile = PipelineUtil.createTempFile(auth.getPrivateKey());
        String userHome = System.getProperty("user.home");
        if (!PipelineUtil.isNoNull(tempFile)){
            throw new ApplicationException("私钥写入失败。");
        }

        //匹配私钥地址
        String address = tempFile.replace(userHome, "").replace("\\", "/");
        String orderClean;
        String orderAdd;
        if (PipelineUtil.findSystemType() == 1){
             orderClean = ".\\git.exe git config --global --unset core.sshCommand";
             orderAdd = ".\\git.exe config --global core.sshCommand \"ssh -i ~" + address + "\"";
        }else {
            orderClean = "/git git config --global --unset core.sshCommand";
            orderAdd = "/git config --global core.sshCommand \"ssh -i ~" + address + "\"";
        }
        list.add(orderClean);
        list.add(orderAdd);
        list.add(s);
        list.add(address);

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
        String branch = code.getCodeBranch();
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
    private String gitThirdOrder(TaskCode taskCode, String fileAddress) throws MalformedURLException, URISyntaxException {
        String authId = taskCode.getAuthId();
        AuthThird auth = thirdService.findOneAuthServer(authId);
        StringBuilder codeAddress = new StringBuilder(taskCode.getCodeAddress());
        StringBuilder stringBuilder = gitUrl(auth.getUsername(), auth.getAccessToken(), codeAddress);
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
        //判断检出类型
        if (auth.getAuthType() == 1){
            String username = auth.getUsername();
            String password = auth.getPassword();
            codeAddress  = codeAddress + " --username "+ " "  +username + " --password " + " " + password + " " +codeDir;
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
    private void typeAddress(int type,int authType,String address) throws ApplicationException{
        String substring = address.substring(0, 3);
        if (type != 5){
            if (substring.equals("htt") && authType == 1){
                return;
            }
            if (substring.equals("git") && authType == 2){
                return;
            }
            throw new ApplicationException("Git地址类型与凭证认证类型不一致。");
        }else {
            if (substring.equals("svn") && authType == 1){
                return;
            }
            substring = address.substring(0, 7);
            if (substring.equals("svn+ssh") && authType == 2){
                return;
            }
            throw new ApplicationException("SVN地址类型与凭证认证类型不一致。");
        }
    }

    /**
     * 获取提交信息
     * @param taskType 类型
     * @param pipelineId 流水线id
     * @return 信息实例
     * @throws IOException 执行失败
     */
    private Process cloneMessage(int taskType,String pipelineId) throws IOException {
        if (taskType == 5){
            return null;
        }
        String order = "git log --pretty=format:\"commit：%cn email：%ae message：%s date：%ad time：%ar \" --date=format:\"%Y-%m-%d %H:%M:%S\" -n 1";
        String fileAddress = PipelineUtil.findFileAddress(pipelineId,1);
        return PipelineUtil.process(fileAddress, order);
    }

    /**
     * 错误
     * @param type 类型
     * @return 错误
     */
    private String[] error(int type){
        String[] strings;
        if (type == 5){
            strings = new String[]{
                "svn: E170000",
                "invalid option",
                "svn: E204900",
                "svn: E170013",
                "svn: E210002",
                "svn: E205000"
            };
            return strings;
        }
        strings = new String[]{
            "fatal: Could not read from remote repository",
            "remote: HTTP Basic: Access denied",
            "fatal: Authentication failed "
        };
        return strings;
    }




}