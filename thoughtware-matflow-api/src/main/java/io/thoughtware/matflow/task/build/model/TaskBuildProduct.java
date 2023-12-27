package io.thoughtware.matflow.task.build.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;



//@ApiModel
@Join
@Mapper
public class TaskBuildProduct {


    //@ApiProperty(name = "taskId",desc = "id")
    private String id;

    //地址
    //@ApiProperty(name = "type",desc = "类型")
    private String type;

    //@ApiProperty(name = "key",desc = "key")
    private String key;

    //@ApiProperty(name = "value",desc = "值")
    private String value;

    //分支
    //@ApiProperty(name = "instanceId",desc = "实例id")
    private String instanceId;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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
