package com.doublekit.pipeline.example.entity;

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
    private String type;

    //地址
    @Column(name = "structure_address",notNull = true)
    private String structureAddress;

    //分支
    @Column(name = "structure_order",notNull = true)
    private String structureOrder;



    public String getStructureId() {
        return structureId;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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
}
