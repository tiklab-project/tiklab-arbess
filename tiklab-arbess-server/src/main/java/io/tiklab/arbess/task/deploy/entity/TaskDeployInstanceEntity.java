package io.tiklab.arbess.task.deploy.entity;

import io.tiklab.dal.jpa.annotation.Column;
import io.tiklab.dal.jpa.annotation.Entity;
import io.tiklab.dal.jpa.annotation.Id;
import io.tiklab.dal.jpa.annotation.Table;

@Entity
@Table(name="pip_task_deploy_instance")
public class TaskDeployInstanceEntity {

    @Id
    @Column(name = "id" ,notNull = true)
    private String id;

    @Column(name = "task_instance_id")
    private String taskInstanceId;

    // 名称
    @Column(name = "name")
    private String name;

    // 时间
    @Column(name = "run_time")
    private String runTime;

    // 状态
    @Column(name = "run_status")
    private String runStatus;

    // 运行日志
    @Column(name = "run_log")
    private String runLog;

    @Column(name = "sort")
    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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
