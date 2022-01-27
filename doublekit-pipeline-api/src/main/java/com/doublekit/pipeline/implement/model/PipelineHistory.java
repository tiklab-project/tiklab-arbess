package com.doublekit.pipeline.implement.model;

import com.doublekit.apibox.annotation.ApiModel;
import com.doublekit.apibox.annotation.ApiProperty;
import com.doublekit.beans.annotation.Mapping;
import com.doublekit.beans.annotation.Mappings;
import com.doublekit.join.annotation.Join;
import com.doublekit.join.annotation.JoinQuery;
import com.doublekit.pipeline.definition.model.Pipeline;


@ApiModel
@Join
public class PipelineHistory {

    //构建历史id
    @ApiProperty(name="historyId",desc="构建历史id")
    private String historyId;

    //创建构建时间
    @ApiProperty(name="pipelineName",desc="创建构建时间",required = true)
    private String historyCreateTime;

    //构建执行时间
    @ApiProperty(name="pipelineName",desc="构建执行时间",required = true)
    private String historyImplementTime;

    //构建方式
    @ApiProperty(name="pipelineName",desc="构建方式",required = true)
    private int historyWay;

    //运行状态
    @ApiProperty(name="pipelineName",desc="运行状态",required = true)
    private int historyRunState;

    //上次运行时间
    @ApiProperty(name="pipelineName",desc="上次运行时间",required = true)
    private String historyLastTime;

    //上次成功时间
    @ApiProperty(name="pipelineName",desc="上次成功时间",required = true)
    private String historyLastSuccessTime;

    //分支
    @ApiProperty(name="pipelineName",desc="分支",required = true)
    private String historyBranch;


    @ApiProperty(name="pipeline",desc="流水线",eg="@selectOne")
    @Mappings({
            @Mapping(source = "pipeline.id",target = "pipelineId")
    })
    @JoinQuery(key = "id")
    private Pipeline pipeline;

    //凭证id
    @ApiProperty(name="pipelineName",desc="凭证id",required = true)
    private int proofId;

}
