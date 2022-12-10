package net.tiklab.matflow.task.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineBuildEntity")
public class PipelineBuild {

    //id
    @ApiProperty(name = "buildId",desc="id")
    private String buildId;

    //构建文件地址
    @ApiProperty(name="buildAddress",desc="构建文件地址")
    private String buildAddress;

    //构建命令
    @ApiProperty(name="buildOrder",desc="构建命令")
    private String buildOrder;

    @ApiProperty(name="configId",desc="配置id")
    private String configId;

    //顺序
    private int sort;

    //构建类型
    private int type;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBuildAddress() {
        return buildAddress;
    }

    public void setBuildAddress(String buildAddress) {
        this.buildAddress = buildAddress;
    }

    public String getBuildOrder() {
        return buildOrder;
    }

    public void setBuildOrder(String buildOrder) {
        this.buildOrder = buildOrder;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }


}