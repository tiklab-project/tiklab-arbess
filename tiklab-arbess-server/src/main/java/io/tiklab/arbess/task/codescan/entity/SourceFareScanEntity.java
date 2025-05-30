package io.tiklab.arbess.task.codescan.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_code_scan_sourcefare")
public class SourceFareScanEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id" ,notNull = true)
    private String id;

    // 扫描状态 OK / ERROR / WARN
    @Column(name = "status")
    private String status;

    @Column(name = "url")
    private String url;

    // 流水线ID
    @Column(name = "pipeline_id")
    private String pipelineId;

    // 创建时间
    @Column(name = "create_time")
    private String createTime;

    // 计划ID
    @Column(name = "plan_id")
    private String planId;

    // 项目ID
    @Column(name = "project_id")
    private String projectId;

    // 所有问题数量
    @Column(name = "all_trouble")
    private Integer allTrouble;

    // 严重问题
    @Column(name = "severity_trouble")
    private Integer severityTrouble;

    // 警告问题
    @Column(name = "notice_trouble")
    private Integer noticeTrouble;

    // suggestTrouble
    @Column(name = "suggest_trouble")
    private Integer suggestTrouble;

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

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
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
