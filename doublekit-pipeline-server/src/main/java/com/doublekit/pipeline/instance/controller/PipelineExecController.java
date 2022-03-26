package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;

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

    //查询所有历史
    @RequestMapping(path="/start",method = RequestMethod.POST)
    @ApiMethod(name = "start",desc = "执行")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Integer> start(@NotNull String pipelineId) throws Exception {
        int start = pipelineExecService.start(pipelineId);
        return Result.ok(start);
    }

}
