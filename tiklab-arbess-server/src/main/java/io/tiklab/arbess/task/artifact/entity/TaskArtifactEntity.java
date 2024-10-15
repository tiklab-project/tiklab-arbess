package io.tiklab.arbess.task.artifact.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_artifact")
public class TaskArtifactEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "artifact_type")
    private String artifactType;

    @Column(name = "artifact_id")
    private String artifactId;

    @Column(name = "version")
    private String version;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_address")
    private String fileAddress;

    @Column(name = "put_address")
    private String putAddress;

    @Column(name = "xpack_id")
    private String xpackId;

    @Column(name = "auth_id")
    private String authId;

    @Column(name = "rule",notNull = true)
    private String rule;

    @Column(name = "docker_image",notNull = true)
    private String dockerImage;

    public String getDockerImage() {
        return dockerImage;
    }

    public TaskArtifactEntity setDockerImage(String dockerImage) {
        this.dockerImage = dockerImage;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public TaskArtifactEntity setRule(String rule) {
        this.rule = rule;
        return this;
    }

    public String getArtifactType() {
        return artifactType;
    }

    public TaskArtifactEntity setArtifactType(String artifactType) {
        this.artifactType = artifactType;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskArtifactEntity setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public TaskArtifactEntity setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public TaskArtifactEntity setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public TaskArtifactEntity setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public TaskArtifactEntity setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public TaskArtifactEntity setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
        return this;
    }

    public String getPutAddress() {
        return putAddress;
    }

    public TaskArtifactEntity setPutAddress(String putAddress) {
        this.putAddress = putAddress;
        return this;
    }

    public String getXpackId() {
        return xpackId;
    }

    public TaskArtifactEntity setXpackId(String xpackId) {
        this.xpackId = xpackId;
        return this;
    }

    public String getAuthId() {
        return authId;
    }

    public TaskArtifactEntity setAuthId(String authId) {
        this.authId = authId;
        return this;
    }
}
