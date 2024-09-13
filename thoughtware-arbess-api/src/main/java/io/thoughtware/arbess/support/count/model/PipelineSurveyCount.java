package io.thoughtware.arbess.support.count.model;

import io.thoughtware.arbess.pipeline.definition.model.Pipeline;
import io.thoughtware.user.user.model.User;

public class PipelineSurveyCount {

    private Pipeline pipeline;

    // 负责人
    private User user;

    // 所有运行数量
    private Integer allInstanceNumber;

    // 成功率
    private String successRate;

    // 成功数
    private String successNumber;

    // 失败率
    private String errorRate;

    // 失败数
    private String errorNumber;

    // 停止率
    private String haltRate;

    // 停止数
    private String haltNumber;

    // 最近运行时间
    private String recentlyRunTime;

    // 运行时长
    private String runTime;


    public String getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(String successNumber) {
        this.successNumber = successNumber;
    }

    public String getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(String errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getHaltNumber() {
        return haltNumber;
    }

    public void setHaltNumber(String haltNumber) {
        this.haltNumber = haltNumber;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getAllInstanceNumber() {
        return allInstanceNumber;
    }

    public void setAllInstanceNumber(Integer allInstanceNumber) {
        this.allInstanceNumber = allInstanceNumber;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public String getErrorRate() {
        return errorRate;
    }

    public void setErrorRate(String errorRate) {
        this.errorRate = errorRate;
    }

    public String getHaltRate() {
        return haltRate;
    }

    public void setHaltRate(String haltRate) {
        this.haltRate = haltRate;
    }

    public String getRecentlyRunTime() {
        return recentlyRunTime;
    }

    public void setRecentlyRunTime(String recentlyRunTime) {
        this.recentlyRunTime = recentlyRunTime;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }
}
