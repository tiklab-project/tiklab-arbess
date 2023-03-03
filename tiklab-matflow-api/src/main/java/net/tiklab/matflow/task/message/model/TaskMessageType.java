package net.tiklab.matflow.task.message.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

import java.util.List;

@ApiModel
@Join
@Mapper(targetAlias = "TaskMessageTypeEntity")
public class TaskMessageType {

    @ApiProperty(name = "taskId",desc = "id")
    private String taskId;

    @ApiProperty(name="taskType",desc="消息类型")
    private String taskType;

    @ApiProperty(name="typeList",desc="site:站内信  sms:短信发送  wechat:微信  mail:邮箱发送")
    private List<String> typeList;

    @ApiProperty(name="userList",desc="接收人信息")
    private List<TaskUserSendMessageType> userList;

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public List<TaskUserSendMessageType> getUserList() {
        return userList;
    }

    public void setUserList(List<TaskUserSendMessageType> userList) {
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
