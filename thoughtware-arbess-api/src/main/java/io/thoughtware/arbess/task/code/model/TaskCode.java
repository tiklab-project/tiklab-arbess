package io.thoughtware.arbess.task.code.model;


import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;



/**
 * 任务源码模型
 */
//@ApiModel
@Join
@Mapper
public class TaskCode {

    //@ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //地址名
    //@ApiProperty(name="codeName",desc="地址名")
    private String codeName;

    //代码源地址
    //@ApiProperty(name="codeAddress",desc="代码地址")
    private String codeAddress;

    //分支
    //@ApiProperty(name="codeBranch",desc="分支")
    private String codeBranch;

    //授权id
    //@ApiProperty(name="authId",desc="授权id")
    private String authId;

    //@ApiProperty(name="svnFile",desc="svn检出文件夹")
    private String svnFile;

    // 第三方仓库ID
    private String houseId;


    //授权信息
    private Object auth;

    //顺序
    private int sort;

    //代码类型
    private String type;

    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getHouseId() {
        return houseId;
    }

    public TaskCode setHouseId(String houseId) {
        this.houseId = houseId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskCode setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getCodeName() {
        return codeName;
    }

    public TaskCode setCodeName(String codeName) {
        this.codeName = codeName;
        return this;
    }

    public String getCodeAddress() {
        return codeAddress;
    }

    public TaskCode setCodeAddress(String codeAddress) {
        this.codeAddress = codeAddress;
        return this;
    }

    public String getCodeBranch() {
        return codeBranch;
    }

    public TaskCode setCodeBranch(String codeBranch) {
        this.codeBranch = codeBranch;
        return this;
    }

    public String getAuthId() {
        return authId;
    }

    public TaskCode setAuthId(String authId) {
        this.authId = authId;
        return this;
    }

    public String getSvnFile() {
        return svnFile;
    }

    public TaskCode setSvnFile(String svnFile) {
        this.svnFile = svnFile;
        return this;
    }

    public Object getAuth() {
        return auth;
    }

    public TaskCode setAuth(Object auth) {
        this.auth = auth;
        return this;
    }

    public int getSort() {
        return sort;
    }

    public TaskCode setSort(int sort) {
        this.sort = sort;
        return this;
    }

    public String getType() {
        return type;
    }

    public TaskCode setType(String type) {
        this.type = type;
        return this;
    }
}
