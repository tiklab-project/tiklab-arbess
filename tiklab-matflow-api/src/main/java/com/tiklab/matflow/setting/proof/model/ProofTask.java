package com.tiklab.matflow.setting.proof.model;


import com.tiklab.beans.annotation.Mapper;
import com.tiklab.join.annotation.Join;
import com.tiklab.postlink.annotation.ApiModel;
import com.tiklab.postlink.annotation.ApiProperty;

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
    @ApiProperty(name = "matflowId" ,desc = "流水线id")
    private String matFlowId;

    public ProofTask() {
    }

    public ProofTask(String proofId, String matFlowId) {
        this.proofId = proofId;
        this.matFlowId = matFlowId;
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

    public String getMatFlowId() {
        return matFlowId;
    }

    public void setMatFlowId(String matFlowId) {
        this.matFlowId = matFlowId;
    }
}
