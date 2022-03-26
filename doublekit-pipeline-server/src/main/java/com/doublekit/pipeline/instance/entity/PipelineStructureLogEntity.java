package com.doublekit.pipeline.instance.entity;

import com.doublekit.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_structure_log")
public class PipelineStructureLogEntity {

    //日志id
    @Id
    @GeneratorValue
    @Column(name = "structure_id")
    private String logStructureId;

    //运行时间
    @Column(name = "structure_run_time",notNull = true)
    private int structureRunTime;

    //运行状态（10 ：成功  1：失败   0 :其他）
    @Column(name = "structure_run_state",notNull = true)
    private int structureRunStatus ;

    //运行日志
    @Column(name = "structure_run_log",notNull = true)
    private String structureRunLog;


    public String getLogStructureId() {
        return logStructureId;
    }

    public void setLogStructureId(String logStructureId) {
        this.logStructureId = logStructureId;
    }

    public int getStructureRunTime() {
        return structureRunTime;
    }

    public void setStructureRunTime(int structureRunTime) {
        this.structureRunTime = structureRunTime;
    }

    public int getStructureRunStatus() {
        return structureRunStatus;
    }

    public void setStructureRunStatus(int structureRunStatus) {
        this.structureRunStatus = structureRunStatus;
    }

    public String getStructureRunLog() {
        return structureRunLog;
    }

    public void setStructureRunLog(String structureRunLog) {
        this.structureRunLog = structureRunLog;
    }
}
