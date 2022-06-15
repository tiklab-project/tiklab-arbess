package com.doublekit.pipeline.setting.view.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_view")
public class PipelineViewEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String id;

    @Column(name = "pipeline_id")
    private String pipelineId;

    @Column(name = "view_name")
    private String viewName;

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

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
