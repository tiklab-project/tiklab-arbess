package com.doublekit.pipeline.implement.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.controller.PipelineController;
import com.doublekit.pipeline.implement.model.PipelineHistory;
import com.doublekit.pipeline.implement.service.PipelineHistoryService;
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
@RequestMapping("/PipelineHistory")
@Api(name = "PipelineHistoryController",desc = "流水线历史")
public class PipelineHistoryController {

    private static Logger logger = LoggerFactory.getLogger(PipelineController.class);

    @Autowired
    PipelineHistoryService pipelineHistoryService;

    //添加
    @RequestMapping(path="/createPipelineHistory",method = RequestMethod.POST)
    @ApiMethod(name = "createPipelineHistory",desc = "创建流水线历史")
    @ApiParam(name = "PipelineHistory",desc = "PipelineHistory",required = true)
    public Result<String> createPipelineHistory(@RequestBody @NotNull @Valid PipelineHistory pipelineHistory){

        String pipelineHistoryId = pipelineHistoryService.createPipelineHistory(pipelineHistory);

        return Result.ok(pipelineHistoryId);
    }

    //删除
    @RequestMapping(path="/deletePipelineHistory",method = RequestMethod.POST)
    @ApiMethod(name = "deletePipelineHistory",desc = "删除流水线历史")
    @ApiParam(name = "PipelineHistory",desc = "PipelineHistory",required = true)
    public Result<Void> deletePipelineHistory(@NotNull String  id){

         pipelineHistoryService.deletePipelineHistory(id);

        return Result.ok();
    }

    //修改
    @RequestMapping(path="/updatePipelineHistory",method = RequestMethod.POST)
    @ApiMethod(name = "updatePipelineHistory",desc = "修改流水线历史")
    @ApiParam(name = "PipelineHistory",desc = "PipelineHistory",required = true)
    public Result<Void> updatePipelineHistory(@RequestBody @NotNull @Valid PipelineHistory pipelineHistory){

        pipelineHistoryService.updatePipelineHistory(pipelineHistory);

        return Result.ok();
    }

    //查询
    @RequestMapping(path="/selectPipelineHistory",method = RequestMethod.POST)
    @ApiMethod(name = "selectPipelineHistory",desc = "查询流水线历史")
    @ApiParam(name = "PipelineHistory",desc = "PipelineHistory",required = true)
    public Result<PipelineHistory> selectPipelineHistory(@NotNull String  id){

        PipelineHistory pipelineHistory = pipelineHistoryService.selectPipelineHistory(id);

        return Result.ok(pipelineHistory);
    }

    //查询所有
    @RequestMapping(path="/selectAllPipelineHistory",method = RequestMethod.POST)
    @ApiMethod(name = "selectAllPipelineHistory",desc = "查询所有流水线历史")
    @ApiParam(name = "PipelineHistory",desc = "PipelineHistory",required = true)
    public Result<List<PipelineHistory>> selectAllPipelineHistory(){

        List<PipelineHistory> pipelineHistoryList = pipelineHistoryService.selectAllPipelineHistory();

        return Result.ok(pipelineHistoryList);
    }








}
