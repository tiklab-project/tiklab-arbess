package io.tiklab.matflow.task.task.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.core.order.Order;
import io.tiklab.core.order.OrderBuilders;
import io.tiklab.core.page.Page;
import io.tiklab.join.annotation.Join;



import java.util.List;

/**
 * 流水线配置顺序模型
 */

//@ApiModel
public class TasksQuery {

    //@ApiProperty(name="taskId",desc="配置id")
    private String taskId;

    //@ApiProperty(name="taskType",desc= "类型1-10:源码,10-20:测试,20-30:构建,30-40:部署,40-50:代码扫描,50-60:推送制品")
    private String taskType;

    //@ApiProperty(name="pipeline",desc="流水线id",eg="@selectOne")
    private String pipelineId;

    //@ApiProperty(name="postprocessId",desc="后置处理id",eg="@selectOne")
    private String postprocessId;;

    //@ApiProperty(name="stageId",desc="阶段",eg="@selectOne")
    private String stageId;

    //@ApiProperty(name="taskSort",desc="阶段",eg="@selectOne")
    private String taskSort;

    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("taskSort").get();


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getPostprocessId() {
        return postprocessId;
    }

    public void setPostprocessId(String postprocessId) {
        this.postprocessId = postprocessId;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
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

    public String getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(String taskSort) {
        this.taskSort = taskSort;
    }
}
