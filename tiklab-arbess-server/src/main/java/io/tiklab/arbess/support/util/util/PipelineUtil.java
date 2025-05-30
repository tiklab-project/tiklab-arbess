package io.tiklab.arbess.support.util.util;

import io.tiklab.core.exception.ApplicationException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 效验地址，文件操作
 */

public class PipelineUtil {

    /**
     * 返回系统时间
     * @param type 时间类型 1.(yyyy-MM-dd HH:mm:ss) 2.(yyyy-MM-dd) 3.(HH:mm:ss) 4.([format]) 5.(HH:mm)
     * @return 时间
     */
    public static String date(int type){
        switch (type) {
            case 2 -> {
                return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            }
            case 3 -> {
                return new SimpleDateFormat("HH:mm:ss").format(new Date());
            }
            case 4 -> {
                String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                return "[" + format + "]" + "  ";
            }
            case 5 -> {
                return new SimpleDateFormat("HH:mm").format(new Date());
            }
            case 6 -> {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
            }
            default -> {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            }
        }
    }

    /**
     * 获取当前时间的指定的时间 如：获取前一个月，一天，一年，或后一天，一年等
     * @param field Calendar.MONTH 月 ；Calendar.DATE 天；Calendar.YEAR 年，等
     * @param number 1 往后 ，-1 往前
     * @return 时间
     */
    public static Date findDate(int field,int number){
        //获取当前日期
        Date date = new Date();
        //创建Calendar实例
        Calendar cal = Calendar.getInstance();
        //设置当前时间
        cal.setTime(date);
        //在当前时间基础上减一月
        // cal.add(Calendar.MONTH,-1);
        // 同理增加一天的方法：
        // cal.add(Calendar.DATE, 1);
        cal.add(field, number);
        return cal.getTime();
    }

    /**
     * 字符串转换成时间
     * @param time 时间字符串
     * @return 时间
     */
    public static Date StringChengeDate(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date targetTime;
        try {
            targetTime = sdf.parse(time);
        } catch (ParseException e) {
            throw new ApplicationException("时间转换失败，不是yyyy-MM-dd HH:mm:ss格式:"+time);
        }
        return targetTime;
    }

    /**
     * 获取指定指定时间与现在时间是否相差在指定天数内
     * @param targetTime 指定时间
     * @param dayNumber 天数
     * @return 不为空则代表在相差时间内
     */
    public static String findDateTime(Date targetTime,Integer dayNumber){

        Date currentDate = new Date();

        // 将Date类型转换为Calendar类型
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(targetTime);

        // 计算时间差
        long diffMillis =currentDate.getTime() - targetCalendar.getTimeInMillis() ;
        long diffSeconds = diffMillis / 1000;
        long diffMinutes = diffSeconds / 60;
        long diffHours = diffMinutes / 60;
        long diffDays = diffHours / 24;

        int day = (int) diffDays;
        int hours = (int) diffHours % 24;
        int minutes = (int) diffMinutes % 60;
        int seconds = (int) diffSeconds % 60;

        String time = "";

        if (day > dayNumber && dayNumber != 0){
            return null;
        }

        if (seconds != 0){
            time = seconds+" 秒";
        }

        if (minutes != 0){
            time = minutes + " 分 " +  time;
        }

        if (hours != 0){
            if (minutes != 0){
                time = hours + " 小时 "+minutes + " 分";
            }else {
                time = hours + " 小时";
            }
        }

        if (day != 0){
            if (day == 1 && hours == 0){
                return (hours + 24) + " 小时 " + minutes + " 分前 " ;
            }

            if (day > 365){
                int year = day / 365;
                int i = day - year * 365;
                time = year + " 年 " + i + " 天 ";
            }else {
                time = day + " 天 ";
            }

            if (hours != 0){
                time = time + hours + " 小时";
            }
        }

        if (time.isEmpty()){
            return null;
        }
        return time + "前";

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
     * 返回今天星期几
     * @return 1: 周一 7:周天
     */
    public static int week() {
        Calendar calendar=Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (i == 0){
            return 7;
        }
        return i;
    }

    /**
     * 系统类型
     * @return 1.windows 2.其他
     */
    public static int findSystemType(){
        String property = System.getProperty("os.name");
        if (property.contains("Windows")){
            return 1;
        } else if (property.contains("mac")){
            return 3;
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
        String[] cmd;
        if (findSystemType() == 1){
            if (!PipelineUtil.isNoNull(path)){
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", order);
                process = processBuilder.start();
            }else {
                cmd = new String[] { "cmd.exe", "/c", " " + order };
                process = runtime.exec(cmd,null,new File(path));
            }
        } else if (findSystemType() == 2){
            if (!PipelineUtil.isNoNull(path)){
                cmd = new String[] { "/bin/sh", "-c", " source /etc/profile;"+ order };
                process = runtime.exec(cmd);
            }else {
                cmd = new String[] { "/bin/sh", "-c", "cd " + path + ";" + " source /etc/profile;"+ order };
                process = runtime.exec(cmd,null,new File(path));
            }
        }else {
            if (!PipelineUtil.isNoNull(path)){
                cmd = new String[] { "/bin/zsh", "-c", order };
                process = runtime.exec(cmd);
            }else {
                cmd = new String[] { "/bin/zsh", "-c", "cd " + path + ";" + " source /etc/profile;"+ order };
                process = runtime.exec(cmd,null,new File(path));
            }
        }
        return process;
    }

    /**
     * 返回命令集合
     * @param order 命令
     * @return 命令集合
     */
    public static List<String> execOrder(String order){
        if (!isNoNull(order)){
            return Collections.emptyList();
        }
        String[] split = order.split("\n");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            if (!isNoNull(s)){
                continue;
            }
            if (s.contains("#")){
                int i = s.indexOf("#");
                if (i != -1 && i != 0){
                    String[] strings = s.split("#");
                    String string = strings[0];
                    list.add(string);
                }
            }else {
                list.add(s);
            }
        }
        return list;
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
        if (s.equals("\n")){
            return false;
        }
        if (s.equals("null")){
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
        if (inputStream == null){
            return null;
        }

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
                case "git","gitee","github","gitlab","xcode" -> {
                    if ("git".equals(name) || "git.exe".equals(name)) {
                        return ;
                    }
                }
                case "svn" -> {
                    if ("svn".equals(name) || "svn.exe".equals(name)) {
                        return ;
                    }
                }
                case "maven" -> {
                    if ("mvn".equals(name)) {
                        return ;
                    }
                }
                case "nodejs" -> {
                    if ("npm".equals(name)) {
                        return ;
                    }
                }
            }
        }
    }


    /**
     * 效验字段是否为空
     * @param args 字段
     * @return true:不为空 false:空
     */
    public static Boolean validNoNullFiled(Object... args){
        if (Objects.isNull(args)){
            return true;
        }

        List<Object> list = Arrays.stream(args).toList();
        for (Object object : list) {
            if (object instanceof String s){
                if (StringUtils.isBlank(s)){
                    return false;
                }
            } else if (object instanceof Integer){
                if ((Integer) object == 0){
                    return false;
                }
            } else {
                if (Objects.isNull(object)){
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 生成随机字符串（1~33位）
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String randomString(Integer length) {
        String chars = "abcdefghijklmnopqrstuvwxyz123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }


    public static String findTiklabToken() {
        return Base64.getEncoder().encodeToString("tiklab".getBytes());
    }


}





































