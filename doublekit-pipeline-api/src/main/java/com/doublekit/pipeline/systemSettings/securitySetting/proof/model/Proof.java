package com.doublekit.pipeline.systemSettings.securitySetting.proof.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.dal.jpa.mapper.annotation.Column;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
public class Proof {

    //凭证id
    @ApiProperty(name="pipelineId",desc="凭证id")
    private String proofId;

    //凭证名
    @ApiProperty(name="proofName",desc="凭证名")
    private String proofName;

    //作用域
    @ApiProperty(name="proofScope",desc="作用域")
    private int proofScope;

    //凭证类型
    @ApiProperty(name="proofType",desc="凭证类型")
    private String proofType;

    //账户
    @ApiProperty(name="proofUsername",desc="账户")
    private String proofUsername;

    //密码
    @ApiProperty(name="proofPassword",desc="密码")
    private String proofPassword;

    //描述
    @ApiProperty(name="proofDescribe",desc="描述")
    private String proofDescribe;

    //端口号
    @ApiProperty(name = "proofPort" ,desc="端口号")
    private int proofPort;


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

    public String getProofName() {
        return proofName;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName;
    }

    public int getProofScope() {
        return proofScope;
    }

    public void setProofScope(int proofScope) {
        this.proofScope = proofScope;
    }

    public int getProofPort() {
        return proofPort;
    }

    public void setProofPort(int proofPort) {
        this.proofPort = proofPort;
    }
}
