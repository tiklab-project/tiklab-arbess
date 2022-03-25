package com.doublekit.pipeline.example.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineDeployEntity")
public class PipelineDeploy {

    //id
    @ApiProperty(name = "deployId" , desc = "id")
    private String deployId;

    //部署类型
    @ApiProperty(name = "deployType" , desc = "部署类型")
    private int deployType;

    //打包文件地址
    @ApiProperty(name = "deployTargetAddress" , desc = "打包文件地址")
    private String deployTargetAddress;

    //发送文件地址
    @ApiProperty(name="deployAddress",desc="文件地址")
    private String deployAddress;

    //shell脚本
    @ApiProperty(name = "deployShell" , desc = "部署脚本")
    private String deployShell;

    //凭证信息
    @ApiProperty(name="proofName",desc="凭证信息")
    private String proofName;

    //凭证id
    @ApiProperty(name="proofId",desc="凭证id",eg="@selectOne")
    private String proofId;


    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public int getDeployType() {
        return deployType;
    }

    public void setDeployType(int deployType) {
        this.deployType = deployType;
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

    public String getProofName() {
        return proofName;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName;
    }
}
