package net.tiklab.matflow.setting.model;



import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineScmEntity")
public class PipelineScm {

    @ApiProperty(name = "scmId",desc = "id")
    private String scmId;

    @ApiProperty(name = "scmType",desc = "类型")
    private int scmType;

    @ApiProperty(name = "scmName",desc = "名称")
    private String scmName;

    @ApiProperty(name = "createTime",desc = "创建时间")
    private String createTime;

    @ApiProperty(name = "scmAddress",desc = "地址")
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
