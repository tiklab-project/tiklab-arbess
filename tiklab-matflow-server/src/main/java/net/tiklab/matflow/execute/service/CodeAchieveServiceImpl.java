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
import java.util.HashMap;
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
    private Process codeStart(PipelineCode pipelineCode,String pipelineName) throws IOException, URISyntaxException ,ApplicationException{
        //源码存放位置
        String fileAddress = PipelineUntil.findFileAddress()+pipelineName;
        switch (pipelineCode.getType()) {
            case 1, 2, 3, 4 -> {
                //git server地址
                String gitAddress = commonService.getScm(1);
                PipelineUntil.validFile(gitAddress,1);
                String gitOrder = gitOrder(pipelineCode, fileAddress);
                return PipelineUntil.process(gitAddress, gitOrder);
            }
            case 5 -> {
                //svn server地址
                String svnAddress = commonService.getScm(5);
                PipelineUntil.validFile(svnAddress,5);
                String gitOrder = svnOrder(pipelineCode, fileAddress);
                return PipelineUntil.process(svnAddress, gitOrder);
            }
            default -> {
                return null;
            }
        }
    }


    /**
     * 组装http git命令
     * @param pipelineCode 源码信息
     * @param codeDir 存放位置
     * @return 执行命令
     * @throws URISyntaxException url格式不正确
     * @throws MalformedURLException 不是https或者http
     */
    private String gitOrder(PipelineCode pipelineCode,String codeDir) throws URISyntaxException, MalformedURLException {

        //凭证
        Map<String, String> map = findUserPassword(pipelineCode.getAuthId(), pipelineCode.getType());

        StringBuilder url = new StringBuilder(pipelineCode.getCodeAddress());
        String branch = pipelineCode.getCodeBranch();
        if (map == null){
            return branch(url,branch,codeDir,pipelineCode.getType());
        }

        String username = map.get("username");
        String password = map.get("password");

        boolean userName = PipelineUntil.isNoNull(username);
        boolean passWord = PipelineUntil.isNoNull(password);

        //地址信息

        String up ;
        if (userName && passWord){
            up=username.replace("@", "%40")+":"+password+"@";
        }else if (userName && !passWord){
            up=username.replace("@", "%40");
        }else {
            up = "";
        }

        //获取url类型
        URL urls = new URL(url.toString());
        String urlType = urls.toURI().getScheme();

        //根据不同类型拼出不同地址
        if (urlType.equals("http")){
            url.insert(7, up);
        }else {
            url.insert(8, up);
        }

        return branch(url,branch,codeDir,pipelineCode.getType());
    }



    /**
     * 组装svn命令
     * @param pipelineCode 源码信息
     * @param codeDir 存放位置
     * @return 执行命令
     */
    private String svnOrder(PipelineCode pipelineCode,String codeDir){
        //凭证
        Map<String, String> map = findUserPassword(pipelineCode.getAuthId(), pipelineCode.getType());
        String branch = pipelineCode.getCodeBranch();
        //地址信息
        StringBuilder url = new StringBuilder(pipelineCode.getCodeAddress());
        if (map == null){
            return branch(url,branch,codeDir,5);
        }
        String username = map.get("username");
        String password = map.get("password");

        String up=username.replace("@", "%40")+":"+password+"@";

        url.insert(6, up);

        //判断是否存在分支
       return branch(url,branch,codeDir,5);
    }

    /**
     * 获取不同类型的用户名密码
     * @param authId 授权id
     * @param type 类型
     * @return 用户名密码
     */
    private Map<String,String> findUserPassword(String authId,int type){
        Map<String, String> map = new HashMap<>();
        String username;
        String password;
        PipelineAuthThird authServerS = authServerServer.findOneAuthServer(authId);
        PipelineAuth auth = authServer.findOneAuth(authId);
        if (authServerS == null && auth == null){
           return null;
        }
       if (authServerS != null){
           username= authServerS.getUsername();
           password = authServerS.getAccessToken();
       }else {
           username= auth.getUsername();
           password = auth.getPassword();
       }
        map.put("username",username);
        map.put("password",password);
        return map;
    }


    private String branch(StringBuilder url ,String branch,String codeDir,int type){

        //判断是否存在分支
        String order;
        if (branch == null || branch.equals("")){
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


}
