package io.thoughtware.matflow.support.variable.model;

import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.join.annotation.Join;



import java.util.List;

/**
 * 流水线条件模型
 */

public class VariableQuery {

    //@ApiProperty(name="taskType",desc="类型 1.字符串 2.单选")
    private int taskType;

    //@ApiProperty(name="type",desc="类型 1.全局 2.项目")
    private int type;

    //@ApiProperty(name="taskId",desc="任务id")
    private String taskId;

    //@ApiProperty(name = "varKey",desc="名称")
    private String varKey;

    //@ApiProperty(name = "varValue",desc="默认值")
    private String varValue;

    //@ApiProperty(name = "varValues",desc="值")
    private String varValues;

    //@ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("varKey").get();


    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getVarKey() {
        return varKey;
    }

    public void setVarKey(String varKey) {
        this.varKey = varKey;
    }

    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }

    public String getVarValues() {
        return varValues;
    }

    public void setVarValues(String varValues) {
        this.varValues = varValues;
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
