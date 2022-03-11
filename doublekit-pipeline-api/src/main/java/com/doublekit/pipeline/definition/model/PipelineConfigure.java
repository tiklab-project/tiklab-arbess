package com.doublekit.pipeline.definition.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineConfigureEntity")
public class PipelineConfigure {

    //流水线配置id
    @ApiProperty(name="configureId",desc="配置id")
    private String configureId;

    //代码源
    @ApiProperty(name="configureCodeSource",desc="代码源")
    private int configureCodeSource;

    //代码源地址
    @ApiProperty(name="configureCodeSourceAddress",desc="代码源地址")
    private String configureCodeSourceAddress;

    //分支
    @ApiProperty(name="configureBranch",desc="分支")
    private String configureBranch;

    //构建源
    @ApiProperty(name = "configureCodeStructure",desc="构建源")
    private int configureCodeStructure;

    //构建文件地址
    @ApiProperty(name="configureStructureAddress",desc="构建文件地址")
    private String configureStructureAddress;

    //构建命令
    @ApiProperty(name="configureStructureOrder",desc="构建命令")
    private String configureStructureOrder;

    //部署地址
    @ApiProperty(name="configureDeployAddress",desc="部署地址")
    private String configureDeployAddress;

    //创建配置时间
    @ApiProperty(name="configureCreateTime",desc="创建时间")
    private String configureCreateTime;

    //流水线id
    @ApiProperty(name="pipelineId",desc="流水线id",eg="流水线id")
    private String pipelineId;

    //git凭证id
    @ApiProperty(name = "gitProofId",desc = "凭证构建id")
    private String gitProofId;

    //凭证部署id
    @ApiProperty(name = "deployProofId",desc = "凭证部署id")
    private String deployProofId;

    //打包文件地址
    @ApiProperty(name = "configureTargetAddress" , desc = "打包文件地址")
    private String configureTargetAddress;

    //shell脚本
    @ApiProperty(name = "configureShell" , desc = "shell脚本")
    private String configureShell;

    public String getConfigureCodeSourceAddress() {
        return configureCodeSourceAddress;
    }

    public void setConfigureCodeSourceAddress(String configureCodeSourceAddress) {
        this.configureCodeSourceAddress = configureCodeSourceAddress;
    }

    public String getConfigureStructureAddress() {
        return configureStructureAddress;
    }

    public void setConfigureStructureAddress(String configureStructureAddress) {
        this.configureStructureAddress = configureStructureAddress;
    }

    public String getConfigureStructureOrder() {
        return configureStructureOrder;
    }

    public void setConfigureStructureOrder(String configureStructureOrder) {
        this.configureStructureOrder = configureStructureOrder;
    }

    public String getConfigureDeployAddress() {
        return configureDeployAddress;
    }

    public void setConfigureDeployAddress(String configureDeployAddress) {
        this.configureDeployAddress = configureDeployAddress;
    }

    public String getConfigureBranch() {
        return configureBranch;
    }

    public void setConfigureBranch(String configureBranch) {
        this.configureBranch = configureBranch;
    }

    public String getConfigureCreateTime() {
        return configureCreateTime;
    }

    public void setConfigureCreateTime(String configureCreateTime) {
        this.configureCreateTime = configureCreateTime;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getGitProofId() {
        return gitProofId;
    }

    public void setGitProofId(String gitProofId) {
        this.gitProofId = gitProofId;
    }

    public String getDeployProofId() {
        return deployProofId;
    }

    public void setDeployProofId(String deployProofId) {
        this.deployProofId = deployProofId;
    }

    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }

    public String getConfigureTargetAddress() {
        return configureTargetAddress;
    }

    public void setConfigureTargetAddress(String configureTargetAddress) {
        this.configureTargetAddress = configureTargetAddress;
    }

    public int getConfigureCodeSource() {
        return configureCodeSource;
    }

    public void setConfigureCodeSource(int configureCodeSource) {
        this.configureCodeSource = configureCodeSource;
    }

    public int getConfigureCodeStructure() {
        return configureCodeStructure;
    }

    public void setConfigureCodeStructure(int configureCodeStructure) {
        this.configureCodeStructure = configureCodeStructure;
    }

    public String getConfigureShell() {
        return configureShell;
    }

    public void setConfigureShell(String configureShell) {
        this.configureShell = configureShell;
    }
}
