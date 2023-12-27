package io.thoughtware.matflow.support.postprocess.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.toolkit.join.annotation.Join;


import java.util.List;

/**
 * 流水线后置处理模型
 */
//@ApiModel
public class PostprocessQuery {


    //@ApiProperty(name="taskId",desc="任务id")
    private String taskId;

    //@ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;


    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("taskId").get();



    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
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
