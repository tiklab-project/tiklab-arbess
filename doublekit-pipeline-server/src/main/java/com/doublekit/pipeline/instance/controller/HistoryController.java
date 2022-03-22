package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.controller.PipelineController;
import com.doublekit.pipeline.instance.model.HistoryDetails;
import com.doublekit.pipeline.instance.model.Log;
import com.doublekit.pipeline.instance.service.HistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipelineHistory")
@Api(name = "HistoryController",desc = "流水线历史")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    //查询所有历史
    @RequestMapping(path="/selectHistoryDetails",method = RequestMethod.POST)
    @ApiMethod(name = "selectHistoryDetails",desc = "查看所有历史")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<HistoryDetails> selectPipelineHistoryDetails(@NotNull String pipelineId){

        List<HistoryDetails> historyDetailsList = historyService.findAll(pipelineId);

        return Result.ok(historyDetailsList);
    }

    //根据历史查询日志信息
    @RequestMapping(path="/selectHistoryLog",method = RequestMethod.POST)
    @ApiMethod(name = "selectHistoryLog",desc = "根据历史查询日志")
    @ApiParam(name = "historyId",desc = "历史id",required = true)
    public Result<Log> selectPipelineHistoryLog(@NotNull String historyId){

        Log log = historyService.findHistoryLog(historyId);

        return Result.ok(log);
    }

    //删除历史以及日志
    @RequestMapping(path="/deleteHistoryLog",method = RequestMethod.POST)
    @ApiMethod(name = "deleteHistoryLog",desc = "删除历史和日志")
    @ApiParam(name = "historyId",desc = "历史id",required = true)
    public Result<Void> deleteHistoryLog(@NotNull String historyId){

        historyService.deleteHistoryLog(historyId);

        return Result.ok();
    }
}
