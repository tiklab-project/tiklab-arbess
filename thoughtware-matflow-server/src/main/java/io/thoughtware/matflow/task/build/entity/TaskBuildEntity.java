package io.thoughtware.matflow.task.build.entity;

import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_build")
public class TaskBuildEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    //地址
    @Column(name = "build_address",notNull = true)
    private String buildAddress;

    //分支
    @Column(name = "build_order",notNull = true)
    private String buildOrder;

    @Column(name = "product_rule",notNull = true)
    private String productRule;

    @Column(name = "docker_name",notNull = true)
    private String dockerName;

    @Column(name = "docker_version",notNull = true)
    private String dockerVersion;

    @Column(name = "docker_file",notNull = true)
    private String dockerFile;

    @Column(name = "docker_order",notNull = true)
    private String dockerOrder;

    public String getDockerOrder() {
        return dockerOrder;
    }

    public TaskBuildEntity setDockerOrder(String dockerOrder) {
        this.dockerOrder = dockerOrder;
        return this;
    }

    public String getDockerName() {
        return dockerName;
    }

    public TaskBuildEntity setDockerName(String dockerName) {
        this.dockerName = dockerName;
        return this;
    }

    public String getDockerVersion() {
        return dockerVersion;
    }

    public TaskBuildEntity setDockerVersion(String dockerVersion) {
        this.dockerVersion = dockerVersion;
        return this;
    }

    public String getDockerFile() {
        return dockerFile;
    }

    public TaskBuildEntity setDockerFile(String dockerFile) {
        this.dockerFile = dockerFile;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBuildAddress() {
        return buildAddress;
    }

    public void setBuildAddress(String buildAddress) {
        this.buildAddress = buildAddress;
    }

    public String getBuildOrder() {
        return buildOrder;
    }

    public void setBuildOrder(String buildOrder) {
        this.buildOrder = buildOrder;
    }

    public String getProductRule() {
        return productRule;
    }

    public void setProductRule(String productRule) {
        this.productRule = productRule;
    }
}
