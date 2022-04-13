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
    private String codeId;

    //测试
    @Column(name = "structure_id",notNull = true)
    private String structureId;


    //构建
    @Column(name = "test_id",notNull = true)
    private String testId;

    //构建
    @Column(name = "deploy_id",notNull = true)
    private String deployId;

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

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }
}
