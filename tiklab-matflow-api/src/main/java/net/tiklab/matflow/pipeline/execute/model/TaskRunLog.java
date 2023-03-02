package net.tiklab.matflow.pipeline.execute.model;

import java.util.List;

/**
 * 流水线运行状态模型
 */
public class TaskRunLog {

    //任务
    private String id;

    //任务名称
    private String name;

    //执行时间
    private int time;

    //执行状态
    private int state;

    //执行类型
    private int type;

    //运行日志
    private String runLog;

    //阶段状态
    private int allState;

    //阶段运行时间
    private int allTime;

    //阶段任务集合
    private List<TaskRunLog> runLogList;

    public TaskRunLog() {
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

    public List<TaskRunLog> getRunLogList() {
        return runLogList;
    }

    public void setRunLogList(List<TaskRunLog> runLogList) {
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
