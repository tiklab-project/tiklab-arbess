package io.tiklab.matflow.task.build.model;



public class TaskBuildProductQuery {

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

    public String getType() {
        return type;
    }

    public TaskBuildProductQuery setType(String type) {
        this.type = type;
        return this;
    }

    public String getKey() {
        return key;
    }

    public TaskBuildProductQuery setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public TaskBuildProductQuery setValue(String value) {
        this.value = value;
        return this;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public TaskBuildProductQuery setInstanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }
}
