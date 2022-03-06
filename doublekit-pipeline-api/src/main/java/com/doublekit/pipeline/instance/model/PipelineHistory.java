package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.doublekit.pipeline.definition.model.Pipeline;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.systemSettings.securitySetting.proof.model.Proof;


@ApiModel
@Join
public class PipelineHistory {

    //构建历史id
    @ApiProperty(name="historyId",desc="构建历史id")
    private String historyId;

    //构建次数
    @ApiProperty(name = "historyNumber",desc="构建次数")
    private String historyNumber;

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
    @ApiProperty(name="pipelineLog",desc="日志id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineLog.logId",target = "logId")
    })
    @JoinQuery(key = "logId")
    private PipelineLog pipelineLog;

    //凭证
    @ApiProperty(name="PipelineConfigure",desc="配置id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "PipelineConfigure.configureId",target = "configureId")
    })
    @JoinQuery(key = "configureId")
    private PipelineConfigure PipelineConfigure;


    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getHistoryNumber() {
        return historyNumber;
    }

    public void setHistoryNumber(String historyNumber) {
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

    public PipelineLog getPipelineLog() {
        return pipelineLog;
    }

    public void setPipelineLog(PipelineLog pipelineLog) {
        this.pipelineLog = pipelineLog;
    }

    public com.doublekit.pipeline.definition.model.PipelineConfigure getPipelineConfigure() {
        return PipelineConfigure;
    }

    public void setPipelineConfigure(com.doublekit.pipeline.definition.model.PipelineConfigure pipelineConfigure) {
        PipelineConfigure = pipelineConfigure;
    }
}
