package com.doublekit.pipeline.execute.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_deploy")
public class PipelineDeployEntity {

    //Id
    @Id
    @GeneratorValue
    @Column(name = "deploy_id")
    private String deployId;

    //类型
    @Column(name = "type",notNull = true)
    private int type;

    //地址
    @Column(name = "deploy_address",notNull = true)
    private String deployAddress;

    //打包文件地址
    @Column(name = "deploy_target_address",notNull = true)
    private String deployTargetAddress;

    //shell
    @Column(name = "deploy_shell")
    private String deployShell;

    //凭证id
    @Column(name = "proof_id")
    private String proofId;

    //启动端口
    @Column(name = "deploy_docker_port")
    private int dockerPort;

    //映射端口
    @Column(name = "deploy_mapping_port")
    private int mappingPort;

    //顺序
    @Column(name = "sort",notNull = true)
    private int sort;

    //别名
    @Column(name = "deploy_alias",notNull = true)
    private String deployAlias;

    //端口号
    @Column(name = "port",notNull = true )
    private int port;

    //ip地址
    @Column(name = "ip",notNull = true )
    private String ip;


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

    public String getDeployAddress() {
        return deployAddress;
    }

    public void setDeployAddress(String deployAddress) {
        this.deployAddress = deployAddress;
    }

    public String getDeployTargetAddress() {
        return deployTargetAddress;
    }

    public void setDeployTargetAddress(String deployTargetAddress) {
        this.deployTargetAddress = deployTargetAddress;
    }

    public String getDeployShell() {
        return deployShell;
    }

    public void setDeployShell(String deployShell) {
        this.deployShell = deployShell;
    }

    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    public int getDockerPort() {
        return dockerPort;
    }

    public void setDockerPort(int dockerPort) {
        this.dockerPort = dockerPort;
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

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
