package net.tiklab.matflow.orther.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.definition.model.Pipeline;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 效验地址，文件操作
 */

public class PipelineUntil {

    public static final String appName = "matflow";

    //消息类型
    public static final String MES_PIPELINE = "MES_PIPELINE"; //流水线
    public static final String MES_PIPELINE_RUN = "MES_PIPELINE_RUN"; //运行

    //消息模板
    public static final String MES_TEM_PIPELINE_CREATE = "MES_TEM_PIPELINE_CREATE"; //流水线创建信息模板
    public static final String MES_TEM_PIPELINE_DELETE = "MES_TEM_PIPELINE_DELETE"; //流水线删除信息模板
    public static final String MES_TEM_PIPELINE_RUN = "MES_TEM_PIPELINE_RUN"; //流水线运行信息模板
    public static final String MES_TEM_PIPELINE_EXEC = "MES_TEM_PIPELINE_EXEC"; //流水线执行信息模板

    //日志类型
    public static final String LOG_PIPELINE = "LOG_PIPELINE"; //流水线
    public static final String LOG_PIPELINE_CONFIG = "LOG_PIPELINE_CONFIG"; //配置
    public static final String LOG_PIPELINE_RUN = "LOG_PIPELINE_RUN"; //运行
    public static final String LOG_PIPELINE_USER = "LOG_PIPELINE_USER"; //成员邀请
    public static final String LOG_PIPELINE_AUTH = "LOG_PIPELINE_AUTH"; //权限变更

    //日志模板
    public static final String LOG_TEM_PIPELINE_CREATE = "LOG_TEM_PIPELINE_CREATE";
    public static final String LOG_TEM_PIPELINE_DELETE = "LOG_TEM_PIPELINE_DELETE";
    public static final String LOG_TEM_PIPELINE_UPDATE = "LOG_TEM_PIPELINE_UPDATE";
    public static final String LOG_TEM_PIPELINE_EXEC = "LOG_TEM_PIPELINE_EXEC";
    public static final String LOG_TEM_PIPELINE_RUN = "LOG_TEM_PIPELINE_RUN";
    public static final String LOG_TEM_PIPELINE_CONFIG_CREATE = "LOG_TEM_PIPELINE_CONFIG_CREATE";
    public static final String LOG_TEM_PIPELINE_CONFIG_DELETE = "LOG_TEM_PIPELINE_CONFIG_DELETE";
    public static final String LOG_TEM_PIPELINE_CONFIG_UPDATE = "LOG_TEM_PIPELINE_CONFIG_UPDATE";


    //返回系统当前时间
    public static String date(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

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

    /**
     * 系统类型
     * @return 1.windows 2.其他
     */
    public static int findSystemType(){
        String property = System.getProperty("os.name");
        String[] s1 = property.split(" ");
        if (s1[0].equals("Windows")){
            return 1;
        }else {
            return 2;
        }
    }

    /**
     * 执行cmd命令
     * @param path 执行文件夹
     * @param order 执行命令
     * @return 执行信息
     * @throws IOException 调取命令行失败
     */
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
            DateTimes= days + " 天" + hours + " 时" + minutes + " 分" + seconds + " 秒";
        }else if(hours>0){
            DateTimes=hours + " 时" + minutes + " 分" + seconds + " 秒";
        }else if(minutes>0){
            DateTimes=minutes + " 分" + seconds + " 秒";
        }else{
            DateTimes=seconds + " 秒";
        }
        return DateTimes;
    }

    /**
     * 系统默认存储位置
     * @return 位置
     */
    public static String findFileAddress(){
        String files = "/usr/local/matflow/";

        String property = System.getProperty("os.name");
        String[] s = property.split(" ");
        if (s[0].equals("Windows")){
            files = "D:\\clone\\";
        }
        return files;
    }

    /**
     * 删除文件
     * @param file 文件地址
     * @return 是否删除 true 删除成功,false 删除失败
     */
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

    /**
     * 匹配文件路径
     * @param pipelineName 流水线名称
     * @param regex 条件
     * @return 文件全路径
     */
    public static String getFile(String pipelineName, String regex){
        List<String> list = new ArrayList<>();
        String path= findFileAddress() + pipelineName;
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

    /**
     * 判断字符串是否为空
     * @param s 字符串
     * @return true:不为空 false:空
     */
    public static boolean isNoNull(String s){
        if (s == null){
            return false;
        }
        if (s.equals(" ")){
            return false;
        }
        return !s.isEmpty();
    }

    /**
     * 格式化输出流
     * @param inputStream 流
     * @param encode  GBK,US-ASCII,ISO-8859-1,ISO-8859-1,UTF-16BE ,UTF-16LE, UTF-16,UTF-8
     * @return 输出流
     */
    public static InputStreamReader encode(InputStream inputStream,String encode){
        if (encode != null){
            return  new InputStreamReader(inputStream, Charset.forName(encode));
        }
        if (findSystemType() == 1){
            return new InputStreamReader(inputStream, Charset.forName("GBK"));
        }else {
            return new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        }
    }

    /**
     * 效验地址是否存在配置文件
     * @param fileAddress 文件地址
     * @param type 文件类型
     // * @return 匹配状态  1.不是个目录或不存在这个文件夹  2. 空目录找不到可执行文件 0. 匹配成功
     */
    public static void validFile(String fileAddress, int type) throws ApplicationException {
        File file = new File(fileAddress);

        //不存在这个目录
        if (!file.exists()){
           throw new ApplicationException("找不到 "+fileAddress+" 这个目录。");
        }
        //不是个目录
        if (!file.isDirectory()){
            throw new ApplicationException(fileAddress+"不是个目录。");
        }
        //不存在可执行文件
        File[] files = file.listFiles();
        if (files == null || files.length == 0){
            throw new ApplicationException("在"+fileAddress+"找不到可执行文件。");
        }

        for (File listFile : Objects.requireNonNull(file.listFiles())) {
            if (listFile.isDirectory()){
                continue;
            }
            String name = listFile.getName();
            switch (type) {
                case 1 -> {
                    if (name.equals("git") || name.equals("git.exe")) {
                        return ;
                    }
                }
                case 5 -> {
                    if (name.equals("svn") || name.equals("svn.exe")) {
                        return ;
                    }
                }
                case 21 -> {
                    if (name.equals("mvn")) {
                        return ;
                    }
                }
                case 22 -> {
                    if (name.equals("npm")) {
                        return ;
                    }
                }
            }
        }
    }


    public static Map<String,String> initMap(Pipeline pipeline){
        Map<String,String> map = new HashMap<>();
        map.put("pipelineId", pipeline.getPipelineId());
        map.put("pipelineName", pipeline.getPipelineName());
        map.put("name", pipeline.getPipelineName().substring(0,1).toUpperCase());
        map.put("color", ""+pipeline.getColor());
        return map;
    }

    /**
     * 创建临时文件写入私钥
     * @param key 私钥内容
     * @return 文件地址
     */
    public static String createTempFile(String key){
        File tempFile;
        String path;
        try {
            tempFile = File.createTempFile("key", ".txt");
            path = tempFile.getPath();
            FileWriter writer;
            writer = new FileWriter(path);
            writer.write(key);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            return null;
        }
        return path;
    }

}





































