package net.tiklab.matflow.task.build.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pip_pipeline_task_build")
public class TaskBuildEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "build_id")
    private String buildId;

    //地址
    @Column(name = "build_address",notNull = true)
    private String buildAddress;

    //分支
    @Column(name = "build_order",notNull = true)
    private String buildOrder;

    @Column(name = "config_id",notNull = true)
    private String configId;

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
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

}
