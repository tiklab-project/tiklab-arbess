package io.tiklab.arbess.task.codescan.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 代码扫描
 * @author zcamy
 */

@Entity
@Table(name="pip_task_code_scan")
public class TaskCodeScanEntity {

    @Id
    @Column(name = "task_id" )
    private String taskId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "auth_id")
    private String authId;

    // 是否开启断言
    @Column(name = "open_assert")
    private String openAssert;

    // 是否启用调试模式
    @Column(name = "open_debug")
    private String openDebug;

    @Column(name = "scan_path")
    private String scanPath;

    // 扫描等级 min--最小，default--默认，max--最大
    @Column(name = "scan_grade" )
    private String scanGrade;

    // 扫描错误级别 default--默认 max--最大
    @Column(name = "err_grade" )
    private String errGrade;

    // 扫描类型
    @Column(name = "code_type")
    private String codeType;

    // jdk版本
    @Column(name = "tool_jdk" )
    private String toolJdk;

    // maven版本
    @Column(name = "tool_maven" )
    private String toolMaven;

    @Column(name = "tool_sonar" )
    private String toolSonar;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getToolSonar() {
        return toolSonar;
    }

    public void setToolSonar(String toolSonar) {
        this.toolSonar = toolSonar;
    }

    public String getToolJdk() {
        return toolJdk;
    }

    public void setToolJdk(String toolJdk) {
        this.toolJdk = toolJdk;
    }

    public String getToolMaven() {
        return toolMaven;
    }

    public void setToolMaven(String toolMaven) {
        this.toolMaven = toolMaven;
    }

    public String getScanGrade() {
        return scanGrade;
    }

    public TaskCodeScanEntity setScanGrade(String scanGrade) {
        this.scanGrade = scanGrade;
        return this;
    }

    public String getErrGrade() {
        return errGrade;
    }

    public TaskCodeScanEntity setErrGrade(String errGrade) {
        this.errGrade = errGrade;
        return this;
    }

    public String getScanPath() {
        return scanPath;
    }

    public TaskCodeScanEntity setScanPath(String scanPath) {
        this.scanPath = scanPath;
        return this;
    }

    public String getOpenAssert() {
        return openAssert;
    }

    public TaskCodeScanEntity setOpenAssert(String openAssert) {
        this.openAssert = openAssert;
        return this;
    }

    public String getOpenDebug() {
        return openDebug;
    }

    public TaskCodeScanEntity setOpenDebug(String openDebug) {
        this.openDebug = openDebug;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
