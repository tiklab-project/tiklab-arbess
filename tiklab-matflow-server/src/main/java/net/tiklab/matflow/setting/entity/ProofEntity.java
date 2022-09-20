package net.tiklab.matflow.setting.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="matflow_proof")
public class ProofEntity {

    //凭证id
    @Id
    @GeneratorValue
    @Column(name = "proof_id")
    private String proofId;

    //凭证名
    @Column(name = "proof_name")
    private String proofName;

    //作用域
    @Column(name = "proof_scope")
    private int proofScope;

    //凭证类型 (1 : password  2 : ssh 认证)
    @Column(name = "proof_type")
    private String proofType;

    //用户名
    @Column(name = "proof_username")
    private String proofUsername;

    //凭证密码
    @Column(name = "proof_password")
    private String proofPassword;

    //描述
    @Column(name = "proof_describe")
    private String proofDescribe;

    //创建时间
    @Column(name = "proof_create_time")
    private String proofCreateTime;

    //流水线id
    @Column(name = "matflow_id")
    private String matflowId;

    //类型
    @Column(name = "type")
    private int type;

    //用户id
    @Column(name = "user_id")
    private String userId;



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

    public String getProofCreateTime() {
        return proofCreateTime;
    }

    public void setProofCreateTime(String proofCreateTime) {
        this.proofCreateTime = proofCreateTime;
    }

    public String getMatflowId() {
        return matflowId;
    }

    public void setMatflowId(String matflowId) {
        this.matflowId = matflowId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
