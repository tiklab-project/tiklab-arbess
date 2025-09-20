package io.tiklab.arbess.task.build.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

/**
 * @pi.model:io.tiklab.arbess.task.build.model.TaskBuildProductQuery
 * @desc:构建制品查询模型
 */

public class TaskBuildProductQuery {

    @ApiProperty(name = "instanceId",desc = "实例id")
    private String instanceId;

    @ApiProperty(name = "pipelineId",desc = "流水线id")
    private String pipelineId;

    @ApiProperty(name = "agentAddress",desc = "agent地址")
    private String agentAddress;

    @ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    @ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("id").get();

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getAgentAddress() {
        return agentAddress;
    }

    public void setAgentAddress(String agentAddress) {
        this.agentAddress = agentAddress;
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

    public String getInstanceId() {
        return instanceId;
    }

    public TaskBuildProductQuery setInstanceId(String instanceId) {
        this.instanceId = instanceId;
        return this;
    }
}
