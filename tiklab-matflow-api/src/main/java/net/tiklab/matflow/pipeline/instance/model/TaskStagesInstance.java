package net.tiklab.matflow.pipeline.instance.model;

import java.util.List;

/**
 * 阶段日志返回值封装
 */

public class TaskStagesInstance {

    private int taskSort;

    private int type;

    private int stages;

    private List<TaskInstanceLog> logList;

    private List<TaskStagesInstance> stagesLogList;

    public List<TaskStagesInstance> getStagesLogList() {
        return stagesLogList;
    }

    public void setStagesLogList(List<TaskStagesInstance> stagesLogList) {
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

    public List<TaskInstanceLog> getLogList() {
        return logList;
    }

    public void setLogList(List<TaskInstanceLog> logList) {
        this.logList = logList;
    }
}
