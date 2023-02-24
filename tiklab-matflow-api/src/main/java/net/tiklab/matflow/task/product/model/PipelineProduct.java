package net.tiklab.matflow.task.product.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "PipelineProductEntity")
public class PipelineProduct {

    @ApiProperty(name = "productId",desc = "id")
    private String productId;

    @ApiProperty(name = "groupId",desc = "groupId")
    private String groupId;

    @ApiProperty(name = "artifactId",desc = "artifactId")
    private String artifactId;

    @ApiProperty(name = "version",desc = "version")
    private String version;

    @ApiProperty(name = "fileType",desc = "文件类型")
    private String fileType;

    @ApiProperty(name = "fileAddress",desc = "文件地址")
    private String fileAddress;

    @ApiProperty(name = "putAddress",desc = "发送地址")
    private String putAddress;

    //授权id
    @ApiProperty(name="authId",desc="授权id")
    private String authId;


    @ApiProperty(name="configId",desc="配置id")
    private String configId;

    //授权信息
    private Object auth;

    private int type;

    private int sort;


    @ApiProperty(name="name",desc="名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public Object getAuth() {
        return auth;
    }

    public void setAuth(Object auth) {
        this.auth = auth;
    }

    public String getPutAddress() {
        return putAddress;
    }

    public void setPutAddress(String putAddress) {
        this.putAddress = putAddress;
    }
}
