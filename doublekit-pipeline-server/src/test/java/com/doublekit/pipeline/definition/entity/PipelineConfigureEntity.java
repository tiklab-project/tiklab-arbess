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

    //构建文件地址
    @Column(name = "configure_structure_address",notNull = true)
    private String configureStructureAddress;

    //构建命令
    @Column(name = "configure_structure_order",notNull = true)
    private String configureStructureOrder;

    //部署地址
    @Column(name = "configure_deploy_address",notNull = true)
    private String configureDeployAddress;

    //流水线id
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //凭证id
    @Column(name = "proof_id",notNull = true)
    private String proofId;




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
}
