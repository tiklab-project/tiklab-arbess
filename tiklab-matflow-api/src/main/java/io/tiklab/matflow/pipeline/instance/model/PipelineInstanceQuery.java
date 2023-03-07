package io.tiklab.matflow.pipeline.instance.model;


import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.matflow.pipeline.definition.model.Pipeline;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

/**
 * 流水线实例分页筛选模型
 */

@ApiModel
public class PipelineInstanceQuery {

    @ApiProperty(name ="pipelineId",desc = "流水线id")
    private String pipelineId;

    @ApiProperty(name ="state",desc = "状态")
    private int state;

    @ApiProperty(name ="userId",desc = "用户id")
    private String userId;

    @ApiProperty(name ="type",desc = "类型")
    private int type;

    @ApiProperty(name ="pipelineList",desc = "用户流水线")
    private List<Pipeline> pipelineList;

    @ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    @ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();

    public List<Pipeline> getPipelineList() {
        return pipelineList;
    }

    public void setPipelineList(List<Pipeline> pipelineList) {
        this.pipelineList = pipelineList;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public void setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
    }
}
