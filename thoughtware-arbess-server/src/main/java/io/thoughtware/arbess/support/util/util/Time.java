package io.thoughtware.arbess.support.util.util;

public class Time {

    // 当前为周几
    private String week;

    // 当前为几月
    private String month;

    // 当前周开始时间
    private String weekBeginTime;

    // 当前周结束时间
    private String weekEndTime;

    // 当前月开始时间
    private String monthBeginTime;

    // 当前月介绍时间
    private String monthEndTime;


    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getWeekBeginTime() {
        return weekBeginTime;
    }

    public void setWeekBeginTime(String weekBeginTime) {
        this.weekBeginTime = weekBeginTime;
    }

    public String getWeekEndTime() {
        return weekEndTime;
    }

    public void setWeekEndTime(String weekEndTime) {
        this.weekEndTime = weekEndTime;
    }

    public String getMonthBeginTime() {
        return monthBeginTime;
    }

    public void setMonthBeginTime(String monthBeginTime) {
        this.monthBeginTime = monthBeginTime;
    }

    public String getMonthEndTime() {
        return monthEndTime;
    }

    public void setMonthEndTime(String monthEndTime) {
        this.monthEndTime = monthEndTime;
    }
}
