package net.tiklab.matflow.setting.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_scm")
public class PipelineScmEntity {

    //凭证id
    @Id
    @GeneratorValue
    @Column(name = "scm_id")
    private String scmId;

    //1.git 2.svn 11.node 12.maven
    @Column(name = "scm_type")
    private int scmType;

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
}
