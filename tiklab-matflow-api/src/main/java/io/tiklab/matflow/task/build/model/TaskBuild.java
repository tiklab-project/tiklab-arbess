package io.tiklab.matflow.task.build.model;


import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
/**
 * 任务构建模型
 */
@ApiModel
@Join
@Mapper
public class TaskBuild {

    @ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //构建文件地址
    @ApiProperty(name="buildAddress",desc="构建文件地址")
    private String buildAddress;

    //构建命令
    @ApiProperty(name="buildOrder",desc="构建命令")
    private String buildOrder;

    @ApiProperty(name="productRule",desc="制品规则")
    private String productRule;


    @ApiProperty(name = "dockerName",desc="镜像名称")
    private String dockerName;

    @ApiProperty(name = "dockerVersion",desc="镜像版本")
    private String dockerVersion;

    @ApiProperty(name = "dockerFile",desc="DockerFile文件地址")
    private String dockerFile;

    @ApiProperty(name = "dockerOrder",desc="Docker部署命令")
    private String dockerOrder;


    //顺序
    private int sort;

    //构建类型
    private String type;

    public String getDockerOrder() {
        return dockerOrder;
    }

    public TaskBuild setDockerOrder(String dockerOrder) {
        this.dockerOrder = dockerOrder;
        return this;
    }

    public String getDockerName() {
        return dockerName;
    }

    public TaskBuild setDockerName(String dockerName) {
        this.dockerName = dockerName;
        return this;
    }

    public String getDockerVersion() {
        return dockerVersion;
    }

    public TaskBuild setDockerVersion(String dockerVersion) {
        this.dockerVersion = dockerVersion;
        return this;
    }

    public String getDockerFile() {
        return dockerFile;
    }

    public TaskBuild setDockerFile(String dockerFile) {
        this.dockerFile = dockerFile;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getProductRule() {
        return productRule;
    }

    public void setProductRule(String productRule) {
        this.productRule = productRule;
    }
}
