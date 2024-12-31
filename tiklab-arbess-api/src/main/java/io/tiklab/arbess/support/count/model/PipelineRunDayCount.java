package io.tiklab.arbess.support.count.model;

import java.util.List;

/**
 * 流水线运行日统计模型
 */
public class PipelineRunDayCount {

    /**
     * 日期
     */
    private String day;

    /**
     * 运行时间统计列表
     */
    private List<PipelineRunTimeCount> runTimeCountList;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public List<PipelineRunTimeCount> getRunTimeCountList() {
        return runTimeCountList;
    }

    public void setRunTimeCountList(List<PipelineRunTimeCount> runTimeCountList) {
        this.runTimeCountList = runTimeCountList;
    }
}
