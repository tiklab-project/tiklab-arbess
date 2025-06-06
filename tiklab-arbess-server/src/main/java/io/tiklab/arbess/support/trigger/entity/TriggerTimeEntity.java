package io.tiklab.arbess.support.trigger.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_trigger_time")
public class TriggerTimeEntity {

    @Id
    @GeneratorValue(length = 12)
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
    @Column(name = "trigger_id")
    private String triggerId;

    @Column(name = "exec_status")
    private String execStatus;

    public String getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }

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

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
