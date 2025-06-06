package io.tiklab.arbess.support.message.entity;

import io.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

@Entity
@Table(name="pip_message")
public class TaskMessageEntity {

    @Id
    // @GeneratorValue(length = 12)
    @Column(name = "id" ,notNull = true)
    private String id;

    @Column(name = "name")
    private String name;

    // all 全局 task 任务
    @Column(name = "type")
    private Integer type;

    @Column(name = "pipeline_id")
    private String pipelineId;

    @Column(name = "notice_type")
    private Integer noticeType;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "create_time")
    private Timestamp createTime;


    public Integer getType() {
        return type;
    }

    public TaskMessageEntity setType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getNoticeType() {
        return noticeType;
    }

    public TaskMessageEntity setNoticeType(Integer noticeType) {
        this.noticeType = noticeType;
        return this;
    }

    public String getId() {
        return id;
    }

    public TaskMessageEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TaskMessageEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public TaskMessageEntity setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
        return this;
    }

    public String getTaskId() {
        return taskId;
    }

    public TaskMessageEntity setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public TaskMessageEntity setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }
}
