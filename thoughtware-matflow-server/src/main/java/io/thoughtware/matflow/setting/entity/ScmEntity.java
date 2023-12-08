package io.thoughtware.matflow.setting.entity;


import io.thoughtware.dal.jpa.annotation.*;

@Entity
@Table(name="pip_setting_scm")
public class ScmEntity {

    //凭证id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "scm_id")
    private String scmId;

    //1.git 2.svn 21.node 22.maven
    @Column(name = "scm_type")
    private int scmType;

    @Column(name = "create_time")
    private String createTime;

    //名称
    @Column(name = "scm_name")
    private String scmName;

    //地址
    @Column(name = "scm_address")
    private String scmAddress;

    public String getScmId() {
        return scmId;
    }

    public void setScmId(String scmId) {
        this.scmId = scmId;
    }

    public int getScmType() {
        return scmType;
    }

    public void setScmType(int scmType) {
        this.scmType = scmType;
    }

    public String getScmName() {
        return scmName;
    }

    public void setScmName(String scmName) {
        this.scmName = scmName;
    }

    public String getScmAddress() {
        return scmAddress;
    }

    public void setScmAddress(String scmAddress) {
        this.scmAddress = scmAddress;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
