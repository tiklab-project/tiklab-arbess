package net.tiklab.matflow.execute.model;


import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.postin.annotation.ApiModel;

/**
 * 存放执行的信息
 */

@ApiModel
public class PipelineProcess {

    //历史
    private PipelineExecHistory pipelineExecHistory;

    //日志
    private PipelineExecLog pipelineExecLog;

    //流水线
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

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

}
