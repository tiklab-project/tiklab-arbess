package com.doublekit.pipeline.example.entity;

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
    @Column(name = "deploy_type",notNull = true)
    private String deployType;

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
    @Column(name = "proof_name")
    private String proofName;

    //凭证id
    @Column(name = "proof_Name")
    private String proofId;

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public String getDeployType() {
        return deployType;
    }

    public void setDeployType(String deployType) {
        this.deployType = deployType;
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

    public String getProofName() {
        return proofName;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName;
    }

    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }
}
