package io.tiklab.arbess.pipeline.execute.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_queue")
public class PipelineQueueEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "pipeline_id")
    private String pipelineId;

    @Column(name = "status")
    private String status;

    @Column(name = "instance_id")
    private String instanceId;

    @Column(name = "agent_id")
    private String agentId;


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














