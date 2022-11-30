package net.tiklab.matflow.definition.entity;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_after_config")
public class PipelineAfterConfigEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "config_id")
    private String configId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "pipeline_id")
    private String pipelineId;


}
