package io.tiklab.arbess.pipeline.definition.model;


import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.List;

/**
 * @pi.model:io.tiklab.matflow.pipeline.definition.model.PipelineOpen
 * @desc:流水线最近打开模型
 */

@Join
@Mapper
public class PipelineOpenQuery {

    /**
     * @pi.name:userId
     * @pi.dataType:string
     * @pi.desc:用户id
     * @pi.value:userId
     */
    private String userId;


    /**
     * @pi.model:pipeline
     * @pi.desc:流水线id
     */
    private String pipelineId;

    // 流水线ID列表
    private String[] pipelineIds;


    private Page pageParam= new Page();

    private List<Order> orderParams = OrderBuilders.instance().desc("updateTime").get();

    public String[] getPipelineIds() {
        return pipelineIds;
    }

    public void setPipelineIds(String[] pipelineIds) {
        this.pipelineIds = pipelineIds;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
