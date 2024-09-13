package io.thoughtware.arbess.task.test.model;



public class TestPlanExecResult {



    //@ApiProperty(name = "status", desc = "状态类型：0：未开始，1：正在执行,2：结束")
    private Integer status;

    //@ApiProperty(name = "testPlanInstance", desc = "测试实例")

    private TestOnPlanInstance testPlanInstance;

    // @ApiProperty(
    //         name = "testPlanCaseInstanceList",
    //         desc = "测试计划用例历史"
    // )
    // private List<TestPlanCaseInstanceBind> testPlanCaseInstanceList;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TestOnPlanInstance getTestPlanInstance() {
        return testPlanInstance;
    }

    public void setTestPlanInstance(TestOnPlanInstance testPlanInstance) {
        this.testPlanInstance = testPlanInstance;
    }
}
