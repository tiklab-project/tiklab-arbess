package io.tiklab.matflow.task.build.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper
public class TaskBuildProduct {


    @ApiProperty(name = "taskId",desc = "id")
    private String id;

    //地址
    @ApiProperty(name = "productAddress",desc = "制品地址")
    private String productAddress;

    @ApiProperty(name = "productName",desc = "制品名称")
    private String productName;

    //分支
    @ApiProperty(name = "instanceId",desc = "实例id")
    private String instanceId;

    public TaskBuildProduct() {
    }

    public TaskBuildProduct(String productAddress, String instanceId) {
        this.productAddress = productAddress;
        this.instanceId = instanceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductAddress() {
        return productAddress;
    }

    public void setProductAddress(String productAddress) {
        this.productAddress = productAddress;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
