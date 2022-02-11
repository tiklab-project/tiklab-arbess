package com.doublekit.pipeline.systemSettings.securitySetting.proof.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
public class Proof {

    //凭证id
    @ApiProperty(name="pipelineId",desc="凭证id")
    private String proofId;

    //凭证类型
    @ApiProperty(name="pipelineId",desc="凭证类型")
    private String proofType;

    //凭证名
    @ApiProperty(name="pipelineId",desc="凭证名")
    private String proofUsername;

    //凭证密码
    @ApiProperty(name="pipelineId",desc="凭证密码")
    private String proofPassword;

    //描述
    @ApiProperty(name="pipelineId",desc="描述")
    private String proofDescribe;


    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    public String getProofType() {
        return proofType;
    }

    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    public String getProofUsername() {
        return proofUsername;
    }

    public void setProofUsername(String proofUsername) {
        this.proofUsername = proofUsername;
    }

    public String getProofPassword() {
        return proofPassword;
    }

    public void setProofPassword(String proofPassword) {
        this.proofPassword = proofPassword;
    }

    public String getProofDescribe() {
        return proofDescribe;
    }

    public void setProofDescribe(String proofDescribe) {
        this.proofDescribe = proofDescribe;
    }
}
