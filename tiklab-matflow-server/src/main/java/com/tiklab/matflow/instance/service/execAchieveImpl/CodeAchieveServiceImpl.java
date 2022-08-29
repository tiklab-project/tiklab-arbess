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
import java.io.IOException;
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

        //代码克隆路径
        String path = matFlowCommonService.getFileAddress() + matFlowConfigure.getMatFlow().getMatflowName();
        File file = new File(path);

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

        String codeBranch = matFlowCode.getCodeBranch();
        if (codeBranch == null){
            codeBranch = "master";
        }

        //更新日志
        String s = "开始拉取代码 : " + "\n"
                + "FileAddress : " + file + "\n"
                + "Uri : " + matFlowCode.getCodeAddress() + "\n"
                + "Branch : " + codeBranch + "\n"
                + "proofType : " +proof.getProofType();
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+s);
        matFlowExecHistoryList.add(matFlowExecHistory);

        ////开始克隆
        //try {
        //codeStart(proof, matFlowCode,path);
        //} catch (GitAPIException | IOException | SVNException e) {
        //    commonAchieveService.updateTime(matFlowProcess,beginTime);
        //    commonAchieveService.updateState(matFlowProcess,e.toString(), matFlowExecHistoryList);
        //    return 0;
        //}

        //更新状态
        matFlowExecLog.setRunLog( s + "代码拉取成功" + "\n");
        matFlowExecHistory.setRunLog(matFlowExecHistory.getRunLog()+"\n"+ "代码拉取成功" +"\n");
        matFlowExecHistoryList.add(matFlowExecHistory);
        commonAchieveService.updateTime(matFlowProcess,beginTime);
        commonAchieveService.updateState(matFlowProcess,null, matFlowExecHistoryList);
        return 1;
    }

    /**
     * 区分不同源
     * @param proof 凭证
     * @param matFlowCode 配置信息
     * @param path 拉取地址
     * @throws GitAPIException git拉取异常
     * @throws IOException 地址找不到
     */
    //public void codeStart(Proof proof, MatFlowCode matFlowCode, String path) throws GitAPIException, IOException, SVNException {
    //    if (matFlowCode == null){
    //        return;
    //    }
    //    switch (matFlowCode.getType()){
    //        case 1,2,3,4 :
    //            if (proof.getProofType().equals("SSH")){
    //                File file = File.createTempFile("matFlow", ".txt");
    //                matFlowCommonService.writePrivateKeyPath(proof.getProofPassword(),file.getAbsolutePath());
    //                sshClone(matFlowCode.getCodeAddress(), file.getAbsolutePath(), path, matFlowCode.getCodeBranch());
    //                file.deleteOnExit();
    //            }else {
    //                if (proof.getProofUsername() == null || proof.getProofPassword() == null){
    //                    throw new IOException("账户或密码不能为空。");
    //                }
    //                UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(proof.getProofUsername(), proof.getProofPassword());
    //                clone(path, matFlowCode.getCodeAddress(), provider, matFlowCode.getCodeBranch());
    //            }
    //            break;
    //        case 5:
    //            if (proof.getProofType().equals("SSH")){
    //                sshSvn(matFlowCode,proof,path);
    //            }else {
    //                svn(matFlowCode,proof,path);
    //            }
    //            break;
    //    }
    //}

    /**
     * git代码拉取
     * @param gitAddress 本地文件地址
     * @param gitUrl git地址
     * @param credentialsProvider 凭证
     * @param branch 分支
     */
    //private void clone(String gitAddress, String gitUrl, CredentialsProvider credentialsProvider, String branch) throws GitAPIException, JGitInternalException {
    //    File fileAddress = new File(gitAddress);
    //    Git git = Git.cloneRepository()
    //            .setURI(gitUrl)
    //            .setCredentialsProvider(credentialsProvider)
    //            .setDirectory(fileAddress)
    //            .setBranch(branch)
    //            .call();
    //    git.close();
    //}


    /**
     * SSH克隆代码
     * @param url 克隆url
     * @param privateKeyPath 秘钥存放地址
     * @param clonePath 克隆位置
     * @param branch 分支
     */
    //public void sshClone(String url, String privateKeyPath,String clonePath,String branch) throws GitAPIException, JGitInternalException {
    //
    //    Git git;
    //    File fileAddress = new File(clonePath);
    //    SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
    //        @Override
    //        protected void configure(OpenSshConfig.Host host, Session session ) {
    //            //取消host文件验证
    //            session.setConfig("StrictHostKeyChecking","no");
    //        }
    //        @Override
    //        protected JSch createDefaultJSch(FS fs) throws JSchException {
    //            JSch defaultJSch = super.createDefaultJSch(fs);
    //            defaultJSch.addIdentity(privateKeyPath);
    //            return defaultJSch;
    //        }
    //    };
    //    git = Git.cloneRepository()
    //            .setURI(url)
    //            .setBranch(branch)
    //            .setTransportConfigCallback(transport -> {
    //                SshTransport sshTransport = (SshTransport) transport;
    //                sshTransport.setSshSessionFactory(sshSessionFactory);
    //            })
    //            .setDirectory(fileAddress)
    //            .call();
    //    git.close();
    //}

    /**
     * SVN 拉取代码
     * @param matFlowCode 配置信息
     * @param proof 凭证信息
     * @param path 文件地址
     */
    //public void svn(MatFlowCode matFlowCode, Proof proof, String path) throws SVNException {
    //    matFlowCommonService.deleteFile(new File(path));
    //    SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
    //    char[] password = proof.getProofPassword().toCharArray();
    //
    //    BasicAuthenticationManager auth = BasicAuthenticationManager.newInstance(proof.getProofUsername(), password);
    //    svnOperationFactory.setAuthenticationManager(auth);
    //    SvnCheckout checkout = svnOperationFactory.createCheckout();
    //    checkout.setSource(SvnTarget.fromURL(SVNURL.parseURIEncoded(matFlowCode.getCodeAddress())));
    //    checkout.setSingleTarget(SvnTarget.fromFile(new File(path)));
    //    checkout.run();
    //    svnOperationFactory.dispose();
    //}

    /**
     * SVN SSH拉取代码
     * @param matFlowCode 配置信息
     * @param proof 凭证信息
     * @param path 文件地址
     */
    //public void sshSvn(MatFlowCode matFlowCode, Proof proof, String path) throws SVNException {
    //    matFlowCommonService.deleteFile(new File(path));
    //    SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
    //    BasicAuthenticationManager auth = BasicAuthenticationManager
    //            .newInstance(proof.getProofUsername(), new File(proof.getProofPassword()),null,22);
    //    svnOperationFactory.setAuthenticationManager(auth);
    //    SvnCheckout checkout = svnOperationFactory.createCheckout();
    //    checkout.setSource(SvnTarget.fromURL(SVNURL.parseURIEncoded(matFlowCode.getCodeAddress())));
    //    checkout.setSingleTarget(SvnTarget.fromFile(new File(path)));
    //    checkout.run();
    //    svnOperationFactory.dispose();
    //}



}
