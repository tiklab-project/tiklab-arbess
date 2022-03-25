package com.doublekit.pipeline.example.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineTestEntity")
public class PipelineTest {

    //id
    @ApiProperty(name="testId",desc="id",required = true)
    private String testId;

    //测试类型
    @ApiProperty(name="testType",desc="测试类型",required = true)
    private int testType;

    //测试内容
    @ApiProperty(name="testOrder",desc="测试内容",required = true)
    private String testOrder;



    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getTestType() {
        return testType;
    }

    public void setTestType(int testType) {
        this.testType = testType;
    }

    public String getTestOrder() {
        return testOrder;
    }

    public void setTestOrder(String testOrder) {
        this.testOrder = testOrder;
    }
}
