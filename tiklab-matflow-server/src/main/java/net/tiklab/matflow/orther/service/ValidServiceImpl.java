package net.tiklab.matflow.orther.service;

import com.jcraft.jsch.*;
import net.tiklab.matflow.orther.until.PipelineUntil;
import net.tiklab.matflow.setting.model.PipelineAuthHost;

import java.io.File;

public class ValidServiceImpl {


    public  void main(String[] args) {
        PipelineAuthHost authHost = new PipelineAuthHost();
        authHost.setIp("172.11.1.18");
        authHost.setPort(22);
        authHost.setUsername("root");
        authHost.setPassword("darth20200");
        authHost.setAuthType(1);
        boolean session = false;
        try {
            session = createSession(authHost);
        } catch (JSchException e) {
            System.out.println(e.getMessage());
        }
        if (session){
            System.out.println("连接成功");
        }else {
            System.out.println("连接失败");
        }
    }

    public static void validHost(){

    }

    private static boolean createSession(PipelineAuthHost authHost) throws JSchException {

        boolean state = true;
        String sshIp = authHost.getIp();
        int sshPort = authHost.getPort();
        String username = authHost.getUsername();
        JSch jsch = new JSch();
        if (!PipelineUntil.isNoNull(username)){
            username = "root";
        }
        Session session = jsch.getSession(username, sshIp, sshPort);
        if (authHost.getAuthType() == 2){
            String tempFile = PipelineUntil.createTempFile(authHost.getPrivateKey());
            jsch.addIdentity(tempFile);
            if (tempFile == null){
                return state;
            }
            PipelineUntil.deleteFile(new File(tempFile));
        }else {
            String password = authHost.getPassword();
            session.setPassword(password);
        }
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        session.disconnect();
        return state;
    }

}
