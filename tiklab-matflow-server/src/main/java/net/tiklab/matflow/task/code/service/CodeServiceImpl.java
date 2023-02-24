package net.tiklab.matflow.task.code.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.matflow.pipeline.definition.service.PipelineStagesTaskService;
import net.tiklab.matflow.pipeline.definition.service.PipelineTasksService;
import net.tiklab.matflow.pipeline.execute.model.PipelineProcess;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.setting.model.PipelineAuth;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.matflow.setting.service.PipelineAuthService;
import net.tiklab.matflow.setting.service.PipelineAuthThirdService;
import net.tiklab.matflow.support.until.PipelineUntil;
import net.tiklab.matflow.task.code.model.PipelineCode;
import net.tiklab.rpc.annotation.Exporter;
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
public class CodeServiceImpl implements CodeService {

    @Autowired
    PipelineExecCommonService commonService;

    @Autowired
    PipelineAuthThirdService authServerServer;

    @Autowired
    PipelineAuthService authServer;

    @Autowired
    PipelineTasksService tasksService;

    @Autowired
    PipelineStagesTaskService stagesTaskServer;

    // git克隆
    public boolean clone(PipelineProcess pipelineProcess, String configId ,int taskType){

        Pipeline pipeline = pipelineProcess.getPipeline();

        Boolean variableCond = commonService.variableCond(pipeline.getId(), configId);

        Object o;
        if (pipeline.getType() == 1){
            o = tasksService.findOneTasksTask(configId);
        }else {
            o = stagesTaskServer.findOneStagesTasksTask(configId);
        }
        PipelineCode pipelineCode = (PipelineCode) o;
        String name = pipelineCode.getName();

        //替换配置中的变量
        String key = commonService.variableKey(pipeline.getId(), configId, pipelineCode.getCodeAddress());
        pipelineCode.setCodeAddress(key);
        String variableKey = commonService.variableKey(pipeline.getId(), configId, pipelineCode.getCodeBranch());
        pipelineCode.setCodeBranch(variableKey);

        if (!variableCond){
            commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"任务："+ name+"执行条件不满足,跳过执行。");
            return true;
        }

        commonService.updateExecLog(pipelineProcess, PipelineUntil.date(4)+"执行任务："+ name);

        pipelineCode.setType(taskType);

