package com.tiklab.matfiow.setting.proof.model;


import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;

@ApiModel
public class ProofQuery {

    @ApiProperty(name="pipelineId",desc="流水线id")
    String pipelineId;

    @ApiProperty(name="type",desc="类型")
    int type;

    @ApiProperty(name="userId",desc="用户id")
    String userId;


    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
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
