package io.tiklab.matflow.task.code.entity;


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
