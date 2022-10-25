package net.tiklab.matflow.execute.service.execAchieveImpl;

import com.jcraft.jsch.*;
import net.tiklab.matflow.definition.model.PipelineDeploy;
import net.tiklab.matflow.execute.model.PipelineProcess;
import net.tiklab.matflow.orther.service.PipelineUntil;
import net.tiklab.matflow.setting.model.Proof;

import javax.print.attribute.standard.NumberUp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class fdcyfyjf {
    public static void main(String[] args) throws JSchException, IOException {
        createSession();
    }


    private static void createSession() throws JSchException, IOException {

        JSch jsch = new JSch();

        Session session = jsch.getSession("root","172.11.1.18",22);
        session.setPassword("darth2020");
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect(10000);
        System.out.println("连接成功。");
        String hostKey = session.getHost();
        System.out.println(hostKey);

        ChannelExec exec = (ChannelExec) session.openChannel("exec");

        exec.setCommand("cd /root && mkdir tetsssss");
        exec.connect();

        log(exec.getInputStream(),exec.getErrStream(),true);
        exec.disconnect();
        session.disconnect();
    }

    public static void log(InputStream inputStream,InputStream errStream,boolean b) throws IOException {
        //根据系统指定不同日志输出格式
        InputStreamReader inputStreamReader = PipelineUntil.encode(inputStream,"UTF-8");
        String s;
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String log = null;
        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            log =s;
            System.out.println(s);
        }
        if (log == null && b){
            log(errStream,inputStream,false);
        }
        inputStream.close();
        bufferedReader.close();
    }

}
