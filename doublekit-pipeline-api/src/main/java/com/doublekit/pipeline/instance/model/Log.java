package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
@Mapper(targetAlias = "LogEntity")
public class Log {

    //日志id
    @ApiProperty(name="logId",desc="日志id")
    private String logId;

    //日志地址
    @ApiProperty(name="logRunLog",desc="运行日志")
    private String logRunLog;

    //拉取时间
    @ApiProperty(name="logCodeTime",desc="拉取时间")
    private int logCodeTime;

    //拉取状态
    @ApiProperty(name="logCodeState",desc="拉取状态")
    private int  logCodeState;

    //打包时间
    @ApiProperty(name="logPackTime",desc="打包时间")
    private int logPackTime;

    //打包状态
    @ApiProperty(name="logPackState",desc="打包状态")
    private int  logPackState;

    //部署时间
    @ApiProperty(name="logDeployTime",desc="部署时间")
    private int logDeployTime;

    //部署状态
    @ApiProperty(name="logDeployState",desc="部署状态")
    private int logDeployState;

    //运行状态
    @ApiProperty(name="logRunStatus",desc="运行状态")
    private int logRunStatus = getLogRunStatus() + getLogPackState()+ getLogCodeState();

    //流水线id
    @ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    //测试时间
    @ApiProperty(name="logTestTime",desc="测试时间")
    private int logTestTime;

    //测试状态
    @ApiProperty(name="logTestState",desc="测试状态")
    private int logTestState;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogRunLog() {
        return logRunLog;
    }

    public void setLogRunLog(String logRunLog) {
        this.logRunLog = logRunLog;
    }

    public int getLogCodeTime() {
        return logCodeTime;
    }

    public void setLogCodeTime(int logCodeTime) {
        this.logCodeTime = logCodeTime;
    }

    public int getLogCodeState() {
        return logCodeState;
    }

    public void setLogCodeState(int logCodeState) {
        this.logCodeState = logCodeState;
    }

    public int getLogPackTime() {
        return logPackTime;
    }

    public void setLogPackTime(int logPackTime) {
        this.logPackTime = logPackTime;
    }

    public int getLogPackState() {
        return logPackState;
    }

    public void setLogPackState(int logPackState) {
        this.logPackState = logPackState;
    }

    public int getLogDeployTime() {
        return logDeployTime;
    }

    public void setLogDeployTime(int logDeployTime) {
        this.logDeployTime = logDeployTime;
    }

    public int getLogDeployState() {
        return logDeployState;
    }

    public void setLogDeployState(int logDeployState) {
        this.logDeployState = logDeployState;
    }

    public int getLogRunStatus() {
        return logRunStatus;
    }

    public void setLogRunStatus(int logRunStatus) {
        this.logRunStatus = logRunStatus;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getLogTestTime() {
        return logTestTime;
    }

    public void setLogTestTime(int logTestTime) {
        this.logTestTime = logTestTime;
    }

    public int getLogTestState() {
        return logTestState;
    }

    public void setLogTestState(int logTestState) {
        this.logTestState = logTestState;
    }
}
