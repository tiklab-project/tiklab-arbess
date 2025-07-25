package io.tiklab.arbess.support.variable.entity;

import io.tiklab.dal.jpa.annotation.*;

import java.sql.Timestamp;

@Entity
@Table(name="pip_variable")
public class SystemVariableEntity {

    //流水线配置id
    @Id
    @GeneratorValue(length = 12)
    @Column(name = "id",notNull = true)
    private String id;

    //创建配置时间
    @Column(name = "create_time")
    private Timestamp createTime ;

    //名称
    @Column(name = "var_key")
    private String varKey;

    //值
    @Column(name = "var_value")
    private String varValue;

    @Column(name = "type")
    private int type;

    @Column(name = "description")
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVarKey() {
        return varKey;
    }

    public void setVarKey(String varKey) {
        this.varKey = varKey;
    }

    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }

}






























