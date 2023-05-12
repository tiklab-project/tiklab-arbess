package io.tiklab.matflow.support.postprocess.entity;

import io.tiklab.dal.jpa.annotation.*;

/**
 * 任务实例实体
 */
@Entity
@Table(name="pip_postprocess_instance")
public class PostprocessInstanceEntity {

    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id")
    private String id;

    //实例id
    @Column(name = "instance_id")
    private String instanceId;

    //任务实例id
    @Column(name = "taskInstance_id")
    private String taskInstanceId;

    //日志地址
    @Column(name = "post_address")
    private String postAddress;

    //运行时间
    @Column(name = "post_time")
    private Integer postTime;

    //运行状态
    @Column(name = "post_state")
    private String postState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getTaskInstanceId() {
        return taskInstanceId;
    }

    public void setTaskInstanceId(String taskInstanceId) {
        this.taskInstanceId = taskInstanceId;
    }


    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public Integer getPostTime() {
        return postTime;
    }

    public void setPostTime(Integer postTime) {
        this.postTime = postTime;
    }

    public String getPostState() {
        return postState;
    }

    public void setPostState(String postState) {
        this.postState = postState;
    }
}
