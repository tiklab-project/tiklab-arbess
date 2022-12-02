package net.tiklab.matflow.definition.entity.task;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_time")
public class PipelineTimeEntity {

    @Id
    @GeneratorValue
    @Column(name = "time_id")
    private String timeId;

    // cycle:周期 one:单次
    @Column(name = "task_type")
    private String taskType;

    //时间
    @Column(name = "date")
    private int date;

    //详细时间
    @Column(name = "time")
    private String time;

    //配置id
    @Column(name = "config_id")
    private String configId;

    public String getTimeId() {
        return timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
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

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}
