package net.tiklab.matflow.stages.entity;

import net.tiklab.dal.jpa.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 流水线阶段实体
 */
@Entity
@Table(name="pip_stages")
public class StagesEntity {

    @Id
    @GeneratorValue
    @Column(name = "stages_id",notNull = true)
    private String stagesId;

    //阶段名称
    @Column(name = "stages_name",notNull = true)
    private String stagesName;

    //创建配置时间
    @Column(name = "create_time",notNull = true)
    private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    //流水线
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //主节点
    @Column(name = "stage_id",notNull = true)
    private String stageId;

    //阶段顺序
    @Column(name = "stages_sort",notNull = true)
    private int stagesSort;

    //是否为源码
    @Column(name = "code",notNull = true)
    private String code;


    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }

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

    public String getStagesName() {
        return stagesName;
    }

    public void setStagesName(String stagesName) {
        this.stagesName = stagesName;
    }

    public int getStagesSort() {
        return stagesSort;
    }

    public void setStagesSort(int stagesSort) {
        this.stagesSort = stagesSort;
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
