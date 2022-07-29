package com.tiklab.matflow.instance.entity;


import com.tiklab.dal.jpa.annotation.*;

/**
 * 关注表
 */

@Entity
@Table(name="matflow_follow")
public class MatFlowFollowEntity {

    @Id
    @GeneratorValue
    @Column(name = "id")
    private String id;

    @Column(name = "matflow_id")
    private String matflowId;

    @Column(name = "user_id")
    private String userId ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatflowId() {
        return matflowId;
    }

    public void setMatflowId(String matflowId) {
        this.matflowId = matflowId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
