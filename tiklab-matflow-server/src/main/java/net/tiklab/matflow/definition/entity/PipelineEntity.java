package net.tiklab.matflow.definition.entity;

import net.tiklab.dal.jpa.annotation.*;

/**
 * 流水线
 */
@Entity
@Table(name="pipeline")
public class PipelineEntity {

    //流水线id
    @Id
    @GeneratorValue
    @Column(name = "pipeline_id")
    private String pipelineId;

    //流水线名称
    @Column(name = "pipeline_name",notNull = true)
    private String pipelineName;

    //流水线创建人
    @Column(name = "user_id",notNull = true)
    private String userId;

    //流水线创建时间
    @Column(name = "pipeline_create_time",notNull = true)
    private String  pipelineCreateTime;

    //流水线类型
    @Column(name = "pipeline_type")
    private int pipelineType;

    //运行状态
    @Column(name = "pipeline_state")
    private int pipelineState;

    @Column(name = "pipeline_power")
    private int pipelinePower;

    //颜色
    @Column(name="color")
    private int color;


    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPipelineCreateTime() {
        return pipelineCreateTime;
    }

    public void setPipelineCreateTime(String pipelineCreateTime) {
        this.pipelineCreateTime = pipelineCreateTime;
    }

    public int getPipelineType() {
        return pipelineType;
    }

    public void setPipelineType(int pipelineType) {
        this.pipelineType = pipelineType;
    }

    public int getPipelineState() {
        return pipelineState;
    }

    public void setPipelineState(int pipelineState) {
        this.pipelineState = pipelineState;
    }

    public int getPipelinePower() {
        return pipelinePower;
    }

    public void setPipelinePower(int pipelinePower) {
        this.pipelinePower = pipelinePower;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
