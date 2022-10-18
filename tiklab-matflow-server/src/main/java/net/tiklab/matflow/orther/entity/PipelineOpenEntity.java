package net.tiklab.matflow.orther.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_open")
public class PipelineOpenEntity {

    @Id
    @GeneratorValue
    @Column(name = "open_id")
    private String id;

    @Column(name = "pipeline_id")
    private String pipelineId;

    @Column(name = "number")
    private int number ;

    @Column(name = "user_id")
    private String userId ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
