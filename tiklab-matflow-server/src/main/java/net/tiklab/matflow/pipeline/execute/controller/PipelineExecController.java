package net.tiklab.matflow.pipeline.execute.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.pipeline.execute.service.PipelineExecService;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstance;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/exec")
@Api(name = "PipelineExecController",desc = "流水线执行")
public class PipelineExecController {

    @Autowired
    PipelineExecService pipelineExecService;

    //开始构建
    @RequestMapping(path="/start",method = RequestMethod.POST)
    @ApiMethod(name = "start",desc = "执行")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineInstance> start(@NotNull String pipelineId ){
        PipelineInstance start = pipelineExecService.start(pipelineId,1);
        return Result.ok(start);
    }

    @RequestMapping(path="/stop",method = RequestMethod.POST)
    @ApiMethod(name = "killInstance",desc = "判断是否执行")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Void> killInstance(@NotNull String pipelineId) {
        pipelineExecService.stop(pipelineId);
        return Result.ok();
    }



}




























