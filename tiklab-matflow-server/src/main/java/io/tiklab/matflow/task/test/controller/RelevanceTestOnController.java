package io.tiklab.matflow.task.test.controller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.matflow.task.test.model.RelevanceTestOn;
import io.tiklab.matflow.task.test.model.RelevanceTestOnQuery;
import io.tiklab.matflow.task.test.service.RelevanceTestOnService;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/testOnRelevance")
@Api(name = "RelevanceTestOnController",desc = "teston")
public class RelevanceTestOnController {

    @Autowired
    private RelevanceTestOnService relevanceTestOnService;

    @RequestMapping(path="/findAllRelevance",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRelevance",desc = "获取所有仓库")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result< List<RelevanceTestOn>> findAllRelevance(@NotNull String pipelineId){

        List<RelevanceTestOn> allRelevance = relevanceTestOnService.findAllRelevance(pipelineId);

        return Result.ok(allRelevance);
    }

    @RequestMapping(path="/findAllRelevancePage",method = RequestMethod.POST)
    @ApiMethod(name = "findAllRelevancePage",desc = "获取所有仓库")
    @ApiParam(name = "relevanceTestOnQuery",desc = "条件",required = true)
    public Result<Pagination<RelevanceTestOn>> findAllRelevance(@RequestBody @Valid @NotNull RelevanceTestOnQuery relevanceTestOnQuery){

        Pagination<RelevanceTestOn> allRelevance = relevanceTestOnService.findAllRelevancePage(relevanceTestOnQuery);

        return Result.ok(allRelevance);
    }


    @RequestMapping(path="/deleteRelevance",method = RequestMethod.POST)
    @ApiMethod(name = "deleteRelevance",desc = "获取所有仓库")
    @ApiParam(name = "relevanceId",desc = "流水线id",required = true)
    public Result<Void> deleteRelevance(@NotNull String relevanceId){

        relevanceTestOnService.deleteRelevance(relevanceId);

        return Result.ok();
    }





}
