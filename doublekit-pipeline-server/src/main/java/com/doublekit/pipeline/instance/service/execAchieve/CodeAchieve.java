package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineCode;
import com.doublekit.pipeline.execute.service.PipelineCodeService;
import com.doublekit.pipeline.execute.service.PipelineCodeServiceImpl;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.wc2.SvnCheckout;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnTarget;

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


    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    public int codeStart(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){
      return switch (pipelineConfigure.getTaskType()){
           case 1,2 -> gitClone(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
           case 5 -> svn(pipelineConfigure, pipelineExecHistory, pipelineExecHistoryList);
          default -> 1;
       };
    }

    // git克隆
    private int gitClone(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){
        //开始时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        pipelineExecHistory.setRunLog("流水线开始执行。。。。。。。");
        pipelineExecHistoryList.add(pipelineExecHistory);

        PipelineCode pipelineCode = pipelineCodeService.findOneCode(pipelineConfigure.getTaskId());
        //初始化日志
        PipelineExecLog pipelineExecLog = commonAchieve.initializeLog(pipelineExecHistory, pipelineConfigure);

        //代码克隆路径
        String path = "D:\\clone\\" + pipelineConfigure.getPipeline().getPipelineName();
        File file = new File(path);

        //删除旧的代码
        //commonAchieve.deleteFile(file);

        //获取凭证
        Proof proof = pipelineCode.getProof();
        if (proof == null){
            logger.info("凭证为空。");
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"凭证为空。",pipelineExecHistoryList);
            return 0;
        }

        //更新日志
        String s = "开始拉取代码 : " + "\n"
                + "FileAddress : " + file + "\n"
                + "Uri : " + pipelineCode.getCodeAddress() + "\n"
                + "Branch : " + pipelineCode.getCodeBranch() + "\n"
                + "proofType : " +proof.getProofType();
        pipelineExecHistory.setRunLog(s);
        pipelineExecHistoryList.add(pipelineExecHistory);

        //克隆代码
        try {
            if (proof.getProofType().equals("SSH")&&proof.getProofScope() == 1){
                sshClone(pipelineCode.getCodeAddress(), proof.getProofPassword(), file, pipelineCode.getCodeBranch());
            }else {
                UsernamePasswordCredentialsProvider credentialsProvider = commonAchieve.usernamePassword(proof.getProofUsername(), proof.getProofPassword());
                clone(path, pipelineCode.getCodeAddress(), credentialsProvider, pipelineCode.getCodeBranch());
            }
             //| JGitInternalException
        } catch (GitAPIException |IOException  e) {
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,e.toString(),pipelineExecHistoryList);
            return 0;
        }

        //更新状态e
        pipelineExecLog.setRunLog( s + "代码拉取成功" + "\n");
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+ "代码拉取成功" +"\n");
        pipelineExecHistoryList.add(pipelineExecHistory);
        commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return 1;
    }

    /**
     * git代码拉取
     * @param gitAddress 本地文件地址
     * @param gitUrl git地址
     * @param credentialsProvider 凭证
     * @param branch 分支
     */
    private void clone(String gitAddress, String gitUrl, CredentialsProvider credentialsProvider, String branch) throws GitAPIException, JGitInternalException, IOException {
        Git git;
        File fileAddress = new File(gitAddress);
        if (!fileAddress.exists()){
            git = Git.cloneRepository()
                   .setURI(gitUrl)
                   .setCredentialsProvider(credentialsProvider)
                   .setDirectory(fileAddress)
                   .setBranch(branch)
                   .call();
       }else {
           git = Git.open(new File(gitAddress+"\\.git"));
           git.pull().setCredentialsProvider(credentialsProvider).call();
       }
        git.close();
    }

    /**
     * SSH克隆代码
     * @param url 克隆url
     * @param privateKeyPath 秘钥存放地址
     * @param clonePath 克隆位置
     * @param branch 分支
     */
    public void sshClone(String url, String privateKeyPath,File clonePath,String branch) throws GitAPIException , JGitInternalException {

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
                  SshTransport sshTransport = (SshTransport) transport;
                  sshTransport.setSshSessionFactory(sshSessionFactory);
              })
              .setDirectory(clonePath)
              .call().close();
    }

    /**
     * SVN 拉取代码
     * @param pipelineConfigure 配置信息
     * @param pipelineExecHistory 执行历史
     * @param pipelineExecHistoryList 执行状态
     * @return 拉取状态
     */
    public int svn(PipelineConfigure pipelineConfigure, PipelineExecHistory pipelineExecHistory, List<PipelineExecHistory> pipelineExecHistoryList){

        //开始时间
        long beginTime = new Timestamp(System.currentTimeMillis()).getTime();
        pipelineExecHistory.setRunLog("流水线开始执行。。。。。。。");
        pipelineExecHistoryList.add(pipelineExecHistory);

        PipelineCode pipelineCode = pipelineCodeService.findOneCode(pipelineConfigure.getTaskId());
        //初始化日志
        PipelineExecLog pipelineExecLog = commonAchieve.initializeLog(pipelineExecHistory, pipelineConfigure);

        //代码克隆路径
        String path = "D:\\clone\\" + pipelineConfigure.getPipeline().getPipelineName();

        //获取凭证
        Proof proof = pipelineCode.getProof();
        if (proof == null){
            logger.info("凭证为空。");
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,"凭证为空。",pipelineExecHistoryList);
            return 0;
        }

        //更新日志
        String s = "开始拉取代码 : " + "\n"
                + "FileAddress : " + path + "\n"
                + "Uri : " + pipelineCode.getCodeAddress() + "\n"
                //+ "Branch : " + pipelineCode.getCodeBranch() + "\n"
                + "proofType : " +proof.getProofType() + "\n" ;
        pipelineExecHistory.setRunLog(s);
        pipelineExecLog.setRunLog(s);
        pipelineExecHistoryList.add(pipelineExecHistory);

        SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
        char[] password = proof.getProofPassword().toCharArray();

        BasicAuthenticationManager auth = BasicAuthenticationManager.newInstance(proof.getProofUsername(), password);
        svnOperationFactory.setAuthenticationManager(auth);

        try {
            SvnCheckout checkout = svnOperationFactory.createCheckout();
            checkout.setSource(SvnTarget.fromURL(SVNURL.parseURIEncoded(pipelineCode.getCodeAddress())));
            checkout.setSingleTarget(SvnTarget.fromFile(new File(path)));
            checkout.run();
        } catch (SVNException e) {
            commonAchieve.updateTime(pipelineExecHistory,pipelineExecLog,beginTime);
            return 0;
        } finally {
            svnOperationFactory.dispose();
        }
        //更新状态
        pipelineExecLog.setRunLog( s + "代码拉取成功" + "\n");
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+ "代码拉取成功" +"\n");
        pipelineExecHistoryList.add(pipelineExecHistory);
        commonAchieve.updateState(pipelineExecHistory,pipelineExecLog,null,pipelineExecHistoryList);
        return 1;
    }
}
