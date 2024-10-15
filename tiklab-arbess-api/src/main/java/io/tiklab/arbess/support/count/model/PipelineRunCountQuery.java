package io.tiklab.arbess.support.count.model;

public class PipelineRunCountQuery {


    private String pipelineId;

    // 查询时间
    private String[] queryTime = new String[]{};

    // 查询时间
    private int countDay = 0;

    // success(成功) error(失败) halt(停止) time(时间)，rate(速率)
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String[] getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String[] queryTime) {
        this.queryTime = queryTime;
    }

    public int getCountDay() {
        return countDay;
    }

    public void setCountDay(int countDay) {
        this.countDay = countDay;
    }
}
