package io.tiklab.arbess.support.count.model;

/**
 * 流水线时间统计模型
 */
public class PipelineTimeCount {

    /**
     * 所有数量
     */
    private Integer allNumber = 0;

    /**
     * 错误数量
     */
    private Integer errNumber = 0;

    /**
     * 成功数量
     */
    private Integer successNumber = 0;

    /**
     * 停止数量
     */
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
