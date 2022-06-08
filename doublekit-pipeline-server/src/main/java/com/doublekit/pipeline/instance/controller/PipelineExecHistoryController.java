package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.core.Result;
import com.doublekit.pipeline.instance.model.PipelineExecHistory;
import com.doublekit.pipeline.instance.model.PipelineHistoryQuery;
import com.doublekit.pipeline.instance.service.PipelineExecHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/pipelineHistory")
@Api(name = "PipelineHistoryController",desc = "流水线历史")
public class PipelineExecHistoryController {

    @Autowired
    PipelineExecHistoryService pipelineExecHistoryService;

    //查询所有历史
    @RequestMapping(path="/findAllHistory",method = RequestMethod.POST)
    @ApiMethod(name = "selectHistoryDetails",desc = "查看所有历史")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineExecHistory> selectPipelineHistoryDetails(@NotNull String pipelineId){

        List<PipelineExecHistory> allHistory = pipelineExecHistoryService.findAllHistory(pipelineId);
        return Result.ok(allHistory);
    }


    //删除历史
    @RequestMapping(path="/deleteHistory",method = RequestMethod.POST)
    @ApiMethod(name = "deleteHistory",desc = "删除历史")
    @ApiParam(name = "historyId",desc = "流水线id",required = true)
    public Result<Void> deleteHistory(@NotNull String historyId){
       pipelineExecHistoryService.deleteHistory(historyId);
        return Result.ok();
    }

    //查询历史信息
    @RequestMapping(path="/findLikeHistory",method = RequestMethod.POST)
    @ApiMethod(name = "findLikeHistory",desc = "查询历史信息")
    @ApiParam(name = "pipelineHistoryQuery",desc = "条件",required = true)
    public Result< List<PipelineExecHistory>> findLikeHistory(@NotNull @RequestBody PipelineHistoryQuery pipelineHistoryQuery){
        List<PipelineExecHistory> list = pipelineExecHistoryService.findLikeHistory(pipelineHistoryQuery);
        return Result.ok(list);
    }

}
