package io.tiklab.arbess.support.message.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.tiklab.core.BaseModel;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Join
@Mapper
public class TaskMessage extends BaseModel {

    private String id;

    // 名称
    private String name;

    // 1:全局 2:任务
    private Integer type;

    // 流水线ID
    private String pipelineId;

    // 任务id
    private String taskId;

    // 通知类型 1:全部 2:仅成功 3:仅失败
    private Integer noticeType;

    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss",timezone = "GMT+8")
    private Timestamp createTime;

    // 发送方式
    private List<TaskMessageType> typeList = new ArrayList<>();

    // 接收人
    private List<TaskMessageUser> userList = new ArrayList<>();


    public String getId() {
        return id;
    }

    public TaskMessage setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TaskMessage setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public TaskMessage setType(Integer type) {
        this.type = type;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public TaskMessage setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskMessage setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public TaskMessage setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public TaskMessage setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public List<TaskMessageType> getTypeList() {
        return typeList;
    }

    public TaskMessage setTypeList(List<TaskMessageType> typeList) {
        this.typeList = typeList;
        return this;
    }

    public List<TaskMessageUser> getUserList() {
        return userList;
    }

    public TaskMessage setUserList(List<TaskMessageUser> userList) {
        this.userList = userList;
        return this;
    }
}
