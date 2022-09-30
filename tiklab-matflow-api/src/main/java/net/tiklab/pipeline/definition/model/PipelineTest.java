package net.tiklab.pipeline.definition.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineTestEntity")
public class PipelineTest {

    //id
    @ApiProperty(name="testId",desc="id",required = true)
    private String testId;

    //测试类型
    @ApiProperty(name="type",desc="测试类型",required = true)
    private int type;

    //测试内容
    @ApiProperty(name="testOrder",desc="测试内容",required = true)
    private String testOrder;

    //顺序
    @ApiProperty(name = "sort",desc="顺序")
    private int sort;

    //别名
    @ApiProperty(name = "testAlias",desc="别名")
    private String testAlias;

    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;


    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTestOrder() {
        return testOrder;
    }

    public void setTestOrder(String testOrder) {
        this.testOrder = testOrder;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTestAlias() {
        return testAlias;
    }

    public void setTestAlias(String testAlias) {
        this.testAlias = testAlias;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }
}
