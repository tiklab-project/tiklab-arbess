package net.tiklab.pipeline.setting.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "ProofTaskEntity")
public class ProofTask {

    //id
    @ApiProperty(name = "taskId" ,desc = "id")
    private String taskId;

    //凭证id
    @ApiProperty(name = "proofId",desc = "凭证id")
    private String proofId;

    //流水线id
    @ApiProperty(name = "pipelineId" ,desc = "流水线id")
    private String pipelineId;

    public ProofTask() {
    }

    public ProofTask(String proofId, String pipelineId) {
        this.proofId = proofId;
        this.pipelineId = pipelineId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
