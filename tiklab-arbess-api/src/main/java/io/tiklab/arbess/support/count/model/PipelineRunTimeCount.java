package io.tiklab.arbess.support.count.model;

/**
 * 流水线运行时间统计模型
 */
public class PipelineRunTimeCount {

    /**
     * 时间
     */
    private String time;


    private Integer number;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
