package io.thoughtware.arbess.support.count.model;

public class PipelineTimeCount {

    private Integer allNumber = 0;

    private Integer errNumber = 0;

    private Integer successNumber = 0;

    private Integer haltNumber = 0;

    public Integer getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(Integer allNumber) {
        this.allNumber = allNumber;
    }

    public Integer getErrNumber() {
        return errNumber;
    }

    public void setErrNumber(Integer errNumber) {
        this.errNumber = errNumber;
    }

    public Integer getSuccessNumber() {
        return successNumber;
    }

    public void setSuccessNumber(Integer successNumber) {
        this.successNumber = successNumber;
    }

    public Integer getHaltNumber() {
        return haltNumber;
    }

    public void setHaltNumber(Integer haltNumber) {
        this.haltNumber = haltNumber;
    }
}
