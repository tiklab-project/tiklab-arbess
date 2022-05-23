package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineCode;
import com.doublekit.pipeline.execute.service.PipelineCodeService;
import com.doublekit.pipeline.execute.service.PipelineCodeServiceImpl;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import okhttp3.*;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
@Exporter
public class CodeAchieve {

    @Autowired
    PipelineCodeService pipelineCodeService;

    @Autowired
    CommonAchieve commonAchieve;

    @Autowired
    PipelineExecLogService pipelineExecLogService;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    public String codeStart(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){
        return gitClone(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
    }

    // git克隆
    private String gitClone(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        pipelineExecHistory.setRunLog("流水线开始执行。。。。。。。");
        pipelineExecHistoryList.add(pipelineExecHistory);
        PipelineCode pipelineCode = pipelineCodeService.findOneCode(pipelineConfigure.getTaskId());

        PipelineExecLog pipelineExecLog = new PipelineExecLog();
        pipelineExecLog.setHistoryId(pipelineExecHistory.getHistoryId());
        pipelineExecLog.setTaskAlias(pipelineConfigure.getTaskAlias());
        pipelineExecLog.setTaskSort(pipelineConfigure.getTaskSort());
        pipelineExecLog.setTaskType(pipelineConfigure.getTaskType());
        pipelineExecLog.setRunLog("");
        String logId = pipelineExecLogService.createLog(pipelineExecLog);
        pipelineExecLog.setPipelineLogId(logId);

        Pipeline pipeline = pipelineConfigure.getPipeline();

        //设置代码路径
        String path = "D:\\clone\\" + pipeline.getPipelineName();
        File file = new File(path);

        //调用删除方法删除旧的代码
        commonAchieve.deleteFile(file);
        Proof proof = pipelineCode.getProof();
        if (proof == null){
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"凭证为空。。。。",pipelineExecHistoryList);
            return "凭证为空。。。。";
        }

        String codeAddress =pipelineCode.getCodeAddress();
        String codeBranch = pipelineCode.getCodeBranch();
        UsernamePasswordCredentialsProvider credentialsProvider = commonAchieve.usernamePassword(proof.getProofUsername(), proof.getProofPassword());

        //更新日志
        String s = "开始拉取代码 : " + "\n" + "FileAddress : " + file + "\n"  + "Uri : " + codeAddress + "\n"  + "Branch : " + codeBranch + "\n"   ;
        pipelineExecLog.setTaskSort(pipelineConfigure.getTaskSort());
        pipelineExecLog.setTaskType(pipelineConfigure.getTaskType());
        pipelineExecHistoryList.add(pipelineExecHistory);

        //克隆代码
        try {
            if (proof.getProofType().equals("SSH")&&proof.getProofScope()==1){
                sshClone(codeAddress, proof.getProofPassword(), file, codeBranch);
            }else {
                clone(file, codeAddress, credentialsProvider, codeBranch);
        }} catch (GitAPIException e) {
                commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
                commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,e.toString(),pipelineExecHistoryList);
                return e.toString();
            }

        String log = s + "proofType : " +proof.getProofType() + "\n" + "clone成功。。。。。。。。。。。。。。。" + "\n";
        pipelineExecLog.setRunLog(pipelineExecLog.getRunLog()+log);
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+log);
        pipelineExecHistoryList.add(pipelineExecHistory);
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return null;
    }

    /**
     * git代码拉取
     * @param gitAddress 本地文件地址
     * @param gitUrl git地址
     * @param credentialsProvider 凭证
     * @param branch 分支
     * @throws RuntimeException 拉取异常
     */
    private void clone(File gitAddress, String gitUrl, CredentialsProvider credentialsProvider, String branch) throws GitAPIException {
        Git.cloneRepository().setURI(gitUrl)
                .setCredentialsProvider(credentialsProvider)
                .setDirectory(gitAddress)
                .setBranch(branch)
                .call().close();
    }

    public static void sshClone(String url, String privateKeyPath,File clonePath,String branch) throws GitAPIException {
        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session ) {
                //取消host文件验证
                session.setConfig("StrictHostKeyChecking","no");
            }
            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch defaultJSch = super.createDefaultJSch(fs);
                defaultJSch.addIdentity(privateKeyPath);
                return defaultJSch;
            }
        };
        Git.cloneRepository()
                .setURI(url)
                .setBranch(branch)
                .setTransportConfigCallback(transport -> {
                    SshTransport sshTransport = (SshTransport)transport;
                    sshTransport.setSshSessionFactory(sshSessionFactory);})
                .setDirectory(clonePath)
                .call().close();
    }


    /**
     * 验证账户密码
     * @param gitUrl 克隆地址
     * @param userName 用户名
     * @param password 密码
     * @return 验证状态
     */
    public boolean checkAuth(String gitUrl, String userName, String password) {
        String basic = Credentials.basic(userName, password);
        Request request = new Request.Builder()
                .addHeader("Authorization", basic)
                .url(gitUrl + "/info/refs?service=git-upload-pack")
                .build();
        Call call = new OkHttpClient().newCall(request);
        try {
            Response execute = call.execute();
            return execute.code() == 200;
        } catch (IOException var8) {
            var8.printStackTrace();
            return false;
        }
    }

}
