package com.doublekit.pipeline.instance.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_action")
public class PipelineActionEntity {

    @Id
    @GeneratorValue
    @Column(name = "open_id")
    private String id;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "massage")
    private String massage ;

    @Column(name = "user_id")
    private String userId ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
