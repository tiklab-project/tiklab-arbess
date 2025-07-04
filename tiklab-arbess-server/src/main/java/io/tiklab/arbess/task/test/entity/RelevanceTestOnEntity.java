package io.tiklab.arbess.task.test.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_relevance_teston")
public class RelevanceTestOnEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "relevance_id" ,notNull = true)
    private String relevanceId;

    @Column(name = "teston_id" ,notNull = true)
    private String testonId;

    @Column(name = "test_plan_id" ,notNull = true)
    private String testPlanId;

    @Column(name = "pipeline_id" ,notNull = true)
    private String pipelineId;

    @Column(name = "auth_id" ,notNull = true)
    private String authId;

    @Column(name = "create_time" ,notNull = true)
    private String createTime;

    // 执行状态
    @Column(name = "exec_status")
    private String execStatus;

    // 失败率
    @Column(name = "error_rate")
    private String errorRate;

    // 通过率
    @Column(name = "pass_rate")
    private String passRate;

    // 执行数
    @Column(name = "exec_num")
    private String executableCaseNum;

    // 失败书
    @Column(name = "fail_num")
    private String failNum;

    // 通过数
    @Column(name = "pass_num")
    private String passNum;

    @Column(name = "test_name")
    private String testName;

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }

    public String getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(String errorRate) {
        this.errorRate = errorRate;
    }

    public String getPassRate() {
        return passRate;
    }

    public void setPassRate(String passRate) {
        this.passRate = passRate;
    }

    public String getExecutableCaseNum() {
        return executableCaseNum;
    }

    public void setExecutableCaseNum(String executableCaseNum) {
        this.executableCaseNum = executableCaseNum;
    }

    public String getFailNum() {
        return failNum;
    }

    public void setFailNum(String failNum) {
        this.failNum = failNum;
    }

    public String getPassNum() {
        return passNum;
    }

    public void setPassNum(String passNum) {
        this.passNum = passNum;
    }

    public String getTestPlanId() {
        return testPlanId;
    }

    public void setTestPlanId(String testPlanId) {
        this.testPlanId = testPlanId;
    }

    public String getAuthId() {
        return authId;
    }

    public RelevanceTestOnEntity setAuthId(String authId) {
        this.authId = authId;
        return this;
    }

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTestonId() {
        return testonId;
    }

    public void setTestonId(String testonId) {
        this.testonId = testonId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
