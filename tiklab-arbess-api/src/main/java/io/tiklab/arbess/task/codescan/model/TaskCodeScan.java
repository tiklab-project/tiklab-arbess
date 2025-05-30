package io.tiklab.arbess.task.codescan.model;

import io.tiklab.arbess.setting.tool.model.Scm;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;


/**
 * 任务代码扫描模型
 */
//@ApiModel
@Join
@Mapper
public class TaskCodeScan {

    //@ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //@ApiProperty(name="projectName",desc="项目名称")
    private String projectName;

    //授权id
    //@ApiProperty(name="authName",desc="授权id")
    private String authId;

    // 代码语言类型
    private String codeType;

    // 是否开启断言
    private Boolean openAssert;

    // 是否启用调试模式
    private Boolean openDebug;

    // 扫描路径
    private String scanPath;

    // 扫描等级 min--最小，default--默认，max--最大
    private String scanGrade;

    // 扫描错误级别 default--默认 max--最大
    private String errGrade;

    //授权信息
    private Object auth;

    private int sort;

    private String type;

    private String instanceId;

    // jdk版本
    @Mappings({
            @Mapping(source = "toolJdk.scmId",target = "toolJdk")
    })
    @JoinQuery(key = "scmId")
    private Scm toolJdk;

    // maven版本
    @Mappings({
            @Mapping(source = "toolMaven.scmId",target = "toolMaven")
    })
    @JoinQuery(key = "scmId")
    private Scm toolMaven;

    // maven版本
    @Mappings({
            @Mapping(source = "toolSonar.scmId",target = "toolSonar")
    })
    @JoinQuery(key = "scmId")
    private Scm toolSonar;

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public Scm getToolSonar() {
        return toolSonar;
    }

    public void setToolSonar(Scm toolSonar) {
        this.toolSonar = toolSonar;
    }

    public Scm getToolJdk() {
        return toolJdk;
    }

    public void setToolJdk(Scm toolJdk) {
        this.toolJdk = toolJdk;
    }

    public Scm getToolMaven() {
        return toolMaven;
    }

    public void setToolMaven(Scm toolMaven) {
        this.toolMaven = toolMaven;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getScanGrade() {
        return scanGrade;
    }

    public TaskCodeScan setScanGrade(String scanGrade) {
        this.scanGrade = scanGrade;
        return this;
    }

    public String getErrGrade() {
        return errGrade;
    }

    public TaskCodeScan setErrGrade(String errGrade) {
        this.errGrade = errGrade;
        return this;
    }

    public String getScanPath() {
        return scanPath;
    }

    public TaskCodeScan setScanPath(String scanPath) {
        this.scanPath = scanPath;
        return this;
    }

    public Boolean getOpenAssert() {
        return openAssert;
    }

    public TaskCodeScan setOpenAssert(Boolean openAssert) {
        this.openAssert = openAssert;
        return this;
    }

    public Boolean getOpenDebug() {
        return openDebug;
    }

    public TaskCodeScan setOpenDebug(Boolean openDebug) {
        this.openDebug = openDebug;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }
}
