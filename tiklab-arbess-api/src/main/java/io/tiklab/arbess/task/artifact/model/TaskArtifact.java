package io.tiklab.arbess.task.artifact.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;



/**
 * 任务推送制品模型
 */
//@ApiModel
@Join
@Mapper
public class TaskArtifact {

    //@ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //@ApiProperty(name = "groupId",desc = "groupId")
    private String groupId;

    //@ApiProperty(name = "artifactId",desc = "artifactId")
    private String artifactId;

    //@ApiProperty(name = "artifactType",desc = "推送类型")
    private String artifactType;

    //@ApiProperty(name = "version",desc = "version")
    private String version;

    //@ApiProperty(name = "fileType",desc = "文件类型")
    private String fileType;

    //@ApiProperty(name = "fileAddress",desc = "文件地址")
    private String fileAddress;

    //@ApiProperty(name = "putAddress",desc = "发送地址")
    private String putAddress;

    //@ApiProperty(name = "dockerImage",desc = "docker镜像")
    private String dockerImage;

    //授权id
    //@ApiProperty(name="authId",desc="授权id")
    private String authId;

    //@ApiProperty(name="authId",desc="rule")
    private String rule;

    /**
     * 仓库ID
     */
    @Mappings({
            @Mapping(source = "repository.id",target = "xpackId")
    })
    @JoinQuery(key = "xpackId")
    private XpackRepository repository;

    /**
     * 授权信息
     */
    private Object auth;

    /**
     * 类型
     */
    private String type;

    /**
     * 排序
     */
    private int sort;

    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getDockerImage() {
        return dockerImage;
    }

    public TaskArtifact setDockerImage(String dockerImage) {
        this.dockerImage = dockerImage;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public TaskArtifact setRule(String rule) {
        this.rule = rule;
        return this;
    }

    public String getArtifactType() {
        return artifactType;
    }

    public TaskArtifact setArtifactType(String artifactType) {
        this.artifactType = artifactType;
        return this;
    }

    public XpackRepository getRepository() {
        return repository;
    }

    public void setRepository(XpackRepository repository) {
        this.repository = repository;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }

    public String getPutAddress() {
        return putAddress;
    }

    public void setPutAddress(String putAddress) {
        this.putAddress = putAddress;
    }
}
