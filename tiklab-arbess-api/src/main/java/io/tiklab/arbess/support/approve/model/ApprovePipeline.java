package io.tiklab.arbess.support.approve.model;

import io.tiklab.arbess.pipeline.definition.model.Pipeline;
import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;
import io.tiklab.toolkit.join.annotation.JoinField;


import java.util.List;

@Join
@Mapper
public class ApprovePipeline {

    private String id;

    private String createTime;

    @Mappings({
            @Mapping(source = "pipeline.id",target = "pipelineId")
    })
    @JoinField(key = "id")
    private Pipeline pipeline;

    private String status;

    private String approveId;

    private String instanceId;

    private List<ApproveUser> approveUserList;

    private String approveStatus;

    private Integer execStatus;

    public Integer getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(Integer execStatus) {
        this.execStatus = execStatus;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public List<ApproveUser> getApproveUserList() {
        return approveUserList;
    }

    public void setApproveUserList(List<ApproveUser> approveUserList) {
        this.approveUserList = approveUserList;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public ApprovePipeline() {
    }

    public ApprovePipeline(String pipelineId, String approveId) {
        this.pipeline = new Pipeline(pipelineId);
        this.status = "wait";
        this.approveId = approveId;
    }

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}














