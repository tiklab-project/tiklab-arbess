package io.tiklab.arbess.support.webHook.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_trigger_webhook")
public class WebHookEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "pipeline_id")
    private String pipelineId;

    @Column(name = "parameters")
    private String parameters;

    @Column(name = "url")
    private String url;

    @Column(name = "key")
    private String key;

    @Column(name = "status")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public WebHookEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
