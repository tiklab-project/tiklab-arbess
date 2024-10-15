package io.tiklab.arbess.support.util.util;

import io.tiklab.core.exception.ApplicationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static io.tiklab.arbess.support.util.util.PipelineFinal.FILE_TEMP_PREFIX;

public class PipelineFileUtil {

    private static Logger logger = LoggerFactory.getLogger(PipelineFileUtil.class);


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
     * @param suffix 后缀
     * @return 文件地址
     */
    public static String createTempFile(String key,String suffix){
        FileWriter writer = null;
        String path ;
        try {
            try {
                File tempFile = File.createTempFile(FILE_TEMP_PREFIX, suffix);
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
        if (StringUtils.isEmpty(str)){
            return;
        }
        try (FileWriter writer = new FileWriter(path, StandardCharsets.UTF_8,true)) {
            writer.write(str);
            writer.flush();
        } catch (Exception e) {
            throw new ApplicationException("文件写入失败," + e.getMessage());
        }
    }

    /**
     * 读取文件后100行内容 0读取全部
     * @param fileAddress 文件地址
     * @return 内容
     */
    public static String readFile(String fileAddress,int length) throws ApplicationException {

        if (!PipelineUtil.isNoNull(fileAddress) || !new File(fileAddress).exists()){
            return null;
        }

        StringBuilder s = new StringBuilder();
        try {
            Path path = Paths.get(fileAddress);
            List<String> lines ;
            if (length == 0){
                lines = Files.readAllLines(path,StandardCharsets.UTF_8);
                for (String line : lines) {
                    s.append(line).append("\n");
                }
            }else {
                lines = Files.readAllLines(path,StandardCharsets.UTF_8);
                for (int i = Math.max(0, lines.size() - length); i < lines.size(); i++) {
                    s.append(lines.get(i)).append("\n");
                }
            }
        } catch (IOException e) {
            throw new ApplicationException("读取文件信息失败" + e.getMessage());
        }
        return s.toString();
    }


    /**
     * 删除文件
     * @param file 文件地址
     * @return 是否删除 true 删除成功,false 删除失败
     */
    public static Boolean deleteFile(File file){

        if (!file.exists()){
            logger.warn("文件不存在！{}",file.getAbsolutePath());
            return true;
        }

        boolean b = FileUtils.deleteQuietly(file);
        if (!b){
            logger.error("文件删除失败！{}",file.getAbsolutePath());
            return false;
        }else {
            logger.warn("文件删除成功！{}",file.getAbsolutePath());
        }
        return true;
    }

    /**
     * 获取磁盘大小
     * @param dir
     * @return
     */
    public static float findDiskSize(String dir){
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        while (folder.getParentFile() != null) {
            folder = folder.getParentFile();
        }
        String rootPath = folder.getPath();
        File root = new File(rootPath);
        long diskSpace =  root.getTotalSpace();
        float l = (float)diskSpace / (1024 * 1024 * 1024);
        // 使用 BigDecimal 控制小数位数
        BigDecimal decimalL = new BigDecimal(Float.toString(l));
        decimalL = decimalL.setScale(2, RoundingMode.HALF_UP);

        return decimalL.floatValue();
    }

    /**
     * 获取文件夹大小
     * @param dir 文件夹
     * @param type 返回指定类型 mb 或 gb
     * @return
     */
    public static float findDirSize(String dir,String type){
        File folder = new File(dir);

        if (!folder.exists()) {
            return 0;
        }
        long diskSpace;
        if (folder.isFile()){
            diskSpace = folder.length();
        }else {
            diskSpace = calculateDiskSpace(folder);
        }

        if (type.equals(PipelineFinal.SIZE_TYPE_MB)){
            // 转换成mb
            float mbSize =(float) diskSpace / (1024 * 1024);
            BigDecimal mbDecimalL = new BigDecimal(Float.toString(mbSize));
            mbDecimalL = mbDecimalL.setScale(0, RoundingMode.HALF_UP);
            mbSize = mbDecimalL.floatValue();

            return mbSize;
        }else {
            // 转换成gb
            float gbSize = (float)diskSpace / (1024 * 1024 * 1024);
            BigDecimal decimalL = new BigDecimal(Float.toString(gbSize));
            decimalL = decimalL.setScale(2, RoundingMode.HALF_UP);
            gbSize = decimalL.floatValue();
            return gbSize;
        }

    }


    public static long calculateDiskSpace(File file) {
        long space = 0;
        if (file.isFile()) {
            space = file.length();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File subFile : files) {
                    space += calculateDiskSpace(subFile);
                }
            }
        }
        return space;
    }


}
