package io.tiklab.matflow.task.test.model;

import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;

import java.util.List;

public class TestOnPlanCaseInstanceBindQuery {


    // @ApiProperty(name = "caseInstanceId", desc = "历史id")
    private String caseInstanceId;

    // @ApiProperty(name = "testPlanInstanceId", desc = "testPlanInstanceId")
    private String testPlanInstanceId;


    // @ApiProperty(name = "orderParams", desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().asc("id").get();

    // @ApiProperty(name = "pageParam", desc = "分页参数")
    private Page pageParam = new Page();

    public String getCaseInstanceId() {
        return caseInstanceId;
    }

    public void setCaseInstanceId(String caseInstanceId) {
        this.caseInstanceId = caseInstanceId;
    }

    public String getTestPlanInstanceId() {
        return testPlanInstanceId;
    }

    public void setTestPlanInstanceId(String testPlanInstanceId) {
        this.testPlanInstanceId = testPlanInstanceId;
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
