package io.thoughtware.arbess.support.trigger.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name="pip_trigger")
public class TriggerEntity {

    //id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "trigger_id")
    private String triggerId;

    @Column(name = "name")
    private String name;

    @Column(name = "task_sort")
    private int taskSort;

    @Column(name = "task_type")
    private int taskType;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "pipeline_id")
    private String pipelineId;

    @Column(name = "state")
    private String state;


    public String getState() {
        return state;
    }

    public TriggerEntity setState(String state) {
        this.state = state;
        return this;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
