package net.tiklab.matflow.pipeline.overview.controller;

import net.tiklab.core.Result;
import net.tiklab.matflow.pipeline.overview.model.PipelineOverview;
import net.tiklab.matflow.pipeline.overview.service.PipelineOverviewService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 流水线统计服务控制器
 */
@RestController
@RequestMapping("/overview")
@Api(name = "PipelineOverviewController",desc = "流水线概况")
public class PipelineOverviewController {

    @Autowired
    PipelineOverviewService overviewService;

    @RequestMapping(path="/pipelineCensus",method = RequestMethod.POST)
    @ApiMethod(name = "pipelineCensus",desc = "查询流水线最近状态")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineOverview> pipelineCensus(@NotNull String pipelineId){

        PipelineOverview buildStatus = overviewService.pipelineOverview(pipelineId);

        return Result.ok(buildStatus);
    }



}
