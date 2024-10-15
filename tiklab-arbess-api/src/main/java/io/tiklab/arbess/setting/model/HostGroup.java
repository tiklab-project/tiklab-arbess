package io.tiklab.arbess.setting.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.join.annotation.Join;

import java.util.List;

/**
 * 主机组
 * @author zcamy
 */
@Join
@Mapper
public class HostGroup {

    private String taskInstanceId;

    private List<AuthHostGroupDetails> authHostGroupDetailList;

    public HostGroup() {
    }

    public HostGroup(String taskInstanceId, List<AuthHostGroupDetails> authHostGroupDetailList) {
        this.taskInstanceId = taskInstanceId;
        this.authHostGroupDetailList = authHostGroupDetailList;
    }

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }

    public List<AuthHostGroupDetails> getAuthHostGroupDetailList() {
        return authHostGroupDetailList;
    }

    public void setAuthHostGroupDetailList(List<AuthHostGroupDetails> authHostGroupDetailList) {
        this.authHostGroupDetailList = authHostGroupDetailList;
    }
}
