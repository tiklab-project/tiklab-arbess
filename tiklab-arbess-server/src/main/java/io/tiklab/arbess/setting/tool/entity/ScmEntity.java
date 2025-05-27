package io.tiklab.arbess.setting.tool.entity;


import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_setting_scm")
public class ScmEntity {

    //凭证id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "scm_id")
    private String scmId;

    @Column(name = "scm_type")
    private String scmType;

    @Column(name = "create_time")
    private String createTime;

    //名称
    @Column(name = "scm_name")
    private String scmName;

    //地址
    @Column(name = "scm_address")
    private String scmAddress;

    // 添加类型
    @Column(name = "add_type")
    private String addType;

    // 包地址
    @Column(name = "pkg_address")
    private String pkgAddress;

    public String getPkgAddress() {
        return pkgAddress;
    }

    public void setPkgAddress(String pkgAddress) {
        this.pkgAddress = pkgAddress;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getScmId() {
        return scmId;
    }

    public void setScmId(String scmId) {
        this.scmId = scmId;
    }

    public String getScmType() {
        return scmType;
    }

    public void setScmType(String scmType) {
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
