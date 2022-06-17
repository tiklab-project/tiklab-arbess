package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

/**
 * 流水线日志
 */

@ApiModel
@Join
@Mapper(targetAlias = "PipelineExecLogEntity")
public class PipelineExecLog {

    //日志id
    @ApiProperty(name="pipelineLogId",desc="日志id")
    private String pipelineLogId;

    //historyId
    @ApiProperty(name = "historyId",desc = "历史Id")
    private String historyId;

    //运行类型
    @ApiProperty(name = "taskType",desc = "运行类型")
    private int taskType ;

    //执行顺序
    @ApiProperty(name = "taskSort",desc = "执行顺序")
    private int taskSort ;

    //执行名称
    @ApiProperty(name = "taskSort",desc = "执行顺序")
    private String taskAlias;

    //运行日志
    @ApiProperty(name = "runLog",desc = "运行日志")
    private String runLog;

    //运行时间
    @ApiProperty(name = "runTime",desc = "运行时间")
    private int runTime;

    //运行状态
    @ApiProperty(name = "runState",desc = "运行状态")
    private int runState;

    //运行时间（转换为天，月，日 ，时，分，秒格式）
    private String execTime;


    public String getPipelineLogId() {
        return pipelineLogId;
    }

    public void setPipelineLogId(String pipelineLogId) {
        this.pipelineLogId = pipelineLogId;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public String getRunLog() {
        return runLog;
    }

    public void setRunLog(String runLog) {
        this.runLog = runLog;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public int getRunState() {
        return runState;
    }

    public void setRunState(int runState) {
        this.runState = runState;
    }

    public String getTaskAlias() {
        return taskAlias;
    }

    public void setTaskAlias(String taskAlias) {
        this.taskAlias = taskAlias;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }
}
