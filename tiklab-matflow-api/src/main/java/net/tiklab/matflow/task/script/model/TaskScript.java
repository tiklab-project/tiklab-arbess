package net.tiklab.matflow.task.script.model;

import net.tiklab.beans.annotation.Mapper;
import net.tiklab.join.annotation.Join;
import net.tiklab.postin.annotation.ApiModel;
import net.tiklab.postin.annotation.ApiProperty;

@ApiModel
@Join
@Mapper(targetAlias = "TaskScriptEntity")
public class TaskScript {

    @ApiProperty(name = "scriptId",desc = "id")
    private String scriptId;

    @ApiProperty(name = "name",desc = "名称")
    private String name;

    @ApiProperty(name = "type",desc = "类型 71:bat脚本 72:shell脚本")
    private int type;

    @ApiProperty(name = "scriptOrder",desc = "命令")
    private String scriptOrder;

    private int sort;

    @ApiProperty(name = "configId",desc = "命令")
    private String configId;

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

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
