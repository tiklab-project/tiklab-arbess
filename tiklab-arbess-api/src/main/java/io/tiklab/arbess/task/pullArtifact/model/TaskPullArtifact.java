package io.tiklab.arbess.task.pullArtifact.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;


//@ApiModel
@Join
@Mapper
public class TaskPullArtifact {

    //@ApiProperty(name = "id",desc = "id")
    private String taskId;

    //@ApiProperty(name = "id",desc = "id")
    private String pullType;

    //@ApiProperty(name = "id",desc = "id")
    private String dockerImage;

    //@ApiProperty(name = "id",desc = "id")
    private String remoteAddress;

    //@ApiProperty(name = "id",desc = "id")
    private String localAddress;

    //@ApiProperty(name = "id",desc = "id")
    private String authId;

    //@ApiProperty(name = "version",desc = "version")
    private String version;

    //授权信息
    private Object auth;

    private int sort;

    private String type;

    private String instanceId;

    private String artifactName;

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



    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }


    public String getVersion() {
        return version;
    }

    public TaskPullArtifact setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskPullArtifact setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getPullType() {
        return pullType;
    }

    public TaskPullArtifact setPullType(String pullType) {
        this.pullType = pullType;
        return this;
    }

    public String getDockerImage() {
        return dockerImage;
    }

    public TaskPullArtifact setDockerImage(String dockerImage) {
        this.dockerImage = dockerImage;
        return this;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public TaskPullArtifact setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public TaskPullArtifact setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    public String getAuthId() {
        return authId;
    }

    public TaskPullArtifact setAuthId(String authId) {
        this.authId = authId;
        return this;
    }

    public Object getAuth() {
        return auth;
    }

    public TaskPullArtifact setAuth(Object auth) {
        this.auth = auth;
        return this;
    }

    public int getSort() {
        return sort;
    }

    public TaskPullArtifact setSort(int sort) {
        this.sort = sort;
        return this;
    }

    public String getType() {
        return type;
    }

    public TaskPullArtifact setType(String type) {
        this.type = type;
        return this;
    }
}
