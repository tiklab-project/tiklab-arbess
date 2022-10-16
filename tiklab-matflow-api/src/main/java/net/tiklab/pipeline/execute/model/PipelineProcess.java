package net.tiklab.pipeline.execute.model;


import net.tiklab.join.annotation.Join;
import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.pipeline.execute.model.PipelineExecHistory;
import net.tiklab.pipeline.execute.model.PipelineExecLog;
import net.tiklab.pipeline.setting.model.Proof;
import net.tiklab.postin.annotation.ApiModel;

/**
 * 存放执行的信息
 */

@ApiModel
public class PipelineProcess {

    private Proof proof;

    private PipelineExecHistory pipelineExecHistory;

    private PipelineExecLog pipelineExecLog;

    private Pipeline pipeline;

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

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }
}
