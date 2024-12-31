package io.tiklab.arbess.support.trigger.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;



import java.util.List;
/**
 * 流水线触发器时间模型
 */
//@ApiModel
@Join
@Mapper(targetAlias = "TriggerTimeEntity")
public class TriggerTime {

    //@ApiProperty(name="timeId",desc="id")
    private String timeId;

    //@ApiProperty(name="taskType",desc="类型 cycle:周期 one:单次")
    private int taskType;

    //@ApiProperty(name="date",desc="时间")
    private int date;

    //@ApiProperty(name="time",desc="具体时间")
    private String time;

    //@ApiProperty(name="triggerId",desc="配置id")
    private String triggerId;

    //@ApiProperty(name="cron",desc="配置id")
    private String cron;

    //@ApiProperty(name="execTime",desc="具体执行时间")
    private String weekTime;

    //@ApiProperty(name="execTime",desc="执行时间")
    private String execTime;

    //@ApiProperty(name="timeList",desc="天数,周几")
    private List<Integer> timeList;

    private Integer dayTime;

    //@ApiProperty(name="type",desc="类型")
    private int type;

    //@ApiProperty(name="name",desc="名称")
    private String name;

    /**
     * 状态
     */
    private String state;

    public String getState() {
        return state;
    }

    public TriggerTime setState(String state) {
        this.state = state;
        return this;
    }

    public Integer getDayTime() {
        return dayTime;
    }

    public void setDayTime(Integer dayTime) {
        this.dayTime = dayTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Integer> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Integer> timeList) {
        this.timeList = timeList;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getWeekTime() {
        return weekTime;
    }

    public void setWeekTime(String weekTime) {
        this.weekTime = weekTime;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }
}
