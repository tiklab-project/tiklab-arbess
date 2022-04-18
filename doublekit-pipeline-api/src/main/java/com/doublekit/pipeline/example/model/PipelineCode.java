package com.doublekit.pipeline.example.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapper;
import com.doublekit.join.annotation.Join;


@ApiModel
@Join
@Mapper(targetAlias = "PipelineCodeEntity")
public class PipelineCode {

    //id
    @ApiProperty(name="codeId",desc="codeId")
    private String codeId;

    //代码类型
    @ApiProperty(name="type",desc="代码类型")
    private int type;

    //地址名
    @ApiProperty(name="codeName",desc="地址名")
    private String codeName;

    //代码源地址
    @ApiProperty(name="codeAddress",desc="代码地址")
    private String codeAddress;

    //分支
    @ApiProperty(name="codeBranch",desc="分支")
    private String codeBranch;

    //凭证名称
    @ApiProperty(name="proofName",desc="凭证名称",eg="@selectOne")
    private String proofName;


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

    public String getProofName() {
        return proofName;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName;
    }

}
