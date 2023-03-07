package io.tiklab.matflow.support.trigger.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_pipeline_trigger_time")
public class TriggerTimeEntity {

    @Id
    @GeneratorValue
    @Column(name = "time_id")
    private String timeId;

    // 1:单次 2:周期
    @Column(name = "task_type")
    private String taskType;

    //时间
    @Column(name = "date")
    private int date;

    @Column(name = "cron")
    private String cron;

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

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
