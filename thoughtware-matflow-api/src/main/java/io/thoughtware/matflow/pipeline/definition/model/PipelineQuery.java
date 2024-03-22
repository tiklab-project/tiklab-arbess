package io.thoughtware.matflow.pipeline.definition.model;

import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;


import java.util.List;

/**
 * @pi.model:io.thoughtware.matflow.pipeline.definition.model.PipelineQuery
 * @desc: 流水线查询模型
 */

public class PipelineQuery {

    //@ApiProperty(name ="userId",desc = "用户")
    private String userId;

    //@ApiProperty(name ="pipelineName",desc = "流水线名称")
    private String pipelineName;


    /**
     *  流水线状态 1.运行中 2.未运行,3.等待执行
     */
    private Integer pipelineState;

    /**
     *  流水线类型 1.多任务 2.多阶段
     */
    private Integer pipelineType;

    //@ApiProperty(name ="pipelinePower",desc = "流水线权限")
    private Integer pipelinePower;

    //@ApiProperty(name ="pipelineFollow",desc = "收藏, 1.收藏 0.未收藏")
    private Integer pipelineFollow;

    //@ApiProperty(name ="idString",desc = "流水线id数组")
    private String[] idString;

    private boolean eqName;

    // 环境
    private String envId;

    // 分组
    private String groupId;


    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    // private List<Order> orderParams = OrderBuilders.instance().desc("createTime").get();
    private List<Order> orderParams = OrderBuilders.instance().desc("name").get();


    public String getEnvId() {
        return envId;
    }

    public PipelineQuery setEnvId(String envId) {
        this.envId = envId;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public PipelineQuery setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public Integer getPipelineState() {
        return pipelineState;
    }

    public void setPipelineState(Integer pipelineState) {
        this.pipelineState = pipelineState;
    }

    public Integer getPipelineType() {
        return pipelineType;
    }

    public void setPipelineType(Integer pipelineType) {
        this.pipelineType = pipelineType;
    }

    public Integer getPipelinePower() {
        return pipelinePower;
    }

    public void setPipelinePower(Integer pipelinePower) {
        this.pipelinePower = pipelinePower;
    }

    public Integer getPipelineFollow() {
        return pipelineFollow;
    }

    public void setPipelineFollow(Integer pipelineFollow) {
        this.pipelineFollow = pipelineFollow;
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

    public String[] getIdString() {
        return idString;
    }

    public void setIdString(String[] idString) {
        this.idString = idString;
    }

    public boolean isEqName() {
        return eqName;
    }

    public void setEqName(boolean eqName) {
        this.eqName = eqName;
    }
}
