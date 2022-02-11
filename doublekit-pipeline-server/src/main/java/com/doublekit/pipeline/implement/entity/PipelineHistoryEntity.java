package com.doublekit.pipeline.implement.entity;

import com.doublekit.dal.jpa.mapper.annotation.*;

/**
 *流水线历史
 */

@Entity
@Table(name="pipeline_history")
public class PipelineHistoryEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "history_id")
    private String historyId;

    //创建构建时间
    @Column(name = "history_create_time",notNull = true)
    private String historyCreateTime;

    //构建方式
    @Column(name = "history_way",notNull = true)
    private int historyWay;

    //分支
    @Column(name = "history_branch",notNull = true)
    private String historyBranch;

    //流水线id
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //凭证id
    @Column(name = "proof_id",notNull = true)
    private String proofId;

    //日志id
    @Column(name = "log_id",notNull = true)
    private String logId;

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
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

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
}
