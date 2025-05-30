package io.tiklab.arbess.task.deploy.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_deploy")
public class TaskDeployEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    //认证类型
    @Column(name = "auth_type",notNull = true )
    private int authType;

    //文件地址
    @Column(name = "local_address",notNull = true)
    private String localAddress;

    //部署地址
    @Column(name = "deploy_address",notNull = true)
    private String deployAddress;

    //认证id
    @Column(name = "auth_id",notNull = true)
    private String authId;

    //部署命令
    @Column(name = "deploy_order",notNull = true )
    private String deployOrder;

    //启动文件地址
    @Column(name = "start_address",notNull = true )
    private String startAddress;

    //启动命令
    @Column(name = "start_order")
    private String startOrder;

    @Column(name = "docker_image")
    private String dockerImage;;

    @Column(name = "rule")
    private String rule;

    // 命名空间
    @Column(name = "k8s_namespace")
    private String k8sNamespace;

    // 配置文件
    @Column(name = "k8s_json")
    private String k8sJson;

    @Column(name = "k8s_address")
    private String k8sAddress;

    // 策略类型
    @Column(name = "strategy_type")
    private String strategyType;

    // 策略数量
    @Column(name = "strategy_number")
    private Integer strategyNumber;

    @Column(name = "kube_conf_type")
    public String kubeConfType;

    public String getKubeConfType() {
        return kubeConfType;
    }

    public void setKubeConfType(String kubeConfType) {
        this.kubeConfType = kubeConfType;
    }

    public String getK8sAddress() {
        return k8sAddress;
    }

    public void setK8sAddress(String k8sAddress) {
        this.k8sAddress = k8sAddress;
    }

    public String getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    public Integer getStrategyNumber() {
        return strategyNumber;
    }

    public void setStrategyNumber(Integer strategyNumber) {
        this.strategyNumber = strategyNumber;
    }

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

    public String getDockerImage() {
        return dockerImage;
    }

    public TaskDeployEntity setDockerImage(String dockerImage) {
        this.dockerImage = dockerImage;
        return this;
    }

    public String getRule() {
        return rule;
    }

    public TaskDeployEntity setRule(String rule) {
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
}
