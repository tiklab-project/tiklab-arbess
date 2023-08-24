package io.tiklab.matflow.task.build.model;

import io.tiklab.postin.annotation.ApiProperty;

public class TaskBuildProductQuery {

    //地址
    @ApiProperty(name = "type",desc = "类型")
    private String type;

    @ApiProperty(name = "key",desc = "key")
    private String key;

    @ApiProperty(name = "value",desc = "值")
    private String value;

    //分支
    @ApiProperty(name = "instanceId",desc = "实例id")
    private String instanceId;

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
