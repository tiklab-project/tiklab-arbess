package io.tiklab.matflow.support.trigger.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;
/**
 * 流水线触发器时间模型
 */
@ApiModel
@Join
@Mapper(targetAlias = "TriggerTimeEntity")
public class TriggerTime {

    @ApiProperty(name="timeId",desc="id")
    private String timeId;

    @ApiProperty(name="taskType",desc="类型 cycle:周期 one:单次")
    private int taskType;

    @ApiProperty(name="date",desc="时间")
    private int date;

    @ApiProperty(name="time",desc="详细时间")
    private String time;

    @ApiProperty(name="configId",desc="配置id")
    private String configId;

    @ApiProperty(name="cron",desc="配置id")
    private String cron;

    //具体执行时间
    private String weekTime;

    //执行时间
    private String execTime;

    //天数
    private List<Integer> timeList;

    private int type;

    @ApiProperty(name="name",desc="名称")
    private String name;

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

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
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
