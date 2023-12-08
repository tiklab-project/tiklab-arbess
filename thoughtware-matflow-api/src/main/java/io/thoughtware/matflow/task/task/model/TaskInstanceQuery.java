package io.thoughtware.matflow.task.task.model;


import io.thoughtware.beans.annotation.Mapper;
import io.thoughtware.core.order.Order;
import io.thoughtware.core.order.OrderBuilders;
import io.thoughtware.core.page.Page;
import io.thoughtware.join.annotation.Join;



import java.util.List;

/**
 * 任务实例模型
 */

//@ApiModel
@Join
@Mapper
public class TaskInstanceQuery {

    //@ApiProperty(name = "instanceId",desc = "历史Id")
    private String instanceId;

    //@ApiProperty(name = "taskType",desc = "运行类型")
    private String taskType ;

    //@ApiProperty(name = "runState",desc = "运行状态 error.失败 success.成功 halt.停止 wait.等待")
    private String runState;

    //@ApiProperty(name="stageId",desc="阶段id")
    private String stagesId;

    //@ApiProperty(name="postprocessId",desc="后置任务id")
    private String postprocessId;


    //@ApiProperty(name ="pageParam",desc = "分页参数")
    private Page pageParam= new Page();

    //@ApiProperty(name ="orderParams",desc = "排序参数")
    private List<Order> orderParams = OrderBuilders.instance().desc("taskType").get();


    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getRunState() {
        return runState;
    }

    public void setRunState(String runState) {
        this.runState = runState;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }

    public String getPostprocessId() {
        return postprocessId;
    }

    public void setPostprocessId(String postprocessId) {
        this.postprocessId = postprocessId;
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
