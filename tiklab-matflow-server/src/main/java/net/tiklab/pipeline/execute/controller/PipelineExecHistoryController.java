package net.tiklab.pipeline.execute.controller;


import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.pipeline.execute.model.PipelineExecHistory;
import net.tiklab.pipeline.execute.model.PipelineHistoryQuery;
import net.tiklab.pipeline.execute.service.PipelineExecHistoryService;
import net.tiklab.postin.annotation.Api;
import net.tiklab.postin.annotation.ApiMethod;
import net.tiklab.postin.annotation.ApiParam;
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
    @ApiMethod(name = "findAllHistory",desc = "查看所有历史")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineExecHistory> findAllHistory(@NotNull String pipelineId){

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


    @RequestMapping(path="/findPageHistory",method = RequestMethod.POST)
    @ApiMethod(name = "findPageHistory",desc = "查询历史信息")
    @ApiParam(name = "pipelineHistoryQueryPage",desc = "条件",required = true)
    public Result<Pagination<PipelineExecHistory>> findPageHistory(@RequestBody @NotNull PipelineHistoryQuery pipelineHistoryQuery){
        Pagination<PipelineExecHistory> list = pipelineExecHistoryService.findPageHistory(pipelineHistoryQuery);
        return Result.ok(list);
    }

}
