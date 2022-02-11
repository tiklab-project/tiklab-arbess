package com.doublekit.pipeline.definition.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;

import java.text.SimpleDateFormat;
import java.util.Date;


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
    @ApiProperty(name="configureDeployAddress",desc="部署地址")
    private String configureCreateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    //流水线id
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    //凭证id
    @ApiProperty(name="proof",desc="凭证id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "proof.proofId",target = "proofId")
    })
    @JoinQuery(key = "proofId")
    private Proof proof;


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

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String getConfigureCreateTime() {
        return configureCreateTime;
    }

    public void setConfigureCreateTime(String configureCreateTime) {
        this.configureCreateTime = configureCreateTime;
    }

    public Proof getProof() {
        return proof;
    }

    public void setProof(Proof proof) {
        this.proof = proof;
    }

    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }
}
