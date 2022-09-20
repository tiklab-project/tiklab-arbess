package net.tiklab.matflow.definition.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="matflow_deploy")
public class MatFlowDeployEntity {

    //Id
    @Id
    @GeneratorValue
    @Column(name = "deploy_id")
    private String deployId;

    //容器类型
    @Column(name = "type",notNull = true)
    private int type;

    //部署类型
    @Column(name = "deploy_type",notNull = true )
    private int deployType;

    //部署文件地址
    @Column(name = "deploy_address",notNull = true)
    private String deployAddress;

    //源地址
    @Column(name = "source_address",notNull = true)
    private String sourceAddress;

    //启动脚本
    @Column(name = "start_shell")
    private String startShell;

    //凭证id
    @Column(name = "proof_id")
    private String proofId;

    //启动端口
    @Column(name = "start_port")
    private int startPort;

    //映射端口
    @Column(name = "mapping_port")
    private int mappingPort;

    //顺序
    @Column(name = "sort",notNull = true)
    private int sort;

    //别名
    @Column(name = "deploy_alias",notNull = true)
    private String deployAlias;

    //ssh连接端口
    @Column(name = "ssh_port",notNull = true )
    private int sshPort;

    //ssh连接ip
    @Column(name = "ssh_ip",notNull = true )
    private String sshIp;

    //启动文件地址
    @Column(name = "start_address",notNull = true )
    private String startAddress;

    //部署命令
    @Column(name = "deploy_order",notNull = true )
    private String deployOrder;

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDeployType() {
        return deployType;
    }

    public void setDeployType(int deployType) {
        this.deployType = deployType;
    }

    public String getDeployAddress() {
        return deployAddress;
    }

    public void setDeployAddress(String deployAddress) {
        this.deployAddress = deployAddress;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getStartShell() {
        return startShell;
    }

    public void setStartShell(String startShell) {
        this.startShell = startShell;
    }

    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    public int getStartPort() {
        return startPort;
    }

    public void setStartPort(int startPort) {
        this.startPort = startPort;
    }

    public int getMappingPort() {
        return mappingPort;
    }

    public void setMappingPort(int mappingPort) {
        this.mappingPort = mappingPort;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getDeployAlias() {
        return deployAlias;
    }

    public void setDeployAlias(String deployAlias) {
        this.deployAlias = deployAlias;
    }

    public int getSshPort() {
        return sshPort;
    }

    public void setSshPort(int sshPort) {
        this.sshPort = sshPort;
    }

    public String getSshIp() {
        return sshIp;
    }

    public void setSshIp(String sshIp) {
        this.sshIp = sshIp;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getDeployOrder() {
        return deployOrder;
    }

    public void setDeployOrder(String deployOrder) {
        this.deployOrder = deployOrder;
    }
}
