package com.doublekit.pipeline.definition.model;

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
@Mapper(targetAlias = "ConfigureEntity")
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

    //流水线
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    //凭证
    @ApiProperty(name="gitProof",desc="凭证id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "gitProof.proofId",target = "gitProofId")
    })
    @JoinQuery(key = "proofId")
    private Proof gitProof;

    //仓库名称
    @ApiProperty(name="configureCodeName",desc="仓库名称")
    private String configureCodeName;


    //凭证
    @ApiProperty(name="deployProof",desc="凭证id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "deployProof.proofId",target = "deployProofId")
    })
    @JoinQuery(key = "proofId")
    private Proof deployProof;


    //打包文件地址
    @ApiProperty(name = "configureTargetAddress" , desc = "打包文件地址")
    private String configureTargetAddress;

    //shell脚本
    @ApiProperty(name = "configureShell" , desc = "shell脚本")
    private String configureShell;

    //测试类型
    @ApiProperty(name = "configureTestType" , desc = "测试类型")
    private int configureTestType;

    //测试内容
    @ApiProperty(name = "configureTestText" , desc = "测试内容")
    private String configureTestText;




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

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public Proof getGitProof() {
        return gitProof;
    }

    public void setGitProof(Proof gitProof) {
        this.gitProof = gitProof;
    }

    public Proof getDeployProof() {
        return deployProof;
    }

    public void setDeployProof(Proof deployProof) {
        this.deployProof = deployProof;
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

    public int getConfigureTestType() {
        return configureTestType;
    }

    public void setConfigureTestType(int configureTestType) {
        this.configureTestType = configureTestType;
    }

    public String getConfigureTestText() {
        return configureTestText;
    }

    public void setConfigureTestText(String configureTestText) {
        this.configureTestText = configureTestText;
    }

    public String getConfigureCodeName() {
        return configureCodeName;
    }

    public void setConfigureCodeName(String configureCodeName) {
        this.configureCodeName = configureCodeName;
    }
}
