package com.tiklab.matflow.setting.proof.model;


import com.tiklab.postin.annotation.ApiModel;
import com.tiklab.postin.annotation.ApiProperty;

@ApiModel
public class ProofQuery {

    @ApiProperty(name="matFlowId",desc="流水线id")
    String matFlowId;

    @ApiProperty(name="type",desc="类型")
    int type;

    @ApiProperty(name="userId",desc="用户id")
    String userId;


    public String getMatFlowId() {
        return matFlowId;
    }

    public void setMatFlowId(String matFlowId) {
        this.matFlowId = matFlowId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
