package io.tiklab.arbess.task.tool.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;
import java.util.List;

@Join
@Mapper
public class TaskCheckPoint {


    private String taskId;

    // 等待时间
    private Integer wailTime;

    //审批id
    private String inspectIds;

    // 审批人
    private List<String> inspectIdList;

    //顺序
    private int sort;

    private String instanceId;

    public List<String> getInspectIdList() {
        return inspectIdList;
    }

    public void setInspectIdList(List<String> inspectIdList) {
        this.inspectIdList = inspectIdList;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getWailTime() {
        return wailTime;
    }

    public void setWailTime(Integer wailTime) {
        this.wailTime = wailTime;
    }

    public String getInspectIds() {
        return inspectIds;
    }

    public void setInspectIds(String inspectIds) {
        this.inspectIds = inspectIds;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
