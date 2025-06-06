package io.tiklab.arbess.support.trigger.entity;

import io.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

@Entity
@Table(name="pip_trigger")
public class TriggerEntity {

    //id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "pipeline_id")
    private String pipelineId;

    @Column(name = "status")
    private Integer status;

    @Column(name = "exec_type")
    private Integer execType;

    @Column(name = "week_time")
    private Integer weekTime;

    @Column(name = "data")
    private String data;

    @Column(name = "cron")
    private String cron;


    public String getId() {
        return id;
    }

    public TriggerEntity setId(String id) {
        this.id = id;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public TriggerEntity setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public TriggerEntity setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public TriggerEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getExecType() {
        return execType;
    }

    public TriggerEntity setExecType(Integer execType) {
        this.execType = execType;
        return this;
    }

    public Integer getWeekTime() {
        return weekTime;
    }

    public TriggerEntity setWeekTime(Integer weekTime) {
        this.weekTime = weekTime;
        return this;
    }

    public String getData() {
        return data;
    }

    public TriggerEntity setData(String data) {
        this.data = data;
        return this;
    }

    public String getCron() {
        return cron;
    }

    public TriggerEntity setCron(String cron) {
        this.cron = cron;
        return this;
    }
}
