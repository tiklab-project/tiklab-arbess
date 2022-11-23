package net.tiklab.matflow.execute.model;


import net.tiklab.matflow.definition.model.Pipeline;
import net.tiklab.postin.annotation.ApiModel;

import java.io.InputStream;

/**
 * 存放执行的信息
 */

@ApiModel
public class PipelineProcess {

    //执行历史
    private PipelineExecHistory pipelineExecHistory;

    //执行日志
    private PipelineExecLog pipelineExecLog;

    //运行的流水线
    private Pipeline pipeline;

    //运行日志
    private InputStream inputStream;

    //执行错误信息
    private InputStream errInputStream;

    //编码
    private String enCode;

    //错误字段
    private String[] error;


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


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public InputStream getErrInputStream() {
        return errInputStream;
    }

    public void setErrInputStream(InputStream errInputStream) {
        this.errInputStream = errInputStream;
    }

    public String getEnCode() {
        return enCode;
    }

    public void setEnCode(String enCode) {
        this.enCode = enCode;
    }

    public String[] getError() {
        return error;
    }

    public void setError(String[] error) {
        this.error = error;
    }
}
