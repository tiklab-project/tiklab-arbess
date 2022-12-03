package net.tiklab.matflow.trigger.service;

import net.tiklab.core.exception.ApplicationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PipelineCronUtils {

    public static String weekCron (String date,int day){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date parse ;
        try {
            parse = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new ApplicationException("时间格式转换错误，错误时间："+date);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("mm");
        String mm = dateFormat.format(parse);

        SimpleDateFormat format = new SimpleDateFormat("HH");
        String hh = format.format(parse);
        String week = String.valueOf(day);
        if (day == 0){
            week ="*";
        }

        return "0"+ " "+mm+" "+ hh+" "+"? *"+" "+ week;
    }

    public static  Map<String, String > cronWeek(String cron) {
        String[] s = cron.split(" ");
        String date = s[2]+":"+s[1];
        String day = s[5];
        String time = "";
        switch (day){
            case "1" -> {
                time = "星期一";
            }
            case "2" -> {
                time = "星期二";
            }
            case "3" -> {
                time = "星期三";
            }
            case "4" -> {
                time = "星期四";
            }
            case "5" -> {
                time = "星期五";
            }
            case "6" -> {
                time = "星期六";
            }
            case "7" -> {
                time = "星期天";
            }
        }

        Map<String, String > map = new HashMap<>();
        map.put("cron",time+"/"+date);

        map.put("time",date);

        return map;
    }


}

























