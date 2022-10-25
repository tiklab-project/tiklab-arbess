package net.tiklab.matflow.orther.service;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 效验地址，文件操作
 */

public class PipelineUntil {

    public static String appName = "matflow";

    //效验git地址
    public static boolean validGit(String address){
        String valid = "^(http(s)?:\\/\\/([^\\/]+?\\/){2}|git@[^:]+:[^\\/]+?\\/).*?\\.git$";
        return Pattern.matches(valid,address);
    }

    //效验svn地址
    public static boolean validSvn(String address){
        String valid = "^svn(\\+ssh)?:\\/\\/([^\\/]+?\\/){2}.*$";
        return Pattern.matches(valid,address);
    }

    //效验ip
    public static boolean validIp(String ip){
        String valid = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";
        return Pattern.matches(valid,ip);
    }

    //系统类型
    public static int findSystemType(){
        String property = System.getProperty("os.name");
        String[] s1 = property.split(" ");
        if (s1[0].equals("Windows")){
            return 1;
        }else {
            return 2;
        }
    }

    //执行cmd命令
    public static Process process(String path,String order) throws IOException {
        Runtime runtime=Runtime.getRuntime();
        Process process;
        if (findSystemType()==1){
            process = runtime.exec(" cmd.exe /c cd " + path + " &&" + " " + order);
        }else {
            String[] cmd = new String[] { "/bin/sh", "-c", "cd "+path+";"+" source /etc/profile;"+ order };
            process = runtime.exec(cmd);
        }
        return process;
    }

    //时间转换成时分秒
    public static String formatDateTime(long time) {
        String DateTimes ;
        long days = time / ( 60 * 60 * 24);
        long hours = (time % ( 60 * 60 * 24)) / (60 * 60);
        long minutes = (time % ( 60 * 60)) /60;
        long seconds = time % 60;
        if(days>0){
            DateTimes= days + "天" + hours + "时" + minutes + "分" + seconds + "秒";
        }else if(hours>0){
            DateTimes=hours + "时" + minutes + "分" + seconds + "秒";
        }else if(minutes>0){
            DateTimes=minutes + "分" + seconds + "秒";
        }else{
            DateTimes=seconds + "秒";
        }
        return DateTimes;
    }

    //获取系统文件存储地址
    public static String findFileAddress(){
        String files = "/usr/local/matflow/";

        String property = System.getProperty("os.name");
        String[] s = property.split(" ");
        if (s[0].equals("Windows")){
            files = "D:\\clone\\";
        }
        return files;
    }

    //删除文件
    public static Boolean deleteFile(File file){
        if (file.isDirectory()) {
            String[] children = file.list();
            //递归删除目录中的子目录下
            if (children != null) {
                for (String child : children) {
                    boolean state = deleteFile(new File(file, child));
                    int tryCount = 0;
                    while (!state && tryCount++ < 10) {

                        System.gc();
                        state = file.delete();
                    }
                }
            }
            // 目录此时为空，删除
        }
        return file.delete();
    }

    //获取文件流
    public static List<String> readFile(String path) {
        if (path == null){
            return null;
        }
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return  null;
        }
        return lines;
    }

    //字符串写入文件中
    public static void writePrivateKeyPath(String massage, String filePath) throws IOException {
        BufferedReader bufferedReader ;
        BufferedWriter bufferedWriter;
        File distFile= new File(filePath);
        if (!distFile.getParentFile().exists()){
            boolean mkdirs = distFile.getParentFile().mkdirs();
        }
        bufferedReader = new BufferedReader(new StringReader(massage));
        bufferedWriter = new BufferedWriter(new FileWriter(distFile));
        char[] buf = new char[1024]; //?????
        int len;
        while ( (len = bufferedReader.read(buf)) != -1) {
            bufferedWriter.write(buf, 0, len);
        }
        bufferedWriter.flush();
        bufferedReader.close();
        bufferedWriter.close();
    }

    //获取符合条件的文件名
    public static List<String> getFilePath(File path,List<String> list){
        File[] fa = path.listFiles();
        if (fa != null) {
            for (File file : fa) {
                if (file.isDirectory()){
                    getFilePath(file,list);
                }
                list.add(file.getPath());
            }
        }
        return list;
    }

    //匹配字符串获取文件全路径
    public static String getFile(String pipelineName, String regex){
        List<String> list = new ArrayList<>();
        String  path= findFileAddress() + pipelineName;
        List<String> filePath = PipelineUntil.getFilePath(new File(path),new ArrayList<>());
        for (String s : filePath) {
            File file = new File(s);
            if (file.getName().matches("^(.*"+regex+".*)") || file.getName().matches(regex)){
                list.add(s);
            }
        }
        if (list.size()==1){
            return list.get(0);
        }
        return null;
    }

    //判断字符串是否为空  true:不为空 false:空
    public static boolean isNoNull(String s){
        if (s == null){
            return false;
        }
        if (s.equals(" ")){
            return false;
        }
        return !s.isEmpty();
    }

    //格式化输出流  encode:US-ASCII,ISO-8859-1,ISO-8859-1,UTF-16BE ,UTF-16LE, UTF-16,UTF-8
    public static InputStreamReader encode(InputStream inputStream,String encode){
        if (encode != null){
            return  new InputStreamReader(inputStream, Charset.forName(encode));
        }
        if (findSystemType()==1){
            return new InputStreamReader(inputStream, Charset.forName("GBK"));
        }else {
            return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }
    }


}





































