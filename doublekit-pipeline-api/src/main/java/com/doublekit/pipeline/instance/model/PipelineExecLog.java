package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineExecLogEntity")
public class PipelineExecLog {

    //日志id
    @ApiProperty(name="logTestState",desc="日志id")
    private String logId;

    //historyId
    @ApiProperty(name = "history_id",desc = "")
    private String historyId;

    //运行类型
    @ApiProperty(name = "task_log_type",desc = "")
    private int taskLogType ;

    @ApiProperty(name = "task_sort",desc = "")
    private int taskLogSort ;

    //运行日志
    @ApiProperty(name = "log_run_log",desc = "")
    private String logRunLog;

    //运行时间
    @ApiProperty(name = "log_run_time",desc = "")
    private int logRunTime;

    //运行状态
    @ApiProperty(name = "log_run_state",desc = "")
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
