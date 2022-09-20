package net.tiklab.matflow.definition.model;


import net.tiklab.beans.annotation.Mapper;
import net.tiklab.beans.annotation.Mapping;
import net.tiklab.beans.annotation.Mappings;
import net.tiklab.join.annotation.Join;
import net.tiklab.join.annotation.JoinQuery;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "MatFlowConfigureEntity")
public class MatFlowConfigure {

    //流水线配置id
    @ApiProperty(name="id",desc="配置id")
    private String configureId;

    //创建配置时间
    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    //流水线
    @ApiProperty(name="matFlow",desc="流水线id",eg="@selectOne")
    @Mappings({
            @Mapping(source = "matFlow.matflowId",target = "matflowId")
    })
    @JoinQuery(key = "matflowId")
    private MatFlow matFlow;

    //源码
    @ApiProperty(name = "taskId",desc = "源码")
    private String taskId;

    //源码类型
    @ApiProperty(name = "taskType",desc = "源码类型")
    private int taskType;

    //顺序
    @ApiProperty(name = "taskSort",desc = "顺序")
    private int taskSort;

    //别名
    @ApiProperty(name = "taskAlias",desc = "别名")
    private String taskAlias;

    @ApiProperty(name = "view",desc = "视图")
    private int view;

    @ApiProperty(name = "userId",desc = "用户Id")
    private String userId;


    public String getConfigureId() {
        return configureId;
    }

    public void setConfigureId(String configureId) {
        this.configureId = configureId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public MatFlow getMatFlow() {
        return matFlow;
    }

    public void setMatFlow(MatFlow matFlow) {
        this.matFlow = matFlow;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public String getTaskAlias() {
        return taskAlias;
    }

    public void setTaskAlias(String taskAlias) {
        this.taskAlias = taskAlias;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
