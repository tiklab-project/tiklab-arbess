package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;

import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.service.PipelineExecService;
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
    public Result<Integer> start(@NotNull String pipelineId) throws Exception {
        int start = pipelineExecService.start(pipelineId);
        return Result.ok(start);
    }


    @RequestMapping(path="/findState",method = RequestMethod.POST)
    @ApiMethod(name = "findState",desc = "执行")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineExecHistory> findState(@NotNull String pipelineId)  {
        PipelineExecHistory instanceState = pipelineExecService.findInstanceState(pipelineId);
        return Result.ok(instanceState);
    }

    @RequestMapping(path="/findExecState",method = RequestMethod.POST)
    @ApiMethod(name = "findExecState",desc = "判断是否执行")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Integer> findExecState(@NotNull String pipelineId) {
        int state = pipelineExecService.findState(pipelineId);
        return Result.ok(state);
    }

}
