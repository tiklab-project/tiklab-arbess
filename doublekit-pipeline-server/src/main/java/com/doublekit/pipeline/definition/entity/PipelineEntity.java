package com.doublekit.pipeline.definition.entity;

import com.doublekit.dal.jpa.annotation.*;

/**
 * 流水线
 */

@Entity
@Table(name="pipeline")
public class PipelineEntity {

    //流水线id
    @Id
    @GeneratorValue
    @Column(name = "pipeline_id")
    private String pipelineId;

    //流水线名称
    @Column(name = "pipeline_name",notNull = true)
    private String pipelineName;

    //流水线创建人
    @Column(name = "pipeline_create_user",notNull = true)
    private String pipelineCreateUser;

    //流水线创建时间
    @Column(name = "pipeline_create_time",notNull = true)
    private String  pipelineCreateTime;

    //流水线类型
    @Column(name = "pipeline_create_type")
    private int pipelineType;

    //流水线状态
    @Column(name = "pipeline_collect")
    private int pipelineCollect;

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public String getPipelineCreateUser() {
        return pipelineCreateUser;
    }

    public void setPipelineCreateUser(String pipelineCreateUser) {
        this.pipelineCreateUser = pipelineCreateUser;
    }

    public String getPipelineCreateTime() {
        return pipelineCreateTime;
    }

    public void setPipelineCreateTime(String pipelineCreateTime) {
        this.pipelineCreateTime = pipelineCreateTime;
    }

    public int getPipelineType() {
        return pipelineType;
    }

    public void setPipelineType(int pipelineType) {
        this.pipelineType = pipelineType;
    }

    public int getPipelineCollect() {
        return pipelineCollect;
    }

    public void setPipelineCollect(int pipelineCollect) {
        this.pipelineCollect = pipelineCollect;
    }
}
