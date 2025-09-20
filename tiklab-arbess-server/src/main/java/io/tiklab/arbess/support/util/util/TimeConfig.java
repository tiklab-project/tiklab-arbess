package io.tiklab.arbess.support.util.util;

import io.tiklab.core.exception.ApplicationException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class TimeConfig {

    public static Time findDataTime(String time){

        Time statisticsTime = new Time();

        // 将字符串解析为LocalDate对象
        LocalDate specifiedDate = LocalDate.parse(time);

        // 获取指定天数是星期几
        DayOfWeek dayOfWeek = specifiedDate.getDayOfWeek();
        int weekValue = dayOfWeek.getValue();
        statisticsTime.setWeek(findWeek(weekValue));

        // 计算所在周的开始日期和结束日期
        LocalDate startOfWeek = specifiedDate.minusDays(dayOfWeek.getValue() - 1);
        LocalDate endOfWeek = specifiedDate.plusDays(7 - dayOfWeek.getValue());

        statisticsTime.setWeekBeginTime(startOfWeek.toString());
        statisticsTime.setWeekEndTime(endOfWeek.toString());


        // 获取指定时间的月份
        Month specifiedMonth = specifiedDate.getMonth();

        // 获取指定月份的开始日期
        LocalDate startOfMonth = specifiedDate.with(TemporalAdjusters.firstDayOfMonth());

        // 获取指定月份的结束日期
        LocalDate endOfMonth = specifiedDate.with(TemporalAdjusters.lastDayOfMonth());


        statisticsTime.setMonth(findMonth(specifiedMonth.getValue()));
        statisticsTime.setMonthBeginTime(startOfMonth.toString());
        statisticsTime.setMonthEndTime(endOfMonth.toString());

        return statisticsTime;
    }

    // 转换周
    private static String findWeek(int week){
        switch (week){
            case 1 -> {
                return "周一";
            }
            case 2 -> {
                return "周二";
            }
            case 3 -> {
                return "周三";
            }
            case 4 -> {
                return "周四";
            }
            case 5 -> {
                return "周五";
            }
            case 6 -> {
                return "周六";
            }
            case 7 -> {
                return "周天";
            }
            default -> throw new ApplicationException("获取当前周失败，week：" + week);
        }




    }

    // 转换月
    private static String findMonth(int month){
        switch (month){
            case 1 -> {
                return "一月";
            }
            case 2 -> {
                return "二月";
            }
            case 3 -> {
                return "三月";
            }
            case 4 -> {
                return "四月";
            }
            case 5 -> {
                return "五月";
            }
            case 6 -> {
                return "六月";
            }
            case 7 -> {
                return "七月";
            }
            case 8 -> {
                return "八月";
            }
            case 9 -> {
                return "九月";
            }
            case 10 -> {
                return "十月";
            }
            case 11 -> {
                return "十一月";
            }
            case 12 -> {
                return "十二月";
            }
            default -> throw new ApplicationException("获取指定月份失败，month：" + month);
        }
    }




}














