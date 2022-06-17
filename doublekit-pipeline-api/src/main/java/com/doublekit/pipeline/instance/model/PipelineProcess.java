package com.doublekit.pipeline.instance.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.join.annotation.Join;
import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineDeploy;
import com.doublekit.pipeline.setting.proof.model.Proof;

/**
 * 存放执行的信息
 */

@ApiModel
@Join
public class PipelineProcess {

    private Proof proof;

    private PipelineExecHistory pipelineExecHistory;

    private PipelineExecLog pipelineExecLog;

    private PipelineConfigure pipelineConfigure;

    private PipelineDeploy pipelineDeploy;

    public PipelineExecHistory getPipelineExecHistory() {
        return pipelineExecHistory;
    }

    public void setPipelineExecHistory(PipelineExecHistory pipelineExecHistory) {
        this.pipelineExecHistory = pipelineExecHistory;
    }

    public PipelineExecLog getPipelineExecLog() {
        return pipelineExecLog;
    }

    public void setPipelineExecLog(PipelineExecLog pipelineExecLog) {
        this.pipelineExecLog = pipelineExecLog;
    }

    public Proof getProof() {
        return proof;
    }

    public void setProof(Proof proof) {
        this.proof = proof;
    }

    public PipelineConfigure getPipelineConfigure() {
        return pipelineConfigure;
    }

    public void setPipelineConfigure(PipelineConfigure pipelineConfigure) {
        this.pipelineConfigure = pipelineConfigure;
    }

    public PipelineDeploy getPipelineDeploy() {
        return pipelineDeploy;
    }

    public void setPipelineDeploy(PipelineDeploy pipelineDeploy) {
        this.pipelineDeploy = pipelineDeploy;
    }
}
