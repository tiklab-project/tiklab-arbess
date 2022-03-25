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

    @Column(name = "test_type",notNull = true)
    private String testType;

    //地址
    @Column(name = "test_order",notNull = true)
    private String testOrder;



    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getTestOrder() {
        return testOrder;
    }

    public void setTestOrder(String testOrder) {
        this.testOrder = testOrder;
    }
}
