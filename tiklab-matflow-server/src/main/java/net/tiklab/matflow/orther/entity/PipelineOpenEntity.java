package net.tiklab.matflow.orther.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_open")
public class PipelineOpenEntity {

    @Id
    @GeneratorValue
    @Column(name = "open_id")
    private String openId;

    @Column(name = "pipeline_id")
    private String pipelineId;

    @Column(name = "number")
    private int number ;

    @Column(name = "user_id")
    private String userId ;

    @Column(name = "create_time")
    private String createTime;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
