package com.tiklab.matflow.definition.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.tiklab.matflow.execute.model.PipelineCode;
import com.tiklab.matflow.execute.model.PipelineDeploy;
import com.tiklab.matflow.execute.model.PipelineStructure;
import com.tiklab.matflow.execute.model.PipelineTest;
import com.doublekit.user.user.model.User;

/**
 * 需要保存流水线配置信息
 */

@ApiModel
@Join
public class PipelineExecConfigure {

    //流水线
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    @ApiProperty(name="user",desc="用户",required = true)
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    @ApiProperty(name="configureCreateTime",desc="id")
    private String configureCreateTime;


    @ApiProperty(name="pipelineCode",desc="源码管理",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineCode.codeId",target = "pipelineId")
    })
    @JoinQuery(key = "codeId")
    private PipelineCode pipelineCode;

    @ApiProperty(name="pipelineTest",desc="测试",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineTest.testId",target = "testId")
    })
    @JoinQuery(key = "testId")
    private PipelineTest pipelineTest;

    @ApiProperty(name="pipelineStructure",desc="构建",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineStructure.structureId",target = "structureId")
    })
    @JoinQuery(key = "structureId")
    private PipelineStructure pipelineStructure;

    @ApiProperty(name="pipelineDeploy",desc="部署",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineDeploy.deployId",target = "deployId")
    })
    @JoinQuery(key = "deployId")
    private PipelineDeploy pipelineDeploy;

    @ApiProperty(name="view",desc="视图")
    private int view;


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
