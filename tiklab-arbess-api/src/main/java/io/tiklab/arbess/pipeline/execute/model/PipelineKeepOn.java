package io.tiklab.arbess.pipeline.execute.model;

public class PipelineKeepOn {

    // 流水线ID
    private String pipelineId;

    // 任务实例ID
    private String taskInstanceId;

    // 状态 keepon 通过 stop 停止
    private Integer status;

    // 用户ID
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
