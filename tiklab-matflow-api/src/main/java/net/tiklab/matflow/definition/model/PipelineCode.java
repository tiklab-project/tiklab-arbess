package net.tiklab.matflow.definition.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;


@ApiModel
@Join
@Mapper(targetAlias = "PipelineCodeEntity")
public class PipelineCode {

    //id
    @ApiProperty(name="codeId",desc="codeId")
    private String codeId;

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
    @ApiProperty(name="authName",desc="授权id")
    private String authId;

    //授权名称
    private Object auth;

    //顺序
    private int sort;

    //代码类型
    private int type;

    //别名
    @ApiProperty(name = "codeAlias",desc="别名")
    private String codeAlias;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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


    public String getCodeAlias() {
        return codeAlias;
    }

    public void setCodeAlias(String codeAlias) {
        this.codeAlias = codeAlias;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }
}
