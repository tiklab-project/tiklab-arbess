package com.doublekit.pipeline.definition.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
public class PipelineConfigure {

    //流水线配置id
    @ApiProperty(name="configureId",desc="配置id")
    private String configureId;

    //代码源
    @ApiProperty(name="configureCodeSource",desc="代码源")
    private String configureCodeSource;

    //代码源地址
    @ApiProperty(name="configureCodeSourceAddress",desc="代码源地址")
    private String configureCodeSourceAddress;

    //构建源
    @ApiProperty(name = "configureCodeStructure",desc="构建源")
    private String configureCodeStructure;

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

    //凭证id
    @ApiProperty(name="proofId",desc="凭证id",eg="凭证id")
    private String proofId;


    public String getConfigureCodeSource() {
        return configureCodeSource;
    }

    public void setConfigureCodeSource(String configureCodeSource) {
        this.configureCodeSource = configureCodeSource;
    }

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

    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }

    public String getConfigureCodeStructure() {
        return configureCodeStructure;
    }

    public void setConfigureCodeStructure(String configureCodeStructure) {
        this.configureCodeStructure = configureCodeStructure;
    }
}
