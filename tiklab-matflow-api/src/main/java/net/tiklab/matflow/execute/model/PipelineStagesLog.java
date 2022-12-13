package net.tiklab.matflow.execute.model;

import java.util.List;

/**
 * 阶段日志返回值封装
 */

public class PipelineStagesLog {

    private int taskSort;

    private int type;

    private int stages;

    private List<PipelineExecLog> logList;

    private List<PipelineStagesLog> stagesLogList;

    public List<PipelineStagesLog> getStagesLogList() {
        return stagesLogList;
    }

    public void setStagesLogList(List<PipelineStagesLog> stagesLogList) {
        this.stagesLogList = stagesLogList;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStages() {
        return stages;
    }

    public void setStages(int stages) {
        this.stages = stages;
    }

    public List<PipelineExecLog> getLogList() {
        return logList;
    }

    public void setLogList(List<PipelineExecLog> logList) {
        this.logList = logList;
    }
}
