package com.doublekit.pipeline.definition.entity;


import com.doublekit.dal.jpa.annotation.*;

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

    //构建执行时间
    @Column(name = "history_implement_time",notNull = true)
    private String historyImplementTime;

    //构建方式
    @Column(name = "history_way",notNull = true)
    private int historyWay;

    //运行状态
    @Column(name = "history_run_state",notNull = true)
    private int historyRunState;

    //上次运行时间
    @Column(name = "history_last_time",notNull = true)
    private String historyLastTime;

    //上次成功时间
    @Column(name = "history_last_success_time")
    private String historyLastSuccessTime;

    //分支
    @Column(name = "history_branch",notNull = true)
    private String historyBranch;

    //流水线id
    @Column(name = "pipeline_id",notNull = true)
    private int pipelineId;

    //凭证id
    @Column(name = "proof_id",notNull = true)
    private int proofId;

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

    public String getHistoryImplementTime() {
        return historyImplementTime;
    }

    public void setHistoryImplementTime(String historyImplementTime) {
        this.historyImplementTime = historyImplementTime;
    }

    public int getHistoryWay() {
        return historyWay;
    }

    public void setHistoryWay(int historyWay) {
        this.historyWay = historyWay;
    }

    public int getHistoryRunState() {
        return historyRunState;
    }

    public void setHistoryRunState(int historyRunState) {
        this.historyRunState = historyRunState;
    }

    public String getHistoryLastTime() {
        return historyLastTime;
    }

    public void setHistoryLastTime(String historyLastTime) {
        this.historyLastTime = historyLastTime;
    }

    public String getHistoryLastSuccessTime() {
        return historyLastSuccessTime;
    }

    public void setHistoryLastSuccessTime(String historyLastSuccessTime) {
        this.historyLastSuccessTime = historyLastSuccessTime;
    }

    public String getHistoryBranch() {
        return historyBranch;
    }

    public void setHistoryBranch(String historyBranch) {
        this.historyBranch = historyBranch;
    }

    public int getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(int pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getProofId() {
        return proofId;
    }

    public void setProofId(int proofId) {
        this.proofId = proofId;
    }
}
