package com.doublekit.pipeline.instance.model;

import com.doublekit.pipeline.definition.model.PipelineConfigure;
import com.doublekit.pipeline.execute.model.PipelineCode;
import com.doublekit.pipeline.execute.model.PipelineDeploy;
import com.doublekit.pipeline.execute.model.PipelineStructure;
import com.doublekit.pipeline.execute.model.PipelineTest;
import com.doublekit.pipeline.setting.proof.model.Proof;

/**
 * 存放执行的信息
 */
public class PipelineProcess {

    private Proof proof;

    private PipelineExecHistory pipelineExecHistory;

    private PipelineExecLog pipelineExecLog;

    private PipelineConfigure pipelineConfigure;

    private PipelineCode pipelineCode;

    private PipelineStructure pipelineStructure;

    private PipelineTest pipelineTest;

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

    public PipelineCode getPipelineCode() {
        return pipelineCode;
    }

    public void setPipelineCode(PipelineCode pipelineCode) {
        this.pipelineCode = pipelineCode;
    }

    public PipelineStructure getPipelineStructure() {
        return pipelineStructure;
    }

    public void setPipelineStructure(PipelineStructure pipelineStructure) {
        this.pipelineStructure = pipelineStructure;
    }

    public PipelineTest getPipelineTest() {
        return pipelineTest;
    }

    public void setPipelineTest(PipelineTest pipelineTest) {
        this.pipelineTest = pipelineTest;
    }

    public PipelineDeploy getPipelineDeploy() {
        return pipelineDeploy;
    }

    public void setPipelineDeploy(PipelineDeploy pipelineDeploy) {
        this.pipelineDeploy = pipelineDeploy;
    }
}
