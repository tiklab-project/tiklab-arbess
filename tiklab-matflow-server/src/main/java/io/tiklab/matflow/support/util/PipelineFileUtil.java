package io.tiklab.matflow.support.util;

import io.tiklab.core.exception.ApplicationException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class PipelineFileUtil {


    /**
     * 初始化项目空间
     * @param type 类型 1.工作空间目录   2.日志目录
     */
    public static String initMatFlowAddress(int type){

        //根目录
        String userHome = System.getProperty("user.home");

        //工作空间目录
        File file = new File(userHome + PipelineFinal.MATFLOW_WORKSPACE);
        String path = file.getAbsolutePath();
        if (!file.exists()){
            createDirectory(path);
            try {
                //更改为隐藏文件夹
                if (PipelineUtil.findSystemType() == 1){
                    PipelineUtil.process(null," attrib +H " + userHome);
                }
            } catch (IOException e) {
                throw new ApplicationException("更改工作空间为隐藏失败。");
            }
        }

        //日志目录
        File logFile = new File(userHome+ PipelineFinal.MATFLOW_LOGS);
        String logAddress = logFile.getAbsolutePath();
        if (!logFile.exists()){
            createDirectory(logAddress);
        }

        if (type == 2){
            return logAddress;
        }else {
            return path;
        }

    }


    /**
     * 获取文件夹下所有的文件
     * @param path 目录地址
     * @param list new ArrayList<>()
     * @return 文件夹下的文件集合
     */
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
     * 效验地址是否存在配置文件
     * @param fileAddress 文件地址
     * @param type 文件类型
    // * @return 匹配状态  1.不是个目录或不存在这个文件夹  2. 空目录找不到可执行文件 0. 匹配成功
     */
    public static void validFile(String fileAddress, String type) throws ApplicationException {
        File file = new File(fileAddress);

        //不存在这个目录
        if (!file.exists()){
            throw new ApplicationException("git可执行程序地址错误，找不到 "+fileAddress+" 这个目录。");
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
                case "1","2","3","4","git","gitee","github","gitlab","xcode" -> {
                    if (name.equals("git") || name.equals("git.exe")) {
                        return ;
                    }
                }
                case "5","svn" -> {
                    if (name.equals("svn") || name.equals("svn.exe")) {
                        return ;
                    }
                }
                case "21","maven" -> {
                    if (name.equals("mvn")) {
                        return ;
                    }
                }
                case "22","nodejs" -> {
                    if (name.equals("npm")) {
                        return ;
                    }
                }
            }
        }
    }

    /**
     * 创建临时文件写入信息
     * @param key 私钥内容
     * @return 文件地址
     */
    public static String createTempFile(String key){
        FileWriter writer = null;
        String path ;
        try {
            try {
                File tempFile = File.createTempFile("key", ".txt");
                path = tempFile.getPath();
                writer = new FileWriter(path);
                writer.write(key);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                return null;
            }finally {
                if (writer != null) {
                    writer.close();
                }
            }
        }catch (IOException e){
            return null;
        }
        return path;
    }


    /**
     * 创建目录
     * @param address 文件地址
     * @throws ApplicationException 文件创建失败
     */
    public static void createDirectory(String address) throws ApplicationException {
        File file = new File(address);
        if (file.exists()) {
            return;
        }
        int i = 0;
        boolean b = false;
        if (!file.exists()) {
            while (!b && i <= 10) {
                b = file.mkdirs();
                i++;
            }
        }
        if (i >= 10) {
            throw new ApplicationException("项目工作目录创建失败。");
        }
    }

    /**
     * 创建文件
     * @param address 文件地址
     * @throws ApplicationException 文件创建失败
     */
    public static void createFile(String address) throws ApplicationException {
        File file = new File(address);
        String parent = file.getParent();
        File parentFile = new File(parent);
        try {
            if (!parentFile.exists()){
                createDirectory(parent);
            }
            boolean newFile = file.createNewFile();
            if (!newFile){
                throw new ApplicationException("文件创建失败!"+address);
            }
        } catch (IOException | ApplicationException e) {
            throw new ApplicationException("文件创建失败。");
        }

    }

    /**
     * 字符串写入文件
     * @param str 字符串
     * @param path 文件地址
     * @throws ApplicationException 写入失败
     */
    public static void logWriteFile(String str, String path) throws ApplicationException {
        try (FileWriter writer = new FileWriter(path, StandardCharsets.UTF_8,true)) {
            writer.write(str);
            writer.flush();
        } catch (Exception e) {
            throw new ApplicationException("文件写入失败。");
        }
    }

    /**
     * 读取文件后100行内容
     * @param fileAddress 文件地址
     * @return 内容
     */
    public static String readFile(String fileAddress,int length) throws ApplicationException {
        if (!PipelineUtil.isNoNull(fileAddress)){
            return null;
        }

        File file = new File(fileAddress);

        if (!file.exists()){
            return null;
        }

        Path path = Paths.get(fileAddress);

        StringBuilder s = new StringBuilder();
        List<String> lines ;
        try {
            lines = Files.readAllLines(path,StandardCharsets.UTF_8);
            for (int i = Math.max(0, lines.size() - length); i < lines.size(); i++) {
                s.append(lines.get(i)).append("\n");
            }
        } catch (IOException e) {
            throw new ApplicationException("读取文件信息失败"+ e.getMessage());
        }
        return s.toString();
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
                    while (!state && tryCount ++ < 10) {
                        System.gc();
                        state = file.delete();
                    }
                }
            }
            // 目录此时为空，删除
        }
        return file.delete();
    }


}
