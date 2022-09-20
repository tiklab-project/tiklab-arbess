package net.tiklab.matflow.execute.service.execAchieveImpl;

import com.jcraft.jsch.*;
import java.io.*;

public class test {

    public static void main(String[] args) throws JSchException, IOException {
        exec();
    }

    public static void exec() throws JSchException, IOException {
        JSch jsch = new JSch();

        //1.创建session

        Session  session = jsch.getSession("root", "172.12.1.30", 22);
        session.setPassword("darth2020");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelExec exec = (ChannelExec) session.openChannel("exec");

        exec.setCommand("ls -all");

        InputStream input = exec.getInputStream();
        exec.connect();
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(input));
        String inputLine = null;
        while ((inputLine = inputReader.readLine()) != null) {
            System.out.println(inputLine);
        }
        exec.disconnect();
        session.disconnect();
    }
}