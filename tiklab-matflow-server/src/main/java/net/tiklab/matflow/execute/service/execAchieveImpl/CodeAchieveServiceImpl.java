package net.tiklab.matflow.execute.service.execAchieveImpl;



import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.MatFlow;
import net.tiklab.matflow.definition.model.MatFlowConfigure;
import net.tiklab.matflow.definition.service.MatFlowCommonService;
import net.tiklab.matflow.definition.model.MatFlowCode;
import net.tiklab.matflow.definition.service.MatFlowCodeService;
import net.tiklab.matflow.definition.service.MatFlowCodeServiceImpl;
import net.tiklab.matflow.execute.model.MatFlowExecHistory;
import net.tiklab.matflow.execute.model.MatFlowExecLog;
import net.tiklab.matflow.execute.service.MatFlowExecServiceImpl;
import net.tiklab.matflow.execute.service.execAchieveService.CodeAchieveService;
import net.tiklab.matflow.execute.service.execAchieveService.CommonAchieveService;
import net.tiklab.matflow.orther.model.MatFlowProcess;
import net.tiklab.matflow.setting.model.Proof;
import net.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.List;

/**
 * 源码管理执行方法
 */

@Service
@Exporter
public class CodeAchieveServiceImpl implements CodeAchieveService {

    @Autowired
    MatFlowCodeService matFlowCodeService;

    @Autowired
    CommonAchieveService commonAchieveService;

    @Autowired
    MatFlowCommonService matFlowCommonService;

    private static final Logger logger = LoggerFactory.getLogger(MatFlowCodeServiceImpl.class);

    // git克隆
    public String clone(MatFlowProcess matFlowProcess){
        List<MatFlowExecHistory> matFlowExecHistoryList = MatFlowExecServiceImpl.matFlowExecHistoryList;
        //开始时间
        MatFlowExecHistory matFlowExecHistory = matFlowProcess.getMatFlowExecHistory();
        MatFlowConfigure matFlowConfigure = matFlowProcess.getMatFlowConfigure();
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        if (matFlowExecHistory.getRunLog() == null){
            matFlowExecHistory.setRunLog("");
        }

        matFlowExecHistory.setRunLog("流水线开始执行。。。。。。。");

        matFlowExecHistoryList.add(matFlowExecHistory);

        MatFlowCode matFlowCode = matFlowCodeService.findOneCode(matFlowConfigure.getTaskId());
        //初始化日志
        MatFlowExecLog matFlowExecLog = commonAchieveService.initializeLog(matFlowExecHistory, matFlowConfigure);

        MatFlow matFlow = matFlowConfigure.getMatFlow();
        //代码保存路径
        String codeDir = matFlowCommonService.getFileAddress() + matFlowConfigure.getMatFlow().getMatflowName();
        File file = new File(codeDir);

        //删除旧的代码
        matFlowCommonService.deleteFile(file);
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n开始分配空间。");
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n空间分配成功。\n");
        matFlowExecHistoryList.add(matFlowExecHistory);

        //获取凭证
        Proof proof = matFlowCode.getProof();
        matFlowProcess.setProof(proof);
        matFlowProcess.setMatFlowExecLog(matFlowExecLog);
        if (proof == null){
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
            return "凭证为空。";
        }
        //分支
        String codeBranch = matFlowCode.getCodeBranch();
        //更新日志
        String s = "开始拉取代码 : " + "\n"
                + "FileAddress : " + file + "\n"
                + "Uri : " + matFlowCode.getCodeAddress() + "\n"
                + "Branch : " + codeBranch + "\n"
                + "proofType : " +proof.getProofType();

        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+s);
        matFlowExecHistoryList.add(matFlowExecHistory);

        try {
             Process process = codeStart(proof, matFlowCode,matFlow.getMatflowName());
             commonAchieveService.log(process.getInputStream(), matFlowProcess, matFlowExecHistoryList);
            commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
        } catch (IOException e) {
            commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            return "系统执行命令错误 \n" + e;
        }catch (URISyntaxException e){
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
            return "git地址错误 \n" + e;
        }catch (ApplicationException e){
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            commonAchieveService.updateState(matFlowProcess,false, matFlowExecHistoryList);
            return "" + e;
        }

