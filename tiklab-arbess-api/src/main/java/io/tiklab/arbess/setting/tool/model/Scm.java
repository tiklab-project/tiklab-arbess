package io.tiklab.arbess.setting.tool.model;


import io.tiklab.core.BaseModel;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;



/**
 * 流水线环境配置模型
 */
//@ApiModel
@Join
@Mapper
public class Scm extends BaseModel {

    //@ApiProperty(name = "scmId",desc = "id")
    private String scmId;

    //@ApiProperty(name = "scmType",desc = "类型")
    private String scmType;

    //@ApiProperty(name = "scmName",desc = "名称")
    private String scmName;

    //@ApiProperty(name = "createTime",desc = "创建时间")
    private String createTime;

    //@ApiProperty(name = "scmAddress",desc = "地址")
    private String scmAddress;

    // 添加类型
    private String addType;

    // 包地址
    private String pkgAddress;

    // 版本
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

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
