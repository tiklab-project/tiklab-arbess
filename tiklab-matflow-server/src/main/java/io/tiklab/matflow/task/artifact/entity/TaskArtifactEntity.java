package io.tiklab.matflow.task.artifact.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_artifact")
public class TaskArtifactEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    @Column(name = "group_id")
    private String groupId;

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


    public String getXpackId() {
        return xpackId;
    }

    public void setXpackId(String xpackId) {
        this.xpackId = xpackId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }


    public String getPutAddress() {
        return putAddress;
    }

    public void setPutAddress(String putAddress) {
        this.putAddress = putAddress;
    }
}
