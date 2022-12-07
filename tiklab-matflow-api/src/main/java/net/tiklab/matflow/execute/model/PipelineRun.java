package net.tiklab.matflow.execute.model;

import java.util.List;
import java.util.Map;

public class PipelineRun {

    private String historyId;

    private int runTime;

    private String runLog;

    private int stagesLength;

    private int runWay;

    private List<Integer> timeList;

    private Map<Integer, List<Integer>> stagesTimes;

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getRunLog() {
        return runLog;
    }

    public void setRunLog(String runLog) {
        this.runLog = runLog;
    }

    public int getStagesLength() {
        return stagesLength;
    }

    public void setStagesLength(int stagesLength) {
        this.stagesLength = stagesLength;
    }

    public int getRunWay() {
        return runWay;
    }

    public void setRunWay(int runWay) {
        this.runWay = runWay;
    }

    public Map<Integer, List<Integer>> getStagesTimes() {
        return stagesTimes;
    }

    public void setStagesTimes(Map<Integer, List<Integer>> stagesTimes) {
        this.stagesTimes = stagesTimes;
    }

    public List<Integer> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Integer> timeList) {
        this.timeList = timeList;
    }
}
