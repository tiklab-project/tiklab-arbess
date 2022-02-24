package com.doublekit.pipeline.instance.controller;

import com.doublekit.apibox.annotation.Api;
import com.doublekit.apibox.annotation.ApiMethod;
import com.doublekit.apibox.annotation.ApiParam;
import com.doublekit.common.Result;
import com.doublekit.pipeline.definition.controller.PipelineController;
import com.doublekit.pipeline.instance.model.PipelineHistory;
import com.doublekit.pipeline.instance.model.PipelineHistoryDetails;
import com.doublekit.pipeline.instance.service.PipelineHistoryService;
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
@Api(name = "PipelineHistoryController",desc = "流水线历史")
public class PipelineHistoryController {

    private static Logger logger = LoggerFactory.getLogger(PipelineController.class);

    @Autowired
    PipelineHistoryService pipelineHistoryService;

    //查询所有
    @RequestMapping(path="/selectAllPipelineHistory",method = RequestMethod.POST)
    @ApiMethod(name = "selectAllPipelineHistory",desc = "流水线历史")
    public Result<List<PipelineHistory>> selectAllPipelineHistory(){

        List<PipelineHistory> pipelineHistories = pipelineHistoryService.selectAllPipelineHistory();

        return Result.ok(pipelineHistories);
    }

    //查询
    @RequestMapping(path="/selectPipelineHistoryDetails",method = RequestMethod.POST)
    @ApiMethod(name = "selectPipelineHistoryDetails",desc = "流水线历史详情")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineHistoryDetails> selectPipelineHistoryDetails(@NotNull String pipelineId){

        List<PipelineHistoryDetails> pipelineHistoryDetailsList = pipelineHistoryService.selectAll(pipelineId);

        return Result.ok(pipelineHistoryDetailsList);
    }
}
