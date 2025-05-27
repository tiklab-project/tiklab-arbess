package io.tiklab.arbess.task.codescan.model;


import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

@Join
@Mapper
public class SonarQubeScan {

    private String id;

    // 扫描状态 OK / ERROR / WARN
    private String status;

    // 扫描结果链接
    private String url;

    // 代码行数
    private int ncloc;

    // 文件数量
    private int files;

    // bug数量
    private int bugs;

    // 异味数量
    private int codeSmells;

    // 漏洞数量
    private int loophole;

    // 重复率
    private String repetition;

    // 覆盖率
    private String coverage;

    // 重复代码行数
    private int duplicatedLines;

    // 流水线ID
    private String pipelineId;

    // 创建时间
    private String createTime;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
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

    public int getNcloc() {
        return ncloc;
    }

    public void setNcloc(int ncloc) {
        this.ncloc = ncloc;
    }

    public int getFiles() {
        return files;
    }

    public void setFiles(int files) {
        this.files = files;
    }

    public int getBugs() {
        return bugs;
    }

    public void setBugs(int bugs) {
        this.bugs = bugs;
    }

    public int getCodeSmells() {
        return codeSmells;
    }

    public void setCodeSmells(int codeSmells) {
        this.codeSmells = codeSmells;
    }

    public int getLoophole() {
        return loophole;
    }

    public void setLoophole(int loophole) {
        this.loophole = loophole;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public int getDuplicatedLines() {
        return duplicatedLines;
    }

    public void setDuplicatedLines(int duplicatedLines) {
        this.duplicatedLines = duplicatedLines;
    }
}
