package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineTestLogEntity")
public class PipelineTestLog {
    //日志id
    @ApiProperty(name="logTestId",desc="日志id")
    private String logTestId;

    //运行时间
    @ApiProperty(name="testRunTime",desc="运行时间")
    private int testRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @ApiProperty(name="testRunStatus",desc="运行状态")
    private int testRunStatus ;

    //运行日志
    @ApiProperty(name="testRunLog",desc="运行日志")
    private String testRunLog;



    public String getLogTestId() {
        return logTestId;
    }

    public void setLogTestId(String logTestId) {
        this.logTestId = logTestId;
    }

    public int getTestRunTime() {
        return testRunTime;
    }

    public void setTestRunTime(int testRunTime) {
        this.testRunTime = testRunTime;
    }

    public int getTestRunStatus() {
        return testRunStatus;
    }

    public void setTestRunStatus(int testRunStatus) {
        this.testRunStatus = testRunStatus;
    }

    public String getTestRunLog() {
        return testRunLog;
    }

    public void setTestRunLog(String testRunLog) {
        this.testRunLog = testRunLog;
    }
}
