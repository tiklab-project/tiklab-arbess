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
    @ApiProperty(name = "logDeployId", desc = "日志id")
    private String logDeployId;

    //运行时间
    @ApiProperty(name = "deployRunTime", desc = "运行时间")
    private int deployRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @ApiProperty(name = "deployRunStatus", desc = "运行状态")
    private int deployRunStatus;

    //运行日志
    @ApiProperty(name = "deployRunLog", desc = "运行日志")
    private String deployRunLog;



    public String getLogDeployId() {
        return logDeployId;
    }

    public void setLogDeployId(String logDeployId) {
        this.logDeployId = logDeployId;
    }

    public int getDeployRunTime() {
        return deployRunTime;
    }

    public void setDeployRunTime(int deployRunTime) {
        this.deployRunTime = deployRunTime;
    }

    public int getDeployRunStatus() {
        return deployRunStatus;
    }

    public void setDeployRunStatus(int deployRunStatus) {
        this.deployRunStatus = deployRunStatus;
    }

    public String getDeployRunLog() {
        return deployRunLog;
    }

    public void setDeployRunLog(String deployRunLog) {
        this.deployRunLog = deployRunLog;
    }
}
