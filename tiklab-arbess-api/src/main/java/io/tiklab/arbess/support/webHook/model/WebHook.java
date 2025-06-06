package io.tiklab.arbess.support.webHook.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

/**
 * 流水线webhook模型
 */
@Join
@Mapper
public class WebHook {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 流水线ID
     */
    private String pipelineId;

    /**
     * 地址
     */
    private String url;


    /**
     *  密钥
     */
    private String key ;

    /**
     * 状态 0:禁用 1:启用
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public WebHook setStatus(Integer status) {
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

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
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
