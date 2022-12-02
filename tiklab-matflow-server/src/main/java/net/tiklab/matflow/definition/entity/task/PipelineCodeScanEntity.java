package net.tiklab.matflow.definition.entity.task;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_code_scan")
public class PipelineCodeScanEntity {

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
