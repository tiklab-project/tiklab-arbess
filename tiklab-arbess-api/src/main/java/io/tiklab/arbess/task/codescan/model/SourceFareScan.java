package io.tiklab.arbess.task.codescan.model;


import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

@Join
@Mapper
public class SourceFareScan {

    private String id;

    // 扫描状态 success / run / fail
    private String status;

    // 流水线ID
    private String pipelineId;

    // 创建时间
    private String createTime;

    // 项目ID
    private String projectId;

    // 所有问题数量
    private Integer allTrouble;

    // 严重问题
    private Integer severityTrouble;

    // 警告问题
    private Integer noticeTrouble;

    // 提示问题
    private Integer suggestTrouble;

    // 错误问题
    private Integer errorTrouble;

    private String url;

    public Integer getErrorTrouble() {
        return errorTrouble;
    }

    public SourceFareScan setErrorTrouble(Integer errorTrouble) {
        this.errorTrouble = errorTrouble;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getAllTrouble() {
        return allTrouble;
    }

    public void setAllTrouble(Integer allTrouble) {
        this.allTrouble = allTrouble;
    }

    public Integer getSeverityTrouble() {
        return severityTrouble;
    }

    public void setSeverityTrouble(Integer severityTrouble) {
        this.severityTrouble = severityTrouble;
    }

    public Integer getNoticeTrouble() {
        return noticeTrouble;
    }

    public void setNoticeTrouble(Integer noticeTrouble) {
        this.noticeTrouble = noticeTrouble;
    }

    public Integer getSuggestTrouble() {
        return suggestTrouble;
    }

    public void setSuggestTrouble(Integer suggestTrouble) {
        this.suggestTrouble = suggestTrouble;
    }
}
