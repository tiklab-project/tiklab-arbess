package net.tiklab.matflow.definition.model.task;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineTimeEntity")
public class PipelineTime {

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

    private List<Integer> timeList;

    private int type;

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
}
