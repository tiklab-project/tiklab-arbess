package io.tiklab.matflow.task.code.model;


import io.tiklab.beans.annotation.Mapper;
import io.tiklab.beans.annotation.Mapping;
import io.tiklab.beans.annotation.Mappings;
import io.tiklab.join.annotation.Join;
import io.tiklab.join.annotation.JoinQuery;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

/**
 * 任务源码模型
 */
@ApiModel
@Join
@Mapper(targetAlias = "TaskCodeEntity")
public class TaskCode {

    @ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //地址名
    @ApiProperty(name="codeName",desc="地址名")
    private String codeName;

    //代码源地址
    @ApiProperty(name="codeAddress",desc="代码地址")
    private String codeAddress;

    //分支
    @ApiProperty(name="codeBranch",desc="分支")
    private String codeBranch;

    //授权id
    @ApiProperty(name="authId",desc="授权id")
    private String authId;

    @ApiProperty(name="svnFile",desc="svn检出文件夹")
    private String svnFile;


    @Mappings({
            @Mapping(source = "repository.id",target = "xcodeId")
    })
    @JoinQuery(key = "xcodeId")
    private XcodeRepository repository;


    @Mappings({
            @Mapping(source = "branch.id",target = "branchId")
    })
    @JoinQuery(key = "branchId")
    private XcodeBranch branch;

    //授权信息
    private Object auth;

    //顺序
    private int sort;

    //代码类型
    private String type;


    public XcodeRepository getRepository() {
        return repository;
    }

    public void setRepository(XcodeRepository repository) {
        this.repository = repository;
    }

    public XcodeBranch getBranch() {
        return branch;
    }

    public void setBranch(XcodeBranch branch) {
        this.branch = branch;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeAddress() {
        return codeAddress;
    }

    public void setCodeAddress(String codeAddress) {
        this.codeAddress = codeAddress;
    }

    public String getCodeBranch() {
        return codeBranch;
    }

    public void setCodeBranch(String codeBranch) {
        this.codeBranch = codeBranch;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }

    public String getSvnFile() {
        return svnFile;
    }

    public void setSvnFile(String svnFile) {
        this.svnFile = svnFile;
    }
}
