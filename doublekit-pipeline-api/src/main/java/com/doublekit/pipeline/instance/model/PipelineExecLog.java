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

    //运行日志
    @ApiProperty(name="logTestState",desc="运行日志")
    private String logRunLog;

    //运行状态（30 ：成功  1：失败   0 :其他）
    @ApiProperty(name="logTestState",desc="运行状态")
    private int logRunStatus ;

    //运行时间
    @ApiProperty(name="logTestState",desc="运行时间")
    private int logRunTime;

    //codeId
    @ApiProperty(name="codeLog",desc="id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "codeLog.logCodeId",target = "logCodeId")
    })
    @JoinQuery(key = "logCodeId")
    private PipelineCodeLog codeLog;

    //testId
    @ApiProperty(name="testLog",desc="测试id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "testLog.logTestId",target = "logTestId")
    })
    @JoinQuery(key = "logTestId")
    private PipelineTestLog testLog;

    //structureId
    @ApiProperty(name="structureLog",desc="id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "structureLog.logStructureId",target = "logStructureId")
    })
    @JoinQuery(key = "logStructureId")
    private PipelineStructureLog structureLog;

    //deployId
    @ApiProperty(name="deployLog",desc="id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "deployLog.logDeployId",target = "logDeployId")
    })
    @JoinQuery(key = "logDeployId")
    private PipelineDeployLog deployLog;

    //pipelineId
    @ApiProperty(name = "pipelineId",desc="pipelineId")
    private String pipelineId;


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

    public int getLogRunStatus() {
        return logRunStatus;
    }

    public void setLogRunStatus(int logRunStatus) {
        this.logRunStatus = logRunStatus;
    }

    public int getLogRunTime() {
        return logRunTime;
    }

    public void setLogRunTime(int logRunTime) {
        this.logRunTime = logRunTime;
    }

    public PipelineCodeLog getCodeLog() {
        return codeLog;
    }

    public void setCodeLog(PipelineCodeLog codeLog) {
        this.codeLog = codeLog;
    }

    public PipelineTestLog getTestLog() {
        return testLog;
    }

    public void setTestLog(PipelineTestLog testLog) {
        this.testLog = testLog;
    }

    public PipelineStructureLog getStructureLog() {
        return structureLog;
    }

    public void setStructureLog(PipelineStructureLog structureLog) {
        this.structureLog = structureLog;
    }

    public PipelineDeployLog getDeployLog() {
        return deployLog;
    }

    public void setDeployLog(PipelineDeployLog deployLog) {
        this.deployLog = deployLog;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
