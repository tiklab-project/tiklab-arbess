package io.tiklab.arbess.pipeline.execute.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

@Join
@Mapper
public class PipelineQueue {

    private String id;

    // 创建时间
    private String createTime;

    // 创建人
    private String userId;

    // 流水线ID
    private String pipelineId;

    // 状态  等待：wait, 运行：run, 成功：success, 失败：failed
    private String status;

    // 实例ID
    private String instanceId;

    // agentId
    private String agentId;

    public PipelineQueue() {
    }

    public PipelineQueue(String userId, String pipelineId) {
        this.userId = userId;
        this.pipelineId = pipelineId;
    }

    public PipelineQueue(String userId, String pipelineId, String status) {
        this.userId = userId;
        this.pipelineId = pipelineId;
        this.status = status;
    }

    public PipelineQueue(String userId, String pipelineId, String status, String instanceId, String agentId) {
        this.userId = userId;
        this.pipelineId = pipelineId;
        this.status = status;
        this.instanceId = instanceId;
        this.agentId = agentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}














