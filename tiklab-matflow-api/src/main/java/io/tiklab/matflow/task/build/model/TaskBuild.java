package io.tiklab.matflow.task.build.model;


import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
/**
 * 任务构建模型
 */
@ApiModel
@Join
@Mapper
public class TaskBuild {

    @ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    //构建文件地址
    @ApiProperty(name="buildAddress",desc="构建文件地址")
    private String buildAddress;

    //构建命令
    @ApiProperty(name="buildOrder",desc="构建命令")
    private String buildOrder;

    //顺序
    private int sort;

    //构建类型
    private String type;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
