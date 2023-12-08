package io.thoughtware.matflow.pipeline.definition.model;

public class PipelineRecently {

    // 流水线id
    private String pipelineId;

    // 流水线实例id
    private String instanceId;

    private String createTime;

    // 流水线名称
    private String pipelineName;

    // 状态 1.未运行 2.运行中
    private String execTime;

    // 上次运行时间
    private String lastRunTime;

    // 上次运行状态
    private String lastRunState;

    // 构建次数
    private Integer number;

    private Integer color;

    private Integer lastRunType;


    public Integer getLastRunType() {
        return lastRunType;
    }

    public void setLastRunType(Integer lastRunType) {
        this.lastRunType = lastRunType;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
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

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public String getLastRunTime() {
        return lastRunTime;
    }

    public void setLastRunTime(String lastRunTime) {
        this.lastRunTime = lastRunTime;
    }

    public String getLastRunState() {
        return lastRunState;
    }

    public void setLastRunState(String lastRunState) {
        this.lastRunState = lastRunState;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
