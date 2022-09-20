package net.tiklab.matflow.definition.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="matflow_build")
public class MatFlowBuildEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "build_id")
    private String buildId;

    @Column(name = "type",notNull = true)
    private int type;

    //地址
    @Column(name = "build_address",notNull = true)
    private String buildAddress;

    //分支
    @Column(name = "build_order",notNull = true)
    private String buildOrder;

    //顺序
    @Column(name = "sort",notNull = true)
    private int sort;

    //别名
    @Column(name = "build_alias",notNull = true)
    private String buildAlias;


    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
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

    public String getBuildAlias() {
        return buildAlias;
    }

    public void setBuildAlias(String buildAlias) {
        this.buildAlias = buildAlias;
    }
}
