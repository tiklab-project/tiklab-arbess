package net.tiklab.matflow.definition.entity;

import net.tiklab.dal.jpa.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="pip_pipeline_stages")
public class PipelineStagesEntity {

    //流水线配置id
    @Id
    @GeneratorValue
    @Column(name = "stages_id",notNull = true)
    private String stagesId;

    @Column(name = "name",notNull = true)
    private String name;

    //创建配置时间
    @Column(name = "create_time",notNull = true)
    private String createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    //流水线
    @Column(name = "pipeline_id",notNull = true)
    private String pipelineId;

    //顺序
    @Column(name = "task_sort",notNull = true)
    private int taskSort;

    //阶段
    @Column(name = "task_stage",notNull = true)
    private int taskStage;

    @Column(name = "main_stage",notNull = true)
    private String mainStage;

    @Column(name = "code",notNull = true)
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public int getTaskSort() {
        return taskSort;
    }

    public void setTaskSort(int taskSort) {
        this.taskSort = taskSort;
    }

    public int getTaskStage() {
        return taskStage;
    }

    public void setTaskStage(int taskStage) {
        this.taskStage = taskStage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMainStage() {
        return mainStage;
    }

    public void setMainStage(String mainStage) {
        this.mainStage = mainStage;
    }
}
