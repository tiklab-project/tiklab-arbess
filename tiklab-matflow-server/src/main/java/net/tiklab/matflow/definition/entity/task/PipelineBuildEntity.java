package net.tiklab.matflow.definition.entity.task;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_build")
public class PipelineBuildEntity {

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
