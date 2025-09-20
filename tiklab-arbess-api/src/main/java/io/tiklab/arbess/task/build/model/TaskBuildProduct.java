package io.tiklab.arbess.task.build.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tiklab.postin.annotation.ApiProperty;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.sql.Timestamp;

/**
 * @pi.model:io.tiklab.arbess.task.build.model.TaskBuildProduct
 * @desc:构建制品模型
 */

//@ApiModel
@Join
@Mapper
public class TaskBuildProduct {

    @ApiProperty(name = "id",desc = "id")
    private String id;

    @ApiProperty(name = "value",desc = "保存位置")
    private String value;

    @ApiProperty(name = "pipelineId",desc = "流水线id")
    private String pipelineId;

    @ApiProperty(name = "instanceId",desc = "实例id")
    private String instanceId;

    @ApiProperty(name = "agentId",desc = "agentId")
    private String agentId;

    @ApiProperty(name = "downloadUrl",desc = "下载地址")
    private String downloadUrl;

    @ApiProperty(name = "createTime",desc = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    @ApiProperty(name = "fileSize",desc = "文件大小")
    private String fileSize;

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public TaskBuildProduct() {
    }

    public TaskBuildProduct(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // public String getType() {
    //     return type;
    // }
    //
    // public void setType(String type) {
    //     this.type = type;
    // }
    //
    // public String getKey() {
    //     return key;
    // }
    //
    // public void setKey(String key) {
    //     this.key = key;
    // }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
