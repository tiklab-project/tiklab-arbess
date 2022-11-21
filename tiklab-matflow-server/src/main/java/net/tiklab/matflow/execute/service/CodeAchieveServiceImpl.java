package net.tiklab.matflow.execute.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.matflow.definition.model.PipelineCode;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuth;
import net.tiklab.matflow.setting.model.PipelineAuthThird;
import net.tiklab.matflow.setting.service.PipelineAuthServer;
import net.tiklab.matflow.setting.service.PipelineAuthThirdServer;
import net.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 源码管理执行方法
 */

@Service
@Exporter
public class CodeAchieveServiceImpl implements CodeAchieveService {

    @Autowired
    ConfigCommonService commonService;

    @Autowired
    PipelineAuthThirdServer authServerServer;

    @Autowired
    PipelineAuthServer authServer;


    // git克隆
    public boolean clone(PipelineProcess pipelineProcess, PipelineCode pipelineCode){

        if (!PipelineUntil.isNoNull(pipelineCode.getCodeAddress())){
            commonService.execHistory(pipelineProcess,"代码源地址未配置。");
            return false;
        }

        Pipeline pipeline = pipelineProcess.getPipeline();

        //代码保存路径
        String codeDir = PipelineUntil.findFileAddress() + pipeline.getPipelineName();
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

        commonService.execHistory(pipelineProcess,"分配源码空间。\n空间分配成功。");

        //分支
        String codeBranch = pipelineCode.getCodeBranch();
        if(!PipelineUntil.isNoNull(codeBranch)){
            codeBranch = "master";
        }

        //更新日志
        String s = "开始拉取代码 : " + "\n"
                + "FileAddress : " + file + "\n"
                + "Uri : " + pipelineCode.getCodeAddress() + "\n"
                + "Branch : " + codeBranch + "\n"
                + "代码拉取中。。。。。。 ";

        commonService.execHistory(pipelineProcess,s);

        try {
            //命令执行失败
            Process process = codeStart(pipelineCode,pipeline.getPipelineName());
            if (process == null){
                commonService.execHistory(pipelineProcess,"代码拉取失败。");
                return false;
            }
            //项目执行过程失败
            int log = commonService.log(process.getInputStream(), process.getErrorStream(), pipelineProcess,"UTF-8");
            if (log == 0){
                commonService.execHistory(pipelineProcess,"代码拉取失败。 \n" );
                return false;
            }
        } catch (IOException e) {
            commonService.execHistory(pipelineProcess,"系统执行命令错误 \n" + e);
            return false;
        }catch (URISyntaxException e){
            commonService.execHistory(pipelineProcess,"git地址错误 \n" + e);
            return false;
        }catch (ApplicationException e){
            commonService.execHistory(pipelineProcess,"" + e);
            return false;
        }
        commonService.execHistory(pipelineProcess,"代码拉取成功");
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
        switch (pipelineCode.getType()) {
            //账号密码或ssh登录
            case 1, 4 -> {
                List<String> list = gitUpOrder(pipelineCode, fileAddress);
                if (list.size() > 1){
                    PipelineUntil.process(serverAddress, list.get(0));
                    PipelineUntil.process(serverAddress, list.get(1));
                }
                gitOrder = list.get(list.size()-1);
            }
            //第三方授权
            case  2, 3 -> gitOrder = gitThirdOrder(pipelineCode, fileAddress);
            //svn
            case 5 -> gitOrder = svnOrder(pipelineCode, fileAddress);
            //错误
            default ->
                throw new ApplicationException(50001,"没有类型为"+pipelineCode.getType()+"的源码配置");
        }
        return PipelineUntil.process(serverAddress, gitOrder);

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

        StringBuilder codeAddress = new StringBuilder(pipelineCode.getCodeAddress());

        if (auth.getAuthType() == 1){
            urlType(auth.getUsername(), auth.getPassword(), codeAddress);
        }

        String s = addBranch(codeAddress, pipelineCode, fileAddress);

        List<String> list = new ArrayList<>();

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

        String orderClean = ".\\git.exe git config --global --unset core.sshCommand";

        String orderAdd = ".\\git.exe config --global core.sshCommand \"ssh -i ~" + address + "\"";

        list.add(orderClean);
        list.add(orderAdd);
        list.add(s);

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
    private StringBuilder urlType(String username,String password,StringBuilder url) throws URISyntaxException, MalformedURLException {
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
    private String addBranch(StringBuilder url ,PipelineCode code,String codeDir){

        String branch = code.getCodeBranch();
        int type = code.getType();

        //判断是否存在分支
        String order;
        if (!PipelineUntil.isNoNull(branch)){
            order = url+" "+codeDir;
        }else {
            order =" -b "+branch+" "+ url+" "+codeDir;
        }

        if (type == 5){
            //根据不同系统更新命令
            if (PipelineUntil.findSystemType() == 1){
                order=".\\svn.exe checkout"+" "+order;
            }else {
                order="./svn checkout"+" "+order;
            }
            return order;
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
        StringBuilder stringBuilder = urlType(auth.getUsername(), auth.getPassword(), codeAddress);
        return addBranch(stringBuilder,pipelineCode,fileAddress);
    }

    /**
     * 组装svn命令
     * @param pipelineCode 源码信息
     * @param codeDir 存放位置
     * @return 执行命令
     */
    private String svnOrder(PipelineCode pipelineCode,String codeDir){
        //凭证
        Map<String, String> map = findUserPassword(pipelineCode.getAuthId());
        //地址信息
        StringBuilder url = new StringBuilder(pipelineCode.getCodeAddress());
        if (map == null){
            return addBranch(url,pipelineCode,codeDir);
        }
        String username = map.get("username");
        String password = map.get("password");

        String up=username.replace("@", "%40")+":"+password+"@";

        url.insert(6, up);

        //判断是否存在分支
       return addBranch(url,pipelineCode,codeDir);
    }

    /**
     * 获取不同类型的用户名密码
     * @param authId 授权id
     * @return 用户名密码
     */
    private Map<String,String> findUserPassword(String authId){
        Map<String, String> map = new HashMap<>();
        String username = null;
        String password = null;
        String key = null;
        String status = "username";
        PipelineAuthThird authServerThird = authServerServer.findOneAuthServer(authId);
        PipelineAuth auth = authServer.findOneAuth(authId);
        if (authServerThird == null && auth == null){
           return null;
        }
       if (authServerThird != null){
           username= authServerThird.getUsername();
           password = authServerThird.getAccessToken();
       }
       if(auth != null) {
           if (auth.getAuthType() == 2){
               key = auth.getPrivateKey();
               status = "key";
           }
           username= auth.getUsername();
           password = auth.getPassword();
       }
        map.put("username",username);
        map.put("password",password);
        map.put("key",key);
        map.put("status",status);
        return map;
    }





}
