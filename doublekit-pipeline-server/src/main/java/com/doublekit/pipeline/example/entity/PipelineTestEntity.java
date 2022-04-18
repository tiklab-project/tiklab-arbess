package com.doublekit.pipeline.example.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_test")
public class PipelineTestEntity {

    //gitId
    @Id
    @GeneratorValue
    @Column(name = "test_id")
    private String testId;

    @Column(name = "type",notNull = true)
    private String type;

    //地址
    @Column(name = "test_order",notNull = true)
    private String testOrder;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTestOrder() {
        return testOrder;
    }

    public void setTestOrder(String testOrder) {
        this.testOrder = testOrder;
    }
}
