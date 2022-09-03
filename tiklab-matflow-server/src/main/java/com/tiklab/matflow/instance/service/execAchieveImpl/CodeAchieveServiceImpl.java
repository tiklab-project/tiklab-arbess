package com.tiklab.matflow.instance.service.execAchieveImpl;



import com.tiklab.matflow.definition.model.MatFlowConfigure;
import com.tiklab.matflow.definition.service.MatFlowCommonService;
import com.tiklab.matflow.execute.model.MatFlowCode;
import com.tiklab.matflow.execute.service.MatFlowCodeService;
import com.tiklab.matflow.execute.service.MatFlowCodeServiceImpl;
import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowExecLog;
import com.tiklab.matflow.instance.model.MatFlowProcess;
import com.tiklab.matflow.instance.service.execAchieveService.CodeAchieveService;
import com.tiklab.matflow.instance.service.execAchieveService.CommonAchieveService;
import com.tiklab.matflow.setting.proof.model.Proof;
import com.tiklab.rpc.annotation.Exporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
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
    public int clone(MatFlowProcess matFlowProcess, List<MatFlowExecHistory> matFlowExecHistoryList){
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

        //代码保存路径
        String codeDir = matFlowCommonService.getFileAddress() + matFlowConfigure.getMatFlow().getMatflowName();
        File file = new File(codeDir);

        //删除旧的代码
        matFlowCommonService.deleteFile(file);
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n开始分配代码空间。");
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n代码空间分配成功。\n");
        matFlowExecHistoryList.add(matFlowExecHistory);

        //获取凭证
        Proof proof = matFlowCode.getProof();
        matFlowProcess.setProof(proof);
        matFlowProcess.setMatFlowExecLog(matFlowExecLog);
        if (proof == null){
            logger.info("凭证为空。");
            commonAchieveService.updateTime(matFlowProcess,beginTime);
            commonAchieveService.updateState(matFlowProcess,"凭证为空。", matFlowExecHistoryList);
            return 0;
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

        //throw new ApplicationException(50000, "地址错误");


        //更新状态
        matFlowExecLog.setRunLog( s + "代码拉取成功" + "\n");
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n"+ "代码拉取成功" +"\n");
        matFlowExecHistoryList.add(matFlowExecHistory);
        commonAchieveService.updateTime(matFlowProcess,beginTime);
        commonAchieveService.updateState(matFlowProcess,null, matFlowExecHistoryList);
        return 1;
    }


    public void codeStart(Proof proof, MatFlowCode matFlowCode, String path) {
        if (matFlowCode == null){
            return;
        }

        switch (matFlowCode.getType()){
            case 1,2,3,4 :
                if (proof.getProofType().equals("SSH")){

                }else {
                    if (proof.getProofUsername() == null || proof.getProofPassword() == null){

                    }


                }
                break;
            case 5:
                if (proof.getProofType().equals("SSH")){

                }else {

                }
                break;
        }
    }

    public String getOrder(){
        int systemType = commonAchieveService.getSystemType();


        return null;
    }

    /**
     * 组装git命令
     * @param matFlowCode 源码信息
     * @param codeDir 存放位置
     * @return 命令信息
     * @throws URISyntaxException url格式不正确
     * @throws MalformedURLException 不是https或者http
     */
    public String getGitOrder(MatFlowCode matFlowCode,String codeDir,int systemType) throws URISyntaxException, MalformedURLException {
        Proof proof = matFlowCode.getProof();
        String username= proof.getProofUsername();
        String password = proof.getProofPassword();
        StringBuilder url = new StringBuilder(matFlowCode.getCodeAddress());
        String branch = matFlowCode.getCodeBranch();
        String urlType;
        String up=username.replace("@", "%40")+":"+password+"@";

        //获取url类型
        URL urls = new URL(url.toString());
        urlType = urls.toURI().getScheme();

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










}
