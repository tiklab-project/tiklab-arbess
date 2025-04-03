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

    @Column(name = "artifact_name")
    private String artifactName;

    @Column(name = "artifact_type")
    private String artifactType;

    public String getArtifactType() {
        return artifactType;
    }

    public void setArtifactType(String artifactType) {
        this.artifactType = artifactType;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public void setArtifactName(String artifactName) {
        this.artifactName = artifactName;
    }

    public String getVersion() {
        return version;
    }

    public TaskPullArtifactEntity setVersion(String version) {
        this.version = version;
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
