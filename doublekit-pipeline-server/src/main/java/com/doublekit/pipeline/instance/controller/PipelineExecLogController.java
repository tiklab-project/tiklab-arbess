package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.instance.model.PipelineExecLog;
import com.doublekit.pipeline.instance.service.PipelineExecLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipelineLog")
@Api(name = "PipelineLogController",desc = "流水线日志")
public class PipelineExecLogController {

    @Autowired
    PipelineExecLogService PipelineExecLogService;

    //查询日志
    @RequestMapping(path="/findAllLog",method = RequestMethod.POST)
    @ApiMethod(name = "findAllLog",desc = "查询日志")
    @ApiParam(name = "historyId",desc = "历史id",required = true)
    public Result<List<PipelineExecLog>> findAllLog(@NotNull String historyId){
        List<PipelineExecLog> allLog = PipelineExecLogService.findAllLog(historyId);
        return Result.ok(allLog);
    }
}
