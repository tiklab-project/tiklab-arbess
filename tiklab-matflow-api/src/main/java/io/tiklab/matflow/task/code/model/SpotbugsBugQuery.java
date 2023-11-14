package io.tiklab.matflow.task.code.model;

/**
 * 代码扫描查询
 * @author zcamy
 */
public class SpotbugsBugQuery {

    private String pipelineId;


    public String getPipelineId() {
        return pipelineId;
    }

    public SpotbugsBugQuery setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }
}
