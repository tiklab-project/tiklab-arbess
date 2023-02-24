package net.tiklab.matflow.support.condition.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_pipeline_condition")
public class ConditionEntity {

    //流水线id
    @Id
    @GeneratorValue
    @Column(name = "cond_id")
    private String condId;

    @Column(name = "cond_name")
    private String condName;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "cond_type")
    private int condType;

    @Column(name = "cond_key")
    private String condKey;

    @Column(name = "cond_value")
    private String condValue;

    public String getCondId() {
        return condId;
    }

    public void setCondId(String condId) {
        this.condId = condId;
    }

    public String getCondName() {
        return condName;
    }

    public void setCondName(String condName) {
        this.condName = condName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCondType() {
        return condType;
    }

    public void setCondType(int condType) {
        this.condType = condType;
    }

    public String getCondKey() {
        return condKey;
    }

    public void setCondKey(String condKey) {
        this.condKey = condKey;
    }

    public String getCondValue() {
        return condValue;
    }

    public void setCondValue(String condValue) {
        this.condValue = condValue;
    }
}
















































