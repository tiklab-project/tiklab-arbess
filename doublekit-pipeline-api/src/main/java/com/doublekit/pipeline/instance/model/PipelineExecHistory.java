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
    private int historyId;

    //创建时间
    @ApiProperty(name="pipelineName",desc="创建构建时间",required = true)
    private String historyCreateTime;

    //构建方式
    @ApiProperty(name="pipelineName",desc="构建方式",required = true)
    private int historyWay;

    //分支
    @ApiProperty(name="pipelineName",desc="分支",required = true)
        private String historyBranch;

    //执行人
    @ApiProperty(name="pipelineName",desc="执行人")
    private String pipelineName;

    //状态
    @ApiProperty(name="pipelineName",desc="执行人")
    private int historyStatus;

    //日志id
    @ApiProperty(name="logId",desc="日志id")
    private String logId;

    //流水线id
    @ApiProperty(name="pipelineId",desc="日志id")
    private String pipelineId;

    //执行时间
    @ApiProperty(name="historyTime",desc="日志id")
    private int historyTime;


    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public String getHistoryCreateTime() {
        return historyCreateTime;
    }

    public void setHistoryCreateTime(String historyCreateTime) {
        this.historyCreateTime = historyCreateTime;
    }

    public int getHistoryWay() {
        return historyWay;
    }

    public void setHistoryWay(int historyWay) {
        this.historyWay = historyWay;
    }

    public String getHistoryBranch() {
        return historyBranch;
    }

    public void setHistoryBranch(String historyBranch) {
        this.historyBranch = historyBranch;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public int getHistoryStatus() {
        return historyStatus;
    }

    public void setHistoryStatus(int historyStatus) {
        this.historyStatus = historyStatus;
    }

    public int getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(int historyTime) {
        this.historyTime = historyTime;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
