package io.tiklab.matflow.task.build.entity;

import io.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_task_build_product")
public class TaskBuildProductEntity {

    @Id
    @GeneratorValue(length = 20)
    @Column(name = "id" ,notNull = true)
    private String id;

    //地址
    @Column(name = "product_address",notNull = true)
    private String productAddress;

    //分支
    @Column(name = "instance_id",notNull = true)
    private String instanceId;


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
}
