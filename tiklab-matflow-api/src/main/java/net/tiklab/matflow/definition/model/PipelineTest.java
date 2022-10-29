package net.tiklab.matflow.definition.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
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
    private int type;

    //测试内容
    @ApiProperty(name="testOrder",desc="测试内容",required = true)
    private String testOrder;

    //顺序
    private int sort;


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


}