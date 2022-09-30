package net.tiklab.pipeline.setting.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.pipeline.definition.model.Pipeline;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;
import net.tiklab.user.user.model.User;

import java.util.List;

@ApiModel
@Join
@Mapper(targetAlias = "ProofEntity")
public class Proof {

    //凭证id
    @ApiProperty(name="proofId",desc="凭证id")
    private String proofId;

    //凭证名
    @ApiProperty(name="proofName",desc="凭证名")
    private String proofName;

    //作用域
    @ApiProperty(name="proofScope",desc="作用域")
    private int proofScope;

    //凭证类型
    @ApiProperty(name="proofType",desc="凭证类型")
    private String proofType;

    //账户
    @ApiProperty(name="proofUsername",desc="账户")
    private String proofUsername;

    //密码
    @ApiProperty(name="proofPassword",desc="密码")
    private String proofPassword;

    //描述
    @ApiProperty(name="proofDescribe",desc="描述")
    private String proofDescribe;

    @ApiProperty(name="User",desc="用户id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    //流水线
    @ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.pipelineId",target = "pipelineId")
    })
    @JoinQuery(key = "pipelineId")
    private Pipeline pipeline;

    //创建时间
    @ApiProperty(name="proofCreateTime",desc="创建时间")
    private String proofCreateTime;

    //类型
    @ApiProperty(name="type",desc="类型")
    private int type;

    @ApiProperty(name="proofList",desc="流水线凭证列表")
    private List<String> proofList;

    public String getProofId() {
        return proofId;
    }

    public void setProofId(String proofId) {
        this.proofId = proofId;
    }

    public String getProofType() {
        return proofType;
    }

    public void setProofType(String proofType) {
        this.proofType = proofType;
    }

    public String getProofUsername() {
        return proofUsername;
    }

    public void setProofUsername(String proofUsername) {
        this.proofUsername = proofUsername;
    }

    public String getProofPassword() {
        return proofPassword;
    }

    public void setProofPassword(String proofPassword) {
        this.proofPassword = proofPassword;
    }

    public String getProofDescribe() {
        return proofDescribe;
    }

    public void setProofDescribe(String proofDescribe) {
        this.proofDescribe = proofDescribe;
    }

    public String getProofName() {
        return proofName;
    }

    public void setProofName(String proofName) {
        this.proofName = proofName;
    }

    public int getProofScope() {
        return proofScope;
    }

    public void setProofScope(int proofScope) {
        this.proofScope = proofScope;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String getProofCreateTime() {
        return proofCreateTime;
    }

    public void setProofCreateTime(String proofCreateTime) {
        this.proofCreateTime = proofCreateTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getProofList() {
        return proofList;
    }

    public void setProofList(List<String> proofList) {
        this.proofList = proofList;
    }
}
