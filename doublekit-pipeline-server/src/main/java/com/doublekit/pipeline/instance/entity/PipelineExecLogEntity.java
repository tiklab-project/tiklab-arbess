package com.doublekit.pipeline.instance.entity;

import com.doublekit.dal.jpa.annotation.*;

/**
 * 流水线日志
 */

@Entity
@Table(name="pipeline_log")
public class PipelineExecLogEntity {

    //日志id
    @Id
    @GeneratorValue
    @Column(name = "log_id")
    private String logId;

    //historyId
    @Column(name = "history_id",notNull = true)
    private String historyId;

    //运行类型
    @Column(name = "task_log_type",notNull = true)
    private int taskLogType ;

    @Column(name = "task_sort",notNull = true)
    private int taskLogSort ;

    //运行日志
    @Column(name = "log_run_log",notNull = true)
    private String logRunLog;

    //运行时间
    @Column(name = "log_run_time",notNull = true)
    private int logRunTime;

    //运行状态
    @Column(name = "log_run_state",notNull = true)
    private int logRunState;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public int getTaskLogType() {
        return taskLogType;
    }

    public void setTaskLogType(int taskLogType) {
        this.taskLogType = taskLogType;
    }

    public int getTaskLogSort() {
        return taskLogSort;
    }

    public void setTaskLogSort(int taskLogSort) {
        this.taskLogSort = taskLogSort;
    }

    public String getLogRunLog() {
        return logRunLog;
    }

    public void setLogRunLog(String logRunLog) {
        this.logRunLog = logRunLog;
    }

    public int getLogRunTime() {
        return logRunTime;
    }

    public void setLogRunTime(int logRunTime) {
        this.logRunTime = logRunTime;
    }

    public int getLogRunState() {
        return logRunState;
    }

    public void setLogRunState(int logRunState) {
        this.logRunState = logRunState;
    }
}
