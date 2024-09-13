package io.thoughtware.arbess.support.count.model;

public class PipelineSurveyResultCount {

    // 成功数
    private Long successNumber;

    // 失败数
    private Long errorNumber;

    // 停止数
    private Long haltNumber;

    // 平均执行时长
    private String runTime;

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public Long getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(Long successNumber) {
        this.successNumber = successNumber;
    }

    public Long getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(Long errorNumber) {
        this.errorNumber = errorNumber;
    }

    public Long getHaltNumber() {
        return haltNumber;
    }

    public void setHaltNumber(Long haltNumber) {
        this.haltNumber = haltNumber;
    }
}
