package io.tiklab.arbess.task.pullArtifact.model;

import io.tiklab.arbess.setting.model.Scm;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;
import io.tiklab.arbess.task.artifact.model.XpackRepository;



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

    //@ApiProperty(name = "groupId",desc = "groupId")
    private String groupId;

    //@ApiProperty(name = "artifactId",desc = "artifactId")
    private String artifactId;

    //@ApiProperty(name = "transitive",desc = "是否拉取关联依赖")
    private Boolean transitive;

    @Mappings({
            @Mapping(source = "repository.id",target = "xpackId")
    })
    @JoinQuery(key = "xpackId")
    private XpackRepository repository;

    //授权信息
    private Object auth;

    private int sort;

    private String type;

    private String instanceId;

    // jdk版本
    @Mappings({
            @Mapping(source = "toolJdk.scmId",target = "toolJdk")
    })
    @JoinQuery(key = "scmId")
    private Scm toolJdk;

    // maven版本
    @Mappings({
            @Mapping(source = "toolMaven.scmId",target = "toolMaven")
    })
    @JoinQuery(key = "scmId")
    private Scm toolMaven;

    // npm版本
    @Mappings({
            @Mapping(source = "toolNodejs.scmId",target = "toolMaven")
    })
    @JoinQuery(key = "scmId")
    private Scm toolNodejs;

    public Scm getToolJdk() {
        return toolJdk;
    }

    public void setToolJdk(Scm toolJdk) {
        this.toolJdk = toolJdk;
    }

    public Scm getToolMaven() {
        return toolMaven;
    }

    public void setToolMaven(Scm toolMaven) {
        this.toolMaven = toolMaven;
    }

    public Scm getToolNodejs() {
        return toolNodejs;
    }

    public void setToolNodejs(Scm toolNodejs) {
        this.toolNodejs = toolNodejs;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public XpackRepository getRepository() {
        return repository;
    }

    public TaskPullArtifact setRepository(XpackRepository repository) {
        this.repository = repository;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public TaskPullArtifact setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public TaskPullArtifact setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public TaskPullArtifact setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public Boolean getTransitive() {
        return transitive;
    }

    public TaskPullArtifact setTransitive(Boolean transitive) {
        this.transitive = transitive;
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
