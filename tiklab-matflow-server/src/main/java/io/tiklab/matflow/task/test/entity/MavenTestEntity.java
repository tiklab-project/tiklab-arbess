package io.tiklab.matflow.task.test.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_test_maven_test")
public class MavenTestEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id" ,notNull = true)
    private String id;

    @Column(name = "pipeline_id" ,notNull = true)
    private String pipelineId;

    @Column(name = "create_time" ,notNull = true)
    private String createTime;

    @Column(name = "user_id" ,notNull = true)
    private String userId;

    // 全部测试用例
    @Column(name = "all_number" ,notNull = true)
    private String allNumber;

    // 失败的用例数
    @Column(name = "fail_number" ,notNull = true)
    private String failNumber;

    // 错误的用例数
    @Column(name = "error_number" ,notNull = true)
    private String errorNumber;

    // 跳过的用例数
    @Column(name = "skip_number" ,notNull = true)
    private String skipNumber;

    @Column(name = "message" ,notNull = true)
    private String message;

    @Column(name = "test_id" ,notNull = true)
    private String testId;

    // 名称
    @Column(name = "name" ,notNull = true)
    private String name;

    // 包路径
    @Column(name = "package_path" ,notNull = true)
    private String packagePath;

    @Column(name = "test_state" ,notNull = true)
    private String testState;

    public String getTestState() {
        return testState;
    }

    public MavenTestEntity setTestState(String testState) {
        this.testState = testState;
        return this;
    }

    public String getName() {
        return name;
    }

    public MavenTestEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public MavenTestEntity setPackagePath(String packagePath) {
        this.packagePath = packagePath;
        return this;
    }

    public String getTestId() {
        return testId;
    }

    public MavenTestEntity setTestId(String testId) {
        this.testId = testId;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MavenTestEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public MavenTestEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public MavenTestEntity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public MavenTestEntity setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public String getId() {
        return id;
    }

    public MavenTestEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getAllNumber() {
        return allNumber;
    }

    public MavenTestEntity setAllNumber(String allNumber) {
        this.allNumber = allNumber;
        return this;
    }

    public String getFailNumber() {
        return failNumber;
    }

    public MavenTestEntity setFailNumber(String failNumber) {
        this.failNumber = failNumber;
        return this;
    }

    public String getErrorNumber() {
        return errorNumber;
    }

    public MavenTestEntity setErrorNumber(String errorNumber) {
        this.errorNumber = errorNumber;
        return this;
    }

    public String getSkipNumber() {
        return skipNumber;
    }

    public MavenTestEntity setSkipNumber(String skipNumber) {
        this.skipNumber = skipNumber;
        return this;
    }
}
