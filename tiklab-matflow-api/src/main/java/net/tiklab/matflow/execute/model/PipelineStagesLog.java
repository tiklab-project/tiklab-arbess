package net.tiklab.matflow.execute.model;

import java.util.List;

/**
 * 阶段日志
 */

public class PipelineStagesLog {

    private int taskSort;

    private int type;

    private int stages;

    private List<PipelineExecLog> logList;

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
