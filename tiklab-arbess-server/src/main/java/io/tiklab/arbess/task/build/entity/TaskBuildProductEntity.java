package io.tiklab.arbess.task.build.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_build_product")
public class TaskBuildProductEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id" ,notNull = true)
    private String id;


    @Column(name = "instance_id",notNull = true)
    private String instanceId;

    //地址
    @Column(name = "type",notNull = true)
    private String type;


    @Column(name = "key",notNull = true)
    private String key;

    @Column(name = "value",notNull = true)
    private String value;

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

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
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
}
