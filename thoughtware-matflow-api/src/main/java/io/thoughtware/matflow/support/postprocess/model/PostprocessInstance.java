package io.thoughtware.matflow.support.postprocess.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;


/**
 * 流水线后置处理实例
 */
//@ApiModel
@Join
@Mapper(targetAlias = "PostprocessInstanceEntity")
public class PostprocessInstance {

    //@ApiProperty(name = "id",desc="id")
    private String id;


    //@ApiProperty(name = "instanceId",desc="实例id")
    private String instanceId;


    //@ApiProperty(name = "taskInstanceId",desc="任务实例id")
    private String taskInstanceId;


    //@ApiProperty(name = "postAddress",desc="日志地址")
    private String postAddress;

    //@ApiProperty(name = "postTime",desc="运行时间")
    private Integer postTime;


    //@ApiProperty(name = "postState",desc="运行状态")
    private String postState;

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


    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public Integer getPostTime() {
        return postTime;
    }

    public void setPostTime(Integer postTime) {
        this.postTime = postTime;
    }

    public String getPostState() {
        return postState;
    }

    public void setPostState(String postState) {
        this.postState = postState;
    }
}
