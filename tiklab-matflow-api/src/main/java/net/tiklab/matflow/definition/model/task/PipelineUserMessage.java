package net.tiklab.matflow.definition.model.task;

import net.tiklab.user.user.model.User;

public class PipelineUserMessage {

    //用户id
    private User user;

    //类型（1.全部 2.仅成功 3.仅失败）
    private int type;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
