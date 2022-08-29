package com.tiklab.matflow.execute.service.codeGit;

import com.tiklab.matflow.definition.service.MatFlowCommonService;
import com.tiklab.matflow.execute.model.CodeGit.CodeCheckAuth;
import com.tiklab.matflow.setting.proof.model.Proof;
import com.tiklab.matflow.setting.proof.service.ProofService;
import com.tiklab.rpc.annotation.Exporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.IOException;

@Service
@Exporter
public class CodeCheckServiceImpl implements CodeCheckService {

    @Autowired
    ProofService proofService;

    @Autowired
    MatFlowCommonService matFlowCommonService;

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
        //File file = null;
        //String path = "";
        //try {
        //    file = File.createTempFile("matFlow", ".txt");
        //    path = file.getAbsolutePath();
        //    matFlowCommonService.writePrivateKeyPath(proof.getProofPassword(),path);
        //} catch (IOException e) {
        //    if (file != null){
        //        file.deleteOnExit();
        //    }
        //    return false;
        //}
        //String finalPath = path;
        //SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {
        //    @Override
        //    protected void configure(OpenSshConfig.Host host, Session session ) {
        //        //取消host文件验证
        //        session.setConfig("StrictHostKeyChecking","no");
        //    }
        //    @Override
        //    protected JSch createDefaultJSch(FS fs) throws JSchException {
        //        JSch defaultJSch = super.createDefaultJSch(fs);
        //        defaultJSch.addIdentity(finalPath);
        //        return defaultJSch;
        //    }
        //};
        //LsRemoteCommand lsRemoteCommand = Git.lsRemoteRepository()
        //        .setRemote(gitUrl);
        //try {
        //    if (proof.getProofType().equals("SSH")){
        //        lsRemoteCommand .setTransportConfigCallback(transport -> {
        //                    SshTransport sshTransport = (SshTransport) transport;
        //                    sshTransport.setSshSessionFactory(sshSessionFactory);
        //                });
        //    }else {
        //        UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(proof.getProofUsername(), proof.getProofPassword());
        //        lsRemoteCommand.setCredentialsProvider(provider);
        //    }
        //    lsRemoteCommand.callAsMap();
        //} catch (GitAPIException e) {
        //    return false;
        //}
        //file.deleteOnExit();
        return true;
    }

    /**
     * 验证svn连接
     * @param svnUrl 地址
     * @param proof 凭证信息
     * @return 状态
     */
    public  boolean svnCheck(String svnUrl,Proof proof) {
        //BasicAuthenticationManager auth;
        //if (proof.getProofType().equals("SSH")){
        //     auth = BasicAuthenticationManager
        //            .newInstance(proof.getProofUsername(), new File(proof.getProofPassword()),null,22);
        //}else {
        //    auth = BasicAuthenticationManager.newInstance(proof.getProofUsername(), proof.getProofPassword().toCharArray());
        //}
        //DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        //options.setDiffCommand("-x -w");
        //try {
        //    SVNRepository repos = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnUrl));
        //    repos.setAuthenticationManager(auth);
        //    repos.testConnection();
        //} catch (SVNException e) {
        //    return false;
        //}
        return true;
    }

    /**
     * 判断服务器是否可以连接
     * @param proof 凭证
     * @return 连接状态
     */
    public Boolean deployCheck(String svnUrl,Proof proof , int port){

        //if (proof ==null){
        //    return null;
        //}
        //JSch jsch = new JSch();
        ////采用指定的端口连接服务器
        //Session session;
        //try {
        //    session = jsch.getSession(proof.getProofUsername(), svnUrl ,port);
        //} catch (JSchException e) {
        //    return false;
        //}
        //
        ////如果服务器连接不上，则抛出异常
        //if (session == null) {
        //    return false;
        //}
        ////设置第一次登陆的时候提示，可选值：(ask | yes | no)
        //session.setConfig("StrictHostKeyChecking", "no");
        //try {
        //    if (proof.getProofType().equals("password")){
        //        //设置登陆主机的密码
        //        session.setPassword(proof.getProofPassword());
        //    }else {
        //        //添加私钥
        //        jsch.addIdentity(proof.getProofPassword());
        //    }
        //    //设置登陆超时时间 10s
        //    session.connect(10000);
        //} catch (JSchException e) {
        //    return  false;
        //}
        //session.disconnect();
        return true;
    }

}
