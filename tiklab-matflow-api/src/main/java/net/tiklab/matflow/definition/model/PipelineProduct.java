package net.tiklab.matflow.definition.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.matflow.setting.model.PipelineAuth;
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

    @ApiProperty(name="pipelineAuth",desc="授权id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipelineAuth.authId",target = "authId")
    })
    @JoinQuery(key = "authId")
    private PipelineAuth pipelineAuth;

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

    public PipelineAuth getPipelineAuth() {
        return pipelineAuth;
    }

    public void setPipelineAuth(PipelineAuth pipelineAuth) {
        this.pipelineAuth = pipelineAuth;
    }
}
