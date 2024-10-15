package io.tiklab.arbess.support.count.model;

import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.user.user.model.User;

public class PipelineDayRateCount {

    // 流水线
    private Pipeline pipeline;

    // 总数
    private Integer allNumber;

    // 成功数
    private Integer successNumber;

    // 失败数
    private Integer errorNumber;

    // 成功率
    private String successRate;

    // 用户数
    private User user;

    public PipelineDayRateCount() {
    }

    public PipelineDayRateCount(Pipeline pipeline, Integer allNumber) {
        this.pipeline = pipeline;
        this.allNumber = allNumber;
    }

    public PipelineDayRateCount(User user, Integer allNumber) {
        this.user = user;
        this.allNumber = allNumber;
    }

    public Integer getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(Integer allNumber) {
        this.allNumber = allNumber;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public Integer getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(Integer successNumber) {
        this.successNumber = successNumber;
    }

    public Integer getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(Integer errorNumber) {
        this.errorNumber = errorNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
