package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pipelineHistory")
@Api(name = "PipelineHistoryController",desc = "流水线历史")
public class PipelineExecHistoryController {

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    // //查询所有历史
    // @RequestMapping(path="/selectHistoryDetails",method = RequestMethod.POST)
    // @ApiMethod(name = "selectHistoryDetails",desc = "查看所有历史")
    // @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    // public Result<PipelineExecHistoryDetails> selectPipelineHistoryDetails(@NotNull String pipelineId){
    //
    //     return Result.ok();
    // }


}
