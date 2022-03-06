package com.doublekit.pipeline.definition.entity;

import com.doublekit.dal.jpa.annotation.*;


/**
 * 流水线配置
 */

@Entity
@Table(name="pipeline_configure")
public class PipelineConfigureEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "configure_id")
    private String configureId;

    //代码源
    @Column(name = "configure_code_source",notNull = true)
    private String configureCodeSource;

    //代码源地址
    @Column(name = "configure_code_source_address",notNull = true)
    private String configureCodeSourceAddress;

    //分支
    @Column(name = "configure_branch",notNull = true)
    private String configureBranch;

    //构建源
    @Column(name = "configure_code_structure",notNull = true)
    private String configureCodeStructure;

    //构建文件地址
    @Column(name = "configure_structure_address",notNull = true)
    private String configureStructureAddress;

    //构建命令
    @Column(name = "configure_structure_order",notNull = true)
    private String configureStructureOrder;

    //部署地址
    @Column(name = "configure_deploy_address",notNull = true)
    private String configureDeployAddress;

    //配置创建时间
    @Column(name = "configure_create_time",notNull = true)
    private String configureCreateTime;

    //流水线id
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //凭证构建id
    @Column(name = "proof_id_structure",notNull = true)
    private String proofIdStructure;

    //凭证部署id
    @Column(name = "proof_id_deploy",notNull = true)
    private String proofIdDeploy;

    //ip地址
    @Column(name = "configure_deploy_ip",notNull = true)
    private String configureDeployIp;

    //打包文件地址
    @Column(name = "configure_target_address" ,notNull = true)
    private String configureTargetAddress;



    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }

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

    public String getConfigureCodeStructure() {
        return configureCodeStructure;
    }

    public void setConfigureCodeStructure(String configureCodeStructure) {
        this.configureCodeStructure = configureCodeStructure;
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

    public String getProofIdStructure() {
        return proofIdStructure;
    }

    public void setProofIdStructure(String proofIdStructure) {
        this.proofIdStructure = proofIdStructure;
    }

    public String getConfigureBranch() {
        return configureBranch;
    }

    public void setConfigureBranch(String configureBranch) {
        this.configureBranch = configureBranch;
    }

    public String getConfigureDeployIp() {
        return configureDeployIp;
    }

    public void setConfigureDeployIp(String configureDeployIp) {
        this.configureDeployIp = configureDeployIp;
    }

    public String getConfigureTargetAddress() {
        return configureTargetAddress;
    }

    public void setConfigureTargetAddress(String configureTargetAddress) {
        this.configureTargetAddress = configureTargetAddress;
    }

    public String getProofIdDeploy() {
        return proofIdDeploy;
    }

    public void setProofIdDeploy(String proofIdDeploy) {
        this.proofIdDeploy = proofIdDeploy;
    }
}
