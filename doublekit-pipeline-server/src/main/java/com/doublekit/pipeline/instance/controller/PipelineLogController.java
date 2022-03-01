package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.instance.model.PipelineLog;
import com.doublekit.pipeline.instance.service.PipelineLogService;
import com.doublekit.pipeline.instance.service.PipelineStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pipelineLog")
@Api(name = "PipelineLogController",desc = "流水线日志")
public class PipelineLogController {

    @Autowired
    PipelineLogService PipelineLogService;

    @Autowired
    PipelineStructureService pipelineStructureService;

    private static Logger logger = LoggerFactory.getLogger(PipelineLogController.class);

    //开始构建
    @RequestMapping(path="/pipelineStructure",method = RequestMethod.POST)
    @ApiMethod(name = "pipelineStructure",desc = "开始构建")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<String> pipelineStructure(@NotNull String pipelineId ) throws Exception {

        String logId = pipelineStructureService.pipelineStructure(pipelineId);

        return Result.ok(logId);
    }

    //查询构建状态
    @RequestMapping(path="/selectStructureState",method = RequestMethod.POST)
    @ApiMethod(name = "selectStructureState",desc = "查询构建状态")
    public Result<PipelineLog> selectStructureState(){

        PipelineLog pipelineLog = pipelineStructureService.selectStructureState();

        return Result.ok(pipelineLog);
    }

    //查询单个流水线
    @RequestMapping(path="/selectPipelineLog",method = RequestMethod.POST)
    @ApiMethod(name = "selectPipelineLog",desc = "查询日志")
    @ApiParam(name = "logId",desc = "日志id",required = true)
    public Result<PipelineLog> selectPipelineLog(@NotNull String logId ){

        PipelineLog pipelineLog = PipelineLogService.selectPipelineLog(logId);

        return Result.ok(pipelineLog);
    }
}
