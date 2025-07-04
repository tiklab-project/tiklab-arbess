package io.tiklab.arbess.setting.k8s.model;

import io.tiklab.arbess.setting.tool.model.Scm;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;

import io.tiklab.toolkit.join.annotation.JoinField;
import io.tiklab.user.user.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @pi.model: io.tiklab.matflow.setting.model.AuthHost
 * desc:流水线主机认证模型
 */
@Join
@Mapper
public class Kubectl {

    private String id;


    private String name;


    private String createTime;


    private String kubeConfig;

    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinField(key = "id")
    private User user;

    @Mappings({
            @Mapping(source = "toolKubectl.scmId",target = "toolId")
    })
    @JoinField(key = "scmId")
    private Scm toolKubectl;

    private String kubeAddress;

    private Boolean isConnect = false;
    private KubectlVersion k8sVersion ;

    private List<KubectlNode> allNodes = new ArrayList<>() ;

    public KubectlVersion getK8sVersion() {
        return k8sVersion;
    }

    public void setK8sVersion(KubectlVersion k8sVersion) {
        this.k8sVersion = k8sVersion;
    }

    public List<KubectlNode> getAllNodes() {
        return allNodes;
    }

    public void setAllNodes(List<KubectlNode> allNodes) {
        this.allNodes = allNodes;
    }

    public Boolean getConnect() {
        return isConnect;
    }

    public void setConnect(Boolean connect) {
        isConnect = connect;
    }

    public Scm getToolKubectl() {
        return toolKubectl;
    }

    public void setToolKubectl(Scm toolKubectl) {
        this.toolKubectl = toolKubectl;
    }

    public String getKubeAddress() {
        return kubeAddress;
    }

    public void setKubeAddress(String kubeAddress) {
        this.kubeAddress = kubeAddress;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
