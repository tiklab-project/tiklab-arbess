package io.tiklab.arbess.support.approve.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_approve_pipeline")
public class ApprovePipelineEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "pipeline_id")
    private String pipelineId;

    @Column(name = "status")
    private String status;

    @Column(name = "approve_id")
    private String approveId;

    @Column(name = "instance_id")
    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}














