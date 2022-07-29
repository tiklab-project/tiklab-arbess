package com.tiklab.matflow.instance.model;


import com.tiklab.beans.annotation.Mapper;
import com.tiklab.beans.annotation.Mapping;
import com.tiklab.beans.annotation.Mappings;
import com.tiklab.join.annotation.Join;
import com.tiklab.join.annotation.JoinQuery;
import com.tiklab.matflow.definition.model.MatFlow;
import com.tiklab.postlink.annotation.ApiModel;
import com.tiklab.postlink.annotation.ApiProperty;

/**
 * 流水线最近打开记录
 */

@ApiModel
@Join
@Mapper(targetAlias = "MatFlowOpenEntity")
public class MatFlowOpen {

    @ApiProperty(name="id",desc="日志id")
    private String id;

    @ApiProperty(name="userId",desc="用户",eg="@selectOne")
    private String userId;

    //流水线
    @ApiProperty(name="matFlow",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "matFlow.matflowId",target = "matflowId")
    })
    @JoinQuery(key = "matflowId")
    private MatFlow matFlow;

    @ApiProperty(name="number",desc="数量")
    private int number;

    private String matFlowName;

    private String matFlowId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public MatFlow getMatFlow() {
        return matFlow;
    }

    public void setMatFlow(MatFlow matFlow) {
        this.matFlow = matFlow;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getMatFlowName() {
        return matFlowName;
    }

    public void setMatFlowName(String matFlowName) {
        this.matFlowName = matFlowName;
    }

    public String getMatFlowId() {
        return matFlowId;
    }

    public void setMatFlowId(String matFlowId) {
        this.matFlowId = matFlowId;
    }
}
