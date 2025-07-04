package io.tiklab.arbess.support.trigger.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;


import java.sql.Timestamp;


/**
 * 流水线触发器模型
 */
//@ApiModel
@Join
@Mapper
public class Trigger {

    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    // 流水线id
    private String pipelineId;

    // 执行类型 1 定时 2 周期
    private Integer  execType;

    // 触发表达式
    private String cron;

    // 周几 1~7
    private Integer weekTime;

    // 具体时间 10:00
    private String data;

    // 状态 1 开启 2 未开启
    private Integer status;

    public Integer getExecType() {
        return execType;
    }

    public Trigger setExecType(Integer execType) {
        this.execType = execType;
        return this;
    }


    public Integer getStatus() {
        return status;
    }

    public Trigger setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getId() {
        return id;
    }

    public Trigger setId(String id) {
        this.id = id;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Trigger setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public Trigger setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public String getCron() {
        return cron;
    }

    public Trigger setCron(String cron) {
        this.cron = cron;
        return this;
    }

    public Integer getWeekTime() {
        return weekTime;
    }

    public Trigger setWeekTime(Integer weekTime) {
        this.weekTime = weekTime;
        return this;
    }

    public String getData() {
        return data;
    }

    public Trigger setData(String data) {
        this.data = data;
        return this;
    }
}
