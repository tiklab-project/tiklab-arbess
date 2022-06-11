package com.doublekit.pipeline.execute.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.doublekit.pipeline.setting.proof.model.Proof;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineDeployEntity")
public class PipelineDeploy {

    //id
    @ApiProperty(name = "deployId" , desc = "id")
    private String deployId;

    //部署类型
    @ApiProperty(name = "type" , desc = "部署类型")
    private int type;

    //打包文件地址
    @ApiProperty(name = "deployTargetAddress" , desc = "打包文件地址")
    private String deployTargetAddress;

    //发送文件地址
    @ApiProperty(name="deployAddress",desc="文件地址")
    private String deployAddress;

    //shell脚本
    @ApiProperty(name = "deployShell" , desc = "部署脚本")
    private String deployShell;

    //凭证id
    @ApiProperty(name="proof",desc="凭证id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "proof.proofId",target = "proofId")
    })
    @JoinQuery(key = "proofId")
    private Proof proof;

    //启动端口
    @ApiProperty(name = "dockerPort",desc="启动端口")
    private int dockerPort;

    //映射端口
    @ApiProperty(name = "mappingPort",desc="映射端口")
    private int mappingPort;

    //顺序
    @ApiProperty(name = "sort",desc="顺序")
    private int sort;

    //别名
    @ApiProperty(name = "deployAlias",desc="别名")
    private String deployAlias;

    //端口号
    @ApiProperty(name = "port" ,desc="端口号")
    private int port;

    //ip地址
    @ApiProperty(name = "ip" ,desc="ip地址")
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

    public String getDeployTargetAddress() {
        return deployTargetAddress;
    }

    public void setDeployTargetAddress(String deployTargetAddress) {
        this.deployTargetAddress = deployTargetAddress;
    }


    public String getDeployAddress() {
        return deployAddress;
    }

    public void setDeployAddress(String deployAddress) {
        this.deployAddress = deployAddress;
    }

    public String getDeployShell() {
        return deployShell;
    }

    public void setDeployShell(String deployShell) {
        this.deployShell = deployShell;
    }

    public Proof getProof() {
        return proof;
    }

    public void setProof(Proof proof) {
        this.proof = proof;
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
