package io.tiklab.arbess.task.code.model;


import io.tiklab.arbess.setting.model.Scm;
import io.tiklab.core.BaseModel;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;


/**
 * 任务源码模型
 */
//@ApiModel
@Join
@Mapper
public class TaskCode extends BaseModel {

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

    // 实例Id
    private String instanceId;

    // Git版本
    @Mappings({
            @Mapping(source = "toolGit.scmId",target = "toolGit")
    })
    @JoinQuery(key = "scmId")
    private Scm toolGit;

    // svn版本
    @Mappings({
            @Mapping(source = "toolSvn.scmId",target = "toolSvn")
    })
    @JoinQuery(key = "scmId")
    private Scm toolSvn;


    // 认证类型
    private String authType;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 私钥
    private String priKey;


    public Scm getToolGit() {
        return toolGit;
    }

    public void setToolGit(Scm toolGit) {
        this.toolGit = toolGit;
    }

    public Scm getToolSvn() {
        return toolSvn;
    }

    public void setToolSvn(Scm toolSvn) {
        this.toolSvn = toolSvn;
    }

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
}
