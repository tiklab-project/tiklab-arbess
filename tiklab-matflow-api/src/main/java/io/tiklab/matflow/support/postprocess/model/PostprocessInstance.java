package io.tiklab.matflow.support.postprocess.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
/**
 * 流水线后置处理实例
 */
@ApiModel
@Join
@Mapper(targetAlias = "PostprocessInstanceEntity")
public class PostprocessInstance {

    @ApiProperty(name = "id",desc="id")
    private String id;


    @ApiProperty(name = "instanceId",desc="实例id")
    private String instanceId;


    @ApiProperty(name = "taskInstanceId",desc="任务实例id")
    private String taskInstanceId;

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
