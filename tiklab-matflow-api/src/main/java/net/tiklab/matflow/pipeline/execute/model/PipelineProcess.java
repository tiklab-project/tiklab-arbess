package net.tiklab.matflow.pipeline.execute.model;

import net.tiklab.matflow.pipeline.definition.model.Pipeline;
import net.tiklab.postin.annotation.ApiModel;

import java.io.InputStream;

/**
 * 流水线执行过程模型
 */

@ApiModel
public class PipelineProcess {

    //流水线
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
    private String taskId;

    //历史id
    private String instanceId;

    //日志id
    private String taskInstanceId;

    public PipelineProcess() {
    }

    public PipelineProcess(String id) {
        this.pipeline = new Pipeline(id);
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }
}
