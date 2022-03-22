package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.instance.model.Log;
import com.doublekit.pipeline.instance.service.LogService;
import com.doublekit.pipeline.instance.service.StructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/pipelineLog")
@Api(name = "LogController",desc = "流水线日志")
public class LogController {

    @Autowired
    LogService LogService;

    @Autowired
    StructureService structureService;


    //开始构建
    @RequestMapping(path="/pipelineStructure",method = RequestMethod.POST)
    @ApiMethod(name = "pipelineStructure",desc = "开始构建")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<String> pipelineStructure(@NotNull String pipelineId ) throws Exception {

        String logId = structureService.Structure(pipelineId);

        return Result.ok(logId);
    }

    //查询构建状态
    @RequestMapping(path="/selectStructureState",method = RequestMethod.POST)
    @ApiMethod(name = "selectStructureState",desc = "查询构建状态")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<Log> selectStructureState(String pipelineId) {

        Log log = structureService.findStructureState(pipelineId);

        return Result.ok(log);
    }

    //查询单个流水线
    @RequestMapping(path="/selectPipelineLog",method = RequestMethod.POST)
    @ApiMethod(name = "selectPipelineLog",desc = "查询日志")
    @ApiParam(name = "logId",desc = "日志id",required = true)
    public Result<Log> selectPipelineLog(@NotNull String logId ){

        Log log = LogService.findOneLog(logId);

        return Result.ok(log);
    }



}
