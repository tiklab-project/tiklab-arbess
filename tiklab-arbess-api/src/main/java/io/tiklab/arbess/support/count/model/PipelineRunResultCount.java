package io.tiklab.arbess.support.count.model;

public class PipelineRunResultCount {

    private String day;

    // 总数
    private Integer allNumber;

    // 成功次数
    private double successNumber;

    // 成功率
    private String successRate;

    // 失败次数
    private double errorNumber;

    // 暂停次数
    private double haltNumber;

    // 执行时间
    private String execTime;

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

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(double successNumber) {
        this.successNumber = successNumber;
    }

    public double getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(double errorNumber) {
        this.errorNumber = errorNumber;
    }

    public double getHaltNumber() {
        return haltNumber;
    }

    public void setHaltNumber(double haltNumber) {
        this.haltNumber = haltNumber;
    }
}
