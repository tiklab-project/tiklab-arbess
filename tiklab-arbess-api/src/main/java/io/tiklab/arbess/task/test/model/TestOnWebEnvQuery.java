package io.tiklab.arbess.task.test.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class TestOnWebEnvQuery {

    private String repositoryId;

    // @ApiProperty(name = "orderParams", desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

    // @ApiProperty(name = "pageParam", desc = "分页参数")
    private Page pageParam = new Page();


    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
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



}
