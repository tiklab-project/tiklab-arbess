package io.tiklab.arbess.support.count.model;

/**
 * 流水线日统计模型
 */
public class PipelineDayCount {

    /**
     * 日期
     */
    private String time;

    /**
     * 流水线时间统计
     */
    private PipelineTimeCount timeCount;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public PipelineTimeCount getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(PipelineTimeCount timeCount) {
        this.timeCount = timeCount;
    }
}













