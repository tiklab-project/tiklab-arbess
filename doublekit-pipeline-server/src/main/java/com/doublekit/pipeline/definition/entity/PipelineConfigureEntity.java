package com.doublekit.pipeline.definition.entity;

import com.doublekit.dal.jpa.annotation.*;


/**
 * 流水线配置
 */

@Entity
@Table(name="pipeline_configure")
public class PipelineConfigureEntity {

    //流水线配置id
    //id
    @Id
    @GeneratorValue
    @Column(name = "configure_id")
    private String configureId;

    //创建配置时间
    @Column(name = "create_time",notNull = true)
    private String createTime;

    //流水线名称
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //源码
    @Column(name = "taskId",notNull = true)
    private String taskId;

    //测试
    @Column(name = "taskType",notNull = true)
    private String taskType;

    //构建
    @Column(name = "sort",notNull = true)
    private String sort;

    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
