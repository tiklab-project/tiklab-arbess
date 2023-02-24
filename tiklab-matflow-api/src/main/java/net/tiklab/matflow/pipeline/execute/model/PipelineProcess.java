package net.tiklab.matflow.pipeline.execute.model;


import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.postin.annotation.ApiModel;

import java.io.InputStream;

/**
 * 存放执行的信息
 */

@ApiModel
public class PipelineProcess {

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

    //配置id
    private String configId;

    //历史id
    private String historyId;

    private String logId;

    public PipelineProcess() {
    }

    public PipelineProcess(String id) {
        this.pipeline = new Pipeline(id);
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
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

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }



}
