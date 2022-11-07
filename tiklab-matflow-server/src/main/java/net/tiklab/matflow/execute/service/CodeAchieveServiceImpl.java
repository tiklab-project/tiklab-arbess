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
import java.sql.Timestamp;
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

        //开始时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        Pipeline pipeline = pipelineProcess.getPipeline();
        pipelineProcess.setBeginTime(beginTime);

        //代码保存路径
        String codeDir = PipelineUntil.findFileAddress() + pipeline.getPipelineName();
        File file = new File(codeDir);
        //删除旧的代码
        PipelineUntil.deleteFile(file);
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
                commonService.updateState(pipelineProcess,false);
                return false;
            }
            //项目执行过程失败
            int log = commonService.log(process.getInputStream(), process.getErrorStream(), pipelineProcess,"UTF-8");
            if (log == 0){
                commonService.execHistory(pipelineProcess,"代码拉取失败。 \n" );
                commonService.updateState(pipelineProcess,false);
                return false;
            }
        } catch (IOException e) {
            commonService.execHistory(pipelineProcess,"系统执行命令错误 \n" + e);
            commonService.updateState(pipelineProcess,false);
            return false;
        }catch (URISyntaxException e){
            commonService.execHistory(pipelineProcess,"git地址错误 \n" + e);
            commonService.updateState(pipelineProcess,false);
            return false;
        }catch (ApplicationException e){
            commonService.execHistory(pipelineProcess,"" + e);
            commonService.updateState(pipelineProcess,false);
            return false;
        }
        commonService.execHistory(pipelineProcess,"代码拉取成功");
        commonService.updateState(pipelineProcess,true);
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
                validFile(gitAddress,1);
                String gitOrder = gitOrder(pipelineCode, fileAddress);
                return PipelineUntil.process(gitAddress, gitOrder);
            }
            case 5 -> {
                //svn server地址
                String svnAddress = commonService.getScm(5);
                validFile(svnAddress,2);
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

        String username = map.get("username");
        String password = map.get("password");

        boolean userName = PipelineUntil.isNoNull(username);
        boolean passWord = PipelineUntil.isNoNull(password);

        //地址信息

        String up = "";
        if (userName && passWord){
            up=username.replace("@", "%40")+":"+password+"@";
        }else if (userName && !passWord){
            up=username.replace("@", "%40");
        }else {
            up = "";
        }

        StringBuilder url = new StringBuilder(pipelineCode.getCodeAddress());
        String branch = pipelineCode.getCodeBranch();
        //获取url类型
        URL urls = new URL(url.toString());
        String urlType = urls.toURI().getScheme();

        //根据不同类型拼出不同地址
        if (urlType.equals("http")){
            url.insert(7, up);
        }else {
            url.insert(8, up);
        }

        //判断是否存在分支
        String order;
        if (branch == null || branch.equals("")){
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
     * 组装svn命令
     * @param pipelineCode 源码信息
     * @param codeDir 存放位置
     * @return 执行命令
     */
    private String svnOrder(PipelineCode pipelineCode,String codeDir){
        //凭证
        Map<String, String> map = findUserPassword(pipelineCode.getAuthId(), pipelineCode.getType());

        String username = map.get("username");
        String password = map.get("password");

        //地址信息
        StringBuilder url = new StringBuilder(pipelineCode.getCodeAddress());
        String branch = pipelineCode.getCodeBranch();

        String up=username.replace("@", "%40")+":"+password+"@";

        url.insert(6, up);

        //判断是否存在分支
        String order;
        if (branch == null || branch.equals("")){
            order = url+" "+codeDir;
        }else {
            order =" -b "+branch+" "+ url+" "+codeDir;
        }
        //根据不同系统更新命令
        if (PipelineUntil.findSystemType() == 1){
            order=".\\svn.exe checkout"+" "+order;
        }else {
            order="./svn checkout"+" "+order;
        }
        return order;
    }

    /**
     * 效验地址是否存在可执行文件
     * @param address 地址
     * @param type 类型 1:git,2:svn
     * @throws ApplicationException 地址不合法
     */
    private void validFile(String address ,int type) throws ApplicationException{
        int file = PipelineUntil.validFile(address,1);
        if (type == 1){
            if (address == null) {
                throw new ApplicationException("不存在Git配置");
            }
            if (file == 1){
                throw new ApplicationException("Git地址配置错误："+address +" 不是个目录。");
            }
            if (file == 2){
                throw new ApplicationException("Git地址配置错误："+address +" 找不到git的可执行文件。");
            }
            return;
        }
        file = PipelineUntil.validFile(address, 5);
        if (address == null) {
            throw new ApplicationException("不存在Svn配置");
        }
        if (file == 1){
            throw new ApplicationException("Git地址配置错误："+address +" 不是个目录。");
        }
        if (file == 2){
            throw new ApplicationException("Git地址配置错误："+address +" 找不到svn的可执行文件。");
        }

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
        if (type == 2 || type == 3){
            PipelineAuthThird authServer = authServerServer.findOneAuthServer(authId);
            username= authServer.getUsername();
            password = authServer.getAccessToken();
        }else {
            PipelineAuth auth = authServer.findOneAuth(authId);
            username= auth.getUsername();
            password = auth.getPassword();
        }
        map.put("username",username);
        map.put("password",password);
        return map;
    }

}
