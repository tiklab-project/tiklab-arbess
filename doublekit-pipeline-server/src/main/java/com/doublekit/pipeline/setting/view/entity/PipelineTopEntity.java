package com.doublekit.pipeline.setting.view.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_top")
public class PipelineTopEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String id;

    //用户id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "top_id")
    private String topId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopId() {
        return topId;
    }

    public void setTopId(String topId) {
        this.topId = topId;
    }
}
