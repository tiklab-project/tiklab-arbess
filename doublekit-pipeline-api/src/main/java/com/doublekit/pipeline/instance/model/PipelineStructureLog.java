package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineStructureLogEntity")
public class PipelineStructureLog {
    //日志id
    @ApiProperty(name="logStructureId",desc="构建历史id")
    private String logStructureId;

    //运行日志
    @ApiProperty(name="structureRunTime",desc="构建历史id")
    private String structureRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @ApiProperty(name="structureRunStatus",desc="构建历史id")
    private int structureRunStatus ;

    //运行时间
    @ApiProperty(name="structureRunLog",desc="构建历史id")
    private int structureRunLog;


    public String getLogStructureId() {
        return logStructureId;
    }

    public void setLogStructureId(String logStructureId) {
        this.logStructureId = logStructureId;
    }

    public String getStructureRunTime() {
        return structureRunTime;
    }

    public void setStructureRunTime(String structureRunTime) {
        this.structureRunTime = structureRunTime;
    }

    public int getStructureRunStatus() {
        return structureRunStatus;
    }

    public void setStructureRunStatus(int structureRunStatus) {
        this.structureRunStatus = structureRunStatus;
    }

    public int getStructureRunLog() {
        return structureRunLog;
    }

    public void setStructureRunLog(int structureRunLog) {
        this.structureRunLog = structureRunLog;
    }
}
