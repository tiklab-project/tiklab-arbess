package io.tiklab.arbess.task.codescan.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_code_scan_sonar")
public class SonarQubeScanEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id" ,notNull = true)
    private String id;

    @Column(name = "status" )
    private String status;

    // 代码行数
    @Column(name = "ncloc" )
    private int ncloc;

    // 文件数量
    @Column(name = "files" )
    private int files;

    // bug数量
    @Column(name = "bugs" )
    private int bugs;

    // 异味数量
    @Column(name = "code_smells" )
    private int codeSmells;

    // 漏洞数量
    @Column(name = "loophole" )
    private int loophole;

    // 重复率
    @Column(name = "repetition" )
    private String repetition;

    // 覆盖率
    @Column(name = "coverage" )
    private String coverage;

    // 重复代码行数
    @Column(name = "duplicated_lines" )
    private int duplicatedLines;

    @Column(name = "url" )
    private String url;

    @Column(name = "pipeline_id" )
    private String pipelineId;

    @Column(name = "create_time" )
    private String createTime;

    @Column(name = "name")
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

    public int getLoophole() {
        return loophole;
    }

    public void setLoophole(int loophole) {
        this.loophole = loophole;
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
