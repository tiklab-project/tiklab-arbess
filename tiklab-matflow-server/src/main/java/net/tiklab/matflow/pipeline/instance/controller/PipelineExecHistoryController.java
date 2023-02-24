package net.tiklab.matflow.pipeline.instance.controller;


import net.tiklab.core.Result;
import net.tiklab.core.page.Pagination;
import net.tiklab.matflow.pipeline.instance.model.PipelineExecInstance;
import net.tiklab.matflow.pipeline.instance.model.PipelineInstanceQuery;
import net.tiklab.matflow.pipeline.instance.service.PipelineExecInstanceService;
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
    PipelineExecInstanceService pipelineExecInstanceService;

    //查询所有历史
    @RequestMapping(path="/findAllHistory",method = RequestMethod.POST)
    @ApiMethod(name = "findAllHistory",desc = "查看所有历史")
    @ApiParam(name = "pipelineId",desc = "流水线id",required = true)
    public Result<PipelineExecInstance> findAllHistory(@NotNull String pipelineId){

        List<PipelineExecInstance> allHistory = pipelineExecInstanceService.findAllHistory(pipelineId);
        return Result.ok(allHistory);
    }

    //删除历史
    @RequestMapping(path="/deleteHistory",method = RequestMethod.POST)
    @ApiMethod(name = "deleteHistory",desc = "删除历史")
    @ApiParam(name = "historyId",desc = "流水线id",required = true)
    public Result<Void> deleteHistory(@NotNull String historyId){
       pipelineExecInstanceService.deleteHistory(historyId);
        return Result.ok();
    }


    @RequestMapping(path="/findPageHistory",method = RequestMethod.POST)
    @ApiMethod(name = "findPageHistory",desc = "查询历史信息")
    @ApiParam(name = "pipelineHistoryQueryPage",desc = "条件",required = true)
    public Result<Pagination<PipelineExecInstance>> findPageHistory(@RequestBody @NotNull PipelineInstanceQuery pipelineInstanceQuery){
        Pagination<PipelineExecInstance> list = pipelineExecInstanceService.findPageHistory(pipelineInstanceQuery);
        return Result.ok(list);
    }

}
