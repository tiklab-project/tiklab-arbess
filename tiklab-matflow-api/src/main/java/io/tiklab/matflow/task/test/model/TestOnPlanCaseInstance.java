package io.tiklab.matflow.task.test.model;

import io.tiklab.postin.annotation.ApiProperty;

public class TestOnPlanCaseInstance {

    @ApiProperty(name = "id", desc = "id")
    private String id;

    @ApiProperty(name = "testPlanInstanceId", desc = "所属测试计划实例")
    private String testPlanInstanceId;

    @ApiProperty(name = "caseInstanceId", desc = "用例实例id")
    private String caseInstanceId;

    @ApiProperty(name = "name", desc = "名称")
    private String name;

    @ApiProperty(name = "testType", desc = "测试类型")
    private String testType;

    @ApiProperty(name = "caseType", desc = "用例类型")
    private String caseType;

    @ApiProperty(name = "result", desc = "结果")
    private Integer result;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestPlanInstanceId() {
        return testPlanInstanceId;
    }

    public void setTestPlanInstanceId(String testPlanInstanceId) {
        this.testPlanInstanceId = testPlanInstanceId;
    }

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public void setCaseInstanceId(String caseInstanceId) {
        this.caseInstanceId = caseInstanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
