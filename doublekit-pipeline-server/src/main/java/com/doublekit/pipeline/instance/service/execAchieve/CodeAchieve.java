package com.doublekit.pipeline.instance.service.execAchieve;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.CodeGit.FileTree;
import com.doublekit.pipeline.execute.model.PipelineCode;
import com.doublekit.pipeline.execute.service.PipelineCodeService;
import com.doublekit.pipeline.execute.service.PipelineCodeServiceImpl;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.model.PipelineProcess;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Exporter
public class CodeAchieve {

    @Autowired
    PipelineCodeService pipelineCodeService;

    @Autowired
    CommonAchieve commonAchieve;

    private static final Logger logger = LoggerFactory.getLogger(PipelineCodeServiceImpl.class);

    // git克隆
    public int clone(PipelineProcess pipelineProcess, List<PipelineExecHistory> pipelineExecHistoryList){
        //开始时间
        PipelineExecHistory pipelineExecHistory = pipelineProcess.getPipelineExecHistory();
        PipelineConfigure pipelineConfigure = pipelineProcess.getPipelineConfigure();
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
        pipelineProcess.setProof(proof);
        pipelineProcess.setPipelineExecLog(pipelineExecLog);
        if (proof == null){
            logger.info("凭证为空。");
            commonAchieve.updateTime(pipelineProcess,beginTime);
            commonAchieve.updateState(pipelineProcess,"凭证为空。",pipelineExecHistoryList);
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

        try {
        codeStart(proof,pipelineCode,path);
        } catch (GitAPIException | IOException | SVNException e) {
            commonAchieve.updateTime(pipelineProcess,beginTime);
            commonAchieve.updateState(pipelineProcess,e.toString(),pipelineExecHistoryList);
            return 0;
        }

        //更新状态
        pipelineExecLog.setRunLog( s + "代码拉取成功" + "\n");
        pipelineExecHistory.setRunLog(pipelineExecHistory.getRunLog()+"\n"+ "代码拉取成功" +"\n");
        pipelineExecHistoryList.add(pipelineExecHistory);
        commonAchieve.updateTime(pipelineProcess,beginTime);
        commonAchieve.updateState(pipelineProcess,null,pipelineExecHistoryList);
        return 1;
    }

    /**
     * 区分不同源
     * @param proof 凭证
     * @param pipelineCode 配置信息
     * @param path 拉取地址
     * @throws GitAPIException git拉取异常
     * @throws IOException 地址找不到
     */
    public void codeStart(Proof proof,PipelineCode pipelineCode,String path) throws GitAPIException, IOException, SVNException {
        switch (pipelineCode.getType()){
            case 1,2,3,4 :
                if (proof.getProofType().equals("SSH")){
                    sshClone(pipelineCode.getCodeAddress(), proof.getProofPassword(), path, pipelineCode.getCodeBranch());
                }else {
                    UsernamePasswordCredentialsProvider credentialsProvider = commonAchieve.usernamePassword(proof.getProofUsername(), proof.getProofPassword());
                    clone(path, pipelineCode.getCodeAddress(), credentialsProvider, pipelineCode.getCodeBranch());
                }
                break;
            case 5:
                if (proof.getProofType().equals("SSH")){
                    sshSvn(pipelineCode,proof,path);
                }else {
                    svn(pipelineCode,proof,path);
                }
                break;
        }
    }

    /**
     * git代码拉取
     * @param gitAddress 本地文件地址
     * @param gitUrl git地址
     * @param credentialsProvider 凭证
     * @param branch 分支
     */
    private void clone(String gitAddress, String gitUrl, CredentialsProvider credentialsProvider, String branch)
            throws GitAPIException, JGitInternalException, IOException {
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
    public void sshClone(String url, String privateKeyPath,String clonePath,String branch) throws GitAPIException, JGitInternalException, IOException {
        Git git;
        File fileAddress = new File(clonePath);
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
        if (!fileAddress.exists()){
             git = Git.cloneRepository()
                    .setURI(url)
                    .setBranch(branch)
                    .setTransportConfigCallback(transport -> {
                        SshTransport sshTransport = (SshTransport) transport;
                        sshTransport.setSshSessionFactory(sshSessionFactory);
                    })
                    .setDirectory(fileAddress)
                    .call();
        }else {
            git = Git.open(new File(clonePath+"\\.git"));
            git.pull().setTransportConfigCallback(transport -> {
                SshTransport sshTransport = (SshTransport) transport;
                sshTransport.setSshSessionFactory(sshSessionFactory);
            }).call();
        }
        git.close();
    }

    /**
     * SVN 拉取代码
     * @param pipelineCode 配置信息
     * @param proof 凭证信息
     * @param path 文件地址
     */
    public void svn(PipelineCode pipelineCode,Proof proof,String path) throws SVNException {
        SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
        char[] password = proof.getProofPassword().toCharArray();

        BasicAuthenticationManager auth = BasicAuthenticationManager.newInstance(proof.getProofUsername(), password);
        svnOperationFactory.setAuthenticationManager(auth);
        SvnCheckout checkout = svnOperationFactory.createCheckout();
        checkout.setSource(SvnTarget.fromURL(SVNURL.parseURIEncoded(pipelineCode.getCodeAddress())));
        checkout.setSingleTarget(SvnTarget.fromFile(new File(path)));
        checkout.run();
        svnOperationFactory.dispose();
    }

    /**
     * SVN SSH拉取代码
     * @param pipelineCode 配置信息
     * @param proof 凭证信息
     * @param path 文件地址
     */
    public void sshSvn(PipelineCode pipelineCode,Proof proof,String path) throws SVNException {
        SvnOperationFactory svnOperationFactory = new SvnOperationFactory();
        BasicAuthenticationManager auth = BasicAuthenticationManager
                .newInstance(proof.getProofUsername(), new File("proof.getProofPassword()"),null,22);
        svnOperationFactory.setAuthenticationManager(auth);
        SvnCheckout checkout = svnOperationFactory.createCheckout();
        checkout.setSource(SvnTarget.fromURL(SVNURL.parseURIEncoded(pipelineCode.getCodeAddress())));
        checkout.setSingleTarget(SvnTarget.fromFile(new File(path)));
        checkout.run();
        svnOperationFactory.dispose();
    }

    /**
     * 获取文件树
     * @param path 文件地址
     * @param list 存放树的容器
     * @return 树
     */
    public  List<FileTree> fileTree(File path, List<FileTree> list){
        File[] files = path.listFiles();
        if (files != null){
            for (File file : files) {
                FileTree fileTree = new FileTree();
                fileTree.setTreeName(file.getName());
                if (file.isDirectory()){
                    fileTree.setTreeType(2);
                    List<FileTree> trees = new ArrayList<>();
                    fileTree.setFileTree(trees);
                    fileTree(file,trees);
                }else {
                    fileTree.setTreePath(file.getPath());
                    fileTree.setTreeType(1);

                }
                list.add(fileTree);
                list.sort(Comparator.comparing(FileTree::getTreeType,Comparator.reverseOrder()));
            }
        }
        return list;
    }

    /**
     * 获取文件流
     * @param path 文件地址
     * @return 文件信息
     */
    public List<String> readFile(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }

}
