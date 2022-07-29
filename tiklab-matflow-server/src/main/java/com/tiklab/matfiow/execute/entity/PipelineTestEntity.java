package com.tiklab.matfiow.execute.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_test")
public class PipelineTestEntity {

    @Id
    @GeneratorValue
    @Column(name = "test_id")
    private String testId;

    @Column(name = "type",notNull = true)
    private int type;

    //地址
    @Column(name = "test_order",notNull = true)
    private String testOrder;

    //顺序
    @Column(name = "sort",notNull = true)
    private int sort;
    
    //别名
    @Column(name = "test_alias",notNull = true)
    private String testAlias;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTestOrder() {
        return testOrder;
    }

    public void setTestOrder(String testOrder) {
        this.testOrder = testOrder;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTestAlias() {
        return testAlias;
    }

    public void setTestAlias(String testAlias) {
        this.testAlias = testAlias;
    }
}
