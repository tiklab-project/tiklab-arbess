package com.tiklab.matflow.definition.entity;


import com.tiklab.dal.jpa.annotation.*;

/**
 * 流水线
 */

@Entity
@Table(name="matflow")
public class MatFlowEntity {

    //流水线id
    @Id
    @GeneratorValue
    @Column(name = "matflow_id")
    private String matflowId;

    //流水线名称
    @Column(name = "matflow_name",notNull = true)
    private String matflowName;

    //流水线创建人
    @Column(name = "user_id",notNull = true)
    private String userId;

    //流水线创建时间
    @Column(name = "matflow_create_time",notNull = true)
    private String  matflowCreateTime;

    //流水线类型
    @Column(name = "matflow_create_type")
    private int matflowType;

    //收藏状态
    @Column(name = "matflow_collect")
    private int matflowCollect;

    //运行状态
    @Column(name = "matflow_state")
    private int matflowState;

    public String getMatflowId() {
        return matflowId;
    }

    public void setMatflowId(String matflowId) {
        this.matflowId = matflowId;
    }

    public String getMatflowName() {
        return matflowName;
    }

    public void setMatflowName(String matflowName) {
        this.matflowName = matflowName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMatflowCreateTime() {
        return matflowCreateTime;
    }

    public void setMatflowCreateTime(String matflowCreateTime) {
        this.matflowCreateTime = matflowCreateTime;
    }

    public int getMatflowType() {
        return matflowType;
    }

    public void setMatflowType(int matflowType) {
        this.matflowType = matflowType;
    }

    public int getMatflowCollect() {
        return matflowCollect;
    }

    public void setMatflowCollect(int matflowCollect) {
        this.matflowCollect = matflowCollect;
    }

    public int getMatflowState() {
        return matflowState;
    }

    public void setMatflowState(int matflowState) {
        this.matflowState = matflowState;
    }
}
