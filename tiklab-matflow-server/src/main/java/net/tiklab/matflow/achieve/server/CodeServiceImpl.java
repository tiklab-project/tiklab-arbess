package net.tiklab.matflow.achieve.server;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.execute.service.PipelineExecCommonService;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuth;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.matflow.setting.service.PipelineAuthServer;
import net.tiklab.matflow.setting.service.PipelineAuthThirdServer;
import net.tiklab.matflow.task.model.PipelineCode;
import net.tiklab.matflow.task.server.PipelineCodeService;
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
    PipelineAuthThirdServer authServerServer;

    @Autowired
    PipelineAuthServer authServer;

    @Autowired
    PipelineCodeService codeService;


    // git克隆
    public boolean clone(PipelineProcess pipelineProcess, String configId ,int taskType){

        String log = PipelineUntil.date(4);

        PipelineCode pipelineCode = codeService.findOneCodeConfig(configId);
        pipelineCode.setType(taskType);

        if (!PipelineUntil.isNoNull(pipelineCode.getCodeAddress())){
            commonService.execHistory(pipelineProcess,log+"代码源地址未配置。");
            return false;
        }


        Pipeline pipeline = pipelineProcess.getPipeline();

        //代码保存路径
        String codeDir = PipelineUntil.findFileAddress() + pipeline.getName();
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

        commonService.execHistory(pipelineProcess,log+"分配源码空间。" +"\n"+ log+ "空间分配成功。");

        //分支
        String codeBranch = pipelineCode.getCodeBranch();
        if(!PipelineUntil.isNoNull(codeBranch)){
            codeBranch = "master";
        }

        //更新日志
        String s =log + "开始克隆代码 : " + "\n"
                +log + "FileAddress : " + file + "\n"
                +log + "Uri : " + pipelineCode.getCodeAddress() + "\n"
                +log + "Branch : " + codeBranch + "\n"
                +log + "代码克隆中。。。。。。 ";

        commonService.execHistory(pipelineProcess,s);

        try {
            //命令执行失败
            Process process = codeStart(pipelineCode,pipeline.getName());
            if (process == null){
                commonService.execHistory(pipelineProcess,log+"代码克隆失败。");
                return false;
            }
            //项目执行过程失败
            pipelineProcess.setInputStream(process.getInputStream());
            pipelineProcess.setErrInputStream(process.getErrorStream());
            pipelineProcess.setError(error(pipelineCode.getType()));
            if (pipelineCode.getType() != 5){
                pipelineProcess.setEnCode("UTF-8");
            }
            int status = commonService.log(pipelineProcess);
            if (status == 0){
                commonService.execHistory(pipelineProcess,log+"代码克隆失败。 " );
                return false;
            }
        } catch (IOException e) {
            commonService.execHistory(pipelineProcess,log+"系统执行命令错误 \n" + e);
            return false;
        }catch (URISyntaxException e){
            commonService.execHistory(pipelineProcess,log+"Git地址错误 \n" + e);
            return false;
        }catch (ApplicationException e){
            commonService.execHistory(pipelineProcess,log+ e);
            return false;
        }
        commonService.execHistory(pipelineProcess,log+"代码克隆成功");
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
    private Process codeStart(PipelineCode pipelineCode, String pipelineName) throws IOException, URISyntaxException ,ApplicationException{

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
        String fileAddress = PipelineUntil.findFileAddress()+pipelineName;
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
                throw new ApplicationException(50001,"没有类型为"+pipelineCode.getType()+"的源码配置");
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
    private List<String> gitUpOrder(PipelineCode pipelineCode, String fileAddress) throws URISyntaxException, MalformedURLException , ApplicationException {
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
        StringBuilder stringBuilder = gitUrl(auth.getUsername(), auth.getPassword(), codeAddress);
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
