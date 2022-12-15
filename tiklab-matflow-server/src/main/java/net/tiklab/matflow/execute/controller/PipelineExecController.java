package net.tiklab.matflow.execute.controller;


import net.tiklab.core.Result;
import net.tiklab.matflow.execute.model.PipelineRun;
import net.tiklab.matflow.execute.service.PipelineExecService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pipelineExec")
@Api(name = "PipelineExecController",desc = "流水线执行")
public class PipelineExecController {

    @Autowired
    PipelineExecService pipelineExecService;

    //开始构建
    @RequestMapping(path="/start",method = RequestMethod.POST)
    @ApiMethod(name = "start",desc = "执行")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Boolean> start(@NotNull String pipelineId ){
        boolean start = pipelineExecService.start(pipelineId,1);
        return Result.ok(start);
    }


    @RequestMapping(path="/pipelineRunStatus",method = RequestMethod.POST)
    @ApiMethod(name = "pipelineRunStatus",desc = "执行")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineRun> pipelineRunStatus(String pipelineId)  {
        PipelineRun instanceState = pipelineExecService.pipelineRunStatus(pipelineId);
        return Result.ok(instanceState);
    }

    @RequestMapping(path="/findPipelineState",method = RequestMethod.POST)
    @ApiMethod(name = "findPipelineState",desc = "判断是否执行")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Integer> findState(@NotNull String pipelineId) {
        int state = pipelineExecService.findPipelineState(pipelineId);
        return Result.ok(state);
    }

    @RequestMapping(path="/killInstance",method = RequestMethod.POST)
    @ApiMethod(name = "killInstance",desc = "判断是否执行")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Void> killInstance(@NotNull String pipelineId) {
        pipelineExecService.killInstance(pipelineId);
        return Result.ok();
    }



}
