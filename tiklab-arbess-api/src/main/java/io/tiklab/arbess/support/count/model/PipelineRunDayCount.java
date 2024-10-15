package io.tiklab.arbess.support.count.model;

import java.util.List;

public class PipelineRunDayCount {

    private String day;

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
