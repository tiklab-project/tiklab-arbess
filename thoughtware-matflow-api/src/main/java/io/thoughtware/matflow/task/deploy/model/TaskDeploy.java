package io.thoughtware.matflow.task.deploy.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;


/**
 * 任务部署模型
 * @author zcamy
 */
//@ApiModel
@Join
@Mapper
public class TaskDeploy {

    //@ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //@ApiProperty(name = "authType",desc = "部署方式，自定义部署，结构化部署" )
    private int authType;

    //@ApiProperty(name = "localAddress" , desc = "文件地址")
    private String localAddress;

    //@ApiProperty(name="deployAddress",desc="部署地址")
    private String deployAddress;

    //@ApiProperty(name = "authId" , desc = "认证id")
    private String authId;

    //@ApiProperty(name = "deployOrder",desc = "部署命令" )
    private String deployOrder;

    //@ApiProperty(name = "startAddress",desc = "启动文件地址" )
    private String startAddress;

    //@ApiProperty(name="startOrder",desc="启动命令")
    private String startOrder;

    //@ApiProperty(name="rule",desc="规则")
    private String rule;

    //@ApiProperty(name="dockerImage",desc="规则")
    private String dockerImage;

    // 主机类型 host--主机  hostGroup--主机组
    private String hostType;

    //授权信息
    private Object auth;

    private int sort;

    private String type;

    // 命名空间
    private String k8sNamespace;

    // 配置文件
    private String k8sJson;

    public String getK8sNamespace() {
        return k8sNamespace;
    }

    public void setK8sNamespace(String k8sNamespace) {
        this.k8sNamespace = k8sNamespace;
    }

    public String getK8sJson() {
        return k8sJson;
    }

    public void setK8sJson(String k8sJson) {
        this.k8sJson = k8sJson;
    }

    public String getHostType() {
        return hostType;
    }

    public TaskDeploy setHostType(String hostType) {
        this.hostType = hostType;
        return this;
    }

    public String getDockerImage() {
        return dockerImage;
    }

    public TaskDeploy setDockerImage(String dockerImage) {
        this.dockerImage = dockerImage;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public TaskDeploy setRule(String rule) {
        this.rule = rule;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getDeployAddress() {
        return deployAddress;
    }

    public void setDeployAddress(String deployAddress) {
        this.deployAddress = deployAddress;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getDeployOrder() {
        return deployOrder;
    }

    public void setDeployOrder(String deployOrder) {
        this.deployOrder = deployOrder;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getStartOrder() {
        return startOrder;
    }

    public void setStartOrder(String startOrder) {
        this.startOrder = startOrder;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
