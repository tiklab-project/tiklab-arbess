package net.tiklab.pipeline.orther.model;


import net.tiklab.join.annotation.Join;
import net.tiklab.pipeline.execute.model.PipelineExecHistory;
import net.tiklab.pipeline.execute.model.PipelineExecLog;
import net.tiklab.pipeline.setting.model.Proof;
import net.tiklab.postin.annotation.ApiModel;

/**
 * 存放执行的信息
 */

@ApiModel
@Join
public class PipelineProcess {

    private Proof proof;

    private PipelineExecHistory pipelineExecHistory;

    private PipelineExecLog pipelineExecLog;

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
}
