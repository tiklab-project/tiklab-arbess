package com.tiklab.matflow.execute.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_structure")
public class PipelineStructureEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "structure_id")
    private String structureId;

    @Column(name = "type",notNull = true)
    private int type;

    //地址
    @Column(name = "structure_address",notNull = true)
    private String structureAddress;

    //分支
    @Column(name = "structure_order",notNull = true)
    private String structureOrder;

    //顺序
    @Column(name = "sort",notNull = true)
    private int sort;

    //别名
    @Column(name = "structure_alias",notNull = true)
    private String structureAlias;


    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getStructureAddress() {
        return structureAddress;
    }

    public void setStructureAddress(String structureAddress) {
        this.structureAddress = structureAddress;
    }

    public String getStructureOrder() {
        return structureOrder;
    }

    public void setStructureOrder(String structureOrder) {
        this.structureOrder = structureOrder;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStructureAlias() {
        return structureAlias;
    }

    public void setStructureAlias(String structureAlias) {
        this.structureAlias = structureAlias;
    }
}
