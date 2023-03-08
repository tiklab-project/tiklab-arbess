package io.tiklab.matflow.pipeline.definition.controller;

import io.tiklab.matflow.pipeline.definition.model.PipelineOpen;
import io.tiklab.matflow.pipeline.definition.service.PipelineOpenService;
import io.tiklab.core.Result;
import io.tiklab.postin.annotation.Api;
import io.tiklab.postin.annotation.ApiMethod;
import io.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/open")
@Api(name = "PipelineController",desc = "流水线")
public class PipelineOpenController {

    @Autowired
    PipelineOpenService openService;

    @RequestMapping(path="/findAllOpen",method = RequestMethod.POST)
    @ApiMethod(name = "findAllOpen",desc = "查询流水线最近状态")
    @ApiParam(name = "number",desc = "数量",required = true)
    public Result<List<PipelineOpen>> findAllOpen(@NotNull int number){

        List<PipelineOpen> openList = openService.findUserAllOpen(number);

        return Result.ok(openList);
    }

    @RequestMapping(path="/updateOpen",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineOpen",desc = "查询流水线最近状态")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Void> updatePipelineOpen(@NotNull String pipelineId){

       openService.updatePipelineOpen(pipelineId);

        return Result.ok();
    }

}




























