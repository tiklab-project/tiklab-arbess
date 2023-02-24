package net.tiklab.matflow.pipeline.definition.entity;

import net.tiklab.dal.jpa.annotation.*;

/**
 * 流水线
 */
@Entity
@Table(name="pip_pipeline")
public class PipelineEntity {

    //流水线id
    @Id
    @GeneratorValue
    @Column(name = "id")
    private String id;

    //流水线名称
    @Column(name = "name")
    private String name;

    //流水线创建人
    @Column(name = "user_id")
    private String userId;

    //流水线创建时间
    @Column(name = "create_time")
    private String  createTime;

    //流水线类型
    @Column(name = "type")
    private int type;

    //运行状态
    @Column(name = "state")
    private int state;

    //权限
    @Column(name = "power")
    private int power;

    //颜色
    @Column(name="color")
    private int color;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
