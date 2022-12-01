package net.tiklab.matflow.definition.entity.task;

import net.tiklab.dal.jpa.annotation.*;

@Entity
@Table(name="pipeline_script")
public class PipelineScriptEntity {

    //id
    @Id
    @GeneratorValue
    @Column(name = "script_id")
    private String scriptId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "script_order")
    private String scriptOrder;

    @Column(name = "config_id")
    private String configId;

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getScriptOrder() {
        return scriptOrder;
    }

    public void setScriptOrder(String scriptOrder) {
        this.scriptOrder = scriptOrder;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}
