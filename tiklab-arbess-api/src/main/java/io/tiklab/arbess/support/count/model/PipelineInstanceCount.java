package io.tiklab.arbess.support.count.model;

public class PipelineInstanceCount {

    private Integer allNumber = 0;

    private Integer successNumber = 0;

    private Integer errorNumber = 0;

    private Integer haltNumber = 0;

    public Integer getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(Integer allNumber) {
        this.allNumber = allNumber;
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

    public Integer getHaltNumber() {
        return haltNumber;
    }

    public void setHaltNumber(Integer haltNumber) {
        this.haltNumber = haltNumber;
    }
}
