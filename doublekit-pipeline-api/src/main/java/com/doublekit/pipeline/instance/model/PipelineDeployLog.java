package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineDeployLogEntity")
public class PipelineDeployLog {
    //日志id
    @ApiProperty(name = "logDeployId", desc = "构建历史id")
    private String logDeployId;

    //运行日志
    @ApiProperty(name = "deployRunTime", desc = "构建历史id")
    private String deployRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @ApiProperty(name = "deployRunStatus", desc = "构建历史id")
    private int deployRunStatus;

    //运行时间
    @ApiProperty(name = "deployRunLog", desc = "构建历史id")
    private int deployRunLog;

    public String getLogDeployId() {
        return logDeployId;
    }

    public void setLogDeployId(String logDeployId) {
        this.logDeployId = logDeployId;
    }

    public String getDeployRunTime() {
        return deployRunTime;
    }

    public void setDeployRunTime(String deployRunTime) {
        this.deployRunTime = deployRunTime;
    }

    public int getDeployRunStatus() {
        return deployRunStatus;
    }

    public void setDeployRunStatus(int deployRunStatus) {
        this.deployRunStatus = deployRunStatus;
    }

    public int getDeployRunLog() {
        return deployRunLog;
    }

    public void setDeployRunLog(int deployRunLog) {
        this.deployRunLog = deployRunLog;
    }
}
