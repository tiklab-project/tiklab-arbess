package com.tiklab.matflow.setting.view.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_view")
public class PipelineViewEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "view_name")
    private String viewName;

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

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
