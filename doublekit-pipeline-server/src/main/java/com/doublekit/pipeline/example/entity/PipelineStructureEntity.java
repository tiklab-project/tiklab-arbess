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

    @Column(name = "structure_type",notNull = true)
    private String structureType;

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

    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(String structureType) {
        this.structureType = structureType;
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
