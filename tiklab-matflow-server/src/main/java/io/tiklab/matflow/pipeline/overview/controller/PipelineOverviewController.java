package io.tiklab.matflow.pipeline.overview.controller;

import io.tiklab.matflow.pipeline.overview.model.PipelineOverview;
import io.tiklab.matflow.pipeline.overview.service.PipelineOverviewService;
import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @pi.protocol: http
 * @pi.groupName: 流水线统计服务控制器
 */
@RestController
@RequestMapping("/overview")
public class PipelineOverviewController {

    @Autowired
    PipelineOverviewService overviewService;

    /**
     * @pi.name:查询流水线最近执行状态
     * @pi.path:/overview/pipelineCensus
     * @pi.method:post
     * @pi.request-type: formdata
     * @pi.param: name=pipelineId;dataType=string;value=pipelineId;
     */
    @RequestMapping(path="/pipelineCensus",method = RequestMethod.POST)
    public Result<PipelineOverview> pipelineCensus(@NotNull String pipelineId){

        PipelineOverview buildStatus = overviewService.pipelineOverview(pipelineId);

        return Result.ok(buildStatus);
    }



}
