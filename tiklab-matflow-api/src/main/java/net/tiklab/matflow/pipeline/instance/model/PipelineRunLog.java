package net.tiklab.matflow.pipeline.instance.model;

import java.util.List;

/**
 * 流水线运行状态
 */
public class PipelineRunLog {

    private String id;

    private String name;

    private int time;

    private int state;

    private int type;

    private String runLog;

    private int allState;

    private int allTime;

    private List<PipelineRunLog> runLogList;

    public PipelineRunLog() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRunLog() {
        return runLog;
    }

    public void setRunLog(String runLog) {
        this.runLog = runLog;
    }

    public List<PipelineRunLog> getRunLogList() {
        return runLogList;
    }

    public void setRunLogList(List<PipelineRunLog> runLogList) {
        this.runLogList = runLogList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAllState() {
        return allState;
    }

    public void setAllState(int allState) {
        this.allState = allState;
    }

    public int getAllTime() {
        return allTime;
    }

    public void setAllTime(int allTime) {
        this.allTime = allTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
