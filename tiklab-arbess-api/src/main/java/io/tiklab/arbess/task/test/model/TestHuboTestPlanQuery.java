package io.tiklab.arbess.task.test.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class TestHuboTestPlanQuery {

    // @ApiProperty(name = "repositoryId", desc = "空间id")
    private String workspaceId;

    // @ApiProperty(name = "name", desc = "测试计划名字，模糊匹配")
    private String name;

    // @ApiProperty(name = "state", desc = "测试计划名字，模糊匹配")
    private Integer state;

    private String authId;

    // @ApiProperty(name = "pageParam", desc = "分页参数")
    private Page pageParam = new Page();


    // @ApiProperty(name = "orderParams", desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().asc("id").get();


    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public void setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public void setPageParam(Page pageParam) {
        this.pageParam = pageParam;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
