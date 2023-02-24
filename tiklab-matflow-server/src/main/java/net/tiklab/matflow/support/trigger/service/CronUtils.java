package net.tiklab.matflow.support.trigger.service;

import net.tiklab.core.exception.ApplicationException;
import net.tiklab.matflow.support.until.PipelineUntil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CronUtils {

    /**
     * 日期转换成cron表达式
     * @param date 时间 00:00格式
     * @param day 星期几
     * @return cron
     */
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

        //获取
        long time;
        long time1;
        try {
             time = simpleDateFormat.parse(date).getTime();
             time1 = simpleDateFormat.parse(PipelineUntil.date(5)).getTime();
        } catch (ParseException e) {
            throw new ApplicationException("时间格式转换错误，错误时间："+date);
        }

        if (day == PipelineUntil.week() && time > time1){
            week = "*" ;
        }

        return "0"+ " "+mm+" "+ hh+" "+"? *"+" "+ week;
    }

    /**
     * cron 表达式转换成日期
     * @param cron 表达式
     * @return 日期
     */
    public static Map<String, String > cronWeek(String cron) {
        String[] s = cron.split(" ");
        String date = s[2]+":"+s[1];
        String s1 = s[5];
        int day = 0;
        if (!s1.equals("*")){
            day = Integer.parseInt(s[5]);
        }

        String[] weekDays = {"今天", "周一", "周二", "周三", "周四", "周五", "周六","周日"};
        String time = weekDays[day];

        Map<String, String > map = new HashMap<>();
        map.put("cron",time+"/"+date);
        map.put("time",date);
        map.put("weekTime",weekTime(cron));
        return map;
    }

    /**
     * 周几装换成具体日期
     * @param cron 表达式
     * @return 日期
     */
    public static String weekTime(String cron){
        String[] s = cron.split(" ");
        String time = s[2]+":"+s[1];
        int day = 0;
        if (!s[5].equals("*")){
            day = Integer.parseInt(s[5]);
        }

        int week = PipelineUntil.week();
        if (day != 0 && day > week){
            day = day - week;
        }else if (day != 0 && day < week){
            day = day + week;
        }else if (day != 0){
            day = 7;
        }

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DATE, day);
        String format = simpleDateFormat.format(calendar.getTime());
        return format+" "+time+":00";
    }





}

























