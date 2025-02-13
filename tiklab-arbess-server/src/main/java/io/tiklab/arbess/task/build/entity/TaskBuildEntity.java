package io.tiklab.arbess.task.build.entity;

import io.tiklab.dal.jpa.annotation.*;

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

    // jdk版本
    @Column(name = "tool_jdk" ,notNull = true)
    private String toolJdk;

    // maven版本
    @Column(name = "tool_maven" ,notNull = true)
    private String toolMaven;

    // npm版本
    @Column(name = "tool_nodejs" ,notNull = true)
    private String toolNodejs;

    public String getToolJdk() {
        return toolJdk;
    }

    public void setToolJdk(String toolJdk) {
        this.toolJdk = toolJdk;
    }

    public String getToolMaven() {
        return toolMaven;
    }

    public void setToolMaven(String toolMaven) {
        this.toolMaven = toolMaven;
    }

    public String getToolNodejs() {
        return toolNodejs;
    }

    public void setToolNodejs(String toolNodejs) {
        this.toolNodejs = toolNodejs;
    }

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
