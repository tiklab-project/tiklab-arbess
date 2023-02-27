package net.tiklab.matflow.pipeline.definition.entity;


import net.tiklab.dal.jpa.annotation.*;

/**
 * 关注表
 */

@Entity
@Table(name="pip_pipeline_other_follow")
public class PipelineFollowEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String id;

    @Column(name = "pipeline_id")
    private String pipelineId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
