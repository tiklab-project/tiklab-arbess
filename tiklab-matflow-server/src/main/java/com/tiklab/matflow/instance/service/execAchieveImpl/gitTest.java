package com.tiklab.matflow.instance.service.execAchieveImpl;

import com.tiklab.matflow.instance.model.MatFlowExecHistory;
import com.tiklab.matflow.instance.model.MatFlowExecLog;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


public class gitTest {

    public static void main(String[] args) throws IOException {


        Runtime runtime = Runtime.getRuntime();

        //获取脚本地址
        String rootPath = (new File(System.getProperty("user.dir"))).getParent();

        String path = rootPath +"\\mysql-8.0.28";

        //runtime.exec("cmd.exe /c cd D:\\idea\\tiklab\\tiklab-matflow\\mysql-8.0.28 && dir");

        //Process dir = runtime.exec("cmd.exe /c dir");
        Process dir =  runtime.exec("cmd.exe /c cd D:\\idea\\tiklab\\tiklab-matflow\\mysql-8.0.28 && .\\start.bat D:\\idea\\tiklab\\tiklab-matflow\\ tiklab_matflow");
        InputStreamReader inputStreamReader;

        //根据系统指定不同日志输出格式
        inputStreamReader = new InputStreamReader(dir.getInputStream(), Charset.forName("GBK"));

        String s;
        BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);

        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            System.out.println(s);
        }
        //更新状态

        inputStreamReader.close();
        bufferedReader.close();

        //String s = gitCode("matflow_v1.0");
        //String gitDir="D:\\git\\Git\\bin";
        //String gitClone=".\\git.exe clone"+" " + s;
        //try {
        //    Process process = process(gitDir, gitClone, null);
        //    System.out.println(gitClone);
        //    System.out.println("正确：");
        //    log(process.getInputStream());
        //    System.out.println("错误：");
        //    log(process.getErrorStream());
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //}

    }


    public static String gitCode(String branch){

        String codeDir="D:\\test\\git\\yuanyuan";
        String username="gaomengyuan@darthcoud.com";
        String password="darth2020";
        StringBuilder gitUrl = new StringBuilder("http://172.12.1.10/devops-itdd/tiklab-matflow-ui.git");
        String urlType;
        String up=username.replace("@", "%40")+":"+password+"@";
        try {
            //获取url类型
            URL url = new URL(gitUrl.toString());
            urlType = url.toURI().getScheme();
        } catch (MalformedURLException |URISyntaxException e) {
            System.out.println("地址格式错误");
            throw new RuntimeException(e);
        }
        //根据不同类型拼出不同地址
        StringBuilder url = new StringBuilder(gitUrl);
        if (urlType.equals("http")){
            gitUrl = url.insert(7, up);
        }else {
            gitUrl = url.insert(8, up);
        }
        //判断是否存在分支
        String urls;
        if (branch == null){
            urls = gitUrl+" "+codeDir;
        }else {
            urls =" -b "+branch+" "+ gitUrl+" "+codeDir;
        }
       return urls;
    }

    public static Process process(String path,String order,String sourceAddress) throws IOException,NullPointerException {

        Runtime runtime=Runtime.getRuntime();
        Process process;
        String property = System.getProperty("os.name");

        String[] s = property.split(" ");
        if (s[0].equals("Windows")){
            if (sourceAddress != null){
                process = runtime.exec("cmd.exe /c cd " + path + "\\"+sourceAddress + " &&" + " " + order);
                return process;
            }
            //执行命令
            process = runtime.exec("cmd.exe /c cd " + path + " &&" + " " + order);
        }else {
            if (sourceAddress != null){
                String[] cmd = new String[] { "/bin/sh", "-c", "cd "+path+"/"+sourceAddress+";"+" source /etc/profile;"+order };
                process = runtime.exec(cmd);
                return process;
            }
            //执行命令
            String[] cmd = new String[] { "/bin/sh", "-c", "cd "+path+";"+" source /etc/profile;"+ order };
            process = runtime.exec(cmd);
        }
        return process;
    }


    public static int log(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader;
        String property = System.getProperty("os.name");

        String[] st = property.split(" ");
        if (st[0].equals("Windows")){
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("GBK"));
        }else {
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }

        String s;
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        //更新日志信息
        while ((s = bufferedReader.readLine()) != null) {
            System.out.println(s);

        }
        //更新状态
        inputStreamReader.close();
        bufferedReader.close();
        return 1;
    }



}
