package com.doublekit.pipeline.definition.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.common.BaseModel;
import com.doublekit.dal.jpa.criteria.annotation.CriteriaQuery;
import com.doublekit.dal.jpa.criteria.annotation.QueryField;
import com.doublekit.dal.jpa.criteria.annotation.QueryTypeEnum;

@ApiModel
@CriteriaQuery(entityAlias = "PipelineConfigureEntity")
public class PipelineConfigureQuery extends BaseModel {

    @ApiProperty(name ="pipelineName",desc = "流水线姓名")
    @QueryField(type = QueryTypeEnum.equal)
    private String pipelineName;

    @ApiProperty(name ="configureCreateTime",desc = "创建配置时间")
    @QueryField(type = QueryTypeEnum.or)
    private String configureCreateTime;

    @ApiProperty(name ="historyRunState",desc = "构建状态")
    @QueryField(type = QueryTypeEnum.or)
    private String historyRunState;

}
