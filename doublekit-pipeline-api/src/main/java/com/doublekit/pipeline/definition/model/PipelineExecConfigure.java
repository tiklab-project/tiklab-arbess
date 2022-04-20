package com.doublekit.pipeline.definition.model;

import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.JoinQuery;
import com.doublekit.pipeline.example.model.PipelineCode;
import com.doublekit.pipeline.example.model.PipelineDeploy;
import com.doublekit.pipeline.example.model.PipelineStructure;
import com.doublekit.pipeline.example.model.PipelineTest;

public class PipelineExecConfigure {


    @ApiProperty(name="pipelineId",desc="id")
    private String pipelineId;

    @ApiProperty(name="configureCreateTime",desc="id")
    private String configureCreateTime;


    @ApiProperty(name="pipelineCode",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineCode.codeId",target = "pipelineId")
    })
    @JoinQuery(key = "codeId")
    private PipelineCode pipelineCode;


    @ApiProperty(name="pipelineTest",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineTest.testId",target = "testId")
    })
    @JoinQuery(key = "testId")
    private PipelineTest pipelineTest;

    @ApiProperty(name="pipelineStructure",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineStructure.structureId",target = "structureId")
    })
    @JoinQuery(key = "structureId")
    private PipelineStructure pipelineStructure;

    @ApiProperty(name="pipelineDeploy",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineDeploy.deployId",target = "deployId")
    })
    @JoinQuery(key = "deployId")
    private PipelineDeploy pipelineDeploy;


    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getConfigureCreateTime() {
        return configureCreateTime;
    }

    public void setConfigureCreateTime(String configureCreateTime) {
        this.configureCreateTime = configureCreateTime;
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


    @Override
    public String toString() {
        return "PipelineExecConfigure{" +
                "pipelineId='" + pipelineId + '\'' +
                ", configureCreateTime='" + configureCreateTime + '\'' +
                ", pipelineCode=" + pipelineCode +
                ", pipelineTest=" + pipelineTest +
                ", pipelineStructure=" + pipelineStructure +
                ", pipelineDeploy=" + pipelineDeploy +
                '}';
    }
}
