package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.setting.proof.model.Proof;


@ApiModel
@Join
@Mapper(targetAlias = "PipelineExecHistoryEntity")
public class PipelineExecHistory {

    //构建历史id
    @ApiProperty(name="historyId",desc="构建历史id")
    private String historyId;

    //创建时间
    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    //构建方式
    @ApiProperty(name="runWay",desc="运行方式")
    private int runWay;

    //运行日志
    @ApiProperty(name="runLog",desc="运行日志")
    private String runLog;

    //执行人
    @ApiProperty(name="execName",desc="执行人")
    private String execName;

    //状态
    @ApiProperty(name="runStatus",desc="运行状态")
    private int runStatus;

    //执行时间
    @ApiProperty(name="runTime",desc="运行时间")
    private int runTime;

   //流水线id
   @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
   @Mappings({
           @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
   })
   @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    //运行时间（转换为天，月，日 ，时，分，秒格式）
    private String execTime;

    //执行位置
    private int sort;

    //执行状态
    private int status;

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getRunWay() {
        return runWay;
    }

    public void setRunWay(int runWay) {
        this.runWay = runWay;
    }

    public String getRunLog() {
        return runLog;
    }

    public void setRunLog(String runLog) {
        this.runLog = runLog;
    }

    public String getExecName() {
        return execName;
    }

    public void setExecName(String execName) {
        this.execName = execName;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public int getRunStatus() {
        return runStatus;
    }

    public void setRunStatus(int runStatus) {
        this.runStatus = runStatus;
    }

    public int getRunTime() {
        return runTime;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
