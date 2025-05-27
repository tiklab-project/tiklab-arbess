package io.tiklab.arbess.setting.k8s.model;

public class KubectlNode {

    // 节点名称
    private String nodeName;

    // 节点ip
    private String nodeIp;

    // 节点状态
    // Ready: 节点运行正常，Pod 可以被调度到此节点。
    // NotReady: 节点不可用，可能 kubelet 宕机或无法与控制平面通信。
    // Unknown: 节点状态未知，控制平面无法获取 kubelet 状态（可能是网络异常）。
    // SchedulingDisabled: 节点被标记为不可调度（如 kubectl cordon），不会新建 Pod，但已有 Pod 仍然运行。
    // Ready,SchedulingDisabled: 节点正常，但被用户手动标记为不可调度。
    // NotReady,SchedulingDisabled: 节点异常且不可调度，通常需排查网络或 kubelet 问题。
    private String nodeStatus;

    // 节点角色
    private String nodeRole;

    // 节点操作系统
    private String osImage;

    // 节点内核版本
    private String kernelVersion;

    // 容器运行时
    private String containerRuntime;

    // 节点加入时间
    private String acg;

    // 外部ip
    private String externalIp;


    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public String getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(String nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public String getNodeRole() {
        return nodeRole;
    }

    public void setNodeRole(String nodeRole) {
        this.nodeRole = nodeRole;
    }

    public String getOsImage() {
        return osImage;
    }

    public void setOsImage(String osImage) {
        this.osImage = osImage;
    }

    public String getKernelVersion() {
        return kernelVersion;
    }

    public void setKernelVersion(String kernelVersion) {
        this.kernelVersion = kernelVersion;
    }

    public String getContainerRuntime() {
        return containerRuntime;
    }

    public void setContainerRuntime(String containerRuntime) {
        this.containerRuntime = containerRuntime;
    }

    public String getAcg() {
        return acg;
    }

    public void setAcg(String acg) {
        this.acg = acg;
    }

    public String getExternalIp() {
        return externalIp;
    }

    public void setExternalIp(String externalIp) {
        this.externalIp = externalIp;
    }
}
