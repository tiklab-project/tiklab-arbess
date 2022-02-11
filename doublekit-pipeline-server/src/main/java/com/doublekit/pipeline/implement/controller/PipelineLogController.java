package com.doublekit.pipeline.implement.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.controller.PipelineController;
import com.doublekit.pipeline.implement.model.PipelineLog;
import com.doublekit.pipeline.implement.service.PipelineLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/PipelineLog")
@Api(name = "PipelineLogController",desc = "流水线日志")
public class PipelineLogController {

    private static Logger logger = LoggerFactory.getLogger(PipelineController.class);

    @Autowired
    PipelineLogService pipelineLogService;

    //添加
    @RequestMapping(path="/createPipelineLog",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineHistory",desc = "创建流水线日志")
    @ApiParam(name = "PipelineLog",desc = "PipelineLog",required = true)
    public Result<String> createPipelineLog(@RequestBody @NotNull @Valid PipelineLog pipelineLog){

        String pipelineLogId = pipelineLogService.createPipelineLog(pipelineLog);

        return Result.ok(pipelineLogId);
    }

    //删除
    @RequestMapping(path="/deletePipelineLog",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineLog",desc = "删除流水线日志")
    @ApiParam(name = "PipelineLog",desc = "PipelineLog",required = true)
    public Result<Void> deletePipelineLog(@NotNull String id){

        pipelineLogService.deletePipelineLog(id);

        return Result.ok();
    }

    //修改
    @RequestMapping(path="/updatePipelineLog",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineLog",desc = "修改流水线日志")
    @ApiParam(name = "PipelineLog",desc = "PipelineLog",required = true)
    public Result<Void> updatePipelineLog(@RequestBody @NotNull @Valid PipelineLog pipelineLog){

        pipelineLogService.updatePipelineLog(pipelineLog);

        return Result.ok();
    }

    //查询
    @RequestMapping(path="/selectPipelineLog",method = RequestMethod.POST)
    @ApiMethod(name = "selectPipelineLog",desc = "查询流水线日志")
    @ApiParam(name = "PipelineLog",desc = "PipelineLog",required = true)
    public Result<PipelineLog> selectPipelineLog(@NotNull String  id){

        PipelineLog pipelineLog = pipelineLogService.selectPipelineLog(id);

        return Result.ok(pipelineLog);
    }

    //查询所有
    @RequestMapping(path="/selectAllPipelineLog",method = RequestMethod.POST)
    @ApiMethod(name = "selectAllPipelineLog",desc = "查询所有流水线日志")
    @ApiParam(name = "PipelineLog",desc = "PipelineLog",required = true)
    public Result<List<PipelineLog>> selectAllPipelineLog(){

        List<PipelineLog> pipelineLogList = pipelineLogService.selectAllPipelineLog();

        return Result.ok(pipelineLogList);
    }

}
