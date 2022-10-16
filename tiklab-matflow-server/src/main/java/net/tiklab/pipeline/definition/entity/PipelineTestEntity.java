package net.tiklab.pipeline.definition.entity;


import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_test")
public class PipelineTestEntity {

    @Id
    @GeneratorValue
    @Column(name = "test_id")
    private String testId;

    //地址
    @Column(name = "test_order",notNull = true)
    private String testOrder;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestOrder() {
        return testOrder;
    }

    public void setTestOrder(String testOrder) {
        this.testOrder = testOrder;
    }

}
