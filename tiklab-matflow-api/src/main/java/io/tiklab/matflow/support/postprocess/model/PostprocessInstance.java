package io.tiklab.matflow.support.postprocess.model;

import io.tiklab.beans.annotation.Mapper;
import io.tiklab.join.annotation.Join;
import io.tiklab.postin.annotation.ApiModel;
import io.tiklab.postin.annotation.ApiProperty;
/**
 * 流水线后置处理实例
 */
@ApiModel
@Join
@Mapper(targetAlias = "PostprocessInstanceEntity")
public class PostprocessInstance {

    @ApiProperty(name = "postprocessId",desc="id")
    private String processId;


    @ApiProperty(name = "instanceId",desc="实例id")
    private String instanceId;


    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