        //更新状态
        matFlowExecLog.setRunLog( s + "代码拉取成功" + "\n");
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n"+ "代码拉取成功" +"\n");
        matFlowExecHistoryList.add(matFlowExecHistory);
        commonAchieveService.updateTime(matFlowProcess,beginTime);
        commonAchieveService.updateState(matFlowProcess,true, matFlowExecHistoryList);
        return null;
    }

    /**
     * 执行命令
     * @param proof 凭证
     * @param matFlowCode 配置信息
     * @return 命令执行实例
     * @throws IOException 命令错误
     * @throws URISyntaxException git地址错误
     * @throws ApplicationException 不存在配置
     */
    private Process codeStart(Proof proof, MatFlowCode matFlowCode,String matFlowName) throws IOException, URISyntaxException ,ApplicationException{
        if (matFlowCode == null){
            return null;
        }

        //系统类型
        int systemType = commonAchieveService.getSystemType();
        //源码存放位置
        String fileAddress = matFlowCommonService.getFileAddress()+matFlowName;

        switch (matFlowCode.getType()) {
            case 1, 2, 3, 4 -> {
                //git server地址
                String gitAddress = commonAchieveService.getScm(1);
                if (gitAddress == null) {
                    throw new ApplicationException("不存在Git配置");
                }
                if (proof.getProofType().equals("SSH")) {
                    return null;
                } else {
                    String gitOrder = gitOrder(matFlowCode, fileAddress, systemType);
                    return commonAchieveService.process(gitAddress, gitOrder);
                }
            }
            case 5 -> {
                //svn server地址
                String svnAddress = commonAchieveService.getScm(5);
                if (svnAddress == null) {
                    throw new ApplicationException("不存在Svn配置");
                }
                if (proof.getProofType().equals("SSH")) {
                    return null;
                } else {
                    String gitOrder = svnOrder(matFlowCode, fileAddress, systemType);
                    return commonAchieveService.process(svnAddress, gitOrder);
                }
            }
        }
        return null;
    }

    /**
     * 组装http git命令
     * @param matFlowCode 源码信息
     * @param codeDir 存放位置
     * @return 执行命令
     * @throws URISyntaxException url格式不正确
     * @throws MalformedURLException 不是https或者http
     */
    private String gitOrder(MatFlowCode matFlowCode,String codeDir,int systemType) throws URISyntaxException, MalformedURLException {

        //凭证
        Proof proof = matFlowCode.getProof();
        String username= proof.getProofUsername();
        String password = proof.getProofPassword();

        //地址信息
        StringBuilder url = new StringBuilder(matFlowCode.getCodeAddress());
        String branch = matFlowCode.getCodeBranch();

        String up=username.replace("@", "%40")+":"+password+"@";
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
        if (branch == null){
            order = url+" "+codeDir;
        }else {
            order =" -b "+branch+" "+ url+" "+codeDir;
        }

        //根据不同系统更新命令
        if (systemType == 1){
            order=".\\git.exe clone"+" " + order;
        }else {
            order="./git clone"+" " + order;
        }
        return order;
    }

    /**
     * 组装svn命令
     * @param matFlowCode 源码信息
     * @param codeDir 存放位置
     * @return 执行命令
     */
    private String svnOrder(MatFlowCode matFlowCode,String codeDir,int systemType){
        //凭证
        Proof proof = matFlowCode.getProof();
        String username= proof.getProofUsername();
        String password = proof.getProofPassword();

        //地址信息
        StringBuilder url = new StringBuilder(matFlowCode.getCodeAddress());
        String branch = matFlowCode.getCodeBranch();

        String up=username.replace("@", "%40")+":"+password+"@";

        url.insert(6, up);

        //判断是否存在分支
        String order;
        if (branch == null){
            order = url+" "+codeDir;
        }else {
            //order =" -b "+branch+" "+ url+" "+codeDir;
            order =url+" "+codeDir;
        }
        //根据不同系统更新命令
        if (systemType == 1){
            order=".\\svn.exe checkout"+" "+order;
        }else {
            order="./svn checkout"+" "+order;
        }
        return order;
    }


}
