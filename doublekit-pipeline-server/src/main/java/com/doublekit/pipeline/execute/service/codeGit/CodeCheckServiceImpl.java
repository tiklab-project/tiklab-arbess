package com.doublekit.pipeline.execute.service.codeGit;

import com.doublekit.pipeline.execute.model.CodeGit.CodeCheckAuth;
import com.doublekit.pipeline.instance.service.execAchieveService.CommonAchieveService;
import com.doublekit.pipeline.setting.proof.model.Proof;
import com.doublekit.pipeline.setting.proof.service.ProofService;
import com.doublekit.rpc.annotation.Exporter;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LsRemoteCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.*;
import org.eclipse.jgit.util.FS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.File;
import java.io.IOException;

@Service
@Exporter
public class CodeCheckServiceImpl implements CodeCheckService {

    @Autowired
    ProofService proofService;

    @Autowired
    CommonAchieveService commonAchieveService;

    @Override
    public Boolean checkAuth(CodeCheckAuth codeCheckAuth){
        Proof proof = proofService.findOneProof(codeCheckAuth.getProofId());
        return switch (codeCheckAuth.getType()) {
            case 0 -> gitCheck(codeCheckAuth.getUrl(), proof);
            case 1 -> svnCheck(codeCheckAuth.getUrl(), proof);
            case 2 -> deployCheck(codeCheckAuth.getUrl(), proof, codeCheckAuth.getPort());
            default -> false;
        };
    }

    /**
     * 验证git连接
     * @param gitUrl 地址
     * @param proof 凭证信息
     * @return 状态
     */
    public boolean gitCheck(String gitUrl, Proof proof) {
        File file = null;
        try {
            file = File.createTempFile("pipeline", ".txt");
            commonAchieveService.writePrivateKeyPath(proof.getProofPassword(),file.getAbsolutePath());
        } catch (IOException e) {
            if (file != null){
                file.deleteOnExit();
            }
            return false;
        }
        File finalFile = file;
        SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
            @Override
            protected void configure(OpenSshConfig.Host host, Session session ) {
                //取消host文件验证
                session.setConfig("StrictHostKeyChecking","no");
            }
            @Override
            protected JSch createDefaultJSch(FS fs) throws JSchException {
                JSch defaultJSch = super.createDefaultJSch(fs);
                defaultJSch.addIdentity(finalFile.getAbsolutePath());
                return defaultJSch;
            }
        };
        LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository()
                .setRemote(gitUrl);
        try {
            if (proof.getProofType().equals("ssh")){
                lsRemoteCommand .setTransportConfigCallback(transport -> {
                            SshTransport sshTransport = (SshTransport) transport;
                            sshTransport.setSshSessionFactory(sshSessionFactory);
                        });
            }else {
                UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(proof.getProofUsername(), proof.getProofPassword());
                lsRemoteCommand.setCredentialsProvider(provider);
            }
            lsRemoteCommand.callAsMap();
        } catch (GitAPIException e) {
            return false;
        }
        file.deleteOnExit();
        return true;
    }

    /**
     * 验证svn连接
     * @param svnUrl 地址
     * @param proof 凭证信息
     * @return 状态
     */
    public  boolean svnCheck(String svnUrl,Proof proof) {
        BasicAuthenticationManager auth;
        if (proof.getProofType().equals("SSH")){
             auth = BasicAuthenticationManager
                    .newInstance(proof.getProofUsername(), new File(proof.getProofPassword()),null,22);
        }else {
            auth = BasicAuthenticationManager.newInstance(proof.getProofUsername(), proof.getProofPassword().toCharArray());
        }
        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        options.setDiffCommand("-x -w");
        try {
            SVNRepository repos = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));
            repos.setAuthenticationManager(auth);
            repos.testConnection();
        } catch (SVNException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断服务器是否可以连接
     * @param proof 凭证
     * @return 连接状态
     */
    public Boolean deployCheck(String svnUrl,Proof proof , int port){

        if (proof ==null){
            return null;
        }
        JSch jsch = new JSch();
        //采用指定的端口连接服务器
        Session session;
        try {
            session = jsch.getSession(proof.getProofUsername(), svnUrl ,port);
        } catch (JSchException e) {
            return false;
        }

        //如果服务器连接不上，则抛出异常
        if (session == null) {
            return false;
        }
        //设置第一次登陆的时候提示，可选值：(ask | yes | no)
        session.setConfig("StrictHostKeyChecking", "no");
        try {
            if (proof.getProofType().equals("password")){
                //设置登陆主机的密码
                session.setPassword(proof.getProofPassword());
            }else {
                //添加私钥
                jsch.addIdentity(proof.getProofPassword());
            }
            //设置登陆超时时间 10s
            session.connect(10000);
        } catch (JSchException e) {
            return  false;
        }
        session.disconnect();
        return true;
    }

}
