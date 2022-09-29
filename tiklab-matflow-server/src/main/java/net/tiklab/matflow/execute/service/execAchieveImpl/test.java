package net.tiklab.matflow.execute.service.execAchieveImpl;

import com.jcraft.jsch.*;
import java.io.*;

public class test {

    public static void main(String[] args) throws JSchException, SftpException {
        exec();
    }

    public static void exec() throws JSchException, SftpException {
        JSch jsch = new JSch();

        //1.创建session

        Session  session = jsch.getSession("root", "172.12.1.30", 22);
        session.setPassword("darth2020");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");

        String localFile= "";
        String uploadAddress= "";

        sftp.connect();
        File file = new File(localFile);
        if(file.exists()){
            //判断目录是否存在
            sftp.lstat(uploadAddress);
            //ChannelSftp.OVERWRITE 覆盖上传
            sftp.put(localFile,uploadAddress,ChannelSftp.OVERWRITE);
        }
        session.disconnect();

    }
}