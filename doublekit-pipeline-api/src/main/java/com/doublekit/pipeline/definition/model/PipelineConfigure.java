package com.doublekit.pipeline.definition.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.pipeline.example.model.PipelineTest;
import com.doublekit.pipeline.setting.proof.model.Proof;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineConfigureEntity")
public class PipelineConfigure {

    //流水线配置id
    @ApiProperty(name="id",desc="配置id")
    private String configureId;

    //创建配置时间
    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;


    //流水线
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    //源码
    @ApiProperty(name="PipelineCode",desc="源码id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineCode.codeId",target = "codeId")
    })
    @JoinQuery(key = "codeId")
   private PipelineCode pipelineCode;

    //测试
    @ApiProperty(name="pipelineTest",desc="测试id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineTest.testId",target = "testId")
    })
    @JoinQuery(key = "testId")
   private PipelineTest pipelineTest;

    //构建
    @ApiProperty(name="pipelineStructure",desc="构建id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineStructure.structureId",target = "structureId")
    })
    @JoinQuery(key = "structureId")
   private PipelineStructure pipelineStructure;

    //部署
    @ApiProperty(name="pipelineDeploy",desc="部署id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineDeploy.deployId",target = "deployId")
    })
    @JoinQuery(key = "deployId")
   private PipelineDeploy pipelineDeploy;


    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public PipelineCode getPipelineCode() {
        return pipelineCode;
    }

    public void setPipelineCode(PipelineCode pipelineCode) {
        this.pipelineCode = pipelineCode;
    }

    public PipelineTest getPipelineTest() {
        return pipelineTest;
    }

    public void setPipelineTest(PipelineTest pipelineTest) {
        this.pipelineTest = pipelineTest;
    }

    public PipelineStructure getPipelineStructure() {
        return pipelineStructure;
    }

    public void setPipelineStructure(PipelineStructure pipelineStructure) {
        this.pipelineStructure = pipelineStructure;
    }

    public PipelineDeploy getPipelineDeploy() {
        return pipelineDeploy;
    }

    public void setPipelineDeploy(PipelineDeploy pipelineDeploy) {
        this.pipelineDeploy = pipelineDeploy;
    }
}
