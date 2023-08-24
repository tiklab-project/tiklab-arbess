package io.tiklab.matflow.stages.model;


import io.tiklab.beans.annotation.Mapper;
import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.join.annotation.Join;
import io.tiklab.matflow.task.task.model.TaskInstance;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;

import java.util.List;

/**
 * 阶段运行实例模型
 */

@ApiModel
@Join
@Mapper(targetAlias = "StageInstanceEntity")
public class StageInstanceQuery {

    @ApiProperty(name = "stageName" ,desc = "阶段名称")
    private String stageName;

    @ApiProperty(name = "instanceId" ,desc = "实例id")
    private String instanceId;

    @ApiProperty(name = "stageSort" ,desc = "阶段顺序")
    private int stageSort ;

    @ApiProperty(name = "stageState" ,desc = "运行状态")
    private String stageState;

    @ApiProperty(name = "parentId" ,desc = "阶段id")
    private String parentId;


    @ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    @ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("stageSort").get();

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public int getStageSort() {
        return stageSort;
    }

    public void setStageSort(int stageSort) {
        this.stageSort = stageSort;
    }

    public String getStageState() {
        return stageState;
    }

    public void setStageState(String stageState) {
        this.stageState = stageState;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
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
