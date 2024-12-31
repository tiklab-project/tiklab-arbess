package io.tiklab.arbess.support.count.model;

/**
 * 流水线运行统计模型
 */
public class PipelineRunCount {

    /**
     * 日期
     */
    private String day;

    /**
     * 数量
     */
    private double number;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }
}
