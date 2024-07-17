package io.thoughtware.matflow.support.count.model;

public class PipelineRunResultCount {

    private String day;

    // 成功次数
    private double successNumber;

    // 失败次数
    private double errorNumber;

    // 暂停次数
    private double haltNumber;


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
