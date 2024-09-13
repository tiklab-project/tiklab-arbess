package io.thoughtware.arbess.setting.model;

import io.thoughtware.toolkit.beans.annotation.Mapper;
import io.thoughtware.toolkit.beans.annotation.Mapping;
import io.thoughtware.toolkit.beans.annotation.Mappings;
import io.thoughtware.toolkit.join.annotation.Join;
import io.thoughtware.toolkit.join.annotation.JoinQuery;
import io.thoughtware.user.user.model.User;

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
