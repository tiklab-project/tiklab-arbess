package io.thoughtware.matflow.task.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.sql.Timestamp;

public class TestOnPlanInstance {

    //@ApiProperty(name = "id", desc = "id")
    private String id;

    //@ApiProperty(name = "testPlanId", desc = "所属计划")
    private String testPlanId;

    private TestOnTestPlan testPlan;

    //@ApiProperty(name = "repositoryId", desc = "所属仓库")
    private String repositoryId;

    //@ApiProperty(name = "executeNumber", desc = "执行次数")
    private Integer executeNumber;

    //@ApiProperty(name = "result", desc = "结果")
    private Integer result;

    //@ApiProperty(name = "total", desc = "测试总次数")
    private Integer total;

    //@ApiProperty(name = "passNum", desc = "通过数")
    private Integer passNum;

    //@ApiProperty(name = "failNum", desc = "错误数")
    private Integer failNum;

    //@ApiProperty(name = "passRate", desc = "通过率")
    private String passRate;

    //@ApiProperty(name = "errorRate", desc = "错误率")
    private String errorRate;

    //@ApiProperty(name = "createTime", desc = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    //@ApiProperty(name = "createUser", desc = "执行人")
    private String createUser;

    private String testPlanName;

    private String url;

    public TestOnTestPlan getTestPlan() {
        return testPlan;
    }

    public void setTestPlan(TestOnTestPlan testPlan) {
        this.testPlan = testPlan;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTestPlanName() {
        return testPlanName;
    }

    public void setTestPlanName(String testPlanName) {
        this.testPlanName = testPlanName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(String testPlanId) {
        this.testPlanId = testPlanId;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public Integer getExecuteNumber() {
        return executeNumber;
    }

    public void setExecuteNumber(Integer executeNumber) {
        this.executeNumber = executeNumber;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPassNum() {
        return passNum;
    }

    public void setPassNum(Integer passNum) {
        this.passNum = passNum;
    }

    public Integer getFailNum() {
        return failNum;
    }

    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    public String getPassRate() {
        return passRate;
    }

    public void setPassRate(String passRate) {
        this.passRate = passRate;
    }

    public String getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(String errorRate) {
        this.errorRate = errorRate;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
