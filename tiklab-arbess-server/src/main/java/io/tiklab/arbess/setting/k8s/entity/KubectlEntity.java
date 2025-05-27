package io.tiklab.arbess.setting.k8s.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_k8s")
public class KubectlEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "tool_id")
    public String toolId;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "kube_config")
    private String kubeConfig;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "kube_address")
    public String kubeAddress;

    public String getToolId() {
        return toolId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public String getKubeAddress() {
        return kubeAddress;
    }

    public void setKubeAddress(String kubeAddress) {
        this.kubeAddress = kubeAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getKubeConfig() {
        return kubeConfig;
    }

    public void setKubeConfig(String kubeConfig) {
        this.kubeConfig = kubeConfig;
    }
}
