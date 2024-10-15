package io.tiklab.arbess.task.test.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

@Join
@Mapper
public class MavenTest {

    // id
    private String id;

    // 流水线ID
    private String pipelineId;

    // 创建时间
    private String createTime;

    // 名称
    private String name;

    // 包路径
    private String packagePath;

    // 执行人
    private String userId;

    // 全部测试用例
    private String allNumber;

    // 失败的用例数
    private String failNumber;

    // 错误的用例数
    private String errorNumber;

    // 跳过的用例数
    private String skipNumber;

    // 信息
    private String message;

    // 总测试实例ID
    private String testId;

    // 测试状态
    private String testState;

    public String getTestState() {
        return testState;
    }

    public MavenTest setTestState(String testState) {
        this.testState = testState;
        return this;
    }

    public String getName() {
        return name;
    }

    public MavenTest setName(String name) {
        this.name = name;
        return this;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public MavenTest setPackagePath(String packagePath) {
        this.packagePath = packagePath;
        return this;
    }

    public String getTestId() {
        return testId;
    }

    public MavenTest setTestId(String testId) {
        this.testId = testId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MavenTest setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public MavenTest setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public MavenTest setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public MavenTest setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public String getId() {
        return id;
    }

    public MavenTest setId(String id) {
        this.id = id;
        return this;
    }

    public String getAllNumber() {
        return allNumber;
    }

    public MavenTest setAllNumber(String allNumber) {
        this.allNumber = allNumber;
        return this;
    }

    public String getFailNumber() {
        return failNumber;
    }

    public MavenTest setFailNumber(String failNumber) {
        this.failNumber = failNumber;
        return this;
    }

    public String getErrorNumber() {
        return errorNumber;
    }

    public MavenTest setErrorNumber(String errorNumber) {
        this.errorNumber = errorNumber;
        return this;
    }

    public String getSkipNumber() {
        return skipNumber;
    }

    public MavenTest setSkipNumber(String skipNumber) {
        this.skipNumber = skipNumber;
        return this;
    }
}
