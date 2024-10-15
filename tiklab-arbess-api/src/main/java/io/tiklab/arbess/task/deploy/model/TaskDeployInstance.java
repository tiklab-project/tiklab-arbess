package io.tiklab.arbess.task.deploy.model;

import io.tiklab.toolkit.beans.annotation.Mapper;

@Mapper
public class TaskDeployInstance {

    // id
    private String id;

    // 实例ID
    private String taskInstanceId;

    // 名称
    private String name;

    // 时间
    private String runTime;

    // 状态
    private String runStatus;

    // 运行日志
    private String runLog;

    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public TaskDeployInstance() {
    }

    public TaskDeployInstance(String id, String taskInstanceId) {
        this.id = id;
        this.taskInstanceId = taskInstanceId;
        this.runStatus = "wait";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(String runStatus) {
        this.runStatus = runStatus;
    }

    public String getRunLog() {
        return runLog;
    }

    public void setRunLog(String runLog) {
        this.runLog = runLog;
    }
}
