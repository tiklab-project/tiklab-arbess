package net.tiklab.pipeline.definition.model;


import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
public class PipelineMassage {

    @ApiProperty(name="pipelineId",desc="流水线id")
    private String pipelineId;

    @ApiProperty(name="pipelineCollect",desc="收藏状态",required = true)
    private int pipelineCollect;

    @ApiProperty(name="logCreateTime",desc="最近构建状态")
    private int buildStatus;

    @ApiProperty(name="pipelineName",desc="流水线名称")
    private String pipelineName;

    @ApiProperty(name="listBuildTime",desc="最近成功时间")
    private String lastBuildTime;

    @ApiProperty(name="listSuccessStatus",desc="最近构建时间")
    private String lastSuccessTime;

    @ApiProperty(name="pipelineCollect",desc="运行状态")
    private int pipelineState;

    @ApiProperty(name="createTime",desc="创建时间")
    private String createTime;

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public int getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(int buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getPipelineName() {
        return pipelineName;
    }

    public void setPipelineName(String pipelineName) {
        this.pipelineName = pipelineName;
    }

    public String getLastBuildTime() {
        return lastBuildTime;
    }

    public void setLastBuildTime(String lastBuildTime) {
        this.lastBuildTime = lastBuildTime;
    }

    public String getLastSuccessTime() {
        return lastSuccessTime;
    }

    public void setLastSuccessTime(String lastSuccessTime) {
        this.lastSuccessTime = lastSuccessTime;
    }

    public int getPipelineCollect() {
        return pipelineCollect;
    }

    public void setPipelineCollect(int pipelineCollect) {
        this.pipelineCollect = pipelineCollect;
    }

    public int getPipelineState() {
        return pipelineState;
    }

    public void setPipelineState(int pipelineState) {
        this.pipelineState = pipelineState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
