package io.thoughtware.matflow.task.artifact.model;

import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;

import java.util.List;

public class XpackRepositoryQuery {

    // @ApiProperty(name = "orderParams", desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

    // @ApiProperty(name = "pageParam", desc = "分页参数")
    private Page pageParam = new Page();

    // @ApiProperty(name = "name", desc = "制品库名字")
    private String name;

    // @ApiProperty(name = "repositoryType", desc = "制品库类型 maven、npm")
    private String repositoryType;

    // @ApiProperty(name = "type", desc = "类型 local、remote、group")
    private String type;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepositoryType() {
        return repositoryType;
    }

    public void setRepositoryType(String repositoryType) {
        this.repositoryType = repositoryType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
