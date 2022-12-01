package net.tiklab.matflow.definition.model.task;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineCodeScanEntity")
public class PipelineCodeScan {

    @ApiProperty(name="codeScanId",desc="id")
    private String codeScanId;

    @ApiProperty(name="projectName",desc="项目名称")
    private String projectName;

    //授权id
    @ApiProperty(name="authName",desc="授权id")
    private String authId;

    //授权信息
    private Object auth;

    private int sort;

    private int type;


    public String getCodeScanId() {
        return codeScanId;
    }

    public void setCodeScanId(String codeScanId) {
        this.codeScanId = codeScanId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
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
