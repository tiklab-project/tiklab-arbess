package com.tiklab.matflow.setting.view.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_top")
public class PipelineTopEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String id;

    //用户id
    @Column(name = "view_id")
    private String viewId;

    @Column(name = "pipeline_id")
    private String pipelineId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }
}
