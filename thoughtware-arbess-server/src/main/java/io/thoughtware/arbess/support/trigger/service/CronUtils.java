package io.thoughtware.arbess.support.trigger.service;

import io.thoughtware.core.exception.ApplicationException;
import io.thoughtware.arbess.support.util.util.PipelineUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * cron表达式工具类
 */

public class CronUtils {


    /**
     * cron 表达式转换成日期
     * @param cron 表达式
     * @return 日期
     */
    public static Map<String, String > cronWeek(String cron) {
        String[] s = cron.split(" ");
        String date = s[2]+":"+s[1];

        LocalDate localDate = LocalDate.of(Integer.parseInt(s[6]), Integer.parseInt(s[4]), Integer.parseInt(s[3]));
        int value = localDate.getDayOfWeek().getValue();
        String[] weekDays = {"今天","周一", "周二", "周三", "周四", "周五", "周六","周日"};

        String weekTime = weekTime(cron);

        Date chengeDate = PipelineUtil.StringChengeDate(weekTime);

        // 获取当前日期
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDateTime tomorrowMidnight = LocalDateTime.of(tomorrow, LocalTime.MIDNIGHT);
        Date dates = Date.from(tomorrowMidnight.atZone(ZoneId.systemDefault()).toInstant());
        if (new Date().getTime() < chengeDate.getTime() && chengeDate.getTime()< dates.getTime()){
            value = 0;
        }

        String time = weekDays[value];

        Map<String, String > map = new HashMap<>();
        map.put("cron",time);
        map.put("time",date);
        map.put("weekTime",weekTime);
        return map;
    }

    /**
     * 周几转换成具体日期
     * @param cron 表达式
     * @return 日期
     */
    public static String weekTime(String cron){
        String[] s = cron.split(" ");
        // 0 27 16 1 4 ? 2023
        return s[6] + "-" + s[4]+ "-" + s[3] +" "+ s[2] + ":"+ s[1] +":00";
    }

    public static String weekCron(String date,int timeDay){

        int year ;
        int month ;
        int day ;
        String hour ;
        String minute ;

        boolean isToday = false;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        Date parse ;
        try {
            parse = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new ApplicationException("时间格式转换错误，错误时间：" + date);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("mm");
        minute = dateFormat.format(parse);

        SimpleDateFormat format = new SimpleDateFormat("HH");
        hour = format.format(parse);

        // 判断是否为今天
        int nowWeek = PipelineUtil.week();
        if( timeDay == nowWeek){
            LocalTime currentTime = LocalTime.now();
            if (currentTime.getHour() == Integer.parseInt(hour) ){
                if (currentTime.getMinute() < Integer.parseInt(minute) ){
                    isToday = true;
                }
            } else if (currentTime.getHour() < Integer.parseInt(hour)){
                isToday = true;
            }
        }

        if (isToday){
            // 获取今天的日期
            LocalDate currentDate = LocalDate.now();
            // 获取年、月和日
            year = currentDate.getYear();
            month = currentDate.getMonthValue();
            day = currentDate.getDayOfMonth();
        }else {
            Map<String, Integer> weekDetails ;
            if (timeDay <= nowWeek ){
                weekDetails = findNextWeekTime(timeDay);
            }else {
                weekDetails = findNowWeekTime(timeDay);
            }

            year = weekDetails.get("year");
            month = weekDetails.get("month");
            day = weekDetails.get("day");
        }

        return "00 " + minute + " " + hour + " " + day + " " + month + " ? " + year;
    }

    /**
     * 获取下周的日期
     * @param week 周几
     * @return 下周的日期
     */
    public static Map<String, Integer> findNextWeekTime(int week) {

        // 获取当前日期
        LocalDate currentDate = LocalDate.now();
        // 找到下一个周一的日期
        LocalDate nextMonday = currentDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

        LocalDate nextDate = nextMonday.plusDays(week-1);

        // 获取年、月、日和星期几
        int year = nextDate.getYear();
        int month = nextDate.getMonthValue();
        int day = nextDate.getDayOfMonth();
        int dayOfWeek = nextDate.getDayOfWeek().getValue();
        Map<String,Integer> weekMap = new HashMap<>();
        weekMap.put("year", nextDate.getYear());
        weekMap.put("month", nextDate.getMonthValue());
        weekMap.put("day", nextDate.getDayOfMonth());
        return weekMap;
    }

    /**
     * 获取本周的日期
     * @param week 周几
     * @return 下周的日期
     */
    public static Map<String, Integer> findNowWeekTime(int week) {
        // 获取当前日期
        LocalDate currentDate = LocalDate.now();

        // 获取传入数字对应的DayOfWeek
        DayOfWeek targetDayOfWeek = DayOfWeek.of(week);

        // 如果传入的是星期六或星期天，获取当前周的相应星期的日期
        LocalDate nowDate = (week == 6 || week == 7) ?
                currentDate.with(TemporalAdjusters.nextOrSame(targetDayOfWeek)) :
                currentDate.with(TemporalAdjusters.next(targetDayOfWeek));

        Map<String,Integer> weekMap = new HashMap<>();
        weekMap.put("year", nowDate.getYear());
        weekMap.put("month", nowDate.getMonthValue());
        weekMap.put("day", nowDate.getDayOfMonth());
        return weekMap;
    }




}

























