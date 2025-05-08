package io.tiklab.arbess.support.approve.model;

import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinQuery;
import io.tiklab.user.user.model.User;

import java.util.List;

@Join
@Mapper
public class Approve {

    // 审批ID
    private String id;

    // 审批名称
    private String name;

    // 创建时间
    private String createTime;

    // 执行时间
    private String execTime;

    // 创建人
    @Mappings({
            @Mapping(source = "user.id",target = "userId")
    })
    @JoinQuery(key = "id")
    private User user;

    private String corn;

    // 流水线id列表
    private List<String> pipelineIdList;

    // 审批人id
    private List<String> userIdList;

    // 用户列表
    private List<ApproveUser> userList;

    // 流水线列表
    private List<ApprovePipeline> pipelineList;

    /**
     * 审批状态
     * wait：等待审批
     * pending：评审中
     * pass：审批通过
     * reject：审批不通过
     * wait-run：审批通过-等待运行
     * run：运行中
     * cancel：取消
     * complete：完成
     */
    private String status;

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public List<String> getPipelineIdList() {
        return pipelineIdList;
    }

    public void setPipelineIdList(List<String> pipelineIdList) {
        this.pipelineIdList = pipelineIdList;
    }

    public List<ApproveUser> getUserList() {
        return userList;
    }

    public void setUserList(List<ApproveUser> userList) {
        this.userList = userList;
    }

    public List<ApprovePipeline> getPipelineList() {
        return pipelineList;
    }

    public void setPipelineList(List<ApprovePipeline> pipelineList) {
        this.pipelineList = pipelineList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}














