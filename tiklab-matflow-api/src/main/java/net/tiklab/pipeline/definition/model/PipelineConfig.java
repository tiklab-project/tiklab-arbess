package net.tiklab.pipeline.definition.model;


import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

/**
 * 需要保存流水线配置信息
 */

@ApiModel
@Join
public class PipelineConfig {

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
    private net.tiklab.pipeline.definition.model.PipelineTest pipelineTest;

    @ApiProperty(name="pipelineBuild",desc="构建",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineBuild.buildId",target = "buildId")
    })
    @JoinQuery(key = "buildId")
    private PipelineBuild pipelineBuild;

    @ApiProperty(name="pipelineDeploy",desc="部署",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineDeploy.deployId",target = "deployId")
    })
    @JoinQuery(key = "deployId")
    private net.tiklab.pipeline.definition.model.PipelineDeploy pipelineDeploy;


    public PipelineCode getPipelineCode() {
        return pipelineCode;
    }

    public void setPipelineCode(PipelineCode pipelineCode) {
        this.pipelineCode = pipelineCode;
    }

    public net.tiklab.pipeline.definition.model.PipelineTest getPipelineTest() {
        return pipelineTest;
    }

    public void setPipelineTest(net.tiklab.pipeline.definition.model.PipelineTest pipelineTest) {
        this.pipelineTest = pipelineTest;
    }

    public PipelineBuild getPipelineBuild() {
        return pipelineBuild;
    }

    public void setPipelineBuild(PipelineBuild pipelineBuild) {
        this.pipelineBuild = pipelineBuild;
    }

    public net.tiklab.pipeline.definition.model.PipelineDeploy getPipelineDeploy() {
        return pipelineDeploy;
    }

    public void setPipelineDeploy(net.tiklab.pipeline.definition.model.PipelineDeploy pipelineDeploy) {
        this.pipelineDeploy = pipelineDeploy;
    }


}
