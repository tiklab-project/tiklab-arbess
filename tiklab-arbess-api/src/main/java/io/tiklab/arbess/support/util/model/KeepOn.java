package io.tiklab.arbess.support.util.model;

import io.tiklab.user.user.model.User;

public class KeepOn {

    // 操作人
    private User user;

    // 状态：1:继续 2:停止
    private Integer status;

    // 类型：true:开始等待 false:停止等待
    private Boolean wailing;

    // 实例ID
    private String taskInstanceId;

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public KeepOn() {
    }

    public KeepOn(Boolean wailing) {
        this.wailing = wailing;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getWailing() {
        return wailing;
    }

    public void setWailing(Boolean wailing) {
        this.wailing = wailing;
    }
}
