package io.tiklab.arbess.pipeline.definition.model;


import io.tiklab.toolkit.beans.annotation.Mapper;
import io.tiklab.toolkit.beans.annotation.Mapping;
import io.tiklab.toolkit.beans.annotation.Mappings;
import io.tiklab.toolkit.join.annotation.Join;

import io.tiklab.arbess.pipeline.overview.model.PipelineOverview;
import io.tiklab.toolkit.join.annotation.JoinField;


/**
 * @pi.model:io.tiklab.matflow.pipeline.definition.model.PipelineOpen
 * @desc:流水线最近打开模型
 */

@Join
@Mapper
public class PipelineOpen {

    /**
     * @pi.name:openId
     * @pi.dataType:string
     * @pi.desc:id
     * @pi.value:openId
     */
    private String openId;

    /**
     * @pi.name:userId
     * @pi.dataType:string
     * @pi.desc:用户id
     * @pi.value:userId
     */
    private String userId;


    /**
     * @pi.model:pipeline
     * @pi.desc:流水线id
     */
    @Mappings({
            @Mapping(source = "pipeline.id",target = "pipelineId")
    })
    @JoinField(key = "id")
    private Pipeline pipeline;

    /**
     * @pi.name:number
     * @pi.dataType:Integer
     * @pi.desc:数量
     * @pi.value:2
     */
    private int number;

    /**
     * @pi.name:createTime
     * @pi.dataType:string
     * @pi.desc:创建时间
     * @pi.value:createTime
     */
    private String createTime;

    /**
     * @pi.model:pipelineOverview
     * @pi.desc:流水线执行统计信息
     */
    private PipelineOverview pipelineOverview;


    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Pipeline getPipeline() {
        return pipeline;
    }

    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public PipelineOverview getPipelineExecState() {
        return pipelineOverview;
    }

    public void setPipelineExecState(PipelineOverview pipelineOverview) {
        this.pipelineOverview = pipelineOverview;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
