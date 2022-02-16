package com.doublekit.pipeline.definition.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.join.annotation.Join;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApiModel
@Join
public class Pipeline {

    //流水线id
    @ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    //流水线名称
    @ApiProperty(name="pipelineName",desc="流水线名称",required = true)
    private String pipelineName;

    //流水线创建人
    @ApiProperty(name="pipelineCreateUser",desc="流水线创建人",required = true)
    private String pipelineCreateUser;

    //流水线创建时间
    @ApiProperty(name="pipelineCreateTime",desc="流水线创建时间",required = true)
    private String pipelineCreateTime;

    //流水线类型
    @ApiProperty(name="pipelineType",desc="流水线类型",required = true)
    private int pipelineType;


    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public String getPipelineCreateUser() {
        return pipelineCreateUser;
    }

    public void setPipelineCreateUser(String pipelineCreateUser) {
        this.pipelineCreateUser = pipelineCreateUser;
    }

    public String getPipelineCreateTime() {
        return pipelineCreateTime;
    }

    public void setPipelineCreateTime(String pipelineCreateTime) {
        this.pipelineCreateTime = pipelineCreateTime;
    }

    public int getPipelineType() {
        return pipelineType;
    }

    public void setPipelineType(int pipelineType) {
        this.pipelineType = pipelineType;
    }
}
