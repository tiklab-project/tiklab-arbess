package io.tiklab.arbess.task.build.entity;

import io.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

@Entity
@Table(name="pip_task_build_product")
public class TaskBuildProductEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id" ,notNull = true)
    private String id;

    @Column(name = "instance_id",notNull = true)
    private String instanceId;

    @Column(name = "value",notNull = true)
    private String value;

    @Column(name = "create_time",notNull = true)
    private Timestamp createTime;

    @Column(name = "agent_id",notNull = true)
    private String agentId;

    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    @Column(name = "file_size",notNull = true)
    private String fileSize;

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
