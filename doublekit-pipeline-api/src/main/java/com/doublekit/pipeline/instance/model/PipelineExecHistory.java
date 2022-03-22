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
@Mapper(targetAlias = "HistoryEntity")
public class PipelineExecHistory {

    //构建历史id
    @ApiProperty(name="historyId",desc="构建历史id")
    private String historyId;

    //构建次数
    @ApiProperty(name = "historyNumber",desc="构建次数")
    private int historyNumber;

    //创建时间
    @ApiProperty(name="pipelineName",desc="创建构建时间",required = true)
    private String historyCreateTime;

    //构建方式
    @ApiProperty(name="pipelineName",desc="构建方式",required = true)
    private int historyWay;

    //分支
    @ApiProperty(name="pipelineName",desc="分支",required = true)
        private String historyBranch;

    //流水线
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    //凭证
    @ApiProperty(name="proof",desc="凭证id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "proof.proofId",target = "proofId")
    })
    @JoinQuery(key = "proofId")
    private Proof proof;

    //日志
    @ApiProperty(name="log",desc="日志id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "log.logId",target = "logId")
    })
    @JoinQuery(key = "logId")
    private PipelineExecLog pipelineExecLog;

    //凭证
    @ApiProperty(name="configure",desc="配置id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "configure.configureId",target = "configureId")
    })
    @JoinQuery(key = "configureId")
    private PipelineConfigure pipelineConfigure;


    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public int getHistoryNumber() {
        return historyNumber;
    }

    public void setHistoryNumber(int historyNumber) {
        this.historyNumber = historyNumber;
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

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public Proof getProof() {
        return proof;
    }

    public void setProof(Proof proof) {
        this.proof = proof;
    }

    public PipelineExecLog getPipelineLog() {
        return pipelineExecLog;
    }

    public void setPipelineLog(PipelineExecLog pipelineExecLog) {
        this.pipelineExecLog = pipelineExecLog;
    }

    public PipelineExecLog getLog() {
        return pipelineExecLog;
    }

    public void setLog(PipelineExecLog pipelineExecLog) {
        this.pipelineExecLog = pipelineExecLog;
    }

    public PipelineConfigure getConfigure() {
        return pipelineConfigure;
    }

    public void setConfigure(PipelineConfigure pipelineConfigure) {
        this.pipelineConfigure = pipelineConfigure;
    }
}
