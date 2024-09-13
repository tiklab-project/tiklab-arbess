package io.thoughtware.arbess.stages.entity;

import io.thoughtware.dal.jpa.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 流水线阶段实体
 */
@Entity
@Table(name="pip_stage")
public class StageEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "stage_id",notNull = true)
    private String stageId;

    //阶段名称
    @Column(name = "stage_name",notNull = true)
    private String stageName;

    //创建配置时间
    @Column(name = "create_time",notNull = true)
    private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    //流水线
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //主节点
    @Column(name = "parent_id",notNull = true)
    private String parentId;

    //阶段顺序
    @Column(name = "stage_sort",notNull = true)
    private int stageSort;

    //是否为源码
    @Column(name = "code",notNull = true)
    private String code;


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getStageSort() {
        return stageSort;
    }

    public void setStageSort(int stageSort) {
        this.stageSort = stageSort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }
}
