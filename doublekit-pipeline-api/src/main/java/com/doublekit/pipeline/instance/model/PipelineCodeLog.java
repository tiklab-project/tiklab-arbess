package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineCodeLogEntity")
public class PipelineCodeLog {

    //日志id
    @ApiProperty(name="logCodeId",desc="构建历史id")
    private String logCodeId;

    //运行日志
    @ApiProperty(name="codeRunTime",desc="构建历史id")
    private String codeRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @ApiProperty(name="codeRunStatus",desc="构建历史id")
    private int codeRunStatus ;

    //运行时间
    @ApiProperty(name="codeRunLog",desc="构建历史id")
    private int codeRunLog;


    public String getLogCodeId() {
        return logCodeId;
    }

    public void setLogCodeId(String logCodeId) {
        this.logCodeId = logCodeId;
    }

    public String getCodeRunTime() {
        return codeRunTime;
    }

    public void setCodeRunTime(String codeRunTime) {
        this.codeRunTime = codeRunTime;
    }

    public int getCodeRunStatus() {
        return codeRunStatus;
    }

    public void setCodeRunStatus(int codeRunStatus) {
        this.codeRunStatus = codeRunStatus;
    }

    public int getCodeRunLog() {
        return codeRunLog;
    }

    public void setCodeRunLog(int codeRunLog) {
        this.codeRunLog = codeRunLog;
    }
}
