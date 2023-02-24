package net.tiklab.matflow.task.codescan.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_pipeline_task_code_scan")
public class TaskCodeScanEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String codeScanId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "auth_id")
    private String authId;

    @Column(name = "config_id",notNull = true)
    private String configId;


    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getCodeScanId() {
        return codeScanId;
    }

    public void setCodeScanId(String codeScanId) {
        this.codeScanId = codeScanId;
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
