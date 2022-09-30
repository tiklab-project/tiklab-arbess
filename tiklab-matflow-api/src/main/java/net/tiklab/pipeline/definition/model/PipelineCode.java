package net.tiklab.pipeline.definition.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.pipeline.setting.model.Proof;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;


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

    //凭证id
    @ApiProperty(name="proof",desc="凭证id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "proof.proofId",target = "proofId")
    })
    @JoinQuery(key = "proofId")
    private Proof proof;

    //顺序
    @ApiProperty(name = "sort",desc="顺序")
    private int sort;

    //别名
    @ApiProperty(name = "codeAlias",desc="别名")
    private String codeAlias;

    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;



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

    public Proof getProof() {
        return proof;
    }

    public void setProof(Proof proof) {
        this.proof = proof;
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


    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }
}
