package io.tiklab.arbess.task.pullArtifact.entity;

import io.tiklab.dal.jpa.annotation.Column;
import io.tiklab.dal.jpa.annotation.Entity;
import io.tiklab.dal.jpa.annotation.Id;
import io.tiklab.dal.jpa.annotation.Table;

@Entity
@Table(name="pip_task_pull_artifact")
public class TaskPullArtifactEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    @Column(name = "pull_type")
    private String pullType;

    @Column(name = "docker_image")
    private String dockerImage;

    @Column(name = "remote_address")
    private String remoteAddress;

    @Column(name = "local_address")
    private String localAddress;

    @Column(name = "auth_id")
    private String authId;

    @Column(name = "version")
    private String version;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "artifact_id")
    private String artifactId;

    @Column(name = "xpack_id")
    private String xpackId;

    @Column(name = "transitive")
    private String transitive;

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


    public String getXpackId() {
        return xpackId;
    }

    public TaskPullArtifactEntity setXpackId(String xpackId) {
        this.xpackId = xpackId;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public TaskPullArtifactEntity setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public TaskPullArtifactEntity setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public TaskPullArtifactEntity setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public String getTransitive() {
        return transitive;
    }

    public TaskPullArtifactEntity setTransitive(String transitive) {
        this.transitive = transitive;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskPullArtifactEntity setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getPullType() {
        return pullType;
    }

    public TaskPullArtifactEntity setPullType(String pullType) {
        this.pullType = pullType;
        return this;
    }

    public String getDockerImage() {
        return dockerImage;
    }

    public TaskPullArtifactEntity setDockerImage(String dockerImage) {
        this.dockerImage = dockerImage;
        return this;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public TaskPullArtifactEntity setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public TaskPullArtifactEntity setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    public String getAuthId() {
        return authId;
    }

    public TaskPullArtifactEntity setAuthId(String authId) {
        this.authId = authId;
        return this;
    }
}
