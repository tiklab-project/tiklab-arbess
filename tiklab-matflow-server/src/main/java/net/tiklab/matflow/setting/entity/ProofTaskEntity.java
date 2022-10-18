package net.tiklab.matflow.setting.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_proof_task")
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
    @Column(name = "pipeline_id")
    private String pipelineId;


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
