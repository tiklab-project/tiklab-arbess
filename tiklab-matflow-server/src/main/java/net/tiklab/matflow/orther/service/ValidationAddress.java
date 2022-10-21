package net.tiklab.matflow.orther.service;

import java.util.regex.Pattern;

/**
 * 效验地址
 */

public class ValidationAddress {

    //效验code
    public static boolean validCode(String address){
        return validGit(address) || validSvn(address);
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

    //效验ip地址
    public static boolean validIp(String ip){
        String valid = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)";
        return Pattern.matches(valid,ip);
    }

}





































