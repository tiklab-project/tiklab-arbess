package net.tiklab.matflow.definition.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.matflow.setting.model.PipelineAuth;
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

    @ApiProperty(name="pipelineAuth",desc="授权id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineAuth.authId",target = "authId")
    })
    @JoinQuery(key = "authId")
    private PipelineAuth pipelineAuth;

    private int sort;

    private int type;


    public String getCodeScanId() {
        return codeScanId;
    }

    public void setCodeScanId(String codeScanId) {
        this.codeScanId = codeScanId;
    }

    public PipelineAuth getPipelineAuth() {
        return pipelineAuth;
    }

    public void setPipelineAuth(PipelineAuth pipelineAuth) {
        this.pipelineAuth = pipelineAuth;
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
}
