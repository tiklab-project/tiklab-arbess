package net.tiklab.matflow.task.message.model;

import java.util.List;

public class TaskMessage {

    //消息类型（site:站内信  sms:短信发送  wechat:微信  mail:邮箱发送）
    private List<String> typeList;

    //接收人
    private List<TaskUserSendMessageType> userList;


    private int sort;

    private String configId;

    private int type;

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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}