        if (!PipelineUntil.isNoNull(pipelineCode.getCodeAddress())){
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"代码源地址未配置。");
            return false;
        }

        //代码保存路径
        String codeDir = PipelineUntil.findFileAddress(pipeline.getId(),1);
        File file = new File(codeDir);

        //删除旧的代码
        boolean b = false;
        while (!b){
            if (file.exists()){
                b = PipelineUntil.deleteFile(file);
            }else {
                b = true;
            }
        }

        //分支
        String codeBranch = pipelineCode.getCodeBranch();
        if(!PipelineUntil.isNoNull(codeBranch)){
            codeBranch = "master";
        }

        //更新日志
        String s = PipelineUntil.date(4) + "Uri : " + pipelineCode.getCodeAddress() + "\n"
                + PipelineUntil.date(4) + "Branch : " + codeBranch ;

        commonService.updateExecLog(pipelineProcess,s);

        commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"分配源码空间..." );

        try {
            //命令执行失败
            Process process = codeStart(pipelineCode,pipeline.getId());

            pipelineProcess.setError(error(pipelineCode.getType()));
            if (pipelineCode.getType() != 5){
                pipelineProcess.setEnCode("UTF-8");
            }
            commonService.execState(pipelineProcess,process,name);

            process.destroy();
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+ "空间分配成功。" );
            //获取提交信息
            Process message = cloneMessage(pipelineCode.getType(),pipeline.getId());
            if (message != null){
                pipelineProcess.setInputStream(message.getInputStream());
                pipelineProcess.setErrInputStream(message.getErrorStream());
                commonService.log(pipelineProcess);
                message.destroy();
            }

        } catch (IOException e) {
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"系统执行命令错误 \n" + e);
            return false;
        }catch (URISyntaxException e){
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"Git地址错误 \n" + e);
            return false;
        }catch (ApplicationException e){
            commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+ e);
            return false;
        }
        commonService.updateExecLog(pipelineProcess,PipelineUntil.date(4)+"任务"+name+"执行成功。");
        return true;
    }

    /**
     * 执行命令
     * @param pipelineCode 配置信息
     * @return 命令执行实例
     * @throws IOException 命令错误
     * @throws URISyntaxException git地址错误
     * @throws ApplicationException 不存在配置
     */
    private Process codeStart(PipelineCode pipelineCode, String pipelineId) throws IOException, URISyntaxException ,ApplicationException{

        //效验地址是否应用程序地址
        String serverAddress = commonService.getScm(pipelineCode.getType());
        if (serverAddress == null ){
            if (pipelineCode.getType() != 5){
                throw new ApplicationException(50001,"未配置git程序地址");
            }else {
                throw new ApplicationException(50001,"未配置SVN程序地址");
            }
        }

        //效验地址应用程序的合法性
        PipelineUntil.validFile(serverAddress,pipelineCode.getType());

        //源码存放位置
        String fileAddress = PipelineUntil.findFileAddress(pipelineId,1);
        String gitOrder;
        String path = null;
        switch (pipelineCode.getType()) {
            //账号密码或ssh登录
            case 1, 4 -> {
                List<String> list = gitUpOrder(pipelineCode, fileAddress);
                gitOrder = list.get(0);
                if (list.size() > 1){
                    PipelineUntil.process(serverAddress, list.get(0));
                    PipelineUntil.process(serverAddress, list.get(1));
                    gitOrder = list.get(2);
                    path = list.get(3);
                }
            }
            //第三方授权
            case  2, 3 -> gitOrder = gitThirdOrder(pipelineCode, fileAddress);

            //svn
            case 5 -> gitOrder = svnOrder(pipelineCode, fileAddress);
            //错误
            default ->
                    throw new ApplicationException("未知的任务类型");
        }

        Process process = PipelineUntil.process(serverAddress, gitOrder);

        if (PipelineUntil.isNoNull(path)){
            PipelineUntil.deleteFile(new File(path));
        }

        return process;

    }

    /**
     * 组装http git命令
     * @param pipelineCode 源码信息
     * @param fileAddress 存放位置
     * @return 执行命令
     * @throws URISyntaxException url格式不正确
     * @throws MalformedURLException 不是https或者http
     */
    private List<String> gitUpOrder(PipelineCode pipelineCode, String fileAddress)
            throws URISyntaxException, MalformedURLException , ApplicationException {
        String authId = pipelineCode.getAuthId();
        PipelineAuth auth = authServer.findOneAuth(authId);

        List<String> list = new ArrayList<>();

        StringBuilder codeAddress = new StringBuilder(pipelineCode.getCodeAddress());

        //没有凭证
        if (auth == null){
            String s = gitBranch(codeAddress, pipelineCode, fileAddress);
            list.add(s);
            return list;
        }

        typeAddress(pipelineCode.getType(),auth.getAuthType(),pipelineCode.getCodeAddress());

        if (auth.getAuthType() == 1){
            gitUrl(auth.getUsername(), auth.getPassword(), codeAddress);
        }

        String s = gitBranch(codeAddress, pipelineCode, fileAddress);

        if (auth.getAuthType() == 1 ){
            list.add(s);
            return list;
        }

        String tempFile = PipelineUntil.createTempFile(auth.getPrivateKey());
        String userHome = System.getProperty("user.home");
        if (!PipelineUntil.isNoNull(tempFile)){
            throw new ApplicationException("私钥写入失败。");
        }

        //匹配私钥地址
        String address = tempFile.replace(userHome, "").replace("\\", "/");
        String orderClean;
        String orderAdd;
        if (PipelineUntil.findSystemType() == 1){
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
    private String gitBranch(StringBuilder url ,PipelineCode code,String codeDir){
        String branch = code.getCodeBranch();
        //判断是否存在分支
        String order;
        if (!PipelineUntil.isNoNull(branch)){
            order = url+" "+codeDir;
        }else {
            order =" -b "+branch+" "+ url+" "+codeDir;
        }
        //根据不同系统更新命令
        if (PipelineUntil.findSystemType() == 1){
            order=".\\git.exe clone"+" " + order;
        }else {
            order="./git clone"+" " + order;
        }

        return order;
    }

    /**
     * 第三方认证授权
     * @param pipelineCode 配置
     * @param fileAddress 地址
     * @return 命令
     * @throws URISyntaxException 不是个url
     * @throws MalformedURLException 不属于http或者https
     */
    private String gitThirdOrder(PipelineCode pipelineCode,String fileAddress) throws MalformedURLException, URISyntaxException {
        String authId = pipelineCode.getAuthId();
        PipelineAuthThird auth = authServerServer.findOneAuthServer(authId);
        StringBuilder codeAddress = new StringBuilder(pipelineCode.getCodeAddress());
        StringBuilder stringBuilder = gitUrl(auth.getUsername(), auth.getAccessToken(), codeAddress);
        return gitBranch(stringBuilder,pipelineCode,fileAddress);
    }

    /**
     * 组装svn命令
     * @param pipelineCode 源码信息
     * @param codeDir 存放位置
     * @return 执行命令
     */
    private String svnOrder(PipelineCode pipelineCode,String codeDir){
        //凭证
        String authId = pipelineCode.getAuthId();
        PipelineAuth auth = authServer.findOneAuth(authId);

        String codeAddress = pipelineCode.getCodeAddress();

        String svnFile = pipelineCode.getSvnFile();

        //检出文件夹
        if (PipelineUntil.isNoNull(svnFile)){
            codeAddress = codeAddress + "/./" +svnFile;
        }
        //判断检出类型
        if (auth.getAuthType() == 1){
            String username = auth.getUsername();
            String password = auth.getPassword();
            codeAddress  = codeAddress + " --username "+ " "  +username + " --password " + " " + password + " " +codeDir;
        }
        //不同系统检出
        if (PipelineUntil.findSystemType() == 1){
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
        String fileAddress = PipelineUntil.findFileAddress(pipelineId,1);
        return PipelineUntil.process(fileAddress, order);
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
