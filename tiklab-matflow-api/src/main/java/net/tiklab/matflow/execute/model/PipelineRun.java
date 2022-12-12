package net.tiklab.matflow.execute.model;

import java.util.List;

/**
 * 流水线运行信息
 */
public class PipelineRun {

    private String historyId;

    private String createTime;

    private String execUser;

    private int runTime;

    private int stagesTime;

    private String runLog;

    private int runWay;

    private List<String> timeList;

    private List<PipelineRun> runList;

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

    public int getRunWay() {
        return runWay;
    }

    public void setRunWay(int runWay) {
        this.runWay = runWay;
    }

    public int getStagesTime() {
        return stagesTime;
    }

    public void setStagesTime(int stagesTime) {
        this.stagesTime = stagesTime;
    }

    public List<String> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<String> timeList) {
        this.timeList = timeList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExecUser() {
        return execUser;
    }

    public void setExecUser(String execUser) {
        this.execUser = execUser;
    }

    public List<PipelineRun> getRunList() {
        return runList;
    }

    public void setRunList(List<PipelineRun> runList) {
        this.runList = runList;
    }
}
