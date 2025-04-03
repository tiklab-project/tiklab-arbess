package io.tiklab.arbess.task.code.entity;


import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_code")
public class TaskCodeEntity {

    @Id
    @Column(name = "task_id" ,notNull = true)
    private String taskId;

    //地址名
    @Column(name = "code_name",notNull = true)
    private String codeName;

    //地址
    @Column(name = "code_address",notNull = true)
    private String codeAddress;

    //分支
    @Column(name = "code_branch",notNull = true)
    private String codeBranch;

    //凭证信息
    @Column(name = "auth_id",notNull = true)
    private String authId;

    @Column(name = "svn_file",notNull = true)
    private String svnFile;

    @Column(name = "xcode_id",notNull = true)
    private String xcodeId;

    @Column(name = "branch_id",notNull = true)
    private String branchId;

    @Column(name = "house_id",notNull = true)
    private String houseId;

    @Column(name = "tool_git",notNull = true)
    private String toolGit;

    @Column(name = "tool_svn",notNull = true)
    private String toolSvn;

    // 认证类型
    @Column(name = "auth_type",notNull = true)
    private String authType;

    // 用户名
    @Column(name = "username",notNull = true)
    private String username;

    // 密码
    @Column(name = "password",notNull = true)
    private String password;

    // 私钥
    @Column(name = "pri_key",notNull = true)
    private String priKey;


    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPriKey() {
        return priKey;
    }

    public void setPriKey(String priKey) {
        this.priKey = priKey;
    }

    public String getToolSvn() {
        return toolSvn;
    }

    public void setToolSvn(String toolSvn) {
        this.toolSvn = toolSvn;
    }

    public String getToolGit() {
        return toolGit;
    }

    public void setToolGit(String toolGit) {
        this.toolGit = toolGit;
    }

    public String getHouseId() {
        return houseId;
    }

    public TaskCodeEntity setHouseId(String houseId) {
        this.houseId = houseId;
        return this;
    }

    public String getXcodeId() {
        return xcodeId;
    }

    public void setXcodeId(String xcodeId) {
        this.xcodeId = xcodeId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getSvnFile() {
        return svnFile;
    }

    public void setSvnFile(String svnFile) {
        this.svnFile = svnFile;
    }
}
