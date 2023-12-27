package io.thoughtware.matflow.task.message.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.join.annotation.Join;



import java.util.List;
/**
 * 任务消息类型模型
 */
//@ApiModel
@Join
@Mapper
public class TaskMessageType {

    //@ApiProperty(name = "id",desc = "id")
    private String id;

    //@ApiProperty(name = "taskId",desc = "任务id")
    private String taskId;

    //@ApiProperty(name="taskType",desc="消息类型")
    private String taskType;

    //@ApiProperty(name="typeList",desc="site:站内信  sms:短信发送  wechat:微信  mail:邮箱发送")
    private List<String> typeList;

    //@ApiProperty(name="userList",desc="接收人信息")
    private List<TaskMessageUser> userList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<TaskMessageUser> getUserList() {
        return userList;
    }

    public void setUserList(List<TaskMessageUser> userList) {
        this.userList = userList;
    }

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


}
