package com.tiklab.matflow.setting.proof.entity;


import com.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="matflow_proof_task")
public class ProofTaskEntity {

    //凭证id
    @Id
    @GeneratorValue
    @Column(name = "task_id")
    private String taskId;

    //凭证名
    @Column(name = "proof_id")
    private String proofId;

    //凭证名
    @Column(name = "matFlow_id")
    private String matFlowId;


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
