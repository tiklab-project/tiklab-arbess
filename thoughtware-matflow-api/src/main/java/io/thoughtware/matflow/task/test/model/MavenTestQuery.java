package io.thoughtware.matflow.task.test.model;

import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.join.annotation.Join;

import java.util.List;

@Join
@Mapper
public class MavenTestQuery {

    // 流水线ID
    private String pipelineId;

    private String createTime;

    private String testId;

    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


    public String getTestId() {
        return testId;
    }

    public MavenTestQuery setTestId(String testId) {
        this.testId = testId;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public MavenTestQuery setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public MavenTestQuery setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public Page getPageParam() {
        return pageParam;
    }

    public MavenTestQuery setPageParam(Page pageParam) {
        this.pageParam = pageParam;
        return this;
    }

    public List<Order> getOrderParams() {
        return orderParams;
    }

    public MavenTestQuery setOrderParams(List<Order> orderParams) {
        this.orderParams = orderParams;
        return this;
    }
}
