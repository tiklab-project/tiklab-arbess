package io.tiklab.arbess.support.util.util;

import io.tiklab.core.exception.ApplicationException;
import io.tiklab.core.exception.SystemException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * 获取指定时间前多少天
     * @param time 时间
     * @param number 天数
     * @param dateFormat 时间格式
     * @return 时间
     */
    public static Date findBeforeDay(String time, int number,SimpleDateFormat dateFormat) {

        // 将输入字符串解析为 Date 对象
        Date specifiedDate ;
        try {
            specifiedDate = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new SystemException("获取指定时间前时间失败，转换时间错误："+e);
        }

        long l = (long)number * 24 * 60 * 60 * 1000;

        // 计算指定天数前的日期
        long millisBeforeDay = specifiedDate.getTime() - l;

        // 创建一个新的 Date 对象，表示指定天数前的日期
        return new Date(millisBeforeDay);
    }


    /**
     * 获取指定时间前多少天
     * @param time 时间
     * @param number 天数
     * @return 时间
     */
    public static Date findLastDay(String time, int number,SimpleDateFormat dateFormat) {
        // 将输入字符串解析为 Date 对象
        Date specifiedDate ;
        try {
            specifiedDate = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new SystemException("获取指定时间后时间失败，转换时间错误："+e);
        }

        long l = (long)number * 24 * 60 * 60 * 1000;

        // 计算指定天数前的日期
        long millisBeforeDay = specifiedDate.getTime() + l;

        // 创建一个新的 Date 对象，表示指定天数前的日期
        return new Date(millisBeforeDay);
    }


    /**
     * 获取指定时间内的所有日期，日期必须为 yyyy-MM-dd 格式
     * @param startDate 开始时间
     * @param endDate 介绍时间
     * @return 所有日期
     */
    public static List<String> findDatesBetween(String startDate, String endDate) {
        List<String> dateList = new ArrayList<>();

        DateTimeFormatter  dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate start = LocalDate.parse(startDate, dateTimeFormatter);
        LocalDate end = LocalDate.parse(endDate, dateTimeFormatter);

        while (!start.isAfter(end)) {
            dateList.add(start.format(dateTimeFormatter));
            start = start.plusDays(1);
        }

        return dateList;
    }

    /**
     * 获取月份日期
     * @param year 年份
     * @return 月份日期
     */
    public static List<String> findMonthlyStartDates(int year) {
        List<String> startEndDates = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int month = 1; month <= 12; month++) {
            // 构造 YearMonth 对象表示每个月的年月
            YearMonth yearMonth = YearMonth.of(year, month);

            // 获取该月的第一天
            LocalDate firstDayOfMonth = yearMonth.atDay(1);

            // 获取该月的最后一天
            LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

            // 将日期格式化为字符串并添加到列表中
            startEndDates.add(firstDayOfMonth.format(dateTimeFormatter));
            // startEndDates.add(lastDayOfMonth.format(dateTimeFormatter));
        }

        return startEndDates;
    }


    /**
     * 获取指定时间周以及指定前面几周的开始与结束时间格式为 前yyyy-MM-dd;yyyy-MM-dd后
     * @param date 指定时间
     * @param numberOfWeeks 前面几周
     * @return 开始与结束时间
     */
    public static List<String> findPreviousWeeks(String date, int numberOfWeeks) {
        List<String> weeks = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate specifiedDate = LocalDate.parse(date, dateTimeFormatter);

        for (int i = 1; i <= numberOfWeeks; i++) {
            // 获取当前日期所在周的第一天（周一）
            LocalDate startOfWeek = specifiedDate.minusDays(specifiedDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());

            // 获取当前日期所在周的最后一天（周日）
            LocalDate endOfWeek = startOfWeek.plusDays(6);

            String beginWeek = startOfWeek.format(dateTimeFormatter);

            String endWeek = endOfWeek.format(dateTimeFormatter);

            // 将日期格式化为字符串并添加到列表中
            // String[] weekDates = {startOfWeek.format(dateTimeFormatter),
            //         endOfWeek.format(dateTimeFormatter)};

            weeks.add(beginWeek + ";" + endWeek);

            // 更新指定日期为前一周的同一天
            specifiedDate = specifiedDate.minusWeeks(1);
        }

        return weeks;
    }


    /**
     * 解析时间
     * @param time 时间 yyyy-MM-dd格式
     * @param type day month year
     * @return 时间
     */
    public static int findTime(String time,String type){

        // 将字符串解析为 LocalDate 对象
        LocalDate date = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 获取年、月、日
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        switch (type){
            case "day" -> {
                return date.getDayOfMonth();
            }
            case "month" -> {
                return date.getMonthValue();
            }
            case "year" -> {
                return date.getYear();
            }
            default -> {
                return date.getYear();
            }
        }
    }


}














