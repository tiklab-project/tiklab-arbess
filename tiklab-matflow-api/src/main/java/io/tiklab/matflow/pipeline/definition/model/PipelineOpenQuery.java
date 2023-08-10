package io.tiklab.matflow.pipeline.definition.model;


import io.tiklab.beans.annotation.Mapper;
import io.tiklab.beans.annotation.Mapping;
import io.tiklab.beans.annotation.Mappings;
import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.join.annotation.Join;
import io.tiklab.join.annotation.JoinQuery;
import io.tiklab.matflow.pipeline.overview.model.PipelineOverview;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

/**
 * @pi.model:io.tiklab.matflow.pipeline.definition.model.PipelineOpen
 * @desc:流水线最近打开模型
 */

@Join
@Mapper
public class PipelineOpenQuery {

    /**
     * @pi.name:openId
     * @pi.dataType:string
     * @pi.desc:id
     * @pi.value:openId
     */
    private String openId;

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


    private Page pageParam= new Page();

    private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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
